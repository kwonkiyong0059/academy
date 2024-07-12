package com.sparta.academy.instructor.dto;

import com.sparta.academy.instructor.Instructor;
import lombok.Getter;

@Getter
public class InstructorResponseDto {
    private Long id;
    private String name;
    private int experienceYears;
    private String company;
    private String phoneNumber;
    private String introduction;

    public InstructorResponseDto(Instructor instructor) {
        this.id = instructor.getId();
        this.name = instructor.getName();
        this.experienceYears = instructor.getExperienceYears();
        this.company = instructor.getCompany();
        this.phoneNumber = instructor.getPhoneNumber();
        this.introduction = instructor.getIntroduction();
    }
}
