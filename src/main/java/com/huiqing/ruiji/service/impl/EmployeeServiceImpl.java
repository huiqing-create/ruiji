package com.huiqing.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huiqing.ruiji.entry.Employee;
import com.huiqing.ruiji.mapper.EmployeeMapper;
import com.huiqing.ruiji.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
