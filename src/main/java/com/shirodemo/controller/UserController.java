package com.shirodemo.controller;

import com.shirodemo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shiro/")
public class UserController {
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("msg", "shiro测试主页");
        return "index";
    }

    @RequestMapping("403")
    public String noAuth(){
        return "403";
    }

    @RequestMapping("tologin")
    public String toLogin(Model model) {
        model.addAttribute("msg", "需要先登录");
        return "/user/login";
    }

    @RequestMapping("login")
    public String login(@RequestParam("username") String name, @RequestParam("password") String password, Model model) {
        System.out.println("username:" + name);
        System.out.println("password:" + password);
        /**
         * 使用Shiro编写认证操作
         */
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        System.out.println(token.toString());
        //3.执行登录方法
        try {
            subject.login(token);
            //登录成功
            model.addAttribute("msg", "登录成功,欢迎您: "+((User)subject.getPrincipal()).getName());
            return "index";
        } catch (UnknownAccountException e) {
//            e.printStackTrace();
            //登录失败  用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "/user/login";
        } catch (IncorrectCredentialsException e) {
//            e.printStackTrace();
            //登录失败  密码错误
            model.addAttribute("msg", "密码错误");
            return "/user/login";
        }
    }

    @RequestMapping("logout")
    public String logout(Model model){
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg", "注销登录成功");
        return "index";
    }
    @RequestMapping("add")
    public String add() {
        return "/user/add";

    }

    @RequestMapping("update")
    public String update() {
        return "/user/update";
    }
}
