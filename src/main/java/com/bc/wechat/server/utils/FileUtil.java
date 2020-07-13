package com.bc.wechat.server.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 *
 * @author zhou
 */
public class FileUtil {
    /**
     * 下载文件
     *
     * @param fileUrl  文件url
     * @param savePath 保存路径
     * @return 下载下来的文件名
     */
    public static String downLoadFromUrl(String fileUrl, String savePath) {
        String fileName = CommonUtil.generateId() + ".png";
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            fileName = "";
        }
        return fileName;
    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream 输入流
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    static List<File> fileList = new ArrayList<>();

    public synchronized static List<File> getFileList(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (null != files) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileList(file.getAbsolutePath());
                } else {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    public static void resetFileList() {
        fileList.clear();
    }

    /**
     * 根据字节数计算可显示流量(如:B、KB、MB、GB等)
     *
     * @param size long类型的字节数
     * @return 可显示流量
     */
    public static String getShowSize(long size) {
        // 定义GB的计算常量
        int GB = 1024 * 1024 * 1024;
        // 定义MB的计算常量
        int MB = 1024 * 1024;
        // 定义KB的计算常量
        int KB = 1024;
        // 格式化小数
        DecimalFormat df = new DecimalFormat("0.00");
        String resultSize = "";
        if (size / GB >= 1) {
            // 如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB";
        } else if (size / MB >= 1) {
            // 如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB";
        } else if (size / KB >= 1) {
            // 如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB";
        } else {
            resultSize = size + "B";
        }
        return resultSize;
    }

    public static List<List<File>> splitFileList(List<File> fileList, int splitNum) {
        List<List<File>> littleFileList = new ArrayList<>();
        if (splitNum >= fileList.size()) {
            littleFileList.add(fileList);
            return littleFileList;
        }
        //每个小单元的容量
        int littleSize = fileList.size() / splitNum;
        int i = 1;
        List<File> littleUnit = new ArrayList<>();
        for (File file : fileList) {
            littleUnit.add(file);
            if (i % littleSize == 0 && i != fileList.size()) {
                littleFileList.add(littleUnit);
                littleUnit = new ArrayList<>();
            }
            if (i == fileList.size()) {
                littleFileList.add(littleUnit);
                return littleFileList;
            }
            i++;
        }
        return littleFileList;
    }
}
