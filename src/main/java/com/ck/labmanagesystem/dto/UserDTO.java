package com.ck.labmanagesystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "用户信息DTO")
public class UserDTO {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "用户名", example = "student001")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "真实姓名", example = "张三")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;

    @Schema(description = "电话", example = "13800138000")
    private String phone;

    @Schema(description = "用户类型", example = "student", allowableValues = {"admin", "teacher", "student"})
    @NotNull(message = "用户类型不能为空")
    private String userType;

    @Schema(description = "学号", example = "2021001")
    private String studentId;

    @Schema(description = "工号", example = "T001")
    private String teacherId;

    @Schema(description = "院系", example = "计算机学院")
    private String department;

    @Schema(description = "专业", example = "软件工程")
    private String major;

    @Schema(description = "班级ID")
    private Integer classId;

    @Schema(description = "状态", example = "1")
    private Integer status = 1;
}
