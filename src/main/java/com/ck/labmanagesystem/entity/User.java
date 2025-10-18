package com.ck.labmanagesystem.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private String userType;
    private String studentId;
    private String teacherId;
    private String department;
    private String major;
    private Integer classId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    private Integer status = 1;
}
