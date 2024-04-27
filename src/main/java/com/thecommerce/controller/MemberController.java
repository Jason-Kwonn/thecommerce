package com.thecommerce.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MemberController {

    @GetMapping("/home")
    public String home() {
        log.info("Home page 라우트");
        return "forward:/home.html";
    }

    @GetMapping("/auth/login")
    public String login() {
        log.info("Login page 라우트");
        return "forward:/login.html";
    }

    @GetMapping("/auth/register")
    public String register() {
        log.info("Register page 라우트");
        return "forward:/register.html";
    }

    @GetMapping("/auth/update")
    public String update() {
        log.info("Update page 라우트");
        return "forward:/update.html";
    }

}
