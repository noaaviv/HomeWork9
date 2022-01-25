import jdk.javadoc.internal.doclets.toolkit.util.DocFile;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class Solution {
    private static WebDriver driver;
    private WebDriver chromeDriver;
    private DocFile FileUtils;

    @BeforeClass
    public static void beforeAll() {
        driver = DriverSingleton.getDriverInstance();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    //1
    @Test
    public void Test12_POM() throws InterruptedException {
        driver.get("https://dgotlieb.github.io/WebCalculator");
        System.out.println(driver.findElement(By.id(Constants.SEVEN)).getSize());
        System.out.println(driver.findElement(By.id("six")).isDisplayed());
        WebCalculatorPage.pressFive();
        WebCalculatorPage.pressPlus();
        WebCalculatorPage.pressFive();
        WebCalculatorPage.pressEquals();
        Thread.sleep(1000);
        String expectedResult = "10";
        assertEquals(expectedResult, WebCalculatorPage.getResult());
    }

    //2
    @Test
    public void test03_URL_assert() {
        String wallaURL = "https://www.walla.co.il/";
        assertEquals(chromeDriver.getCurrentUrl(), wallaURL);
    }



    //3
    @Test
    public void test02_test_title() {
        String wallaTitle = "וואלה! NEWS - האתר המוביל בישראל - עדכונים מסביב לשעון";
        chromeDriver.navigate().refresh();
        Assert.assertEquals(chromeDriver.getTitle(), wallaTitle);
    }

    //4
    @Test
    public void test14_extensions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-extensions");
        chromeDriver = new ChromeDriver(chromeOptions);
    }


    //5
    @Test
    public void test02_dragAndDrop() {
        driver.navigate().to("https://dgotlieb.github.io/Actions/");
        WebElement locationElement = driver.findElement(By.id("drag1"));
        WebElement destinationElement = driver.findElement(By.id("div1"));
        File screenShotFile = destinationElement.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShotFile, new File("element-screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test03_doubleClick() {
        WebElement doubleClickElement =
                driver.findElement(By.xpath("//p[@ondblclick='doubleClickFunction()']"));
        Actions doubleClickAction = new Actions(driver);
        doubleClickAction.doubleClick(doubleClickElement);
        doubleClickAction.build().perform();

        String result = driver.findElement(By.id("demo")).getText();
        Assert.assertEquals("You double clicked", result);
    }

    @Test
    public void test04_mosueHover() {
        Actions hoverAction = new Actions(driver);
        WebElement firstHoverElement = driver.findElement(By.id("demo"));
        WebElement secondHoverElement = driver.findElement(By.id("close"));
        hoverAction.moveToElement(firstHoverElement)
                .moveToElement(secondHoverElement)
                .click()
                .build()
                .perform();
    }

    @Test
    public void test05_selectMultiple() {
        List<WebElement> elementsList = driver.findElements(By.name("kind"));
        Actions builder = new Actions(driver);
        builder.clickAndHold(elementsList.get(0)).clickAndHold(elementsList.get(2)).click();
        builder.build().perform();
    }

    @Test
    public void test06_uploadFile() {
        driver.findElement(By.name("pic")).sendKeys("1.txt");
    }

    @Test
    public void test07_scrollToElement() throws InterruptedException {
        WebElement element = driver.findElement(By.id("clickMe"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(4000);
    }

    @Test
    public void test08_scrollToLocation() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("javascript:window.scrollBy(250,350)");
        Thread.sleep(4000); // this is here just to see the move after the scroll
    }

    // 6
    @Test
    public void test8_controllers() {
        chromeDriver.get("https://dgotlieb.github.io/Controllers/");
        List<WebElement> list = chromeDriver.findElements(By.name("group1"));// exercise b
        for (WebElement tempElement : list) {
            if (tempElement.getAttribute("value").equals("Cheese")) {
                tempElement.click();
            }
            System.out.println(tempElement.getAttribute("value"));
        }
        Select selection = new Select(chromeDriver.findElement(By.name("dropdownmenu")));
        selection.selectByValue("Milk");
        for (int i = 0; i < selection.getOptions().size(); i++) {
            System.out.println(selection.getOptions().get(i).getText());
        }
    }

    // 7
    @Test
    public void test10_calculator_dimensions() {
        chromeDriver.get("https://dgotlieb.github.io/WebCalculator/");
        System.out.println(chromeDriver.findElement(By.id("two")).getRect().getHeight());
        System.out.println(chromeDriver.findElement(By.id("six")).getRect().getWidth());
    }

    @AfterClass
    public void afterAll() {
        driver.quit();
    }
}