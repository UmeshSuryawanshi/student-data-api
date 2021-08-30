package com.test.student.controller;

import com.test.student.exception.StudentNotFoundException;
import com.test.student.model.Student;
import com.test.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/data")
public class StudentController {

    @Autowired
    private StudentService service;

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> retrieveAllStudents() {
        List<Student> allStudents = service.getAllStudents();
        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> retrieveStudent(@PathVariable long id) {
        Optional<Student> student = service.findStudentBy(id);
        if (!student.isPresent())
            throw new StudentNotFoundException("No Student found of id : " + id);

        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable long id) {
        service.deleteStudentBy(id);
    }

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        Student savedStudent = service.saveOrUpdateStudent(student);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStudent.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable long id) {
        Optional<Student> studentOptional = service.findStudentBy(id);
        if (!studentOptional.isPresent())
            throw new StudentNotFoundException("No Student found of id : " + id);

        student.setId(id);
        service.saveOrUpdateStudent(student);

        return ResponseEntity.noContent().build();
    }
}
