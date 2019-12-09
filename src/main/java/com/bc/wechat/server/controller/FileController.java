package com.bc.wechat.server.controller;

import com.bc.wechat.server.cons.Constant;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 文件
 *
 * @author zhou
 */
@RestController
@RequestMapping("/files")
public class FileController {
    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping(value = "")
    public ResponseEntity<List<String>> uploadFile(HttpServletRequest request) {
        try {
            String path;
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith(Constant.OS_SHORT_NAME_WINDOWS)) {
                path = Constant.FILE_UPLOAD_PATH_WINDOWS;
            } else {
                path = Constant.FILE_UPLOAD_PATH_LINUX;
            }

            // 路径不存在则创建路径
            File basePath = new File(path);

            if (!basePath.exists()) {
                basePath.mkdirs();
            }

            List<String> imgUrlList = new ArrayList<>();

            // 检测是不是存在上传文件
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            // 判断 request 是否有文件上传,即多部分请求
            if (isMultipart) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    String originFileName = file.getOriginalFilename();
                    String prefix = originFileName.substring(originFileName.lastIndexOf(".") + 1);
                    if (file != null) {
                        try {
                            // //取得当前上传文件的文件名称
                            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + '.' + prefix;

                            File localFile = new File(path + File.separator + fileName);
                            if (!localFile.exists()) {
                                localFile.createNewFile();
                            }

                            //文件上传
                            file.transferTo(localFile);

                            imgUrlList.add(fileName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return new ResponseEntity<>(imgUrlList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
