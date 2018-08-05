package cn.diyai.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String get(String url, String param) {
		return get(url + "?" + param);
	}

	public static String get(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String post(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			close(out,in);
		}
		return result;
	}

	public static String post(String url, String param, String cookie) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("cookie", cookie);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			close(out,in);
		}
		return result;
	}
	
	public static String[] post2(String url, String param, String cookie) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		String retCookie = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("cookie", cookie);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			Map<String, List<String>> map = conn.getHeaderFields();
			for (String key : map.keySet()) {
				// Set-Cookie--->[JSESSIONID_bj=NmcqXp1fxQ7tpqv7yLShyy0KvPmvQBswL5vYh3y2KcCNWBYvzt54!-1489778859;
				// path=/; HttpOnly]
				if ("Set-Cookie".equalsIgnoreCase(key)) {
					String temp = map.get(key).get(0).split(";")[0];
					retCookie = temp.substring(0, temp.length());
					System.out.println(key + "--->" + map.get(key)+","+map.get(key).size());
				}
//				System.out.println(key + "--->" + map.get(key)+","+map.get(key).size());
				
			}

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			close(out,in);
		}
		return new String[]{result,retCookie} ;
	}

	private static void close(PrintWriter out,BufferedReader in){
		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 15801006614:7wangxiaomindeMac-mini:~ wxm$ curl
	 * "http://127.0.0.1:7007/getMobile?id=1"
	 * 15801006614:7wangxiaomindeMac-mini:~ wxm$ curl
	 * "http://127.0.0.1:7007/getMobile?id=9"
	 * 15801006616:9wangxiaomindeMac-mini:~ wxm$ curl
	 * "http://127.0.0.1:7007/getMobile?id=9&is_valid=0" wangxiaomindeMac-mini:~
	 * wxm$c-mini:~ wxm$ curl "http://127.0.0.1:7007/updateMobile?id=0&is_valid=
	 * > "http://127.0.0.1:7007/updateMobile?id=0&is_valid=0" curl: (52) Empty
	 * reply from server wangxiaomindeMac-mini:~ wxm$ curl
	 * "http://127.0.0.1:7007/updateMobile?id=0&is_valid=0"
	 * 0wangxiaomindeMac-mini:~ wxm$ curl
	 * "http://127.0.0.1:7007/updateMobile?id=1&is_valid=0"
	 * 0wangxiaomindeMac-mini:~ wxm$ curl
	 * "http://127.0.0.1:7007/updateMobile?id=10&is_valid=0"
	 */
	/*
	 * public static void main(String[] args){
	 * System.out.println(sendGet("http://127.0.0.1:7007/getMobile","id=1"));
	 * System.out.println(sendGet("http://127.0.0.1:7007/updateMobile",
	 * "id=1&is_valid=0"));
	 * 
	 * }
	 */
}