package cn.diyai.util;

import java.io.*;

public class TempFile {

	/**
	 * 删除文件
	 * @param filename
	 */
	public static void del(String filename) {
		File file = new File(filename);
		file.delete();
	}

	/**
	 * 读取文件的最后一行
	 * @param filename
	 * @return
	 */
	public static String get(String filename) {
		File file = new File(filename);
		return readLastLine(file);

	}

	/**
	 * 保存一行数据
	 * */
	public static void save(String filename, String log) {
		del(filename);
		if (log == null) {
			return;
		}

		OutputStream out = null;
		try {
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file, true);
			out.write(log.getBytes());
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

	/**
	 * 读最后一行
	 * @param file
	 * @return
	 */
	private static String readLastLine(File file) {
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			return null;
		}

		String lastLine = "";

		try {
			InputStream instream = new FileInputStream(file);
			if (instream != null) {
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line;
				// ���ж�ȡ

				while ((line = buffreader.readLine()) != null) {
					lastLine = line;
				}
				instream.close();
			}
		} catch (java.io.FileNotFoundException e) {

			return null;
		} catch (IOException e) {
			return null;
		}

		return lastLine;
	}
}
