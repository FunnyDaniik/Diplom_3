package ru.praktikum.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver getDriver(String browser) {
        WebDriver driver;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");

        switch (browser.toLowerCase()) {
            case "yandex":
                // Указываем путь к исполняемому файлу Yandex
                String yandexPath = System.getenv("LOCALAPPDATA") +
                        "\\Yandex\\YandexBrowser\\Application\\browser.exe";
                options.setBinary(yandexPath);
                WebDriverManager.chromedriver()
                        .browserVersion("134") // Версия Yandex
                        .setup();
                driver = new ChromeDriver(options);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                break;
        }
        return driver;
    }

    public static WebDriver getDriver() {
        return getDriver("chrome");
    }
}
