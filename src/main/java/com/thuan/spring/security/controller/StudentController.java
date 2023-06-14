package com.thuan.spring.security.controller;

import com.thuan.spring.security.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private static List<Student> studentList = List.of(
            new Student(1, "Thuan"),
            new Student(2, "Lien"),
            new Student(3, "Hieu"));

    @GetMapping
    public List<Student> getAll() {
        return studentList;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable(name = "id") long id) {
        return studentList.stream().filter(s -> id == s.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student " + id + " Not exist"));
    }

}
