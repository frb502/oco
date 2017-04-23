package com.itman.test;

import com.itman.oco.dao.UserDao;
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
        UserDao dao = session.getMapper(UserDao.class);
        System.out.println(dao.selectOne("2dacd803f196e29fc9d8b567e237eb99"));
    }
}

