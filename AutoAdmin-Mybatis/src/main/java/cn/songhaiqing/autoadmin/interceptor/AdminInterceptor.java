package cn.songhaiqing.autoadmin.interceptor;

import cn.songhaiqing.autoadmin.annotation.AdminPermission;
import cn.songhaiqing.autoadmin.base.BaseController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
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
        HandlerMethod handlerMethod = (HandlerMethod) o;
        Method method = handlerMethod.getMethod();
        AdminPermission permission = method.getAnnotation(AdminPermission.class);
        if(permission != null){
            String permissionValue = permission.menu();
            if(permissionValue != null && permissionValue.length() > 0){
                List<String> menuPermission = (List<String>) httpServletRequest.getSession().getAttribute("menuPermission");
                if(!menuPermission.contains(permissionValue)){
                    httpServletResponse.sendRedirect("/admin/sysUser/loginView");
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        //System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)  {
       // System.out.println("afterCompletion");
    }
}
