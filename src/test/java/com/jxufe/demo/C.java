package com.jxufe.demo;

import java.io.Serializable;

/**
 * @Author wlb
 * @Date 2023/9/11 23:34
 **/
public class C implements Serializable {
    private Integer id;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public C() {
    }

    public C(Integer id, String address) {
        this.id = id;
        this.address = address;
    }
}
