package com.yihuisoft.common.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * 二维码处理工具类
 * @author tonywu
 * @version v1.0.0
 */
public class QRCodeUtil {

	/**二维码文件格式*/
	private static final String QRCODE_FORMAT = "PNG";

	/**
	 * 生成二维码
	 * @param params
	 * @return
	 */
	public static BufferedImage makeQRCode(QRCodeParams params) {

		String contents= null;
		try {
			contents = params.getTxt();
			HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			// 指定纠错等级 二维码容错率
			hints.put(EncodeHintType.ERROR_CORRECTION, params.getLevel());
			// 指定编码格式
			hints.put(EncodeHintType.CHARACTER_SET, params.getCharset());
			//白边大小，取值范围0~4
			hints.put(EncodeHintType.MARGIN, params.getMargin());
			int onColor = params.getOnColor();     //前景色
			int offColor = params.getOffColor();    //背景色

			MatrixToImageConfig config = new MatrixToImageConfig(onColor, offColor);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,BarcodeFormat.QR_CODE, params.getWidth(), params.getHeight(), hints);
			return MatrixToImageWriter.toBufferedImage(bitMatrix, config);

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		return null;
	}

	/**
	 * 从二维码图片解析出二维码信息
	 * @param filePath 二维码文件路径
	 * @return String 二维码信息
	 */
	public static String decodeQRCode(String filePath) {

		QRCodeReader reader= null;
		try {
			reader = new QRCodeReader();
			BufferedImage image=ImageIO.read(new File(filePath));
			LuminanceSource source=new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap imageBinaryBitmap = new BinaryBitmap(binarizer  );
			Result result = reader.decode(imageBinaryBitmap);
			return result.getText();
		} catch (Exception e) {
			/*e.printStackTrace();*/
			System.out.println(e);
		}
		return "";
	}

	/**
	 * 画String
	 * @param str
	 * @param x 坐标
	 * @param y 坐标
	 * @param rate
	 * @param g 2D图形
	 */
	public static void drawString(String str,int x,int y,double rate,Graphics2D g){
		String tempStr = "";
		int orgStringWight=g.getFontMetrics().stringWidth(str);
		int orgStringLength=str.length();
		int tempx = x;
		int tempy = y;
		String strClone = str;//复制参数
		while(strClone.length()>0)
		{
			tempStr = strClone.substring(0, 1);
			strClone = strClone.substring(1, strClone.length());
			g.drawString(tempStr, tempx, tempy);
			tempx=(int)(tempx+(double)orgStringWight/(double)orgStringLength*rate);
		}
	}

	/**
	 * 在二维码中增加Logo
	 * @param image 二维码
	 * @param logoPath logo图片路径
	 * @param logoConfig logo配置
	 * @return
	 */
	public static BufferedImage addLogoInQRCode(BufferedImage image, String logoPath, QRCodeLogoConfig logoConfig)
	{
		try
		{
			BufferedImage logo = ImageIO.read(new File(logoPath));
			System.out.println(logo);
			int widthLogo = logo.getWidth(null)>image.getWidth()*2/10?(image.getWidth()*2/10):logo.getWidth(null),
					heightLogo = logo.getHeight(null)>image.getHeight()*2/10?(image.getHeight()*2/10):logo.getHeight(null);
			/**
			 * logo放在中心
			 */
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - heightLogo) / 2;

			BufferedImage imageBox = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			//开始绘制图片
			Graphics2D g = imageBox.createGraphics();
			g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);

