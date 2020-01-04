/*
 * The author licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

class LiturgicalCalendar {
  private final int year;
  private final Calendar firstDay, lastDay;
  private final SortedMap<Calendar, List<String>> readings;

  LiturgicalCalendar(int year) {
    this.year = year;
    firstDay = calculateBegin(year);
    lastDay = calculateBegin(year + 1);
    lastDay.add(Calendar.DATE, -1);
    readings = new TreeMap<>();
    // Populate all the days so we don't get gaps when we print the calendar
    for (Calendar d = (Calendar)firstDay.clone(); d.compareTo(lastDay) <= 0; d.add(Calendar.DATE, 1)) {
      readings.put((Calendar)d.clone(), new ArrayList<>());
    }
  }

  Calendar getFirstDay() {
    return firstDay;
  }

  Calendar getLastDay() {
    return lastDay;
  }

  int getYear() {
    return year;
  }

  void addReading(Calendar day, String reading) {
    List<String> readingForDay = readings.get(day);
    readingForDay.add(reading);
  }

  void printReadings(PrintStream out) {
    for (Map.Entry<Calendar, List<String>> entry : readings.entrySet()) {
      out.print(stringDate(entry.getKey()));
      out.print(" ");
      for (String r : entry.getValue()) {
        out.print(r);
        out.print(" ");
      }
      out.println();
    }
  }

  private Calendar calculateBegin(int thisYear) {
    // First find the day of the week of Christmas, then find the preceding Sunday, then subtract 4 weeks
    Calendar startOfAdvent = new GregorianCalendar(thisYear - 1, Calendar.DECEMBER, 25);

    switch (startOfAdvent.get(Calendar.DAY_OF_WEEK)) {
      case Calendar.SUNDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.SATURDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.FRIDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.THURSDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.WEDNESDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.TUESDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.MONDAY: startOfAdvent.add(Calendar.DATE, -1);
    }
    startOfAdvent.add(Calendar.DATE, -21);
    return startOfAdvent;
  }

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
