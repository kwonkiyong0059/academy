package com.sparta.academy.instructor;

import com.sparta.academy.enums.UserRoleEnum;
import com.sparta.academy.exception.ForbiddenException;
import com.sparta.academy.instructor.dto.InstructorRequestDto;
import com.sparta.academy.instructor.dto.InstructorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

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


        instructor.update(requestDto);
        instructorRepository.save(instructor);

        return new InstructorResponseDto(instructor);
    }


    @Transactional(readOnly = true)
    public InstructorResponseDto getInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        return new InstructorResponseDto(instructor);
    }

}
