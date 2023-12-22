package org.JavaHW.framework.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.JavaHW.framework.library.PageFactoryCustom;
import org.JavaHW.framework.library.TestFunctions;
import org.JavaHW.framework.tools.LocalStorageJS;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public abstract class TestRunner {

    private static final String BASE_URL = "https://www.greencity.social/";
    private static final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private static final Long ONE_SECOND_DELAY = 1000L;

    protected static TestFunctions testFunctions;
    protected static PageFactoryCustom pageFactoryCustom;
    protected static WebDriver driver;
    protected static LocalStorageJS localStorageJS;


    protected void presentationSleep() {
        presentationSleep(5);
    }

    protected void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1264, 798));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS));
        localStorageJS = new LocalStorageJS(driver);
        System.out.println("@BeforeAll executed");

    }
    @BeforeEach
    public void setupThis() {
        driver.get(BASE_URL);
        pageFactoryCustom = new org.JavaHW.framework.library.PageFactoryCustom(driver);
        testFunctions = new TestFunctions(driver, pageFactoryCustom);

        System.out.println("\t@BeforeEach executed");
    }

    @AfterEach
    public void tearThis() {
        driver.manage().deleteAllCookies();
        localStorageJS.clearLocalStorage();
        localStorageJS.removeItemFromLocalStorage("accessToken");
        localStorageJS.removeItemFromLocalStorage("refreshToken");
        presentationSleep(4);
        driver.navigate().refresh();
        System.out.println("\t@AfterEach executed");}

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit(); // close()
        }
        System.out.println("@AfterAll executed");
    }

}
