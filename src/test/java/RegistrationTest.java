import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.BaseTest;
import ru.praktikum.*;
import ru.praktikum.utils.UserGenerator;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationTest extends BaseTest {
    private MainPage mainPage;
    private RegistrationPage registrationPage;
    private LoginPage loginPage;
    private String accessToken;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        mainPage = new MainPage(driver);
        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);

        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
    }

    @After
    @Override
    public void tearDown() {
        // Удаление пользователя через API, если он был создан
        if (accessToken != null) {
            given()
                    .header("Authorization", accessToken)
                    .delete(Constants.API_USER_URL);
        }
        super.tearDown();
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
}
