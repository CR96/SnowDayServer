/*
 * Copyright 2014-2020 Corey Rowe
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

import com.gbsnowday.server.model.closings.ClosingRecord;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ClosingsScraper {

    private static class Closing {
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

    private boolean closedToday(String orgStatus) {
        return (orgStatus.contains("Closed " + ClosingsScraper.WEEKDAY_TODAY)
                || orgStatus.contains("Closed Today"));
    }

    private boolean closedTomorrow(String orgStatus) {
        return (orgStatus.contains("Closed " + ClosingsScraper.WEEKDAY_TOMORROW)
                || orgStatus.contains("Closed Tomorrow"));
    }

    @SuppressWarnings("SameParameterValue") // Setting all "breakable" variables in main class so they're in one place
    String buildCustomClosingsJson(List<ClosingRecord> closingRecord) {

        List<Closing> customClosings = new ArrayList<>();

        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < closingRecord.size(); i++) {
            Closing closing = new Closing();
            closing.name = closingRecord.get(i).getForcedOrganizationName();
            closing.status = closingRecord.get(i).getForcedStatusName();
            closing.closedToday = closedToday(closing.status);
            closing.closedTomorrow = closedTomorrow(closing.status);
            customClosings.add(closing);
        }

        return new Gson().toJson(customClosings);
    }

}