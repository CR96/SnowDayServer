/*
 * Copyright 2014-2017 Corey Rowe
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

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class WeatherScraper {

    // A single weather warning
    @SuppressWarnings("unused") //Serialized to json using Gson
    private class Warning {
        private String title;
        private String expireTime;
        private String readableTime;
        private String summary;
        private String link;
    }

    // Parsable format of warning expiration times as present in RSS feed
    private final SimpleDateFormat sdfInput = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm", Locale.US);

    // Readable format of warning expiration times as seen by users
    private final SimpleDateFormat sdfOutput = new SimpleDateFormat(
            "MMMM dd 'at' h:mm a", Locale.US);

    @SuppressWarnings("SameParameterValue") // Setting all "breakable" variables in main class so they're in one place
    String getWeatherJson(String url, int timeout) {
        ArrayList<Warning> warnings = new ArrayList<>();

        try {

            Document weather = Jsoup.connect(
                url)
                .timeout(timeout)
                .get();

            // Various elements present in the RSS feed
            Elements title = weather.select("title");
            Elements summary = weather.select("summary");
            Elements expiretime = weather.select("cap|expires");
            Elements link = weather.select("link");

            if (title != null) {
                for (Element aTitle : title) {
                    // The warning titles in the RSS feed contain the text "issued by NWS at ...".
                    // This removes that portion of the warning title if present for a cleaner
                    // appearance in the displayed list. The expiration time is shown separately.
                    // Example: "Winter Storm Warning" vs. "Winter Storm Warning issued by NWS at ..."
                    int stringend = aTitle.text().indexOf("issued");
                    Warning warning = new Warning();
                    if (stringend != -1) {
                        warning.title = aTitle.text().substring(0, stringend);
                    } else {
                        warning.title = aTitle.text();
                    }
                    warnings.add(warning);
                }
            }

            // The first Title element in the feed is the feed title, not a warning title.
            // The text of this element is displayed as a header above any warnings present.
            // The clients compensate for this when constructing the list of warnings.
            // Only subsequent array elements contain warning information,
            // hence the use of "i + 1" in the following statements.

            if (expiretime != null) {
                Date time;
                String readableTime;
                for (int i = 0; i < expiretime.size(); i++) {
                    String expireTime = expiretime.get(i).text();
                    time = sdfInput.parse(expireTime);
                    readableTime = sdfOutput.format(time);

                    warnings.get(i + 1)
                            .expireTime = expireTime;
                    warnings.get(i + 1)
                            .readableTime = "Expires " + readableTime;
                }
            }

            if (summary != null) {
                for (int i = 0; i < summary.size(); i++) {
                    warnings.get(i + 1)
                            .summary = summary.get(i).text() + "...";
                }
            }

            if (link != null) {
                link.remove(0); //Intentionally remove the root-level link tag
                for (int i = 0; i < link.size(); i++) {
                    warnings.get(i + 1)
                            .link = link.get(i).attr("href");
                }
            }

            return new Gson().toJson(warnings);
        } catch (IOException e) {
            // Connectivity issues
            return "ioexception";
        } catch (NullPointerException | IndexOutOfBoundsException | ParseException e) {
            // RSS layout was not recognized.
            return "nullpointerexception";
        }
    }
}
