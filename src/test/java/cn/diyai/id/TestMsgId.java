package cn.diyai.id;

import cn.diyai.id.msgid.DefaultMsgIdUtil;
import org.junit.Test;

public class TestMsgId {
    @Test
    public static void testMsgid(){
        System.out.println(new MsgId("0625102947001015017421").toString());
    }

    //将字符串转成8个字节的int
    @org.junit.Test
    public static void test2(){
        byte[] bytes = DefaultMsgIdUtil.msgId2Bytes(new MsgId("0625102947001015017421"));
    }

    public static void main(String[] args) {
        testMsgid();
        String str = new MsgId().toString()+"."+new MsgId().toString();
        System.out.println(str);
        for(String item : str.split("\\.")){
            System.out.println(item);
        }
        test2();
    }

}
