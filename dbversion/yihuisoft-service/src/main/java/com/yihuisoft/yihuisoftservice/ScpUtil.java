package com.yihuisoft.yihuisoftservice;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import org.springframework.beans.factory.annotation.Value;

/**
 * ScpUtil
 *
 * @author tonywu
 * @version 1.0.0
 **/
public class ScpUtil {

    /**ip */
    @Value("${scp.server.ip}")
    private static String ip = "192.168.1.5";
    /**端口*/
    @Value("${scp.server.port}")
    private static int port = 22;
    /**用户名 */
    @Value("${scp.server.username}")
    private static String user = "ubuntu";//登录用户名

    /**密码 */
    @Value("${scp.server.password}")
    private static String passWord = null;//生成私钥的密码和登录密码，这两个共用这个密码

    /**类初始化 */
    private static Connection connection = new Connection(ip, port);
    /**本机的私钥文件 */
    private static String privaterKey = "C:\\Users\\ubuntu\\.ssh\\id_rsa";

    /**使用用户名和密码来进行登录验证。如果为true则通过用户名和密码登录，false则使用rsa免密码登录 */
    private static boolean usePassword = false;

    /**
     * ssh用户登录验证，使用用户名和密码来认证
     *
     * @param user
     * @param password
     * @return
     */
    public static boolean isAuthedWithPassword(String user, String password) {
        try {
            return connection.authenticateWithPassword(user, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ssh用户登录验证，使用用户名、私钥、密码来认证 其中密码如果没有可以为null，生成私钥的时候如果没有输入密码，则密码参数为null
     *
     * @param user
     * @param privateKey
     * @param password
     * @return
     */
    public static boolean isAuthedWithPublicKey(String user, File privateKey, String password) {
        try {
            return connection.authenticateWithPublicKey(user, privateKey, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
      * @author luweibin
      * @description
      * @param   
      * @return boolean
      * @date Created in 15:13 2018/4/24
      * @modified By
      * @version v1.0.0
    */
    public static boolean isAuth() {
        if (usePassword) {
            return isAuthedWithPassword(user, passWord);
        } else {
            return isAuthedWithPublicKey(user, new File(privaterKey), passWord);
        }
    }

    /**
      * @author luweibin
      * @description
      * @param   remoteFile  path
      * @return 
      * @date Created in 15:14 2018/4/24
      * @modified By
      * @version v1.0.0
    */
    public static void getFile(String remoteFile, String path) {
        try {
            connection.connect();
            boolean isAuthed = isAuth();
            if (isAuthed) {
                System.out.println("认证成功!");
                SCPClient scpClient = connection.createSCPClient();
                scpClient.get(remoteFile, path);
            } else {
                System.out.println("认证失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    /**
      * @author luweibin
      * @description
      * @param   localFile remoteTargetDirectory
      * @return 
      * @date Created in 15:15 2018/4/24
      * @modified By
      * @version v1.0.0
    */
    public static void putFile(String localFile, String remoteTargetDirectory) {
        try {
            connection.connect();
            boolean isAuthed = isAuth();
            if (isAuthed) {
                SCPClient scpClient = connection.createSCPClient();
                scpClient.put(localFile, remoteTargetDirectory);
            } else {
                System.out.println("认证失败!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
        }
    }

    /*public static void main(String[] args) {
        try {
            // getFile("/home/users/ubuntu/error.txt", "c://");
            putFile("c://aaa.txt", "/home/users/ubuntu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
