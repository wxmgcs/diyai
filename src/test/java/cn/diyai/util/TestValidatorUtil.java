package cn.diyai.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by diyai on 2018/7/30.
 */
public class TestValidatorUtil {

    @Test
    public void test(){
        Assert.assertEquals(true,ValidatorUtil.isChinese('你'));
        Assert.assertEquals(false,ValidatorUtil.isChinese('y'));
    }

    @Test
    public  void testIsMessyCode(){
        //判断是否是乱码
        Assert.assertEquals(false,ValidatorUtil.isMessyCode("\u4f60\u597d"));

    }
}
