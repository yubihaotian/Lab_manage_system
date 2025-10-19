// src/main/java/com/ck/labmanagesystem/dto/UserDTO.java
package com.ck.labmanagesystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "用户信息DTO")
public class UserDTO {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "用户名", example = "student001")
    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名长度不能超过50个字符")
    private String username;

    @Schema(description = "真实姓名", example = "张三")
    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 100, message = "真实姓名长度不能超过100个字符")
    private String realName;

    @Schema(description = "邮箱", example = "zhangsan@example.com")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;

    @Schema(description = "电话", example = "13800138000")
    @Size(max = 20, message = "电话长度不能超过20个字符")
    private String phone;

    @Schema(description = "用户类型", example = "student", allowableValues = {"admin", "teacher", "student"})
    @NotBlank(message = "用户类型不能为空")
    @Pattern(regexp = "^(admin|lab_admin|teacher|student)$", message = "用户类型必须为系统管理员、实验室管理员、教师或学生")
    private String userType;

    @Schema(description = "学号", example = "2021001")
    @Size(max = 20, message = "学号长度不能超过20个字符")
    @Pattern(regexp = "^[0-9]+$", message = "学号必须为数字")
    private String studentId;

    @Schema(description = "工号", example = "T001")
    @Size(max = 20, message = "工号长度不能超过20个字符")
    private String teacherId;

    @Schema(description = "院系", example = "计算机学院")
    @Size(max = 100, message = "院系长度不能超过100个字符")
    private String department;

    @Schema(description = "专业", example = "软件工程")
    @Size(max = 100, message = "专业长度不能超过100个字符")
    private String major;

    @Schema(description = "状态", example = "1")
    private Integer status = 1;
}
