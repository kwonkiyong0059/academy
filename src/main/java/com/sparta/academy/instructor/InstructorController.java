package com.sparta.academy.instructor;

import com.sparta.academy.instructor.dto.InstructorRequestDto;
import com.sparta.academy.instructor.dto.InstructorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping
    public ResponseEntity<InstructorResponseDto> createInstructor(@RequestBody InstructorRequestDto requestDto) {
        InstructorResponseDto responseDto = instructorService.createInstructor(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorResponseDto> updateInstructor(@PathVariable Long id, @RequestBody InstructorRequestDto requestDto) {
        InstructorResponseDto responseDto = instructorService.updateInstructor(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorResponseDto> getInstructor(@PathVariable Long id) {
        InstructorResponseDto responseDto = instructorService.getInstructor(id);
        return ResponseEntity.ok(responseDto);
    }
}
