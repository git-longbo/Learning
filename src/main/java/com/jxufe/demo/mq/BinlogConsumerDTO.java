package com.jxufe.demo.mq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author WLB
 * @Date 2023/9/9 11:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BinlogConsumerDTO {
    private Integer id;
    private JSONArray data;
    private JSONArray pkNames;
    private String database;
    private String sql;
    private Long es;
    private JSONArray old;
    private String table;
    private Long ts;
    private String type;
    private JSONObject sqlType;
    private JSONObject mysqlType;
    private String gtid;
    private Boolean isDdl;

}
