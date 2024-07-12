package com.sparta.academy.admin;

import com.sparta.academy.admin.dto.AdminRequestDto;
import com.sparta.academy.admin.dto.AdminResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody @Valid AdminRequestDto requestDto) {
        AdminResponseDto responseDto = adminService.createAdmin(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
