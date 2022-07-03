package com.huiqing.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huiqing.ruiji.entry.User;
import com.huiqing.ruiji.mapper.UserMapper;
import com.huiqing.ruiji.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
