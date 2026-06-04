package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// задание 2 - ip адрес
public class Task2 {

    public static void run(WebDriver driver) throws Exception {
        driver.get("https://api.ipify.org/?format=json");

        WebElement pre = driver.findElement(By.tagName("pre"));
        String json = pre.getText();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(json);
        String ip = (String) obj.get("ip");

        System.out.println("=== Задание №2: IP-адрес клиента ===");
        System.out.println("Ваш IP: " + ip);
        System.out.println();
    }
}
