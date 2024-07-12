package com.sparta.academy.course.dto;

import com.sparta.academy.course.CourseCategoryEnum;
import lombok.Getter;

@Getter
public class CourseRequestDto {
    private String title;
    private Integer price;
    private String description;
    private CourseCategoryEnum category;
    private Long instructorId;
}
