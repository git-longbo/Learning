package com.jxufe.demo;

import com.alibaba.fastjson.JSON;
import com.jxufe.demo.entity.AclUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 * @Author wlb
 * @Date 2023/9/11 22:35
 **/
@SpringBootTest
@Slf4j
public class TestA {
    @Test
    public void test1() {
        B b = new B(1, "wlb");
        C c = new C(2, "上海");
        System.out.println(b.toString());

        String strB = "{\"id\":1, \"name\":\"wlb\"}";
        String strC = "{\"id\":2, \"address\":\"上海\"}";
        Object b1 = JSON.parseObject(strB);
        Object c1 = JSON.parseObject(strC);
        B b2 = JSON.parseObject(JSON.toJSONString(b1), B.class);
        C b3 = JSON.parseObject(JSON.toJSONString(b1), C.class);
        if (B.class.isInstance(b1)) {
            System.out.println("b1是B的实例");
        }
        if (c1 instanceof C) {
            System.out.println("c1是C的实例");
        }
    }

}
