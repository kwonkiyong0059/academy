package com.sparta.academy.admin;

import com.sparta.academy.admin.dto.AdminResponseDto;
import com.sparta.academy.admin.dto.SignupRequestDto;
import com.sparta.academy.enums.DepartmentEnum;
import com.sparta.academy.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public AdminResponseDto createAdmin(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<Admin> checkEmail = adminRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        DepartmentEnum department = DepartmentEnum.valueOf(requestDto.getDepartment());
        UserRoleEnum role = UserRoleEnum.STAFF;
        if (requestDto.getIsManager()) {
            if (department.equals(DepartmentEnum.CURRICULUM) ||
                    department.equals(DepartmentEnum.DEVELOPMENT)) {
                role = UserRoleEnum.MANAGER;
            }
            else throw new IllegalArgumentException("등록하지 못했습니다. 매니저 계정은 개발 및 커리큘럼 부서의 직원만 등록할 수 있습니다.");
        }

        Admin admin = Admin.builder()
                .email(email)
                .password(password)
                .department(department)
                .role(role).build();
        adminRepository.save(admin);


        adminRepository.save(admin);

        return new AdminResponseDto(admin.getId(), admin.getEmail(), admin.getDepartment().name(), admin.getRole().name());
    }

}
