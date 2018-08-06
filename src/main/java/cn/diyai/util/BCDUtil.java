package cn.diyai.util;

public class BCDUtil {


    public static int byte2int(byte b) {
        return b & 0xff;
    }


    public static String decode(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        for (byte b : bytes) {
            byte b1 = (byte) (b >> 4);
            byte b2 = (byte) (b & 0x0f);
            builder.append(byte2int(b1));
            builder.append(byte2int(b2));
        }

        return builder.toString();
    }
}
