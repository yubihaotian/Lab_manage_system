package com.ck.labmanagesystem.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名长度不能超过50个字符")
    private String username;

    private String password;

    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 100, message = "真实姓名长度不能超过100个字符")
    private String realName;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;

    @Size(max = 20, message = "电话长度不能超过20个字符")
    private String phone;

    @NotBlank(message = "用户类型不能为空")
    @Pattern(regexp = "^(admin|lab_admin|teacher|student)$", message = "用户类型必须为系统管理员、实验室管理员、教师或学生")
    private String userType;

    @Size(max = 20, message = "学号长度不能超过20个字符")
    private String studentId;

    @Size(max = 20, message = "工号长度不能超过20个字符")
    private String teacherId;

    @Size(max = 100, message = "院系长度不能超过100个字符")
    private String department;

    @Size(max = 100, message = "专业长度不能超过100个字符")
    private String major;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    private Integer status = 1;
}
