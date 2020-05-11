/*
 * The author licenses this file
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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Deque;
import java.util.GregorianCalendar;
import java.util.List;

class HolyWeek extends Period {
  private EasterPentecost easter;

  HolyWeek(LiturgicalCalendar calendar, EasterPentecost easter) {
    super(calendar, 1);
    this.easter = easter;
  }

  @Override
  protected Calendar beginDate() {
    Calendar begin = easter.beginDate();
    begin.add(Calendar.DATE, -7);
    return begin;
  }

  @Override
  protected Calendar endDate() {
    Calendar end = easter.beginDate();
    end.add(Calendar.DATE, -1);
    return end;
  }

  @Override
  protected void populateHolidays() {
    // Dates fixed to the solar calendar
    Calendar annunciation = new GregorianCalendar(calendar.getYear(), Calendar.MARCH, 25);
    if (beginDate().compareTo(annunciation) >= 0 && endDate().compareTo(annunciation) <= 0) {
      holidays.put(annunciation, new Holiday("Annunciation", annunciation, calendar, "Luke 1:26-38"));
    }
    Calendar palmSunday = beginDate();
    holidays.put(palmSunday, new Holiday("Palm Sunday", palmSunday, calendar,
        "Psalm 24, Zechariah 9, Matthew 21:1-9, John 12:12-19",
        "Psalm 24, Zechariah 9, Mark 11:1-10, John 12:12-19",
        "Psalm 24, Zechariah 9, Luke 19:28-48, John 12:12-19"));

    Calendar holyMonday = (Calendar)palmSunday.clone();
    holyMonday.add(Calendar.DATE, 1);
    holidays.put(holyMonday, new Holiday("Holy Monday", holyMonday, calendar, "Matthew 26:1-16", "John 12:20-13:38"));

    Calendar holyTuesday = (Calendar)palmSunday.clone();
    holyTuesday.add(Calendar.DATE, 2);
    holidays.put(holyTuesday, new Holiday("Holy Tuesday", holyTuesday, calendar, "Mark 14:1-11", "John 14", "John 16"));

    Calendar holyWednesday = (Calendar)palmSunday.clone();
    holyWednesday.add(Calendar.DATE, 3);
    holidays.put(holyWednesday, new Holiday("Holy Wednesday", holyWednesday, calendar, "Luke 22:1-6", "John 15", "John 17"));

    Calendar maundyThursday = (Calendar)palmSunday.clone();
    maundyThursday.add(Calendar.DATE, 4);
    holidays.put(maundyThursday, new Holiday("Maundy Thursday", maundyThursday, calendar,
        "Matthew 26:17-75", "Mark 14:12-72", "Luke 22:7-62", "John 18"));

    Calendar goodFriday = (Calendar)palmSunday.clone();
    goodFriday.add(Calendar.DATE, 5);
    holidays.put(goodFriday, new Holiday("Good Friday", goodFriday, calendar,
        "Matthew 27:1-61", "Mark 15:1-41", "Luke 22:63-23:56", "John 19"));

    Calendar holySaturday = (Calendar)palmSunday.clone();
    holySaturday.add(Calendar.DATE, 6);
    holidays.put(holySaturday, new Holiday("Holy Saturday", holySaturday, calendar, "Matthew 27:62-66", "Mark 15:42-47"));
  }

  @Override
  protected void populateSections() {
    // Nothing here but holidays
  }
}
