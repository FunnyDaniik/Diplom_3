import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.devtools.v133.network.model.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.BaseTest;
import ru.praktikum.*;
import ru.praktikum.utils.UserGenerator;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class LoginTest extends BaseTest {
    private MainPage mainPage;
    private LoginPage loginPage;
    private PasswordRecoveryPage passwordRecoveryPage;
    private String email;
    private String password;
    private String accessToken;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);

        // Генерация данных пользователя
        String name = UserGenerator.generateName();
        email = UserGenerator.generateEmail();
        password = UserGenerator.generatePassword(8);

        // Регистрация пользователя через API
        accessToken = given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}",
                        email, password, name))
                .post(Constants.API_REGISTER_URL)
                .then()
                .extract()
                .path("accessToken");
    }

    @After
    @Override
    public void tearDown() {
        // Удаление пользователя через API
        if (accessToken != null) {
            given()
                    .header("Authorization", accessToken)
                    .delete(Constants.API_USER_URL);
        }
        super.tearDown();
    }


    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    public void testLoginViaMainPageButton() {
        mainPage.clickLoginButton();
        loginPage.login(email, password);
        wait.until(ExpectedConditions.urlToBe(Constants.BASE_URL));
        assertTrue("Кнопка 'Оформить заказ' не отображается",
                mainPage.isOrderButtonDisplayed(wait));
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    public void testLoginViaPersonalAccountButton() {
        mainPage.clickPersonalAccountButton();
        loginPage.login(email, password);
        wait.until(ExpectedConditions.urlToBe(Constants.BASE_URL));
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
        wait.until(ExpectedConditions.urlToBe(Constants.BASE_URL));
        assertTrue("Кнопка 'Оформить заказ' не отображается",
                mainPage.isOrderButtonDisplayed(wait));
    }
}
