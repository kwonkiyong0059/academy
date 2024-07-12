package com.sparta.academy.instructor;

import com.sparta.academy.enums.UserRoleEnum;
import com.sparta.academy.exception.ForbiddenException;
import com.sparta.academy.instructor.dto.InstructorRequestDto;
import com.sparta.academy.instructor.dto.InstructorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // 매니저 권한을 가진 사용자만 업데이트 가능하도록 검사
        if (!currentUserHasRole(UserRoleEnum.MANAGER)) {
            throw new ForbiddenException("강사 정보를 업데이트할 권한이 없습니다.");
        }

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

    // 현재 사용자가 특정 역할을 가지고 있는지 확인하는 메소드
    private boolean currentUserHasRole(UserRoleEnum role) {

        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role.getAuthority()));
    }
}
