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
    private Instructor instructor;
    private LocalDate registrationDate; // Changed to LocalDate to match Course class

    public CourseResponseDto(Course course) {
        this.title = course.getTitle();
        this.price = course.getPrice();
        this.description = course.getDescription();
        this.category = course.getCategory();
        this.instructor = course.getInstructor();
        this.registrationDate = course.getRegisteredAt(); // Correct method call
    }
}
