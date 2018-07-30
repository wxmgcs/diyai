package cn.diyai.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

public class MD5Util {

    public static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);

    public static byte[] md5(byte[] src){
        MessageDigest md = null;
        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        }
        md.reset();
        md.update(src);
        //hash
        return md.digest();
    }

    public static byte[] encode(byte[] src){
        MessageDigest md = null;
        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            LOGGER.error("Exception: " + e);
        }
        md.reset();
        md.update(src);
        //hash
        return md.digest();
    }
}
