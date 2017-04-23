package com.itman.test;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * Created by furongbin on 17/4/8.
 */
public class Course implements Serializable{

    private int id;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    private String cname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                '}';
    }
}
