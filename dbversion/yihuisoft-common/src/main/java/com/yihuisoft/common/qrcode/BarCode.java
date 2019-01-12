package com.yihuisoft.common.qrcode;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.InvalidParameterException;

/**
 * 条形码类
 * @author tonywu
 * @version v1.0.0
 */
public class BarCode {

	/**
	 * 生成条形码
	 * <b>注意</b>条形码的宽度不能等于图片的宽度，否则解析不出来,如果解析不出来，请加大offset的值
	 * @param contents 内容
	 * @param width 宽度
	 * @param height 高度
	 * @param offset 偏移量
	 * @return
	 */
	public static BufferedImage encode(String contents,int width,int height,int offset) {

		//contents=new String(contents.getBytes("UTF-8"),"ISO-8859-1");
		try {
			BitMatrix matrix=new MultiFormatWriter().encode(contents, BarcodeFormat.CODE_128, width-offset, height);
			return MatrixToImageWriter.toBufferedImage(matrix);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		return null;

	}
	/**
	* Code128A字符集 包括大写字母、数字、常用标点符号和一些控制符。
	Code128B字符集 包括大小写字母、数字、常用标点符号。
	Code128C字符集 为纯数字序列。
	Code128Auto 是将上述三种字符集最佳优化组合。
	EAN128条码生成 是由UPC/EAN指定代表意义规则的128码，编码方式同code128条码。
	Code39条码生成字符集包括数字 、大写字母以及- . $ / + % * 空格等字符,其中"*"

	只用于标记开始和结束。
	Code93条码生成是 full ASCII 模式，可使用ASCII全部128个字符。
	库德巴码（Codabar）条码生成，字符集包括数字和- $ : /. + 以及ABCD等字符，

	其中ABCD只用于开始或者结尾，作为标识符使用。
	交叉25码（Interleaved 2 of 5）条码生成，常用于物流管理，字符集仅为数字且个数为偶数,

	                       为奇数将自动在前面加"0"。
	Code11条码 只允许11种字元，分别是0-9和"-",为降低检查错误率，可使用两位的检验码。
	MSI条形码 必须是纯的数字0-9，带有一位检验码。
	EAN13商品条码是纯数字，而且位数是12位，在编码过后外加一位校验码，组成13位数字。
	EAN8商品条码是纯数字，而且位数是7位，在编码过后外加一位校验码，组成8位数字。
	UPC-A条码商品条码是纯数字，而且位数是11位，在编码过后外加一位校验码，

	                                  组成12位数字,主要在美国和加拿大使用。
	UPC-E条码商品条码是纯数字，是由UPC-A缩减而成，位数是7位，而且首位必须为0，

	                               在编码过后外加一位校验码，组成8位数字。
	*/
	
	
	/**
	 * 解析条形码
	 * @param dest 要解码的图片地址
	 * @return String 条形码内容
	 */
	public static String decode(String dest) {

		BufferedImage image= null;
		try {
			image = ImageIO.read(new File(dest));
			LuminanceSource source=new BufferedImageLuminanceSource(image);
			BinaryBitmap imageBinaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
			Result result = new MultiFormatReader().decode(imageBinaryBitmap,null);
			return result.getText();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		return "";
	}
	
	
	/**
	 * 	以条形码 693 69838 0001 3 为例<br>
		此条形码分为4个部分，从左到右分别为：<br>
		1-3位：共3位，对应该条码的693，是中国的国家代码之一。（690--695都是中国大陆的代码，由国际上分配）<br>
		4-8位：共5位，对应该条码的69838，代表着生产厂商代码，由厂商申请，国家分配<br>
		9-12位：共4位，对应该条码的0001，代表着厂内商品代码，由厂商自行确定<br>
		第13位：共1位，对应该条码的3，是校验码，依据一定的算法，由前面12位数字计算而得到。<br>
		（公式第13位算法<br>
		1：取出该数的奇数位的和，c1=6+3+9+3+0+0=21；<br>
		2：取出该数的偶数位的和，c2=9+6+8+8+0+1=32；<br>
		3：将奇数位的和与“偶数位的和的三倍”相加。<br>
		4：取出结果的个位数：117（117%10=7）；<br>
		5：用10减去这个个位数：10-7=3；<br>
		6：对得到的数再取个位数（对10去余）3%10=3；<br>
		参考：<a href="http://baike.baidu.com/view/13740.htm?fr=aladdin">百度百科-条形码</a>
	 * @return String 校验码
	 * @throws Exception 
	 */
	public static String checksum(String countryCode,String factoryCode,String productCode) throws Exception{
		String temp=countryCode+factoryCode+productCode;
		if(!(isNumber(countryCode)&&isNumber(factoryCode)&&isNumber(productCode))){
			throw new InvalidParameterException("不能含有非数字字符");
		}
		if(countryCode.length()!=3){
			throw new InvalidParameterException("国家地区代码不合规范,必须3位");
		}
		if(factoryCode.length()!=5){
			throw new InvalidParameterException("厂商代码不合规范,必须5位");
		}
		if(productCode.length()!=4){
			throw new InvalidParameterException("产品代码不合规范,必须4位");
		}
		char []code=temp.toCharArray();
		
		int oddSum=0;
		int evenSum=0;
		for(int i=0;i<code.length;i++){
			if((i+1)%2 != 0){
				oddSum+=Integer.valueOf(code[i]+"");
			}else{
				evenSum+=Integer.valueOf(code[i]+"");
			}
		}
		int digit=(10-((oddSum+evenSum*3)%10))%10;
		
		return temp+digit;
	}
	
	/**
	 * 校验数字
	 * @param number 数字
	 * @return Boolean
	 */
	public static boolean isNumber(String number) {
		if (null == number || "".equals(number))
			return false;
		String regex = "[0-9]*";
		return number.matches(regex);
	}
}
