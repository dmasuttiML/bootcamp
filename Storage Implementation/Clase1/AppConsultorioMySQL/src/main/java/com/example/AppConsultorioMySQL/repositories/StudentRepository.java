package com.example.AppConsultorioMySQL.repositories;

import com.example.AppConsultorioMySQL.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
