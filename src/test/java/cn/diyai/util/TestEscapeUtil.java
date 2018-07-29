package cn.diyai.util;

import junit.framework.Assert;
import org.junit.Test;

public class TestEscapeUtil {

    @Test
    public void test(){
        String base = "你好";
        String escape = EscapeUtil.escape("你好");
        //TODO
        //Assert.assertEquals(escape,"%u4F60%u597D");
        String s = EscapeUtil.unescape(escape);
        Assert.assertEquals(s,base);
    }
}
