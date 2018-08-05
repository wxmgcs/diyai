package cn.diyai.ruokuai;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RuoKuaiUtil {
	static SimpleDateFormat date_format = new SimpleDateFormat("yyyy_MM_dd");
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SS");
	
	static String username = "xxxx";
	static String password = "xxxx";
	static String softid = "xxxx";
	static String softkey = "xxxxx";


	private static String getVerifyCode(String filePath) throws IOException {

		String typeid = "3040";
		String timeout = "90";
		
		ByteArrayOutputStream baos = null;
		File file = new File(filePath);
		FileInputStream fileIS = new FileInputStream(file);
		BufferedImage bufferedImage = ImageIO.read(fileIS);

		baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", baos);
		baos.flush();
		byte[] imageArr = baos.toByteArray();

		return RuoKuai.createByPost(username, password, typeid, timeout, softid, softkey, imageArr);

	}

	/**
	 * <?xml version="1.0"?> <Root> <Result>emsd</Result>
	 * <Id>7ad4c509-efc1-426c-a6f5-0cf158234098</Id> </Root>
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	private static String parseVerifyCodeRet(File retFile) throws Exception {

		SAXReader reader = new SAXReader();
		Document document = reader.read(retFile);
		Element root = document.getRootElement();
		List<Element> childElements = root.elements();

		for (Element child : childElements) {
			if (child.getName().equals("Result")) {
				return child.getData().toString();
			}
		}

		return null;
	}

	private static String getLocalTime() {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		return sdf.format(c1.getTime());
	}

	private static void save(String dir, String fname, String log) {

		File logDir = new File(dir);
		if (!logDir.exists()) {
			logDir.mkdirs();
		}

		OutputStream out = null;
		try {
			File file = new File(dir + fname);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file, true);
			out.write((log).getBytes());
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String getVerifyCode(String filePath, String alipayUserName) throws Exception {
		String ret = getVerifyCode(filePath);
		String verifyCodeDir = System.getProperty("user.dir") + File.separator + "verifycode_logs"+ File.separator;
		String verifyCodeFileName = alipayUserName + "_" + getLocalTime() + ".xml";
		save(verifyCodeDir, verifyCodeFileName, ret);

		File file = new File(verifyCodeDir + verifyCodeFileName);
		return parseVerifyCodeRet(file);

	}
	
	private static String getVerifyCode(String logfile, String filePath, String alipayUserName) throws Exception {
		String ret = getVerifyCode(filePath);
		String verifyCodeDir = logfile + File.separator + "verifycode_logs"+ File.separator;
		String verifyCodeFileName = alipayUserName + "_" + getLocalTime() + ".xml";
		save(verifyCodeDir, verifyCodeFileName, ret);

		File file = new File(verifyCodeDir + verifyCodeFileName);
		return parseVerifyCodeRet(file);

	}

	private static String getLocalTime2(){
		return format.format(new Date());
	}


	/**
	 * 识别图形码
	 * 
	 * @throws Exception
	 */
	public static String recognitionVerifyCode(String orderID) throws Exception {
		String filePath = System.getProperty("user.dir") + File.separator + "verifycode"+ File.separator + orderID + "_"
				+ getLocalTime2() + ".png";
		File checkfile = new File(filePath);
		if(checkfile == null || !checkfile.exists()){
			return null;
		}
		
		return RuoKuaiUtil.getVerifyCode(filePath,orderID);

	}
	

}
