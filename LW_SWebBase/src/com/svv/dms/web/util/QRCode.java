package com.svv.dms.web.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRCode {

	/**
	 * 生成二维码(QRCode)图片
	 * @param content 二维码图片的内容
	 * @param size 生成二维码图片的大小
	 * @param qrImgPath 生成二维码图片完整的路径
	 * @param ccbpath 二维码图片中间的logo路径
	 */
	public static BufferedImage createQRCode(String content, int size, String qrImgPath, String ccbPath){
		System.out.println("content================="+content);
		System.out.println("size================="+size);
		System.out.println("qrImgPath================="+qrImgPath);
		try {
			int _size = size<=0 ? 1 : size;
			Qrcode qrcodeHandler = new Qrcode();
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
			qrcodeHandler.setQrcodeErrorCorrect('M');
			// N代表数字,A代表字符a-Z,B代表其他字符
			qrcodeHandler.setQrcodeEncodeMode('B');
			// 设置设置二维码版本，取值范围1-40，值越大尺寸越大，可存储的信息越大
			qrcodeHandler.setQrcodeVersion(7);
			
			byte[] contentBytes = content.getBytes("UTF-8");
			BufferedImage image = new BufferedImage(_size*98, _size*98, BufferedImage.TYPE_INT_RGB); //BufferedImage.TYPE_BYTE_BINARY
			Graphics2D g = image.createGraphics();
			g.setBackground(Color.WHITE);
			//g.setBackground(Color.BLACK);
			g.clearRect(0, 0, _size*400, _size*400);
			// 设定图像颜色 > BLACK
			g.setColor(Color.BLACK);
			//g.setColor(Color.YELLOW);//黑底黄图：不可行
			
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				// 设置偏移量 不设置可能导致解析出错
				int pixoff = 3;
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							g.fillRect(_size*(j * 2 + pixoff), _size*(i * 2 + pixoff), _size*2, _size*2);
						}
					}
				}
			} else {
				System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,125]. ");
				return null;
			}

			if(ccbPath!=null && ccbPath.length()>0){
				try{
					System.out.println("ccbPath================="+ccbPath);
					Image img = ImageIO.read(new File(ccbPath));// 实例化一个Image对象。
					g.drawImage(img, _size*98/2-64, _size*98/2-64, null);
				}catch(Exception e){
					System.out.println("ccbPath=================LOGO 无");
				}
			}
			
			g.dispose();
			image.flush();
			
			if(qrImgPath!=null && qrImgPath.length()>0){
				// 生成二维码QRCode图片
				File imgFile = new File(qrImgPath);
				ImageIO.write(image, "png", imgFile);
			}
			
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
