import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.praktikum.LoginPage;
import ru.praktikum.MainPage;
import ru.praktikum.RegistrationPage;
import ru.praktikum.utils.UserGenerator;
import ru.praktikum.utils.WebDriverFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class RegistrationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private RegistrationPage registrationPage;
    private LoginPage loginPage;
    private final String browser;

    public RegistrationTest(String browser) {
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
        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);

        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void testSuccessfulRegistration() {
        String name = UserGenerator.generateName();
        String email = UserGenerator.generateEmail();
        String password = UserGenerator.generatePassword(8);

        registrationPage.register(name, email, password);
        assertTrue("Не отображается форма входа",
                loginPage.isLoginButtonDisplayed());
    }

    @Test
    @DisplayName("Ошибка при некорректном пароле (менее 6 символов)")
    public void testShortPasswordError() {
        String name = UserGenerator.generateName();
        String email = UserGenerator.generateEmail();
        String password = UserGenerator.generatePassword(5);

        registrationPage.register(name, email, password);
        assertEquals("Некорректное сообщение об ошибке",
                "Некорректный пароль", registrationPage.getPasswordError());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
