package cn.diyai.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    private static final String propertiesFile = "global.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
	private static final Properties global = loadProperties(propertiesFile);

	public static String getproperties(String key, String defaultValue)
	{
        LOGGER.info(key);
		String ret = global.getProperty(key);
		return  StringUtils.isBlank(ret) ? defaultValue :ret;
	}

	private static Properties loadProperties(String resources) {
		// 使用InputStream得到一个资源文件
		InputStream inputstream = PropertiesUtils.class.getClassLoader().getResourceAsStream(resources);
		// new 一个Properties
		Properties properties = new Properties();
		try {
			// 加载配置文件
			properties.load(inputstream);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				inputstream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

    public static void reloadProperties(){
        loadProperties(propertiesFile);
    }
}