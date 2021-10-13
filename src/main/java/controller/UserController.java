package controller;

import annotation.Controller;
import annotation.GetMapping;
import annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/signIn.html")
    public String signInPage() {
        System.out.println("회원가입 페이지 요청");
        return "/signIn.html";
    }

    @GetMapping("/login.html")
    public String loginPage() {
        System.out.println("로그인 페이지 요청");
        return "/login.html";
    }

    @PostMapping("/signIn")
    public String signIn() {
        System.out.println("회원가입 요청");
        return "/login.html";
    }

    @PostMapping("/login")
    public String login() {
        System.out.println("로그인 요청");
        return "/index.html";
    }
}
