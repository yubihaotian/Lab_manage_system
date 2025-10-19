package com.ck.labmanagesystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String email;
    private String phone;
    private String userType; // admin, lab_admin, teacher, student
}
