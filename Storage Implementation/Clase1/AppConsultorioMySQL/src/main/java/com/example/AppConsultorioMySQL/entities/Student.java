package com.example.AppConsultorioMySQL.entities;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String dni;
    private String name;
    private String lastName;
}
