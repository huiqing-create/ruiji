package com.huiqing.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huiqing.ruiji.dto.DishDto;
import com.huiqing.ruiji.entry.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    /**
     * 查询菜品信息附带口味信息 ， 用来回显数据
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id);

    /**
     * 用来更信息菜品，以及菜品口味信息，设计两张表 dish 和 dish_flavor
     * @param dishDto
     */
    public void updateWithFlavor(DishDto dishDto);



}
