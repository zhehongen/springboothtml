package com.rectrl.springboothtml.controller;

import com.rectrl.springboothtml.exception.ServiceException;
import com.rectrl.springboothtml.result.Result;
import com.rectrl.springboothtml.result.ResultCode;
import com.rectrl.springboothtml.util.Identities;
import com.rectrl.springboothtml.util.ImportUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@Slf4j
public class HelloController {

    @RequestMapping("/index")
    public String index() {
        return "hello";
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public Object uploadFile(@RequestParam("fileName") MultipartFile deviceFile) throws InterruptedException {
        // 文件为空
        if (null == deviceFile || deviceFile.isEmpty()) {
            throw new ServiceException(ResultCode.FILE_EMPTY.getCode(), ResultCode.FILE_EMPTY.getMessage());

        }
        // 文件扩展名--文件格式判断
        String extension = Objects.requireNonNull(deviceFile.getOriginalFilename()).substring(deviceFile.getOriginalFilename().lastIndexOf(".") + 1);
        List<String> lines;

        try {
            if (extension.equals("xls") || extension.equals("xlsx")) {
                lines = ImportUtils.excel(deviceFile);
            } else if (extension.equals("csv")) {
                lines = ImportUtils.csv(deviceFile);
            } else {
                throw new ServiceException(ResultCode.FILE_EXTENSION.getCode(), ResultCode.FILE_EXTENSION.getMessage());
            }
        } catch (Exception e) {
            log.error("[auditDeviceOrder]文件数据解析失败: {}", e.getMessage());
            throw (e instanceof ServiceException) ? (ServiceException) e : new ServiceException(ResultCode.FILE_READ_ERR.getCode(), ResultCode.FILE_READ_ERR.getMessage());
        }
        Thread.sleep(3 * 1000);
        return lines;
    }


    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public Result<String> uploadFileTest(@RequestParam("fileName") MultipartFile file) {
        // 首先校验图片格式
        List<String> imageType = Arrays.asList("jpg", "jpeg", "png", "bmp", "gif", "xls", "xlsx", "csv");
        // 获取文件名，带后缀
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀格式
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (imageType.contains(fileSuffix)) {
            // 只有当满足图片格式时才进来，重新赋图片名，防止出现名称重复的情况
            String newFileName = Identities.nextId() + originalFilename;
            // 该方法返回的为当前项目的工作目录，即在哪个地方启动的java线程
            String dirPath = System.getProperty("user.dir");
            String path = File.separator + "uploadImg" + File.separator + newFileName;
            File destFile = new File(dirPath + path);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(destFile);
                // 将相对路径返回给前端
                return Result.ok(path);
            } catch (IOException e) {
                log.error("upload file error");
                return null;
            }
        } else {
            // 非法文件
            log.error("the file's suffix is illegal");
            throw new ServiceException(ResultCode.FILE_EXTENSION.getCode(), ResultCode.FILE_EXTENSION.getMessage());

        }
    }


}