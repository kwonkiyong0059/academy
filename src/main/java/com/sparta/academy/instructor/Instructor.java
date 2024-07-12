package com.sparta.academy.instructor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int experienceYears;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String introduction;

    public Instructor(String name, int experienceYears, String company, String phoneNumber, String introduction) {
        this.name = name;
        this.experienceYears = experienceYears;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
    }

    public void update(String company, int experienceYears, String phoneNumber, String introduction) {
        this.company = company;
        this.experienceYears = experienceYears;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
    }
}
