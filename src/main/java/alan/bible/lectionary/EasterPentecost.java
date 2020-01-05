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

class EasterPentecost extends Period {

  EasterPentecost(LiturgicalCalendar calendar) {
    super(calendar);
  }

  @Override
  protected Calendar beginDate() {
    return calculateEaster(calendar.getYear());
  }

  @Override
  protected Calendar endDate() {
    return calendar.getLastDay();
  }

  @Override
  protected void populateHolidays() {
    // Dates fixed to the solar calendar
    Calendar annunciation = new GregorianCalendar(calendar.getYear(), Calendar.MARCH, 25);
    if (annunciation.compareTo(beginDate()) > 0) holidays.put(annunciation, new Holiday("Annunciation", annunciation, calendar, "Luke 1:26-38"));
    Calendar visitation = new GregorianCalendar(calendar.getYear(), Calendar.MAY, 31);
    holidays.put(visitation, new Holiday("Visitation", visitation, calendar, "Luke 1:39-56"));
    Calendar nativityOfJohnB = new GregorianCalendar(calendar.getYear(), Calendar.JUNE, 24);
    holidays.put(nativityOfJohnB, new Holiday("Nativity of John the Baptist", nativityOfJohnB, calendar, "Luke 1:57-80"));

    // Dates fixed to easter
    Calendar easter = beginDate();
    holidays.put(easter, new Holiday("Easter", easter, calendar, "Matthew 28:1-15", "Mark 16:1-8", "Luke 24:1-49", "John 20"));

    Calendar ascensionDay = (Calendar)easter.clone();
    ascensionDay.add(Calendar.DATE, 39);
    holidays.put(ascensionDay, new Holiday("Ascension Day",
        ascensionDay, calendar, "Matthew 28:16-20", "Luke 24:50-53", "Acts 1"));

    Calendar pentecost = (Calendar) easter.clone();
    pentecost.add(Calendar.DATE, 49);
    holidays.put(pentecost, new Holiday("Pentecost", pentecost, calendar, "Acts 2"));
  }

  @Override
  protected void populateSections() {
    // Psalms
    sections.add(new Section(Collections.singletonList(new PsalmsPartTwo())));

    List<Book> allBooks = interleave(getOtBooks(), getNtBooks());
    sections.add(new Section(allBooks));
  }

  @Override
  protected void calculateNumWeeks() {
    numWeeks = endDate().get(Calendar.WEEK_OF_YEAR) - beginDate().get(Calendar.WEEK_OF_YEAR) + 1;
        //(endDate().get(Calendar.DAY_OF_WEEK) == beginDate().get(Calendar.DAY_OF_WEEK) ? 0 : 1);
  }


  private List<Book> getOtBooks() {
    List <Book> books = new ArrayList<>();
    switch (calendar.getYear() % 8) {
    case 0:
      books.add(new Book("Genesis", 50, 26));
      books.add(new Book("Judges", 21));       // 71
      books.add(new Book("Ruth", 4));          // 75
      books.add(new Book("Zechariah", 14));    // 89
      books.add(new Book("Zephaniah", 3));     // 92
      books.add(new Book("Obadiah", 1));       // 93
      break;

    case 1:
      books.add(new Book("Ezekiel", 48, 26));      // 72
      books.add(new Book("Joshua", 24));
      books.add(new Book("Ecclesiastes", 12)); // 84
      books.add(new Book("Amos", 9));          // 93
      break;

    case 2:
      books.add(new Book("Exodus", 40, 26));
      books.add(new Book("Ezra", 10));         // 50
      books.add(new Book("Nehemiah", 13));     // 63
      books.add(new Book("Song of Songs", 8)); // 71
      books.add(new Book("Daniel", 12));       // 83
      books.add(new Book("Malachi", 4));       // 87
      books.add(new Book("Joel", 3));          // 90
      books.add(new Book("Haggai", 2));        // 92
      break;

    case 3:
      books.add(new Book("1 Samuel", 31, 26));
      books.add(new Book("2 Samuel", 24));     // 55
      books.add(new Book("Isaiah", 39));       // 94
      break;

    case 4:
      books.add(new Book("1 Kings", 22));      // 49
      books.add(new Book("2 Kings", 25));      // 74
      books.add(new Book("Hosea", 14));        // 88
      books.add(new Book("Nahum", 3));         // 91
      books.add(new Book("Habakkuk", 3));      // 94
      break;

    case 5:
      books.add(new Book("Deuteronomy", 34, 26));
      books.add(new Book("Esther", 10));       // 44
      books.add(new Book("Job", 42));          // 86
      books.add(new Book("Micah", 7));         // 93
      break;

    case 6:
      books.add(new Book("Numbers", 36, 26));
      books.add(new Book("Jeremiah", 52));     // 88
      books.add(new Book("Lamentations", 5));  // 93
      break;

    case 7:
      books.add(new Book("2 Chronicles", 36)); // 65
      books.add(new Book("Proverbs", 31));     // 96

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
        books.add(new Book("Galatians", 6));      // 34
        books.add(new Book("Ephesians", 6));      // 40
        books.add(new Book("1 Timothy", 6));      // 46
        books.add(new Book("Hebrews", 13));       // 59
        books.add(new Book("Jude", 1));           // 60
        break;

      case 1:
        books.add(new Book("Acts", 28));           // 44
        books.add(new Book("2 Timothy", 4));       // 48
        books.add(new Book("Titus", 3));           // 51
        books.add(new Book("1 Peter", 5));         // 56
        books.add(new Book("2 John", 1));          // 57
        books.add(new Book("3 John", 1));          // 58
        break;

      case 2:
        books.add(new Book("1 Corinthians", 16));  // 40
        books.add( new Book("2 Corinthians", 13)); // 53
        books.add(new Book("Philippians", 4));     // 57
        books.add(new Book("2 Peter", 3));         // 60
        break;

      case 3:
        books.add(new Book("Romans", 16));         // 37
        books.add(new Book("Colossians", 4));      // 41
        books.add(new Book("1 Thessalonians", 5)); // 46
        books.add(new Book("2 Thessalonians", 3)); // 49
        books.add(new Book("Philemon", 1));        // 50
        books.add(new Book("James", 5));           // 55
        books.add(new Book("1 John", 5));          // 60
        break;
    }
    // Gospels
    return books;
  }

  private Calendar calculateEaster(int year) {
    // Taken from https://en.wikipedia.org/wiki/Computus#Anonymous_Gregorian_algorithm
    int a = year % 19;
    int b = year / 100;
    int c = year % 100;
    int d = b / 4;
    int e = b % 4;
    int f = (b + 8) / 25;
    int g = (b - f + 1) / 3;
    int h = (19 * a + b - d - g + 15) % 30;
    int i = c / 4;
    int k = c % 4;
    int ell = (32 + 2 * e + 2 * i - h - k) % 7;
    int m = (a + 11 * h + 22 * ell) / 451;
    int month = (h + ell - 7 * m + 114) / 31;
    int day = ((h + ell - 7 * m + 114) % 31) + 1;

    return new GregorianCalendar(year, month - 1, day);
  }
}
