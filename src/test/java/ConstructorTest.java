import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.praktikum.MainPage;
import ru.praktikum.utils.WebDriverFactory;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ConstructorTest {
    private WebDriver driver;
    private MainPage mainPage;
    private final String browser;

    public ConstructorTest(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Object[][] getBrowser() {
        return new Object[][] {
                {"chrome"},
                {"yandex"}
        };
    }

    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver(browser);
        mainPage = new MainPage(driver);
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    public void testBunsSection() {
        mainPage.clickSaucesSection();
        mainPage.clickBunsSection();
        assertEquals("Булки", mainPage.getSelectedSectionText());
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void testSaucesSection() {
        mainPage.clickSaucesSection();
        assertEquals("Соусы", mainPage.getSelectedSectionText());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void testFillingsSection() {
        mainPage.clickFillingsSection();
        assertEquals("Начинки", mainPage.getSelectedSectionText());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
