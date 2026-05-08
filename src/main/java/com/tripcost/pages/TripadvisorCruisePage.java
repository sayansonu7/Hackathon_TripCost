package com.tripcost.pages;

import com.tripcost.models.CruiseInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TripadvisorCruisePage {

    private WebDriver driver;

    private final By shipTitle = By.xpath("//h1");
    private final By body = By.tagName("body");

    public TripadvisorCruisePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String url) {
        driver.get(url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CruiseInfo fetchCruiseInfo() {
        String shipName = driver.findElement(shipTitle).getText();
        String pageText = driver.findElement(body).getText();

        String passengers = extractFirst(pageText, "Passengers:\\s*(\\d+)");
        String crew = extractFirst(pageText, "Crew:\\s*(\\d+)");
        String launched = extractFirst(pageText, "Launched:\\s*(\\d{4})");
        List<String> languages = extractLanguages(pageText);

        return new CruiseInfo(shipName, passengers, crew, launched, languages);
    }

    private String extractFirst(String text, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : "N/A";
    }

    private List<String> extractLanguages(String text) {
        String[] lines = text.split("\\r?\\n");
        Set<String> languages = new LinkedHashSet<>();
        boolean capture = false;

        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty()) continue;

            if ("Language".equalsIgnoreCase(line)) {
                capture = true;
                continue;
            }

            if (capture) {
                if (line.matches(".*\\(\\d+\\)$")) {
                    languages.add(line);
                    continue;
                }

                if (line.equalsIgnoreCase("Reviews")
                        || line.equalsIgnoreCase("Traveler rating")
                        || line.equalsIgnoreCase("Upcoming itineraries")
                        || line.equalsIgnoreCase("Read more reviews on Cruise Critic")) {
                    break;
                }

                if (!languages.isEmpty()) {
                    break;
                }
            }
        }

        return new ArrayList<>(languages);
    }
}