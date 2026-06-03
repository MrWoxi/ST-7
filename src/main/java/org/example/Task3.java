package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Задание №3: прогноз погоды на сутки для Нижнего Новгорода (56°N 44°E)
 * через open-meteo.com. Результат выводится таблицей и сохраняется в result/forecast.txt.
 */
public class Task3 {

    private static final String URL =
            "https://api.open-meteo.com/v1/forecast"
            + "?latitude=56&longitude=44"
            + "&hourly=temperature_2m,rain"
            + "&current=cloud_cover"
            + "&timezone=Europe%2FMoscow"
            + "&forecast_days=1"
            + "&wind_speed_unit=ms";

    private static final Path OUTPUT = Paths.get("result", "forecast.txt");

    public static void run(WebDriver driver) throws Exception {
        driver.get(URL);

        WebElement pre = driver.findElement(By.tagName("pre"));
        String json = pre.getText();

        JSONParser parser = new JSONParser();
        JSONObject root = (JSONObject) parser.parse(json);
        JSONObject hourly = (JSONObject) root.get("hourly");

        JSONArray time = (JSONArray) hourly.get("time");
        JSONArray temp = (JSONArray) hourly.get("temperature_2m");
        JSONArray rain = (JSONArray) hourly.get("rain");

        StringBuilder sb = new StringBuilder();
        sb.append("Прогноз погоды: Нижний Новгород (56N, 44E)\n\n");
        sb.append(String.format("| %-3s | %-16s | %-13s | %-12s |%n",
                "№", "Дата/время", "Температура °C", "Осадки мм"));
        sb.append(String.format("| %-3s | %-16s | %-13s | %-12s |%n",
                "---", "----------------", "-------------", "------------"));

        for (int i = 0; i < time.size(); i++) {
            sb.append(String.format("| %-3d | %-16s | %-13s | %-12s |%n",
                    i + 1, time.get(i), temp.get(i), rain.get(i)));
        }

        String table = sb.toString();
        System.out.println("=== Задание №3: прогноз погоды ===");
        System.out.print(table);

        save(table);
        System.out.println("\nСохранено: " + OUTPUT.toAbsolutePath());
    }

    private static void save(String content) throws IOException {
        Files.createDirectories(OUTPUT.getParent());
        try (PrintWriter pw = new PrintWriter(
                Files.newBufferedWriter(OUTPUT, StandardCharsets.UTF_8))) {
            pw.print(content);
        }
    }
}
