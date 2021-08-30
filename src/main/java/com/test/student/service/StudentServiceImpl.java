package com.test.student.service;

import com.test.student.exception.StudentNotFoundException;
import com.test.student.model.Student;
import com.test.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Override
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @Override
    public Optional<Student> findStudentBy(long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteStudentBy(long id) {
        try {
            repository.deleteById(id);
        } catch (RuntimeException e) {
            throw new StudentNotFoundException(e.getMessage());
        }
    }

    @Override
    public Student saveOrUpdateStudent(Student student) {
        return repository.save(student);
    }


}
