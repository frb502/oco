package com.itman.oco.dao;

import com.itman.oco.model.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by furongbin on 17/4/22.
 */
public interface UserDao {

    @Select("SELECT id, expire_date, user_name, amount, telephone, account " +
            "FROM user WHERE account = #{account}")
    @Results({
            @Result(column="expire_date", property="expireDate"),
            @Result(column="user_name", property="userName")
    })
    User selectOne(String account);
}
