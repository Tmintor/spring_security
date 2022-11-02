package com.tminto.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tminto.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author 吴员外
 * @date 2022/10/24 18:59
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
