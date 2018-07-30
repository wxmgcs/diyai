package cn.diyai.util;

import junit.framework.Assert;
import org.junit.Test;

public class TestConvertUtil {

    @Test
    public void test(){
        Assert.assertEquals("61",ConvertUtil.toASCII("a"));
        Assert.assertEquals("7e",ConvertUtil.toASCII("~"));
        Assert.assertEquals("20",ConvertUtil.toASCII(" "));


    }
}
