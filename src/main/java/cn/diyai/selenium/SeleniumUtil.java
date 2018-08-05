package cn.diyai.selenium;

import cn.diyai.util.TimeUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.awt.Rectangle;

public class SeleniumUtil {

    //chrome
    public static final String DRIVERTYPE_CHROME = "1";
    //firefox
    public static final String DRIVERTYPE_FIREFOX = "2";

    /**
     * 根据路径获取执行类型(chrome/firefox)的webdriver
     * @param Type
     * @param firefoxPath
     * @return
     */
    public static WebDriver getDriver(String Type, String firefoxPath) {
        WebDriver driver = null;
        if (Type.equals(DRIVERTYPE_CHROME)) {
            System.setProperty("webdriver.chrome.driver", "/Users/diyai/Selenium/chromedriver");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            driver = new ChromeDriver(capabilities);

        } else if (Type.equals(DRIVERTYPE_FIREFOX)) {
            driver = getPhantomjsDriver(firefoxPath);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        }
        return driver;
    }

    /**
     * 获取执行类型(chrome/firefox)的webdriver
     * @param Type
     * @return
     */
    public static WebDriver getDriver(String Type) {
        WebDriver driver = null;
        if (Type.equals(DRIVERTYPE_CHROME)) {

            System.setProperty("webdriver.chrome.driver", "/Users/diyai/Selenium/chromedriver");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            driver = new ChromeDriver(capabilities);

        } else if (Type.equals(DRIVERTYPE_FIREFOX)) {
            driver = new FirefoxDriver();
        }
        return driver;
    }

    /**
     * 获取Phantomjs driver
     * @param driverpath
     * @return
     */
    public static WebDriver getPhantomjsDriver(String driverpath) {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        // caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, value);
        // System.setProperty("phantomjs.binary.path",file+"/phantomjs.exe");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverpath);
        // caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/Users/diyai/github/phantomjs-1.4.1/bin/phantomjs");

        return new PhantomJSDriver(caps);

    }

    /**
     * 在指定时间内根据xpath获取WebElement
     * @param driver
     * @param xpath
     * @param sleepTime
     * @return
     */
    public static WebElement getElement(WebDriver driver, String xpath, int sleepTime) {

        int flag = sleepTime;// 等待20秒
        WebElement element = null;
        while (flag-- > 0) {
            try {
                element = driver.findElement(By.xpath(xpath));

            } catch (NoSuchElementException ex) {
                // ex.printStackTrace();
            }

            if (element != null) {
                try {
                    if (element.isDisplayed()) {
                        return element;
                    }
                } catch (Exception ex) {
                    return null;

                }
            }
            // test
			/*
			 * if(xpath.equals("/html/body/header/p")){ Logger.info(
			 * "waiting recharge result:"+flag); }
			 */

            sleep(1);
        }

        return null;
    }

    /**
     * 设置窗口大小
     * @param driver
     * @param width
     * @param height
     */
    public static void setWindowSize(WebDriver driver, int width, int height) {
        Dimension d = new Dimension(width, height);
        driver.manage().window().setSize(d);
    }

    /**
     * 设置窗口所在的位置
     * @param driver
     * @param positionX
     * @param positionY
     */
    public static void setPosition(WebDriver driver, int positionX, int positionY) {
        Point p = new Point(positionX, positionY);
        driver.manage().window().setPosition(p);
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void closeDriver(WebDriver driver) {

        try {
            driver.quit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     *  页面元素截图
     * @param element
     * @return
     * @throws Exception
     */
    public static File captureElement(WebElement element) throws Exception {
        WrapsDriver wrapsDriver = (WrapsDriver) element;
        // 截图整个页面
        File screen = ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(screen);
        // 获得元素的高度和宽度
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        // 创建一个矩形使用上面的高度，和宽度
        Rectangle rect = new Rectangle(width, height);
        // 得到元素的坐标
        Point p = element.getLocation();
        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
        // 存为png格式
        ImageIO.write(dest, "png", screen);
        return screen;
    }

    /**
     * 截图整个页面
     * @param driver
     * @param savePath
     * @param orderID
     */
    public static void snapshot(WebDriver driver, String savePath, String orderID) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String today = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
            String dir = savePath + File.separator + today;
            File dayFile = new File(dir);
            if (!dayFile.exists()) {
                dayFile.mkdirs();
            }
            String filepath = dir + File.separator + orderID + ".png";
            FileUtils.copyFile(scrFile, new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }


    /**
     * 是否有对话框
     * @param driver
     * @return
     */
    public static Alert hasAlert(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            String text = alert.getText();
            return alert;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 关闭对话框
     * @param driver
     * @return
     */
    public static boolean closeAlert(WebDriver driver) {
        try {

            Alert alert = driver.switchTo().alert();
            if (alert != null) {
                String text = alert.getText();
                alert.dismiss();
                driver.switchTo().defaultContent();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
    }

    /**
     * 关闭当前窗口
     * @param driver
     */
    public static void stopLoadPage(WebDriver driver) {
        runJS(driver, "window.stop()");

    }

    /**
     * 执行js语句
     */
    public static void runJS(WebDriver driver, String js) {
        ((JavascriptExecutor) driver).executeScript(js);

    }
}
