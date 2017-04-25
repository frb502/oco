package com.itman.test;

import java.io.File;

/**
 * Created by furongbin on 17/4/25.
 */
public class FileTest {
    public static void main(String[] args) {
        File file = new File("logs/error.log");
        System.out.println(file.exists());
        System.out.println(file.getName());
    }
}
