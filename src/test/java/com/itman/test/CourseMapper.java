package com.itman.test;

import org.apache.ibatis.annotations.Select;

/**
 * Created by furongbin on 17/4/8.
 */
public interface CourseMapper {
    @Select("SELECT * FROM course WHERE id = #{id}")
    Course selectCourse(int id);
}
