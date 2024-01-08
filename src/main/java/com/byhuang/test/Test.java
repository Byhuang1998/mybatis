package com.byhuang.test;

import com.byhuang.entity.User;
import com.byhuang.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Test {

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // old version: 不需要定义mapper接口，直接引用xml
        try (SqlSession session = sqlSessionFactory.openSession()) {
            User user = (User) session.selectOne("com.byhuang.mapper.UserMapper.selectUser", 2);
            System.out.println(user);
        }

        // new version: 定义mapper接口，接口名与xml中sql id一致
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectUser(1L);
            System.out.println(user);
        }
    }
}
