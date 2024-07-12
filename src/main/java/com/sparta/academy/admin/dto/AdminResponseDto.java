package com.sparta.academy.admin.dto;

import com.sparta.academy.admin.Admin;
import lombok.Getter;

@Getter
public class AdminResponseDto {
    private Long id;
    private String email;
    private String department;
    private String role;

    public AdminResponseDto(Long id, String email, String department, String role) {
        this.id = id;
        this.email = email;
        this.department = department;
        this.role = role;
    }

    public AdminResponseDto(Admin admin) {
        this.id = admin.getId();
        this.email = admin.getEmail();
        this.department = admin.getDepartment().name();
        this.role = admin.getRole().name();
    }
}
