package com.example.demo;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class LombokTest {

    public static void needClose(String path1, String path2) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = new FileInputStream(path1);
            fos = new FileOutputStream(path2);
            // do something...
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void noClose(String path1, String path2) {
        try {
            @Cleanup InputStream is = new FileInputStream(path1);
            @Cleanup FileOutputStream fos = new FileOutputStream(path2);
            // do something...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void noCloseAndNoCatch(String path1, String path2) {
        @Cleanup InputStream is = new FileInputStream(path1);
        @Cleanup FileOutputStream fos = new FileOutputStream(path2);
        // do something...
    }

}
