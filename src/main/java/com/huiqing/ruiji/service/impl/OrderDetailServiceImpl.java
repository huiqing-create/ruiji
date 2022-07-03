package com.huiqing.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huiqing.ruiji.entry.OrderDetail;
import com.huiqing.ruiji.mapper.OrderDetailMapper;
import com.huiqing.ruiji.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}