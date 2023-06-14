package com.thuan.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("courses")
public class CoursesController {
    @GetMapping
    public String getCourses() {
        return "courses";
    }
}
