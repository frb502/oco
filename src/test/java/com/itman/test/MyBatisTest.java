package com.itman.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * Created by furongbin on 17/4/8.
 */
public class MyBatisTest {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        CourseMapper mapper = session.getMapper(CourseMapper.class);
        System.out.println(mapper.selectCourse(1));
    }
}

