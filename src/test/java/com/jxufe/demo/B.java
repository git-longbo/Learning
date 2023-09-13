package com.jxufe.demo;

import java.io.Serializable;

/**
 * @Author wlb
 * @Date 2023/9/11 23:33
 **/
public class B implements Serializable {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public B(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public B() {
    }
}
