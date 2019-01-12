package com.yihuisoft.common.qrcode;


import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码参数配置类
 * @author tonywu
 * @version v1.0.0
 */
public class QRCodeParams {
	/**二维码内容*/
    private String txt;
	/**二维码宽度*/
    private Integer width = 300;
	/**二维码高度*/
    private Integer height = 300;
	/**前景色*/
    private Integer onColor = 0xFF000000;
	/**背景色*/
    private Integer offColor = 0xFFF8F8F8;
	/**白边大小，取值范围0~4*/
    private Integer margin = 1;
	/**二维码容错率*/
    private ErrorCorrectionLevel level = ErrorCorrectionLevel.L;
	/**logo图片路径*/
    private String logoPath=null;
	/**一维码边距*/
    private Integer offset =10;
	/**字符集*/
    private String charset="UTF-8";
	/**Logo配置对象*/
    private QRCodeLogoConfig logoConfig =new QRCodeLogoConfig();

	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getOnColor() {
		return onColor;
	}
	public void setOnColor(Integer onColor) {
		this.onColor = onColor;
	}

	public Integer getOffColor() {
		return offColor;
	}
	public void setOffColor(Integer offColor) {
		this.offColor = offColor;
	}

	public Integer getMargin() {
		return margin;
	}
	public void setMargin(Integer margin) {
		this.margin = margin;
	}

	public ErrorCorrectionLevel getLevel() {
		return level;
	}
	public void setLevel(ErrorCorrectionLevel level) {
		this.level = level;
	}

	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public QRCodeLogoConfig getLogoConfig() {
		return logoConfig;
	}
	public void setLogoConfig(QRCodeLogoConfig logoConfig) {
		this.logoConfig = logoConfig;
	}


	/**
	 * 格式化
	 * @param txt
	 * @param width
	 * @param height
	 * @param onColor
	 * @param offColor
	 * @param margin
	 * @param logoPath
	 * @return
	 */
	public QRCodeParams format(String txt, Integer width, Integer height, Integer onColor, Integer offColor,
			Integer margin,String logoPath){
		this.txt=txt;
		this.width=width;
		this.height=height;
		this.onColor=onColor; 
		this.offColor=offColor; 
		this.margin=margin;
		this.logoPath=logoPath;   
		return this;
	}

	/**
	 * 格式化
	 * @param txt
	 * @param width
	 * @param height
	 * @param onColor
	 * @param offColor
	 * @param margin
	 * @param logoPath
	 * @param level
	 * @return
	 */
    public QRCodeParams format(String txt, Integer width, Integer height, Integer onColor, Integer offColor,
			Integer margin,String logoPath,ErrorCorrectionLevel level){
		this.txt=txt;
		this.width=width;
		this.height=height;
		this.onColor=onColor; 
		this.offColor=offColor; 
		this.margin=margin;
		this.logoPath=logoPath; 
		this.level=level;   
		return this;
	}

	/**
	 * 格式化
	 * @param txt
	 * @param width
	 * @param height
	 * @param onColor
	 * @param offColor
	 * @param margin
	 * @param logoPath
	 * @param level
	 * @param offset
	 * @return
	 */
    public QRCodeParams format(String txt, Integer width, Integer height, Integer onColor, Integer offColor,
			Integer margin,String logoPath,ErrorCorrectionLevel level,Integer offset){
		this.txt=txt;
		this.width=width;
		this.height=height;
		this.onColor=onColor; 
		this.offColor=offColor; 
		this.margin=margin;
		this.logoPath=logoPath; 
		this.level=level;  
		this.offset=offset; 
		return this;
	}

	/**
	 * 格式化
	 * @param txt
	 * @param width
	 * @param height
	 * @param onColor
	 * @param offColor
	 * @param margin
	 * @param logoPath
	 * @param level
	 * @param offset
	 * @param charset
	 * @return
	 */
	public QRCodeParams format(String txt, Integer width, Integer height, Integer onColor, Integer offColor,
			Integer margin,String logoPath,ErrorCorrectionLevel level,Integer offset,String charset){
		this.txt=txt;
		this.width=width;
		this.height=height;
		this.onColor=onColor; 
		this.offColor=offColor; 
		this.margin=margin;
		this.logoPath=logoPath;
		this.level=level;   
		this.offset=offset; 
		this.charset=charset; 
		return this;
	}
	/**
	 * 初始化
	 * @param txt
	 * @param width
	 * @param height
	 * @param onColor
	 * @param offColor
	 * @param margin
	 * @param logoPath
	 * @return
	 */
	public static QRCodeParams initialize(String txt, Integer width, Integer height, Integer onColor, Integer offColor,
			Integer margin,String logoPath){ 
		return new QRCodeParams().format(txt,width,height,onColor,offColor,margin,logoPath);
	}

}
