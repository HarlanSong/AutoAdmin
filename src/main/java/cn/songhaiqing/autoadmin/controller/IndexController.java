package cn.songhaiqing.autoadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {

    @RequestMapping(value = "/admin/")
    public ModelAndView adminIndexView() {
        return new ModelAndView("redirect:/admin/sysUser/loginView");
    }

    @RequestMapping(value = "/")
    public ModelAndView indexView() {
        ModelAndView modelAndView = new ModelAndView("/index");
        return modelAndView;
    }

}
