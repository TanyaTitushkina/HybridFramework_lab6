package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Helpers {
    protected static Logger log = LogManager.getLogger("Helpers");


    public static void waitForVisibleElement(WebDriver driver, WebElement element, int seconds){
        (new WebDriverWait(driver, seconds)).until(ExpectedConditions.visibilityOf(element));
    }

    public static void sleep(int ms){
        try {
            log.info("Sleep for " + ms + " ms");
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void check2StringIfEquals(String value1, String value2){
        log.info("Check 2 string if equals:\n1st: '" + value1 + "'\n2nd: '" + value2 + "'");
        Assert.assertEquals(value1, value2);
    }

    public static void check2StringIfContains(String value1, String value2){
        //log.info("Check 2 string if contains:\n1st: '" + value1 + "'\n2nd: '" + value2 + "'");
        System.out.println("Check 2 string if contains:\n1st: '" + value1 + "'\n2nd: '" + value2 + "'");
        Assert.assertTrue(value1.contains(value2));
    }
}
