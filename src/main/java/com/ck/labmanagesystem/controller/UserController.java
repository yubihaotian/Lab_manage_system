package com.ck.labmanagesystem.controller;

import com.ck.labmanagesystem.entity.User;
import com.ck.labmanagesystem.dto.UserDTO;
import com.ck.labmanagesystem.dto.UserQueryDTO;
import com.ck.labmanagesystem.mapper.UserMapper;
import com.ck.labmanagesystem.service.IUserService;
import com.ck.labmanagesystem.utils.JwtUtil;
import com.ck.labmanagesystem.vo.ResultVO;
import com.ck.labmanagesystem.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "用户管理", description = "用户相关的CRUD操作接口")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 创建用户
     */
    @PostMapping
    @Operation(summary = "创建用户", description = "创建一个新的用户")
    public ResultVO<Void> createUser(
            @Valid @RequestBody @Parameter(description = "用户信息") UserDTO userDTO) {
        // 不要在这里捕获异常，让异常传递给全局异常处理器
        boolean result = userService.createUser(userDTO);
        return result ? ResultVO.success() : ResultVO.error("创建用户失败");
    }

    /**
     * 更新用户
     */
    @PutMapping
    @Operation(summary = "更新用户", description = "更新用户信息")
    public ResultVO<Void> updateUser(
            @Valid @RequestBody @Parameter(description = "用户信息") UserDTO userDTO) {
        // 不要在这里捕获异常
        boolean result = userService.updateUser(userDTO);
        return result ? ResultVO.success() : ResultVO.error("更新用户失败");
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "删除用户", description = "根据用户ID删除用户")
    public ResultVO<Void> deleteUser(
            @PathVariable @Parameter(description = "用户ID") Integer userId) {
        // 不要在这里捕获异常
        boolean result = userService.deleteUser(userId);
        return result ? ResultVO.success() : ResultVO.error("删除用户失败");
    }

    /**
     * 根据ID获取用户
     */
    @GetMapping("/{userId}")
    @Operation(summary = "获取用户详情", description = "根据用户ID获取用户详细信息")
    public ResultVO<User> getUserById(
            @PathVariable @Parameter(description = "用户ID") Integer userId) {
        User user = userService.getById(userId);
        return user != null ? ResultVO.success(user) : ResultVO.error("用户不存在");
    }

    /**
     * 分页查询用户
     */
    @GetMapping
    @Operation(summary = "分页查询用户", description = "分页查询用户列表")
    public ResultVO<PageVO<User>> getUserPage(UserQueryDTO queryDTO) {
        // 不要在这里捕获异常
        PageVO<User> pageVO = userService.getUserPage(queryDTO);
        return ResultVO.success(pageVO);
    }

    /**
     * 修改密码
     */
    @PutMapping("/{userId}/password")
    @Operation(summary = "修改用户密码", description = "根据用户ID修改用户密码")
    public ResultVO<Void> updatePassword(
            @PathVariable @Parameter(description = "用户ID") Integer userId,
            @RequestParam @Parameter(description = "新密码") String newPassword) {
        // 不要在这里捕获异常
        boolean result = userService.updatePassword(userId, newPassword);
        return result ? ResultVO.success() : ResultVO.error("修改密码失败");
    }

    @PutMapping("/profile")
    @Operation(summary = "更新当前用户个人信息", description = "用户更新自己的基本信息")
    public ResultVO<Void> updateProfile(
            @Valid @RequestBody @Parameter(description = "用户信息") UserDTO userDTO,
            HttpServletRequest request) {
        try {
            // 从 token 中获取当前用户
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                String currentUsername = jwtUtil.extractUsername(token);

                // 验证只能更新自己的信息
                User currentUser = userMapper.selectByUsername(currentUsername);
                if (currentUser != null && currentUser.getUserId().equals(userDTO.getUserId())) {
                    boolean result = userService.updateUser(userDTO);
                    return result ? ResultVO.success() : ResultVO.error("更新失败");
                }
            }
            return ResultVO.error("权限不足");
        } catch (Exception e) {
            return ResultVO.error("更新失败：" + e.getMessage());
        }
    }
}
