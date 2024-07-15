package com.sparta.academy.admin;

import com.sparta.academy.admin.dto.AdminResponseDto;
import com.sparta.academy.admin.dto.LoginRequestDto;
import com.sparta.academy.admin.dto.SignupRequestDto;
import com.sparta.academy.enums.DepartmentEnum;
import com.sparta.academy.enums.UserRoleEnum;
import com.sparta.academy.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AdminResponseDto createAdmin(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<Admin> checkEmail = adminRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 권한 검증
        if ((requestDto.getDepartment() == DepartmentEnum.CURRICULUM || requestDto.getDepartment() == DepartmentEnum.DEVELOPMENT) && requestDto.getRole() == UserRoleEnum.MANAGER) {
            // 유효한 부서와 권한 조합
        } else if (requestDto.getRole() == UserRoleEnum.STAFF) {
            throw new IllegalArgumentException("해당 부서는 MANAGER 권한을 가질 수 없습니다.");
        }

        Admin admin = new Admin(
                requestDto.getEmail(),
                password,
                requestDto.getDepartment(),
                requestDto.getRole()
        );

        adminRepository.save(admin);

        return new AdminResponseDto(admin.getId(), admin.getEmail(), admin.getDepartment().name(), admin.getRole().name());
    }
    public ResponseEntity<String> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();


        Admin admin = adminRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(admin.getEmail(), admin.getRole());
        jwtUtil.addJwtToCookie(token, response);

        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

}
