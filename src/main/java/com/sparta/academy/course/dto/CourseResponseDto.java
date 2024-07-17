package com.sparta.academy.course.dto;

import com.sparta.academy.course.Course;
import com.sparta.academy.course.CourseCategoryEnum;
import com.sparta.academy.instructor.Instructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CourseResponseDto {
    private String title;
    private Integer price;
    private String description;
    private CourseCategoryEnum category;
    private Long instructorId;
    private LocalDate registrationDate; // Changed to LocalDate to match Course class

    public CourseResponseDto(Course course) {
        this.title = course.getTitle();
        this.price = course.getPrice();
        this.description = course.getDescription();
        this.category = course.getCategory();
        this.instructorId = course.getInstructor().getId();
        this.registrationDate = course.getRegisteredAt(); // Correct method call
    }
}
