/*
 * Copyright 2017-2020 Corey Rowe
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

import com.gbsnowday.server.model.closings.ClosingInfo;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;

import org.springframework.context.ApplicationContext;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Organize weather and closings information into consumable JSON feeds.
 * Runs as a command-line application for easy cron scheduling.
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.gbsnowday"})
public class ServerApplication {

    private static final String JSON_PATH = "/opt/snowdayserver/";

    public static void main(String[] args) {

		SpringApplication.run(ServerApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

            FileUtils.copyURLToFile(
                new URL(
                    "https://s3.amazonaws.com/grayfilestore-wjrt/closingsData/closings_WJRT.json"
                ), new File(
                    JSON_PATH + "wjrt_closings.json")
            );

		    String closingsJson = new String(
		            Files.readAllBytes(
		                    Paths.get(JSON_PATH + "wjrt_closings.json")),
                    StandardCharsets.UTF_8
            );

            // The WJRT JSON is wrapped in a top-level one-item array.
            // Retrieve the contents of this item.
            JsonParser parser = new JsonParser();
            JsonElement jsonTree = parser.parse(closingsJson);
            JsonArray jsonArray = jsonTree.getAsJsonArray();

            Gson gson = new Gson();
            ClosingInfo closingInfo = gson.fromJson(jsonArray.get(0), ClosingInfo.class);

            String customClosingsJson = new ClosingsParser()
                .buildCustomClosingsJson(closingInfo.getClosingRecord());

            String weatherJson = new WeatherScraper()
                .getWeatherJson(
                    "http://alerts.weather.gov/cap/wwaatmget.php?x=MIZ061&amp;y=0",
                    10000
            );

            Files.write(Paths.get(JSON_PATH + "closings.json"), customClosingsJson.getBytes());
            Files.write(Paths.get(JSON_PATH + "weather.json"), weatherJson.getBytes());
        };
    }
}