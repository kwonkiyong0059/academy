package com.sparta.academy.instructor;

import com.sparta.academy.instructor.dto.InstructorRequestDto;
import com.sparta.academy.instructor.dto.InstructorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;

    @Transactional
    public InstructorResponseDto createInstructor(InstructorRequestDto requestDto) {
        Instructor instructor = new Instructor(
                requestDto.getName(),
                requestDto.getExperienceYears(),
                requestDto.getCompany(),
                requestDto.getPhoneNumber(),
                requestDto.getIntroduction()
        );

        instructorRepository.save(instructor);

        return new InstructorResponseDto(instructor);
    }

    @Transactional
    public InstructorResponseDto updateInstructor(Long id, InstructorRequestDto requestDto) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        instructor.update(
                requestDto.getCompany(),
                requestDto.getExperienceYears(),
                requestDto.getPhoneNumber(),
                requestDto.getIntroduction()
        );

        return new InstructorResponseDto(instructor);
    }

    @Transactional(readOnly = true)
    public InstructorResponseDto getInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        return new InstructorResponseDto(instructor);
    }
}