			g.dispose();
			logo.flush();
			image.flush();
			return imageBox;
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			System.out.println(e);
		}
		return image;
	}

	/**
	 * 生成二维码(中间带LOGO)  且带条形码
	 * @param params
	 * @since jdk 1.7.0_80
	 */
	public static BufferedImage makeQRCodeWithInLogoAndWithBar(QRCodeParams params){
		try {
			BufferedImage barCode=BarCode.encode(params.getTxt(),  params.getWidth(),  params.getHeight() /4, params.getOffset());

			BufferedImage qrcode = makeQRCode(params);
			BufferedImage image=addLogoInQRCode(qrcode,params.getLogoPath(),params.getLogoConfig());
			BufferedImage imageBox = new BufferedImage(image.getWidth()+20, image.getHeight()+barCode.getHeight()+50, BufferedImage.TYPE_INT_RGB);

			//合成
			Graphics2D g = imageBox.createGraphics();
			g.setBackground(java.awt.Color.WHITE);
			g.clearRect(0, 0, imageBox.getWidth(), imageBox.getHeight());
			g.drawImage(barCode, (imageBox.getWidth()-barCode.getWidth()) /2, 20, barCode.getWidth(), barCode.getHeight(), null);
			g.drawImage(image, 10, barCode.getHeight()+30, image.getWidth(), image.getHeight(), null);
			g.setColor(Color.BLACK);
			//g.drawString(params.getTxt(), 10, 15);
			double rate=3.20;
			int x=(int)((double)(imageBox.getWidth()-10)/(double)2 - rate*g.getFontMetrics().stringWidth(params.getTxt())/(double)2);
			drawString(params.getTxt(), x+20, 15, rate, g);

			g.dispose();
			barCode.flush();
			image.flush();
			return imageBox;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e);
		}
		return null;
	}

	/**
	 * 生成二维码(中间带LOGO)  不带条形码
	 * @param params
	 */
	public static BufferedImage makeQRCodeWithInLogo(QRCodeParams params){
		try {
			BufferedImage qrcode = makeQRCode(params);
			BufferedImage image = addLogoInQRCode(qrcode,params.getLogoPath(),params.getLogoConfig());
			BufferedImage imageBox = new BufferedImage(image.getWidth()+20, image.getHeight()+50, BufferedImage.TYPE_INT_RGB);

			Graphics2D g = imageBox.createGraphics();
			g.setBackground(java.awt.Color.WHITE);
			g.clearRect(0, 0, imageBox.getWidth(), imageBox.getHeight());
			g.drawImage(image, 10, 30, image.getWidth(), image.getHeight(), null);
			g.setColor(Color.BLACK);

			g.dispose();
			image.flush();
			return imageBox;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e);
		}
        return null;
	}

	/**
	 * main函数
	 * @param args
	 */
	public static void main(String[] args) {
		//testQRcode();
		//testQRcodeWithInLogo();
		testQRcodeWithInLogoAndWithBar();
	}

	/**
	 * 测试生成二维码
	 */
	public static void testQRcode(){
		try {
			//测试生成二维码
			String content = "www.baidu.com?uid=" + 333333;
			String saveFilePath = "";//findbug:"D:/qrcode.png";
			QRCodeParams params = QRCodeParams.initialize(content,
					400, 400, 0xFF000000, 0xFFFFFFFF, 2, null);
			//生成二维码
			BufferedImage bufferedImage = makeQRCode(params);
			if(bufferedImage != null) {
				//保存文件
				ImageIO.write(bufferedImage, QRCODE_FORMAT, new File(saveFilePath));
			}
			//读二维码
			System.out.println("解析结果：" + decodeQRCode(saveFilePath));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 测试生成二维码(中间带Logo) 且带Bar
	 */
	public static void testQRcodeWithInLogoAndWithBar(){
		try {
			//测试生成二维码
			String content = "www.baidu.com123logo";
			String saveFilePath = "";//findbug:"D:/qrcodewithlogoandbar.png";
			String logoPath = "";//findbug:"D:/slogo.png";
			QRCodeParams params = QRCodeParams.initialize(content,
					400, 400, 0xFF000000, 0xFFFFFFFF, 2, logoPath);
			//生成二维码
			BufferedImage image = makeQRCodeWithInLogoAndWithBar(params);
			//BufferedImage imageWithLogo =addLogoInQRCode(image,logoPath,params.getLogoConfig());
			if(image != null) {
				//保存文件
				ImageIO.write(image, QRCODE_FORMAT, new File(saveFilePath));
			}

			//读二维码
			System.out.println("解析结果：" + decodeQRCode(saveFilePath));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 测试生成二维码(中间带Logo) 不带Bar
	 */
	public static void testQRcodeWithInLogo(){
		try {
			//测试生成二维码
			String content = "www.baidu.com123logo";
			String saveFilePath = "";//findbug:"D:/qrcodewithlogo.png";
			String logoPath = "";//findbug:"D:/slogo.png";
			QRCodeParams params = QRCodeParams.initialize(content,
					400, 400, 0xFF000000, 0xFFFFFFFF, 2, logoPath);
			//生成二维码
			BufferedImage image = makeQRCodeWithInLogo(params);
			//BufferedImage imageWithLogo =addLogoInQRCode(image,logoPath,params.getLogoConfig());
			if(image != null) {
				//保存文件
				ImageIO.write(image, QRCODE_FORMAT, new File(saveFilePath));
			}

			//读二维码
			System.out.println("解析结果：" + decodeQRCode(saveFilePath));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
