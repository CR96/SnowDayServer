/*
 * Copyright 2017 Corey Rowe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.gbsnowday.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Organize weather and closings information into consumable JSON feeds.
 * Runs as a command-line application for easy cron scheduling.
 */
public class Main {

    private static final String JSON_PATH = "/var/www/snowday/html/api/";

    public static void main(String[] args) throws IOException {

        String closingsJson = new ClosingsScraper()
            .getClosingsJson(
                "http://abc12.com/weather/closings",
                10000
        );

        String weatherJson = new WeatherScraper()
            .getWeatherJson(
                "http://alerts.weather.gov/cap/wwaatmget.php?x=MIZ061&amp;y=0",
                10000
        );

        Files.write(Paths.get(JSON_PATH + "closings.json"), closingsJson.getBytes());
        Files.write(Paths.get(JSON_PATH + "weather.json"), weatherJson.getBytes());
    }
}
