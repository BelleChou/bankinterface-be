package com.yihuisoft.yihuisoftservice;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
  * @author luweibin
  * @Date: Created in 11:12 2018/4/24
  * @version v1.0.0
*/
public class FtpUtils {
    /**ftp服务器地址*/
    @Value("${ftp.server.ip}")
    private String hostname = "192.168.1.113";
    /**ftp服务器端口号默认为21*/
    @Value("${ftp.server.port}")
    private Integer port = 21;
    /**ftp登录账号*/
    @Value("${ftp.server.username}")
    private String username = "test";
    /**ftp登录密码*/
    @Value("${ftp.server.password}")
    private String password = "test";
    /**主动模式和被动模式 1：为被动模式 其他为主动模式*/
    @Value("${ftp.server.type}")
    private Integer typeValue = 0;

    /**设置相对路径*/
    @Value("${ftp.server.dir}")
    private String ftpServerDir = "/test/";

    /**初始化类*/
    private FTPClient ftpClient = null;

    /**
     * 初始化ftp服务器
     */
    public boolean initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            System.out.println("connecting...ftp服务器:"+this.hostname+":"+this.port);
            ftpClient.connect(hostname, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("connect failed...ftp服务器:"+this.hostname+":"+this.port);
                return false;
            }
            System.out.println("connect successfu...ftp服务器:"+this.hostname+":"+this.port);
            return true;
        }catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 上传文件
     * @param pathName ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     *  @param originfilename 待上传文件的名称（绝对地址） *
     * @return
     */
    public boolean uploadFile( String pathName, String fileName,String originfilename){
        boolean flag = false;

        InputStream inputStream = null;
        try{
            System.out.println("开始上传文件");
            inputStream = new FileInputStream(new File(originfilename));
            if(initFtpClient()){
                ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
                createDirecroty(ftpServerDir.concat(pathName));
                ftpClient.makeDirectory(ftpServerDir.concat(pathName));
                ftpClient.changeWorkingDirectory(ftpServerDir.concat(pathName));
                if(typeValue == 1){
                    ftpClient.enterLocalPassiveMode();
                }
                ftpClient.storeFile(fileName, inputStream);
                inputStream.close();
                ftpClient.logout();
                flag = true;
                System.out.println("上传文件成功");
            }
        }catch (Exception e) {
            System.out.println("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
    /**
     * 上传文件
     * @param pathName ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public boolean uploadFile( String pathName, String fileName,InputStream inputStream){
        boolean flag = false;
        try{
            System.out.println("开始上传文件");
            if(initFtpClient()){
                ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
                createDirecroty(ftpServerDir.concat(pathName));
                ftpClient.makeDirectory(ftpServerDir.concat(pathName));
                ftpClient.changeWorkingDirectory(ftpServerDir.concat(pathName));
                if(typeValue == 1){
                    ftpClient.enterLocalPassiveMode();
                }
                ftpClient.storeFile(fileName, inputStream);
                inputStream.close();
                ftpClient.logout();
                flag = true;
                System.out.println("上传文件成功");
            }
        }catch (Exception e) {
            System.out.println("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
      * @Author: luweibin
      * @Description: 改变目录路径
      * @param   directory
      * @return 
      * @Date: Created in 13:21 2018/4/24
      * @Modified By:
      * @version v1.0.0
    */
    public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                System.out.println("进入文件夹" + directory + " 成功！");

            } else {
                System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }
    
    /**
      * @Author: luweibin
      * @Description: 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
      * @param   remote
      * @return 
      * @Date: Created in 13:22 2018/4/24
      * @Modified By:
      * @version v1.0.0
    */
    public boolean createDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!("/").equalsIgnoreCase(directory) && !changeWorkingDirectory(directory)) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf('/', start);
            String path = "";
            StringBuffer paths = new StringBuffer();
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths.append("/").append(subDirectory);
                start = end + 1;
                end = directory.indexOf('/', start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    /**
      * @Author: luweibin
      * @Description: 判断ftp服务器文件是否存在
      * @param   path
      * @return boolean
      * @Date: Created in 13:22 2018/4/24
      * @Modified By:
      * @version v1.0.0
    */
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    /**
      * @Author: luweibin
      * @Description: 创建目录
      * @param   dir
      * @return boolean
      * @Date: Created in 13:22 2018/4/24
      * @Modified By:
      * @version v1.0.0
    */
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                System.out.println("创建文件夹" + dir + " 成功！");

            } else {
                System.out.println("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /** * 删除文件 *
     * @param pathName FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return */
    public boolean deleteFile(String pathName, String filename){
        boolean flag = false;
        try {
            System.out.println("开始删除文件");
            initFtpClient();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(ftpServerDir.concat(pathName));
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            System.out.println("删除文件成功");
        } catch (Exception e) {
            System.out.println("删除文件失败");
            e.printStackTrace();
        } finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
      * @Author: luweibin
      * @Description:
      * @param   
      * @return 
      * @Date: Created in 13:23 2018/4/24
      * @Modified By:
      * @version v1.0.0
    */
    public static void main(String[] args) throws IOException{
        //FtpUtils ftp = new FtpUtils();
        //ftp.uploadFile("testtest", "123.docx", "D://123.docx");
        //ftp.downloadFile("ftpFile/data", "123.docx", "D://");
        //ftp.deleteFile("ftpFile/data", "123.docx");
        //System.out.println("ok");

        //checkFile("D://123.docx","D://12312.docx");
        String str = "菜鸟教程:www.runoob.com";
        String subStr1 ="runoob";
        String subStr2 = "com";

        System.out.print("查找字符 o 第一次出现的位置 :" );
        System.out.println(str.indexOf( 'o' ));
        System.out.print("从第14个位置查找字符 o 第一次出现的位置 :" );
        System.out.println(str.indexOf( 'o', 14 ));
        System.out.print("子字符串 SubStr1 第一次出现的位置:" );
        System.out.println( str.indexOf( subStr1 ));
        System.out.print("从第十五个位置开始搜索子字符串 SubStr1 第一次出现的位置 :" );
        System.out.println( str.indexOf( subStr1, 15 ));
        System.out.print("子字符串 SubStr2 第一次出现的位置 :" );
        System.out.println(str.indexOf( subStr2 ));

    }

    /*private static void checkFile(String s, String s1) throws IOException{
        String path = s;
        String v = getMd5ByFile(new File(path));
        System.out.println("MD5:"+v.toUpperCase());
        String path1 = s1;
        String v1 = getMd5ByFile(new File(path1));
        System.out.println("MD5:"+v1.toUpperCase());

        FileInputStream fis= new FileInputStream(path);
        String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        IOUtils.closeQuietly(fis);
        System.out.println("MD5:"+md5);

        FileInputStream fis1= new FileInputStream(path1);
        String md51 = DigestUtils.md5Hex(IOUtils.toByteArray(fis1));
        IOUtils.closeQuietly(fis1);
        System.out.println("MD5:"+md51);
    }*/

    /**
      * @Author: luweibin
      * @Description: 用md5验证文件
      * @param file
      * @return  String
      * @Date: Created in 13:23 2018/4/24
      * @Modified By:
      * @version v1.0.0
    */
    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

}
