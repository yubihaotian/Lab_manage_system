// src/main/java/com/ck/labmanagesystem/service/impl/UserServiceImpl.java
package com.ck.labmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ck.labmanagesystem.entity.User;
import com.ck.labmanagesystem.dto.UserDTO;
import com.ck.labmanagesystem.dto.UserQueryDTO;
import com.ck.labmanagesystem.mapper.UserMapper;
import com.ck.labmanagesystem.service.IUserService;
import com.ck.labmanagesystem.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public boolean createUser(UserDTO userDTO) {
        // 检查用户名是否已存在
        if (getUserByUsername(userDTO.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        // 设置默认密码为123456并加密
        String defaultPassword = DigestUtils.md5DigestAsHex("123456".getBytes());
        user.setPassword(defaultPassword);

        return this.save(user);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        User user = this.getById(userDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 检查用户名是否被其他用户使用
        User existingUser = getUserByUsername(userDTO.getUsername());
        if (existingUser != null && !existingUser.getUserId().equals(userDTO.getUserId())) {
            throw new RuntimeException("用户名已被其他用户使用");
        }

        BeanUtils.copyProperties(userDTO, user);
        return this.updateById(user);
    }

    @Override
    public boolean deleteUser(Integer userId) {
        User user = this.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return this.removeById(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.getBaseMapper().selectByUsername(username);
    }

    @Override
    public PageVO<User> getUserPage(UserQueryDTO queryDTO) {
        Page<User> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        IPage<User> result = this.getBaseMapper().selectUserPage(page, queryDTO);

        return PageVO.of(result.getRecords(), result.getTotal(), queryDTO.getCurrent(), queryDTO.getSize());
    }

    @Override
    public boolean updatePassword(Integer userId, String newPassword) {
        User user = this.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        return this.updateById(user);
    }
}
