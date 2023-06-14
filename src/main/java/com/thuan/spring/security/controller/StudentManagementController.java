package com.thuan.spring.security.controller;

import com.thuan.spring.security.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static List<Student> studentList = List.of(new Student(1, "Thuan"), new Student(2, "Lien"), new Student(3, "Hieu"));

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_TRAINEE')")
    public List<Student> getAll() {
        return studentList;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void addNewStudent(@RequestBody Student student) {
        System.out.println("add new student");
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void delete(@PathVariable(name = "studentId") Integer studentId) {
        System.out.println("delete student " + studentId);
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void update(@PathVariable(name = "studentId") Integer studentId, @RequestBody Student student) {
        System.out.println("update student " + studentId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_TRAINEE')")
    public Student getStudent(@PathVariable(name = "id") long id) {
        return studentList.stream().filter(s -> id == s.getId()).findFirst().orElseThrow(() -> new IllegalArgumentException("Student " + id + " Not exist"));
    }

}
