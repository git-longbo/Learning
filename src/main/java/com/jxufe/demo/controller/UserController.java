package com.jxufe.demo.controller;

import com.jxufe.demo.entity.AclUser;
import com.jxufe.demo.service.AclUserService;
import com.jxufe.demo.util.Result;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wlb
 * @Date 2023/6/5 23:13
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AclUserService aclUserService;

    @PostMapping("add")
    public Result add(@RequestBody AclUser aclUser) {
        aclUserService.save(aclUser);
        return new Result();
    }
}
