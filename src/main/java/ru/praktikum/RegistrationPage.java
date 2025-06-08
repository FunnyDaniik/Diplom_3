package ru.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By nameInput = By.xpath("//fieldset[1]//input[@name='name']");
    private By emailInput = By.xpath("//fieldset[2]//input[@name='name']");
    private By passwordInput = By.xpath("//input[@name='Пароль']");
    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private By loginLink = By.xpath("//a[text()='Войти']");
    private By passwordError = By.xpath("//p[contains(text(), 'Некорректный пароль')]");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
    }

    public void setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
    }

    public void setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    public void clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    public String getPasswordError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).getText();
    }

    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }
}
