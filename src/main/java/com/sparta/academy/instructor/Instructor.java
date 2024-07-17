package com.sparta.academy.instructor;

import com.sparta.academy.course.Course;
import com.sparta.academy.instructor.dto.InstructorRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private Integer  experienceYears;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String introduction;

    @OneToMany(mappedBy = "instructor")
    private List<Course> courseList = new ArrayList<>();

    public void addCourse(Course course) {
        this.courseList.add(course);
    }

    @Builder
    public Instructor(String name, Integer experienceYears, String company, String phoneNumber, String introduction) {
        this.name = name;
        this.experienceYears = experienceYears;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.introduction = introduction;
    }

    public void update(InstructorRequestDto requestDto) {
        if(requestDto.getExperienceYears() != null){
            this.experienceYears = requestDto.getExperienceYears();
        }
        if(requestDto.getCompany() != null){
            this.company = requestDto.getCompany();
        }
        if(requestDto.getPhoneNumber() != null){
            this.phoneNumber = requestDto.getPhoneNumber();
        }
        if(requestDto.getIntroduction() != null){
            this.introduction = requestDto.getIntroduction();
        }
    }
}
