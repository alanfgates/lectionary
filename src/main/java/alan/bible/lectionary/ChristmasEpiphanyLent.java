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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

class ChristmasEpiphanyLent extends Period {
  private HolyWeek holyWeek;

  ChristmasEpiphanyLent(LiturgicalCalendar calendar, HolyWeek holyWeek) {
    super(calendar);
    this.holyWeek = holyWeek;
  }

  @Override
  protected Calendar beginDate() {
    return new GregorianCalendar(calendar.getYear() - 1, Calendar.DECEMBER, 25);
  }

  @Override
  protected Calendar endDate() {
    Calendar end = (Calendar)holyWeek.beginDate().clone();
    end.add(Calendar.DATE, -1);
    return end;
  }

  @Override
  protected void populateHolidays() {
    // Dates fixed to the solar calendar
    holidays.put(beginDate(), new Holiday("Christmas", beginDate(), calendar, "Matthew 1:18-25, Luke 2:1-20"));
    Calendar holyInnocents = new GregorianCalendar(calendar.getYear() - 1, Calendar.DECEMBER, 28);
    holidays.put(holyInnocents, new Holiday("Holy Innocents", holyInnocents, calendar, "Matthew 2:13-23"));
    Calendar epiphany = new GregorianCalendar(calendar.getYear(), Calendar.JANUARY, 6);
    holidays.put(epiphany, new Holiday("Epiphany", epiphany, calendar, "Matthew 2:1-12"));
    Calendar presentation = new GregorianCalendar(calendar.getYear(), Calendar.FEBRUARY, 2);
    holidays.put(presentation, new Holiday("Presentation", presentation, calendar, "Luke 2:21-25"));
    Calendar annunciation = new GregorianCalendar(calendar.getYear(), Calendar.MARCH, 25);
    if (endDate().compareTo(annunciation) < 0) holidays.put(annunciation, new Holiday("Annunciation", annunciation, calendar, "Luke 1:26-38"));

    Calendar ashWednesday = (Calendar)holyWeek.beginDate().clone();
    ashWednesday.add(Calendar.DATE, -39);
    holidays.put(ashWednesday, new Holiday("Ash Wednesday", ashWednesday, calendar,
        "Matthew 3:13-4:11", "Mark 1:9-13", "Luke 3:21,22, 4:1-13"));

  }

  @Override
  protected void populateSections() {
    // Psalms
    sections.add(new Section(Collections.singletonList(new PsalmsPartOne())));

    List<Book> allBooks = interleave(getNtBooks(), getOtBooks());
    sections.add(new Section(allBooks));
  }

  @Override
  protected void calculateNumWeeks() {
    numWeeks = endDate().get(Calendar.WEEK_OF_YEAR);
  }

  private List<Book> getOtBooks() {
    List <Book> books = new ArrayList<>();
    switch (calendar.getYear() % 8) {
    case 0:
      books.add(new Book("Genesis", 25));
      break;

    case 1:
      books.add(new Book("Ezekiel", 25));
      break;

    case 2:
      books.add(new Book("Exodus", 25));
      break;

    case 3:
      books.add(new Book("1 Samuel", 25));
      break;

    case 4:
      books.add(new Book("Leviticus", 27));
      break;

    case 5:
      books.add(new Book("Deuteronomy", 25));
      break;

    case 6:
      books.add(new Book("Numbers", 25));
      break;

    case 7:
      books.add(new Book("1 Chronicles", 29));

      break;
    }
    return books;
  }

  /*
  OT 1 Pentateuch 2 History 1 Wisdom 3 Prophets
NT 1 Gospel + Acts 3 Paul 2 General + Revelation
   */
  private List<Book> getNtBooks() {
    List <Book> books = new ArrayList<>();
    switch (calendar.getYear() % 4) {
      case 0:
        books.add(new Book("Matthew", 28));
        break;

      case 1:
        books.add(new Book("Mark", 16));
        break;

      case 2:
        books.add(new Book("Luke", 24));

        break;

      case 3:
        books.add(new Book("John", 21));

        break;
    }
    // Gospels
    return books;
  }

}
