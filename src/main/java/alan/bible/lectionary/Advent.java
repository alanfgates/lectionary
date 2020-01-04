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

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class Advent extends Period {
  private Calendar startOfAdvent;

  public Advent(LiturgicalCalendar calendar) {
    super(calendar, 4);
  }

  @Override
  protected Calendar beginDate() {
    if (startOfAdvent == null) startOfAdvent = calendar.getFirstDay();
    return (Calendar)startOfAdvent.clone();
  }

  @Override
  protected Calendar endDate() {
    return new GregorianCalendar(calendar.getYear() - 1, Calendar.DECEMBER, 24);
  }

  @Override
  protected void populateHolidays() {
    Calendar firstSunday = (Calendar)calendar.getFirstDay().clone();
    holidays.put(firstSunday, new Holiday("First Sunday in Advent", firstSunday, calendar, "John 1:1-18"));
    Calendar secondSunday = (Calendar)firstSunday.clone();
    secondSunday.add(Calendar.DATE, 7);
    holidays.put(secondSunday, new Holiday("Second Sunday in Advent", secondSunday, calendar, "Luke 1:1-25"));
    Calendar thirdSunday = (Calendar)firstSunday.clone();
    thirdSunday.add(Calendar.DATE, 14);
    holidays.put(thirdSunday, new Holiday("Third Sunday in Advent", thirdSunday, calendar, "Luke 1:26-56"));
    Calendar fourthSunday = (Calendar)firstSunday.clone();
    fourthSunday.add(Calendar.DATE, 21);
    holidays.put(fourthSunday, new Holiday("Fourth Sunday in Advent", fourthSunday, calendar, "Luke 1:57-80"));

  }

  @Override
  protected void populateSections() {
    switch (calendar.getYear() % 4) {
      case 0:
        sections.add(new Section(Collections.singletonList(new Book("Isaiah", 53, 40))));
        break;

      case 1:
        sections.add(new Section(Collections.singletonList(new Book("Revelation", 11))));
        break;

      case 2:
        sections.add(new Section(Collections.singletonList(new Book("Isaiah", 66, 54))));
        break;

      case 3:
        sections.add(new Section(Collections.singletonList(new Book("Revelation", 22, 12))));
        break;
    }
  }
}
