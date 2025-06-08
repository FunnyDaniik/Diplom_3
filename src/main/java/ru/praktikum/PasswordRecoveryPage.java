package ru.praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage {
    private WebDriver driver;

    private By loginLink = By.xpath("//a[text()='Войти']");

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }
}
