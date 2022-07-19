package com.huiqing.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huiqing.ruiji.common.R;
import com.huiqing.ruiji.entry.User;
import com.huiqing.ruiji.service.UserService;
import com.huiqing.ruiji.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);

            /**
             * 这一句是使用阿里云的时候才会用到，我这里暂时没有导入，现在只是用ValidateCodeUtils这个工具类生成验证码代替阿里云的验证码
             */
            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("瑞吉外卖","",phone,code);

            //需要将生成的验证码保存到Session
//            session.setAttribute(phone,code);

            //从session中优化到redis中
            //将生成的验证码缓存到redis中，并且设置有效期为五分钟
            redisTemplate.opsForValue().set(phone,code,5,TimeUnit.MINUTES);


            return R.success("手机验证码短信发送成功");
        }

        return R.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

//        //从Session中获取保存的验证码
//        Object codeInSession = session.getAttribute(phone);

        //优化从session中获取验证码  -> 从redis中获取缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);


        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明登录成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            //如果用户登陆成功，删除redis中缓存的验证码,这里phone就是这个key
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("登录失败");
    }


}
