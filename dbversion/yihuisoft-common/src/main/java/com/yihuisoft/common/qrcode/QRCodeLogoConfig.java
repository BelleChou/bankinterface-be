package com.yihuisoft.common.qrcode;


import java.awt.*;

/**
 * 二维码Logo配置类
 * @author tonywu
 * @version v1.0.0
 */
public class QRCodeLogoConfig {
	/**logo默认边框颜色*/
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    /**logo默认边框宽度*/
    public static final int DEFAULT_BORDER = 2;
    /**logo大小默认为照片的1/5*/
    public static final int DEFAULT_LOGOPART = 5;

    /**logo边框颜色*/
    private Color borderColor;
    /**边框宽度*/
    private int border = DEFAULT_BORDER;
    /**logo大小*/
    private int logoPart;

    /**
     * 构造函数
     */
    public QRCodeLogoConfig()
    {  
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);  
    }

    /**
     * 构造函数
     * @param borderColor
     * @param logoPart
     */
    public QRCodeLogoConfig(Color borderColor, int logoPart)
    {  
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }  
   
    public Color getBorderColor()  { return borderColor; }

    public int getBorder()  {  return border; }
   
    public int getLogoPart() { return logoPart; }
}
