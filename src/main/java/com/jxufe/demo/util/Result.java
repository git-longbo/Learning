package com.jxufe.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author wlb
 * @Date 2023/6/5 23:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result implements Serializable {
    private Integer code;
    private String message;
}
