package com.test.student.service;


import com.test.student.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();

    Optional<Student> findStudentBy(long id);

    void deleteStudentBy(long id);

    Student saveOrUpdateStudent(Student student);
}
