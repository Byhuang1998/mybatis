package com.byhuang.test;

import com.byhuang.entity.User;
import com.byhuang.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

        // 参数查询
        /**
         * （1）散装参数：需要使用@Param
         * （2）实体类参数：只需要SQL中的参数名和实体类属性名对应上
         * （3）map集合：map的键与参数名对应上
         */
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            Map map = new HashMap();
            map.put("id", 2L);
            map.put("name", "ls");
            map.put("phone", "777");


            User user1 = mapper.selectUser1(1L, "zs", "888");
            User user2 = mapper.selectUser2(new User(1L, "zs", "888"));
            User user3 = mapper.selectUser3(map);
            System.out.println("======参数=======");
            System.out.println(user1);
            System.out.println(user2);
            System.out.println(user3);
        }
    }
}
