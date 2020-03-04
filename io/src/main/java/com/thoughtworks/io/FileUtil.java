package com.thoughtworks.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileUtil {

    /**
     * 完成复制文件夹方法:
     * 1. 把给定文件夹from下的所有文件(包括子文件夹)复制到to文件夹下
     * 2. 保证to文件夹为空文件夹，如果to文件夹不存在则自动创建
     * <p>
     * 例如把a文件夹(a文件夹下有1.txt和一个空文件夹c)复制到b文件夹，复制完成以后b文件夹下也有一个1.txt和空文件夹c
     */
    public static void copyDirectory(File from, File to) throws IOException {
        String[] fileList = from.list();
        if (!to.exists()) {
            (new File(to.getAbsolutePath() ) ).mkdir();
        } else if(Objects.requireNonNull(to.listFiles()).length > 0) {
            deleteFile(to);
        }
        for (String s : fileList) {
            if(new File(from.getAbsolutePath() + File.separator + s).isDirectory() ) {
                copyDirectory(new File(from.getAbsolutePath() + File.separator + s),
                              new File(to.getAbsolutePath() + File.separator + s) );
            }
            if(new File(from.getAbsolutePath() + File.separator + s).isFile() ) {
                copyFile(new File(from.getAbsolutePath() + File.separator + s),
                         new File(to.getAbsolutePath() + File.separator + s));
            }
        }
    }

    private static void copyFile(File from, File to) throws IOException {
        FileInputStream in = new FileInputStream(from);
        FileOutputStream out = new FileOutputStream(to);

        byte[] buffer=new byte[2048];
        int readByte = 0;
        while((readByte = in.read(buffer)) != -1){
            out.write(buffer, 0, readByte);
        }
        out.close();
        in.close();
    }

    private static void deleteFile(File to) {
        if (to.isDirectory()) {
            File[] files = to.listFiles();
            for (File file : files) {
                deleteFile(file);
            }
        } else {
            to.delete();
        }
    }
}
