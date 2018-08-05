package cn.diyai.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

public class XmlUtil {
    public static String[] getConfig() throws Exception {

        String configfile = System.getProperty("user.dir") + File.separator + "/config.xml";
        File file = new File(configfile);
        if (!file.exists()) {
            new Throwable("没有找到配置文件");
            return null;
        }

        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        String[] config = new String[4];
        Iterator it = root.elementIterator();

        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element.getName().equals("eid")) {
                config[0] = element.getData().toString();
            }

            if (element.getName().equals("browser")) {
                config[1] = element.getData().toString();
            }

            if (element.getName().equals("location")) {
                config[2] = element.getData().toString();
            }

            if (element.getName().equals("browserPath")) {
                config[3] = element.getData().toString();
            }

        }
        return config;
    }
}
