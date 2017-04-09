package com.itman.oco.util;

import com.itman.oco.exception.OcoException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by furongbin on 17/4/9.
 */
public class SqlSessionHelper implements LazyLogging{
    private SqlSessionHelper(){}
    private static SqlSessionFactory sqlSessionFactory;

    private static void init() throws Exception{
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public synchronized static SqlSession openSession() {
        try {
            if (null == sqlSessionFactory) {
                init();
            }
            return sqlSessionFactory.openSession();
        } catch (Exception e) {
            sqlSessionFactory = null;
            throw new OcoException("[SqlSessionHelper] sqlSessionFactory create error", e);
        }

    }
}
