package com.example.demo.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class IOUtils {

    /**
     * 输入流转换为字节数组
     * @param input 输入流
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int len;
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }

        return output.toByteArray();
    }

    /**
     * 拷贝文件
     * @param is 输入流
     * @param path 目标路径
     * @throws IOException
     */
    public static void copyFile(InputStream is, String path) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(path));
            int len;
            byte[] buff = new byte[1024 * 4];
            while ((len = is.read(buff, 0, buff.length)) != -1) {
                fos.write(buff, 0, len);
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 写入字符串到文件
     * @param str 内容
     * @param path 文件路径
     * @param charset 字符集，默认 UTF-8
     * @throws IOException
     */
    public static void writeStrToFile(String str, String path, String charset) throws IOException {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(new File(path)), charset == null ? "UTF-8" : charset));
            bw.write(str);
            bw.flush();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }

    }

    /**
     * 从文件中读取字符串
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static String readStrFromFile(String path) throws IOException {
        BufferedReader br = null;
        StringBuilder sb;
        try {
            br = new BufferedReader(new FileReader(new File(path)));
            sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return sb == null ? null : sb.toString();
    }

    /**
     * 根据路径创建文件和目录（支持多层目录）
     * @param fullPath 全路径（文件夹必须以"/"结尾）
     * @throws IOException
     */
    public static void createFileOrDir(String fullPath) throws IOException {
        String dirPath = fullPath.substring(0, fullPath.lastIndexOf("/"));
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(fullPath);
        if (!file.exists() && !file.isDirectory()) {
            file.createNewFile();
        }
    }

    /**
     * 解压缩
     * @param zipPath 压缩文件路径
     * @param destPath 解压到某路径
     * @param charset 字符集
     * @throws IOException
     */
    public static void extractZip(String zipPath, String destPath, String charset) throws IOException {
        FileInputStream fis = null;
        ZipInputStream zis = null;

        try {
            fis = new FileInputStream(zipPath);
            zis = new ZipInputStream(fis, Charset.forName(charset == null ? "UTF-8" : charset));
            ZipEntry ze;
            byte[] buff = new byte[1024 * 4];
            while ((ze = zis.getNextEntry()) != null) {
                File zFile = new File(destPath + File.separator + ze.getName());
                File fPath = new File(zFile.getParentFile().getPath());
                if (ze.isDirectory()) {
                    if (!zFile.exists()) {
                        zFile.mkdirs();
                    }
                    zis.closeEntry();
                } else {
                    if (!fPath.exists()) {
                        fPath.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(zFile);
                    int len;
                    while ((len = zis.read(buff)) != -1) {
                        fos.write(buff, 0, len);
                    }
                    zis.closeEntry();
                    fos.close();
                }
            }
        } finally {
            if (null != fis) {
                fis.close();
            }
            if (null != zis) {
                zis.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String path = "/Users/alone/work/test/test.txt";
        writeStrToFile("天外飞仙", path, "GBK");
        System.out.println(readStrFromFile(path));
        extractZip("/Users/alone/work/test/HardRock.zip", "/Users/alone/work/test/", "GBK");
    }
}
