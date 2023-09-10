package com.jxufe.demo.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jxufe.demo.entity.AclUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author wlb
 * @Date 2023/9/9 11:40
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "binlog_topic", consumerGroup = "binlog_consumer_group", selectorExpression = "binlog_tag")
public class Consumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        Object parse = JSONObject.parse(message.getBody());
        BinlogMessageBO binlog = JSONObject.parseObject(parse.toString(), BinlogMessageBO.class);
        log.info("==============consumer消费binlog==============");
        log.info("messageId：{}", message.getMsgId());
        log.info("修改库表：{}.{}", binlog.getDatabase(), binlog.getTable());
        log.info("操作类型：{}", binlog.getType());
        log.info("新数据：{}", binlog.getData());
        log.info("修改数据：{}", binlog.getOld());
        List<String> splitNameList = new ArrayList<>();
        if (Objects.nonNull(binlog.getData())) {
            for (Object datum : binlog.getData()) {
                AclUser user = JSON.parseObject(JSON.toJSONString(datum), AclUser.class);
                try {
                    StringReader reader = new StringReader(user.getUsername());
                    IKSegmentation segmenter = new IKSegmentation(reader, Boolean.FALSE);
                    Lexeme lexeme;
                    while ((lexeme = segmenter.next()) != null) {
                        splitNameList.add(lexeme.getLexemeText());
                    }
                    log.info("分词前name：{}", user.getUsername());
                    log.info("分词后name：{}", StringUtils.join(splitNameList, ", "));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
