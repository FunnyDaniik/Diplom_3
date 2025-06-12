package ru.praktikum;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;

    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private By bunsSection = By.xpath("//span[text()='Булки']/..");
    private By saucesSection = By.xpath("//span[text()='Соусы']/..");
    private By fillingsSection = By.xpath("//span[text()='Начинки']/..");
    private By selectedSection = By.xpath("//div[contains(@class, 'tab_tab_type_current')]");
    private By orderButton = By.xpath("//button[text()='Оформить заказ']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Клик по кнопке 'Личный кабинет'")
    public void clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Клик по разделу 'Булки'")
    public void clickBunsSection() {
        driver.findElement(bunsSection).click();
    }

    @Step("Клик по разделу 'Соусы'")
    public void clickSaucesSection() {
        driver.findElement(saucesSection).click();
    }

    @Step("Клик по разделу 'Начинки'")
    public void clickFillingsSection() {
        driver.findElement(fillingsSection).click();
    }

    @Step("Получение текста активного раздела")
    public String getSelectedSectionText() {
        return driver.findElement(selectedSection).getText();
    }

    @Step("Проверка отображения кнопки 'Оформить заказ'")
    public boolean isOrderButtonDisplayed(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
