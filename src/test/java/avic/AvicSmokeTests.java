package avic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class AvicSmokeTests {
    private WebDriver webDriver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testSetUp() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();

    }
    @Test(priority = 1)
    public void checkThatUrlContainsSearchWords() {
        webDriver.get("https://avic.ua/ua");
        webDriver.findElement(By.xpath("//input[@id = 'input_search']")).sendKeys("Samsung");
        webDriver.findElement(By.xpath("//button[@class = 'button-reset search-btn']")).click();
        assertTrue(webDriver.getCurrentUrl().contains("query=Samsung"));

    }

    @Test(priority = 2)
    public void openAsusComputerCaseListFromSideBar() {
        webDriver.get("https://avic.ua/ua");
        Actions action = new Actions(webDriver);
        WebElement webElement = webDriver.findElement(By.xpath("//span[@class='sidebar-item-title']//*[contains(text(),'Комп')]"));
        action.moveToElement(webElement).pause(1000)
                .moveToElement(webDriver.findElement(By.xpath("//*[contains(@class,'menu-lvl ')]//*[contains(@href,'gotovyie-pk') and contains(@class,'sidebar-item')]")))
                .pause(1000)
                .moveToElement(webDriver.findElement(By.xpath("//a[contains(@href,'gotovyie-pk/proizvoditel--asus')]//div[@class='third-level-img']"))).click().build().perform();
        assertEquals(webDriver.findElement(By.xpath("//div[@class='page-title page-title-category']")).getText(),"Системні блоки Asus");
    }

    @Test(priority = 3)
    public void checkReportAvailabilityButton() {
        webDriver.get("https://avic.ua/ua/gotovyie-pk/proizvoditel--asus");
        webDriver.findElement(By.xpath("//div[@class='prod-cart height']//*[contains(@class,'orange')]")).click();
        webDriver.findElement(By.xpath("//div[contains(@class,'availableProductNotification')]//input[@class='validate']")).sendKeys("test@test.com");
        webDriver.findElement(By.xpath("//*[@id='js_availableProductNotification']//button[@type='submit']")).click();
        assertTrue(webDriver.findElement(By.xpath("//div[@class='ttl js_title']")).isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        webDriver.close();
    }
}
