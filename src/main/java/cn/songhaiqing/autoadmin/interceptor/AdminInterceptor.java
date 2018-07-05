package cn.songhaiqing.autoadmin.interceptor;

import cn.songhaiqing.autoadmin.base.BaseController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String path = httpServletRequest.getServletPath();
        List<String> filter = Arrays.asList("/admin/","/admin/sysUser/loginView","/admin/sysUser/login");
        if(filter.contains(path)){
            return true;
        }
        Object user = httpServletRequest.getSession().getAttribute(BaseController.KEY_ADMIN_USER);
        if(user == null){
            httpServletResponse.sendRedirect("/admin/sysUser/loginView");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
       // System.out.println("afterCompletion");
    }
}
