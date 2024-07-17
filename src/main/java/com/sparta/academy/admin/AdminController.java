package com.sparta.academy.admin;

import com.sparta.academy.admin.dto.AdminResponseDto;
import com.sparta.academy.admin.dto.LoginRequestDto;
import com.sparta.academy.admin.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/signup")
    public ResponseEntity<String> createAdmin(@RequestBody @Valid SignupRequestDto requestDto) {
        AdminResponseDto responseDto = adminService.createAdmin(requestDto);
        String responseBody = "Admin created with ID: " + responseDto.getId();
        return ResponseEntity.ok(responseBody);
    }

}
