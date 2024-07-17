package com.sparta.academy.course;

import com.sparta.academy.course.dto.CourseRequestDto;
import com.sparta.academy.course.dto.CourseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto requestDto) {
        CourseResponseDto responseDto = courseService.createCourse(requestDto);
        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable long id, @RequestBody CourseRequestDto requestDto) {
        CourseResponseDto responseDto = courseService.updateCourse(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable long id) {
        CourseResponseDto responseDto = courseService.getCourse(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getCourseByCategory(@RequestParam String category) {
        return ResponseEntity.ok().body(courseService.findByCategory(category));

    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<CourseResponseDto>> getCoursesByInstructor(@PathVariable long instructorId) {
        return ResponseEntity.ok().body(courseService.findByInstructor(instructorId));
    }
}
