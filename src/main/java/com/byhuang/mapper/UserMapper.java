package com.byhuang.mapper;

import com.byhuang.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserMapper {

    User selectUser(long id);

    User selectUser1(@Param("id") long id, @Param("name") String name, @Param("phone") String phone);

    User selectUser2(User user);

    User selectUser3(Map map);
}
