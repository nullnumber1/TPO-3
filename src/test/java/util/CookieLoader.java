package util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;

public class CookieLoader {

    public static void loadCookiesFromFile(WebDriver driver, String filePath) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            loadCookiesFromString(driver, json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load cookies from file: " + filePath, e);
        }
    }

    public static void loadCookiesFromString(WebDriver driver, String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Cookie.Builder cookieBuilder = new Cookie.Builder(jsonObject.getString("name"), jsonObject.getString("value"))
                        .domain(jsonObject.getString("domain"))
                        .path(jsonObject.getString("path"))
                        .isHttpOnly(jsonObject.getBoolean("httpOnly"))
                        .isSecure(jsonObject.getBoolean("secure"));

                if (jsonObject.has("expirationDate")) {
                    cookieBuilder.expiresOn(Date.from(Instant.ofEpochSecond(jsonObject.getLong("expirationDate"))));
                }

                Cookie cookie = cookieBuilder.build();
                driver.manage().addCookie(cookie);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load cookies from json: " + json, e);
        }
    }
}
