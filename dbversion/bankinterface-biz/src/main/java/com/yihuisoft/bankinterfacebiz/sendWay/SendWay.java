package com.yihuisoft.bankinterfacebiz.sendWay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/***
 *
 * @author ？？
 * @version ??
 */
public class SendWay {
    private static final Logger logger = LoggerFactory.getLogger(SendWay.class);

    /**
     * shiyi 2017/06/29
     * 鄂尔多斯项目发送接收报文通过tcp/ip 短连接的方式发送给中间业务平台
     * 发送TCP请求
     *
     * @param IP         远程主机地址
     * @param port       远程主机端口
     * @param reqData    待发送报文的中文字符串形式
     * @param reqCharset 该方法与远程主机间通信报文的编码字符集(编码为byte[]发送到Server)
     * @return localPort--本地绑定的端口,reqData--请求报文,respData--响应报文
     * @see //根据鄂尔多斯业务平台要求本方法默认的连接超时和读取超时均为60秒
     * @see //编码与解码请求响应字节时,均采用双方约定的字符集,即本方法的第四个参数reqCharset
     */
    public String SendWay(String IP, String port, String reqData, String reqCharset) {
        //Map<String, String> respMap = new HashMap<String, String>();
        OutputStream out = null;      //写
        InputStream in = null;        //读
        //String localPort = null;      //本地绑定的端口(java socket, client, /127.0.0.1:50804 => /127.0.0.1:9901)
        String respData = null;       //响应报文
        Socket socket = new Socket(); //客户机
        try {
            socket.setTcpNoDelay(true);
            socket.setReuseAddress(true);
            socket.setSoTimeout(30000);
            socket.setSoLinger(true, 5);
            socket.setSendBufferSize(1024);
            socket.setReceiveBufferSize(1024);
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress(IP, Integer.parseInt(port)), 30000);
            //	localPort = String.valueOf(socket.getLocalPort());
            /**
             * 发送HTTP请求
             */
            out = socket.getOutputStream();
            out.write(reqData.getBytes(reqCharset));
            /**
             * 接收HTTP响应
             */
            in = socket.getInputStream();
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                bytesOut.write(buffer, 0, len);
            }
            /**
             * 解码HTTP响应的完整报文
             */
            respData = bytesOut.toString(reqCharset);
        } catch (Exception e) {
            logger.error("与[" + IP + ":" + port + "]通信遇到异常,堆栈信息如下");
            //e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if (null != socket && socket.isConnected() && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error("关闭客户机Socket时发生异常,堆栈信息如下");
                    logger.error(e.getMessage());
                    //e.printStackTrace();
                }
            }
        }
        //respMap.put("localPort", localPort);
        //respMap.put("reqData", reqData);
        //respMap.put("respData", respData);
        return respData;
    }


}

