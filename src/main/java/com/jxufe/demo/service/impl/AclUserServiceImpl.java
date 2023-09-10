package com.jxufe.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxufe.demo.entity.AclUser;
import com.jxufe.demo.service.AclUserService;
import com.jxufe.demo.mapper.AclUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86173
* @description 针对表【acl_user(用户表)】的数据库操作Service实现
* @createDate 2023-05-08 19:36:30
*/
@Service
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser>
    implements AclUserService{

}




