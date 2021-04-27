package com.example.AppConsultorioMySQL.services;

import com.example.AppConsultorioMySQL.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

}
