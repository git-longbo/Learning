package com.jxufe.demo.mq;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author wlb
 * @Date 2023/9/9 14:17
 **/
@Component
@Slf4j
public class CanalClient implements ApplicationRunner {

    @Value("${canal.server}")
    private String server;
    @Value("${canal.port}")
    private Integer port;
    public static List<CanalConnector> examples = Lists.newArrayList();
    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    private final Integer BATCH_SIZE = 1000;

    @Override
    @Async
    public void run(ApplicationArguments args) {
        // 创建链接
        CanalConnector connector1 = CanalConnectors.newSingleConnector(new InetSocketAddress(server,
                port), "example", "", "");
        CanalConnector connector2 = CanalConnectors.newSingleConnector(new InetSocketAddress(server,
                port), "example2", "", "");
        examples.add(connector1);
        examples.add(connector2);
        try {
            connector1.connect();
            connector2.connect();
            connector1.subscribe("education_0\\..*");
            connector2.subscribe("education_1\\..*");
            while (true) {
                for (CanalConnector connector : examples) {
                    //回滚到未进行ack的地方，下次fetch的时候，可以从最后一个没有ack的地方开始
                    connector.rollback();
                    Message message = connector.getWithoutAck(BATCH_SIZE);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        sendMessageToMQ(message.getEntries());
                    }
                    // 提交确认
                    connector.ack(batchId);
                }
            }
        } finally {
            for (CanalConnector connector : examples) {
                connector.disconnect();
            }
        }
    }

    private void sendMessageToMQ(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                // 变更的数据信息
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
            }
            EventType eventType = rowChage.getEventType();

            BinlogMessageBO messageBO = new BinlogMessageBO();
            messageBO.setDatabase(entry.getHeader().getSchemaName());
            messageBO.setTable(entry.getHeader().getTableName());
            messageBO.setType(eventType.name());
            messageBO.setSql(rowChage.getSql());
            messageBO.setIsDdl(rowChage.getIsDdl());
            for (RowData rowData : rowChage.getRowDatasList()) {
                JSONArray oldData = parseBinlogData(rowData.getBeforeColumnsList());
                JSONArray newData = parseBinlogData(rowData.getAfterColumnsList());
                messageBO.setOld(oldData);
                messageBO.setData(newData);

                String msgBody = JSON.toJSONString(messageBO);
                log.info("canal监听数据发送至mq {} ",msgBody);
                Map<String, String> map = new HashMap<>();
                for (Column column : rowData.getAfterColumnsList()) {
                    map.put(column.getName(), column.getValue());
                }
                String hashKey = messageBO.getDatabase() + messageBO.getTable() + rowData.getAfterColumnsList() + map.get("id");
                rocketMqTemplate.sendOneWayOrderly("binlog_topic:binlog_tag", MessageBuilder.withPayload(msgBody).build(), hashKey);
            }

        }
    }

    private JSONArray parseBinlogData(List<Column> columns) {
        if (CollectionUtils.isEmpty(columns)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject data = new JSONObject();
        for (Column column : columns) {
            data.put(column.getName(), column.getValue());
        }
        jsonArray.add(data);
        return jsonArray;
    }
}
