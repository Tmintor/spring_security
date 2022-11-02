package com.tminto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tminto.domain.LoginUser;
import com.tminto.domain.User;
import com.tminto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 吴员外
 * @date 2022/10/24 19:01
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    /**
     * 框架会默认调用UserDetailsService的实现类InMemoryUserDetailsManager去内存中查找用户信息
     * 我们需要自己写实现类去数据库中查
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);

        if (Objects.isNull(user)) {
            throw new RuntimeException("该用户不存在");
        }

        //TODO 查询用户的权限信息(此处写死)
        List<String> permissions = Arrays.asList("test", "admin");

        return new LoginUser(user,permissions);
    }
}
