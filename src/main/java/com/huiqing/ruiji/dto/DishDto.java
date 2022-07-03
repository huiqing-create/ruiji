package com.huiqing.ruiji.dto;

import com.huiqing.ruiji.entry.Dish;
import com.huiqing.ruiji.entry.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 说说DishDto这个类，这个类的作用应该就是为了方便给前端响应过去完成的对象，整合了几个实体类的字段，
 * 让多个表查询到的结果能够都整合到这个类的字段里面，之后向封装好的数据相应给前端。
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
