package cn.diyai.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class URLUtil {
    private static Logger logger = LoggerFactory.getLogger(Setting.class);
    /**
     * url 解码
     * @param schemeUrl url
     * @return 解码url
     */
    public static String decodeURL(String schemeUrl) {
        try {
            return URLDecoder.decode(schemeUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return schemeUrl;
    }

    /**
     * url 编码
     * @param schemeUrl url
     * @return 编码url
     */
    public static String encodeURL(String schemeUrl) {
        try {
            return URLEncoder.encode(schemeUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ;
        return schemeUrl;
    }

    /**
     * 返回带参数的get请求url地址
     * @param url url
     * @param params 参数
     * @return 带参数的get请求url地址
     */
    public static String getURLWithParams(String url,Map<String, String> params){
        return url+"?"+joinParam(params);
    }

    /**
     * 连接参数
     * @param params 参数
     * @return 连接结果
     */
    private static StringBuffer joinParam(Map<String, String> params) {
        StringBuffer result = new StringBuffer();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            String key = param.getKey();
            String value = param.getValue();
            result.append(key).append('=').append(value);
            if (iterator.hasNext()) {
                result.append('&');
            }
        }
        return result;
    }

    /**
     * 获得URL
     * @param pathBaseClassLoader 相对路径（相对于classes）
     * @return URL
     */
    public static URL getURL(String pathBaseClassLoader){
        return URLUtil.class.getClassLoader().getResource(pathBaseClassLoader);
    }

    /**
     * 获得URL
     * @param path 相对给定 class所在的路径
     * @param clazz 指定class
     * @return URL
     */
    public static URL getURL(String path, Class<?> clazz){
        return clazz.getResource(path);
    }

    /**
     * 获得URL，常用于使用绝对路径时的情况
     * @param configFile URL对应的文件对象
     * @return URL
     */
    public static URL getURL(File configFile){
        try {
            return configFile.toURI().toURL();
        } catch (MalformedURLException e) {
            logger.error("Error occured when get URL!", e);
        }
        return null;
    }
}
