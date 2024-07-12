package com.sparta.academy.admin.dto;

import com.sparta.academy.enums.DepartmentEnum;
import com.sparta.academy.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AdminRequestDto {

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()_+|<>?:{}]).{8,15}$",
            message = "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다."
    )
    private String password;

    @NotBlank(message = "부서는 필수 항목입니다.")
    private DepartmentEnum department;

    @NotBlank(message = "권한은 필수 항목입니다.")
    private UserRoleEnum role;
}
