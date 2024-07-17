package com.sparta.academy.course;

import com.sparta.academy.course.dto.CourseRequestDto;
import com.sparta.academy.course.dto.CourseResponseDto;
import com.sparta.academy.instructor.Instructor;
import com.sparta.academy.instructor.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;



    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto requestDto) {

        Instructor instructor = instructorRepository.findById(requestDto.getInstructorId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Instructor not found")
                );

        Course course = Course.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .category(requestDto.getCategory())
                .instructor(instructor)
                .build();

        instructor.addCourse(course);
        courseRepository.save(course);
        return new CourseResponseDto(course);

    }

    @Transactional
    public CourseResponseDto updateCourse(long id, CourseRequestDto requestDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        course.update(requestDto);

        return new CourseResponseDto(course);
    }

    @Transactional(readOnly = true)
    public CourseResponseDto getCourse(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Course not found"));
        return new CourseResponseDto(course);
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDto> findByCategory(String category) {
        CourseCategoryEnum categoryEnum = CourseCategoryEnum.valueOf(category);
        List<Course> courses = courseRepository.findByCategoryOrderByRegisteredAtDesc(categoryEnum);
        return courses.stream().map(CourseResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDto> findByInstructor(long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
        List<Course> courses = courseRepository.findByInstructor(instructor);
        return courses.stream().map(CourseResponseDto::new).collect(Collectors.toList());
    }

    // 현재 사용자가 특정 역할을 가지고 있는지 확인하는 메소드
    private boolean currentUserHasRole(String role) {

        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}
