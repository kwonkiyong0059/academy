package com.sparta.academy.course;

import com.sparta.academy.course.Course;
import com.sparta.academy.course.CourseCategoryEnum;
import com.sparta.academy.instructor.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCategoryOrderByRegisteredAtDesc(CourseCategoryEnum category);
    List<Course> findByInstructor(Instructor instructor);
}
