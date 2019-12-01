/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package alan.bible.lectionary;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

abstract class Period {

  protected final int year;

  /**
   * Holidays in this period, must be filled out by the subclass.
   */
  protected List<Holiday> holidays;

  /**
   * Sections in this period, must be filled out by the subclass.
   */
  protected List<Section> sections;

  /**
   * @param year year we are working with, note that this is the year easter happens in, not the
   *             year Christmas happens in
   */
  Period(int year) {
    this.year = year;
    holidays = new ArrayList<>();
    sections = new ArrayList<>();
  }

  /**
   * Find the readings for this Period
   * @param out PrintStream to send information to
   */
  final void determineReadings(PrintStream out) {
    populateHolidays();
    populateSections();

    int daysPassed = 0;
    for (Calendar today = begin(); today.compareTo(end()) <= 0;
         today.add(Calendar.DATE, 1), daysPassed++) {
      StringBuilder output = stringDate(today);
      boolean todayIsAHoliday = false;
      for (Holiday holiday : holidays) {
        if (holiday.readToday(today)) {
          output.append(holiday.getName())
              .append(' ')
              .append(holiday.todaysReading())
              .append(' ');
          todayIsAHoliday = true;
        }
      }
      if (!todayIsAHoliday) {
        for (Section section : sections) {
          int weeksLeft = (length() - daysPassed) / 7;
          if (section.readToday(today, weeksLeft)) {
            output.append(section.todaysReading()).append(' ');
            if (section.doubleDip(today, weeksLeft)) {
              output.append(section.todaysReading()).append(' ');
            }
          }
        }
      }
      if (today.compareTo(end()) == 0) {
        // It's the last day, get any unread-reading handled
        for (Section section : sections) {
          boolean added;
          do {
            StringBuilder tmp = section.todaysReading();
            if (tmp.length() > 0) added = true;
            else added = false;
            output.append(tmp)
              .append(' ');
          } while (added);
        }
      }
      out.println(output.toString());
    }
  }

  /**
   * Get the day the period begins on
   * @return begin day
   */
  abstract protected Calendar begin();

  /**
   * Get the day the period ends on
   * @return end
   */
  abstract protected Calendar end();

  /**
   * Number of days in the period
   * @return days
   */
  abstract protected int length();

  /**
   * Fill out the Holidays list.
   */
  abstract protected void populateHolidays();

  /**
   * Fill out the sections list
   */
  abstract protected void populateSections();

  private StringBuilder stringDate(Calendar cal) {
    StringBuilder buf = new StringBuilder();
    if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
      buf.append(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
          .append("\n");
    }
    buf.append(cal.get(Calendar.DAY_OF_MONTH))
        .append(", ")
        .append(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()))
        .append(' ');
    return buf;
  }
}
