package com.huiqing.ruiji.filter;

import com.alibaba.fastjson.JSON;
import com.huiqing.ruiji.common.BaseContext;
import com.huiqing.ruiji.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取本地的请求URI
        String requestURI = request.getRequestURI(); // /backend/index.html
        log.info("拦截到请求：{}", requestURI);

        //定义不需要处理的请求
        /**
         * 拦截仅仅是拦截Conteroller中的请求路径，对于前端的页面可以不做拦截，访问到也没有数据
         *
         * 当你访问index页面，过滤器会放行，但前端拦截器判断登录状态，没登录则跳转login页面，这就会导致页面闪频
         */
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",//移动端发送短信
                "/user/login"//移动端登录
        };

        //2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3. 如果不需要处理，则直接放行
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4. 判断登陆状态，如果已登录，则直接放行,这个是检查后台登录的
        if (request.getSession().getAttribute("employee") != null){
            Long empId = (Long) request.getSession().getAttribute("employee");
            log.info("用户已登录，用户id为：{}",empId);
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
        //4-2、判断登录状态，如果已登录，则直接放行，这个是检查前台管理的
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        // 5. 如果未登录则返回未登录状态，通过输出流的方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }




    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            //和自己定义的需要放行的请求做对比，一样的放行，return true
            if (match) {
                return true;
            }
        }
        //都不一样返回false
        return false;

    }
}
