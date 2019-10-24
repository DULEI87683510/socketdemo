package com.dl.socketdemo.user.service.impl;/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author DL
 * @Date 2019/10/18 17:13
 * @Version 1.0
 */

import com.dl.socketdemo.user.entity.User;
import com.dl.socketdemo.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *@ClassName UserServiceImpl
 *@Description TODO
 *@Author DL
 *@Date 2019/10/18 17:13    
 *@Version 1.0
 */
@Service
public class UserServiceImpl  implements UserService{

    private static final Logger log = LoggerFactory
            .getLogger(UserServiceImpl.class);
    @Override
    public void saveUser(User user) {

        log.info("保存user：{}成功",user);
    }
}
