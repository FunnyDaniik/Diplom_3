import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum.LoginPage;
import ru.praktikum.MainPage;
import ru.praktikum.PasswordRecoveryPage;
import ru.praktikum.RegistrationPage;
import ru.praktikum.utils.UserGenerator;
import ru.praktikum.utils.WebDriverFactory;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LoginTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private PasswordRecoveryPage passwordRecoveryPage;
    private String email;
    private String password;
    private WebDriverWait wait;
    private final String browser;

    public LoginTest(String browser) {
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);

        String name = UserGenerator.generateName();
        email = UserGenerator.generateEmail();
        password = UserGenerator.generatePassword(8);

        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registrationPage.register(name, email, password);
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/login"));
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    public void testLoginViaMainPageButton() {
        mainPage.clickLoginButton();
        loginPage.login(email, password);
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
        assertTrue("Кнопка 'Оформить заказ' не отображается",
                mainPage.isOrderButtonDisplayed(wait));
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    public void testLoginViaPersonalAccountButton() {
        mainPage.clickPersonalAccountButton();
        loginPage.login(email, password);
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
        assertTrue("Кнопка 'Оформить заказ' не отображается",
                mainPage.isOrderButtonDisplayed(wait));
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void testLoginViaRegistrationForm() {
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registrationPage.clickLoginLink();
        loginPage.login(email, password);
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
        assertTrue("Кнопка 'Оформить заказ' не отображается",
                mainPage.isOrderButtonDisplayed(wait));
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void testLoginViaPasswordRecoveryForm() {
        mainPage.clickLoginButton();
        loginPage.clickRecoverPasswordLink();
        passwordRecoveryPage.clickLoginLink();
        loginPage.login(email, password);
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));
        assertTrue("Кнопка 'Оформить заказ' не отображается",
                mainPage.isOrderButtonDisplayed(wait));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
