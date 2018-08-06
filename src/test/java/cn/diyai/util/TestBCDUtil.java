package cn.diyai.util;

import org.junit.Assert;
import org.junit.Test;

public class TestBCDUtil {

    private static final String[] HEX_CODE = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static final String HEX_STR = "0123456789abcdef";

    @Test
    public void test(){

        byte[] bytes = new byte[]{2, 3, 113, 2, 20, 21, 87, 65, 88, 121};

        Assert.assertEquals("02037102141557415879",BCDUtil.decode(bytes));
    }

    public static String byteArray2HexString(byte[] bytes) {

        StringBuilder builder = new StringBuilder();

        for (byte b : bytes) {
            builder.append(byte2HexString(b));
        }

        return builder.toString();
    }


    public static String byte2HexString(byte b) {

        int n = b;

        if (n < 0) {
            n = 256 + n;
        }

        int d1 = n / 16;
        int d2 = n % 16;

        return HEX_CODE[d1] + HEX_CODE[d2];
    }

    @Test
    public void test2(){
        byte[] bytes =  new byte[10];
        bytes[0] = (byte)0;
        bytes[1]= (byte)0;
        bytes[2] = (byte)2;
        bytes[3] = (byte)47;
        bytes[4]= (byte)179;
        bytes[5] = (byte)39;
        bytes[6] = (byte)209;
        bytes[7]= (byte)56;
        bytes[8] = (byte)198;
        bytes[9] = (byte)77;

        System.out.println(byteArray2HexString(bytes));
    }

}
