package com.huiqing.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huiqing.ruiji.entry.ShoppingCart;
import com.huiqing.ruiji.mapper.ShoppingCartMapper;
import com.huiqing.ruiji.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
