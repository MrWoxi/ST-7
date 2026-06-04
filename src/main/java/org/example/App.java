package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {
        String projectDir = System.getProperty("user.dir");

        String chromeDriver = Paths.get(projectDir, "chromedriver-win64", "chromedriver.exe").toString();
        String chromeBin    = Paths.get(projectDir, "chrome-win64",       "chrome.exe").toString();

        System.setProperty("webdriver.chrome.driver", chromeDriver);

        ChromeOptions options = new ChromeOptions();
        options.setBinary(chromeBin);

        WebDriver webDriver = new ChromeDriver(options);
        try {
            // задание 1
            webDriver.get("https://www.calculator.net/password-generator.html");

            WebDriverWait wait = new WebDriverWait(webDriver, 15);
            WebElement result = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.id("resultid")));
            wait.until(d -> !result.getText().trim().isEmpty());

            String password;
            try {
                password = result.findElement(By.tagName("b")).getText().trim();
            } catch (Exception e) {
                password = result.getText().trim();
            }

            System.out.println("=== Задание №1: сгенерированный пароль ===");
            System.out.println(password);
            System.out.println();

            Task2.run(webDriver);
            Task3.run(webDriver);

        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }
    }
}
