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
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

class ClosingsScraper {

    private class Closing {
        String name;
        String status;
        boolean closedToday;
        boolean closedTomorrow;
    }

    private static final LocalDateTime TODAY = LocalDateTime.now();

    private static final String WEEKDAY_TODAY = TODAY
            .getDayOfWeek()
            .getDisplayName(TextStyle.FULL, Locale.US);

    private static final String WEEKDAY_TOMORROW = TODAY
            .plusDays(1)
            .getDayOfWeek()
            .getDisplayName(TextStyle.FULL, Locale.US);

    private boolean closedToday(String orgStatus, String weekdayToday) {
        return (orgStatus.contains("Closed " + weekdayToday)
                || orgStatus.contains("Closed Today"));
    }

    private boolean closedTomorrow(String orgStatus, String weekdayTomorrow) {
        return (orgStatus.contains("Closed " + weekdayTomorrow)
                || orgStatus.contains("Closed Tomorrow"));
    }

    @SuppressWarnings("SameParameterValue") // Setting all "breakable" variables in main class so they're in one place
    String getClosingsJson(String url, int timeout) {
        ArrayList<Closing> closings = new ArrayList<>();
        Document schools = null;
        try {
            schools = Jsoup.connect(
                    url)
                    .timeout(timeout)
                    .get();

            Element table = schools
                    .select("table")
                    .last();
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) { // Skip header row
                Element row = rows.get(i);

                Closing closing = new Closing();
                closing.name = row.select("td").get(0).text();
                closing.status = row.select("td").get(1).text();
                closing.closedToday = closedToday(closing.status, WEEKDAY_TODAY);
                closing.closedTomorrow = closedTomorrow(closing.status, WEEKDAY_TOMORROW);
                closings.add(closing);
            }

            return new Gson().toJson(closings);

        } catch (IOException e) {
            // Connectivity issues
            return "ioexception";
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            // This shows in place of the table (as plain text)
            // if no schools or institutions are closed.
            if (schools != null
                    && !schools.text().contains("no closings or delays")) {
                // Webpage layout was not recognized.
                return "nullpointerexception";
            }else{
                return "none";
            }
        }
    }

}
