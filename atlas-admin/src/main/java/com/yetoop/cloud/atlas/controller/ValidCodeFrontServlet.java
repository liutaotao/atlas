package com.yetoop.cloud.atlas.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author zhenyi  
 *
 */
public class ValidCodeFrontServlet extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger(ValidCodeFrontServlet.class);

	public static final String SESSION_VALID_CODE = "valid_code";

	/**
	 * 产生随机字体
	 * 
	 * @return
	 */
	private Font getFont() {
		int size =55;
		Random random = new Random();
		Font font[] = new Font[5];
		//font[0] = new Font("Ravie", Font.PLAIN, size);
		font[0] = new Font("Antique Olive Compact", Font.PLAIN, size);
		font[1] = new Font("Forte", Font.PLAIN, size);
		//font[2] = new Font("Wide Latin", Font.PLAIN, size);
		//font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);
		int num = random.nextInt(1);
		System.out.println("font:"+num);
		return font[num];
	}

	protected Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		// 在内存中创建图象
		int width = 160, height = 80;
		resp.addHeader("Cache-Control", "no-cache");
		resp.addHeader("Cache-Control", "no-store");
		resp.addHeader("Cache-Control", "must-revalidate");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", -1);

		String vtype = "";

		if (req.getParameter("vtype") != null) {
			vtype = req.getParameter("vtype");
		}
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		if (req.getParameter("vtype") != null) {
			vtype = req.getParameter("vtype");
		}

		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(this.getFont());

		// 画边框
		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1);

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		
		String sRand = "",rand = "";
		int rNum1 = random.nextInt(20),rNum2 = random.nextInt(20),op = random.nextInt(2);
		
		if(op == 0){//加法
			rand = rNum1 + "+" + rNum2 + "=";
			sRand = String.valueOf(rNum1 + rNum2);
		}else{
			if(rNum1 >= rNum2){
				rand = rNum1 + "-" + rNum2 + "=";
			}else{
				rand = rNum2 + "-" + rNum1 + "=";
			}
			sRand = String.valueOf(Math.abs(rNum1 - rNum2));
		}
		
		for(int i = 0; i < rand.length() ; i++){
			char randChar = rand.charAt(i);
			g.setColor(new Color(30 + random.nextInt(80), 30 + random.nextInt(80), 30 + random.nextInt(80)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(String.valueOf(randChar), 20 * i + 20, 58);
		}
		if (log.isDebugEnabled()) {
			log.debug("doGet-->(rand+sRand):{},vtype:{},sRand:{}", (rand + sRand), vtype, sRand);
		}
        HttpSession session = req.getSession();
        session.setAttribute(SESSION_VALID_CODE + vtype, sRand);

		// 图象生效
		g.dispose();
		// 输出图象到页面
		ImageIO.write(image, "JPEG", resp.getOutputStream());
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}



}
