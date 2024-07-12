package com.sparta.academy.admin;

import com.sparta.academy.admin.dto.AdminRequestDto;
import com.sparta.academy.admin.dto.AdminResponseDto;
import com.sparta.academy.enums.DepartmentEnum;
import com.sparta.academy.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional
    public AdminResponseDto createAdmin(AdminRequestDto requestDto) {
        if (adminRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        // 권한 검증
        if ((requestDto.getDepartment() == DepartmentEnum.CURRICULUM || requestDto.getDepartment() == DepartmentEnum.DEVELOPMENT) && requestDto.getRole() == UserRoleEnum.MANAGER) {
            // 유효한 부서와 권한 조합
        } else if (requestDto.getRole() == UserRoleEnum.MANAGER) {
            throw new IllegalArgumentException("해당 부서는 MANAGER 권한을 가질 수 없습니다.");
        }

        Admin admin = new Admin(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getDepartment(),
                requestDto.getRole()
        );

        adminRepository.save(admin);

        return new AdminResponseDto(admin.getId(), admin.getEmail(), admin.getDepartment().name(), admin.getRole().name());
    }
}
