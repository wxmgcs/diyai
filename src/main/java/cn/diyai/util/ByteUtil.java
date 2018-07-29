package cn.diyai.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(ByteUtil.class);

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);// 每个字节8，转为16进制标志，2个16进制位
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static final String bytesToHexStringFlag(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);// 每个字节8，转为16进制标志，2个16进制位
            sb.append(sTemp.toUpperCase());
        }
        return "0x"+sb.toString();
    }

    public static int bytesToInt(byte[] bytes)
    {
        int value = 0;
        for (int i = 0; i < bytes.length; i++)
        {
            //value += (int)bytes[i] << (8*i);
            value |= (bytes[i] & 0xFF) << (8*i);
        }
        return value;
    }


    public static int byte2Int(byte num){
        int value = 0;
        value |= (num & 0xFF) << 0;
        return value;

    }

    //高位在前，低位在后
    public static byte[] int2bytes(int num){
        byte[] result = new byte[4];
        result[0] = (byte)((num >>> 24) & 0xff);
        result[1] = (byte)((num >>> 16)& 0xff );
        result[2] = (byte)((num >>> 8) & 0xff );
        result[3] = (byte)((num >>> 0) & 0xff );
        return result;
    }

    public static byte int2byte(int num){
        return (byte)((num >>> 0) & 0xff );
    }

    public static byte[] int2Singlebytes(int num){
        return new byte[]{(byte)((num >>> 0) & 0xff ) };
    }

    /**
     * 去除字节为0的占位
     * @param bytes
     * @return
     */
    public static String bytes2String(byte[] bytes){
        String ret = new String(bytes).replace("\u0000","").replace("\u000F","");
        return ret.equals("")? null:ret;
        }

    /**
     * 将16进制字符串转换成字节数组
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
