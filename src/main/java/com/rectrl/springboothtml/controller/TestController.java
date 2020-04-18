package com.rectrl.springboothtml.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping("/test")
    public String test() {
        System.out.println("test.html");
        return "test";
    }

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hello.html");
        return "hello";
    }
}