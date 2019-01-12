package com.yihuisoft.yihuisoftservice.controller;

import com.yihuisoft.common.exception.ApplicationException;
import com.yihuisoft.common.util.*;
import com.yihuisoft.yihuisoftservice.FtpUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传类
 * @author tonywu
 * @version v1.0.0
 */
@RestController
public class FileUploadController extends BaseController {

    /**文件服务器地址*/
/*    @Value("${fileServer.Dir}")
    private String fileServerDir;*/

    //@Value("${fileLocalPath}")
    //private String fileLocalPath;

    /**文件服务器地址*/
/*    @Value("${fileServer.upload.URL}")
    private String fileServerURL;*/

    /**文件服务器访问地址*/
/*    @Value("${fileServer.access.URL}")
    private String accessFileURL;*/

    /**文件上传最大值*/
    @Value("${uploadfile.max.size}")
    private Long uploadFileMaxSize;

    /**文件上传模式*/
    @Value("${fileServer.type}")
    private String fileServerType;

    /**
      * @author luweibin
      * @description
      * @param   
      * @return 
      * @date Created in 15:09 2018/4/24
      * @modified By
      * @version v1.0.0
    */
    @RequestMapping(value = "/FileUpload")
    @ResponseBody
    //public String fileUpload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path, HttpServletRequest request, HttpServletResponse response) {
    public String fileUpload(@RequestParam("path") String path, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        MultipartFile file = null;

        try {
            response.setContentType("text/html; charset=utf-8");

            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if(!multipartResolver.isMultipart(request)) {
                throw new ApplicationException("文件上传格式不对");
            }

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            file = multipartRequest.getFileMap().get("file");
            if (file == null) {
                //throw new Exception("上传文件未选择");
                throw new ApplicationException("上传文件未选择");
            }
            if (StringUtils.isEmpty(path)) {
                throw new ApplicationException("相对路径不为空");
            }

            if (file.getSize() > uploadFileMaxSize) {
                throw new ApplicationException("上传文件太大(不能超过5M)");
            }

            String name = file.getOriginalFilename();
            String extName = "";
            if (name.lastIndexOf('.') >= 0) {
                extName = name.substring(name.lastIndexOf('.'));
            }
            String newFileName = new Date().getTime() + StringUtil.getRandNumberStr(6).toString() + extName;
            boolean result = uploadFile(file, path, newFileName);
            if (!result) {
                throw new ApplicationException("上传文件失败");
            }

            map.put("errCode", "success");
            map.put("errMessage", "success");
            map.put("fileName", newFileName);

        } catch (ApplicationException ae) {
            ae.printStackTrace();
            map.put("errCode", "error");
            map.put("errMessage", ae.getErrCode());
        }catch (Exception e) {
            e.printStackTrace();
            map.put("errCode", "error");
            map.put("errMessage", e.getMessage());
        }
        return JSONObject.valueToString(map);
    }

    /***
     * 上传文件
     * @param file
     * @param path
     * @param newFileName
     * @return
     */
    private boolean uploadFile(MultipartFile file, String path, String newFileName) {
        if("1".equalsIgnoreCase(fileServerType)){
            return uploadLocalFile(file,path+newFileName);
        }else if("2".equalsIgnoreCase(fileServerType)){
            FtpUtils ftp = new FtpUtils();
            try {
                return ftp.uploadFile(path,newFileName,file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return uploadLocalFile(file,path+newFileName);
        }
    }

   /* private boolean upload(MultipartFile file, String path) {
        if(fileServerType.equalsIgnoreCase("1")){
            return uploadLocalFile(file,path);
        }else if(fileServerType.equalsIgnoreCase("2")){
            FtpUtils ftp = new FtpUtils();
            try {
                return ftp.uploadFile(path,file.getName(),file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return uploadLocalFile(file,path);
        }
    }*/

    /***
     * 上传至本地文件
     * @param file
     * @param path
     * @return
     */
    private boolean uploadLocalFile(MultipartFile file, String path) {
        try {
            File f = new File(path);
            FileUtils.copyInputStreamToFile(file.getInputStream(), f);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //    private String fileUpload(MultipartFile file, String path) {
//        String tmpPath = fileLocalPath + path;
//        String name = file.getOriginalFilename();
//        int a = -1;
//        try {
//            String extName = "";
//            if (name.lastIndexOf(".") >= 0) {
//                extName = name.substring(name.lastIndexOf("."));
//            }
//            File f = new File(tmpPath, name);
//            FileUtils.copyInputStreamToFile(file.getInputStream(), f);
//            a = postHttp(fileServerURL, "file" + path, f);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "failed";
//        }
//        if (a == -1) {
//            return "failed";
//        } else {
//            return accessFileURL + path + name;
//        }
//    }
//
//
    /*private int postHttp(String url, String path, File file) throws IOException {

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.addBinaryBody("file", file);
        mEntityBuilder.addTextBody("path", path);
        httppost.setEntity(mEntityBuilder.build());
        HttpResponse resp = httpClient.execute(httppost);
        int code = resp.getStatusLine().getStatusCode();
        if (200 == code) {
            return 0;
        } else {
            return -1;
        }
    }*/

}
