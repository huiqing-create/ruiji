package com.huiqing.ruiji.dto;

import com.huiqing.ruiji.entry.Setmeal;
import com.huiqing.ruiji.entry.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
