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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EpiphanyThroughNormal extends Period {
  private Calendar end;
  private int length;

  private Calendar[] easterDates;

  public EpiphanyThroughNormal(int year) {
    super(year);

    easterDates = new Calendar[5];
    // TODO figure out how to calculate this automatically
    easterDates[0] = new GregorianCalendar(2015, Calendar.APRIL, 5, 0, 0, 0);
    easterDates[1] = new GregorianCalendar(2016, Calendar.MARCH, 27);
    easterDates[2] = new GregorianCalendar(2017, Calendar.APRIL, 16);
    easterDates[3] = new GregorianCalendar(2018, Calendar.APRIL, 1);
    easterDates[4] = new GregorianCalendar(2019, Calendar.APRIL, 21);
  }

  @Override
  protected Calendar begin() {
    return new GregorianCalendar(year - 1, Calendar.DECEMBER, 25);
  }

  @Override
  protected Calendar end() {
    end = Advent.startOfAdvent(year + 1);
    end.add(Calendar.DATE, -1);
    return end;
  }

  @Override
  protected int length() {
    if (length == 0) {
      // There must be a better way to do this
      Calendar day = begin();
      while (day.compareTo(end()) < 1) {
        day.add(Calendar.DATE, 1);
        length++;
      }
    }
    return length;
  }

  @Override
  protected void populateHolidays() {
    // Dates fixed to the solar calendar
    holidays.add(new Holiday("Christmas", begin(), year, "Matthew 1:18-25, Luke 2:1-20"));
    holidays.add(new Holiday("Holy Innocents",
      new GregorianCalendar(year - 1, Calendar.DECEMBER, 28), year, "Matthew 2:13-23"));
    holidays.add(new Holiday("Epiphany",
        new GregorianCalendar(year, Calendar.JANUARY, 6), year, "Matthew 2:1-12"));
    holidays.add(new Holiday("Presentation",
        new GregorianCalendar(year, Calendar.FEBRUARY, 2), year, "Luke 2:21-25"));
    holidays.add(new Holiday("Annunciation",
        new GregorianCalendar(year, Calendar.MARCH, 25), year, "Luke 1:26-38"));
    holidays.add(new Holiday("Visitation",
        new GregorianCalendar(year, Calendar.MAY, 31), year, "Luke 1:39-56"));
    holidays.add(new Holiday("Nativity of John the Baptist",
        new GregorianCalendar(year, Calendar.JUNE, 24), year, "Luke 1:57-80"));
    holidays.add(new Holiday("Transfiguration",
        new GregorianCalendar(year, Calendar.AUGUST, 6), year, "Matthew 17:1-9", "Mark 9:2-10",
        "Luke 9:28-36"));

    // Dates fixed to easter
    Calendar easter = easterDates[year - 2015];
    holidays.add(new Holiday("Easter",
        easter, year, "Matthew 28:1-15", "Mark 16:1-8", "Luke 24:1-49", "John 20"));

    Calendar ashWednesday = (Calendar)easter.clone();
    ashWednesday.add(Calendar.DATE, -46);
    holidays.add(new Holiday("Ash Wednesday", ashWednesday, year,
        "Matthew 3:13-4:11", "Mark 1:9-13", "Luke 3:21,22, 4:1-13"));

    Calendar palmSunday = (Calendar)easter.clone();
    palmSunday.add(Calendar.DATE, -7);
    holidays.add(new Holiday("Palm Sunday", palmSunday, year,
        "Psalm 24, Zechariah 9, Matthew 21:1-9, John 12:12-19",
        "Psalm 24, Zechariah 9, Mark 11:1-10, John 12:12-19",
        "Psalm 24, Zechariah 9, Luke 19:28-48, John 12:12-19"));

    Calendar holyMonday = (Calendar)easter.clone();
    holyMonday.add(Calendar.DATE, -6);
    holidays.add(new Holiday("Holy Monday", holyMonday, year, "Matthew 26:1-14, John 12:20-13:38"));

    Calendar holyTuesday = (Calendar)easter.clone();
    holyTuesday.add(Calendar.DATE, -5);
    holidays.add(new Holiday("Holy Tuesday", holyTuesday, year, "Mark 14:1-11, John 14,15"));

    Calendar holyWednesday = (Calendar)easter.clone();
    holyWednesday.add(Calendar.DATE, -4);
    holidays.add(new Holiday("Holy Wednesday", holyWednesday, year, "Luke 22:1-6, John 16,17"));

    Calendar maundyThursday = (Calendar)easter.clone();
    maundyThursday.add(Calendar.DATE, -3);
    holidays.add(new Holiday("Maundy Thursday", maundyThursday, year,
        "Matthew 26:17-75, John 18", "Mark 14:12-72, John 18", "Luke 22:7-62, John 18"));

    Calendar goodFriday = (Calendar)easter.clone();
    goodFriday.add(Calendar.DATE, -2);
    holidays.add(new Holiday("Good Friday", goodFriday, year,
        "Matthew 27:1-61, John 19", "Mark 15:1-41, John 19", "Luke 22:63-23:56, John 19"));

    Calendar holySaturday = (Calendar)easter.clone();
    holySaturday.add(Calendar.DATE, -1);
    holidays.add(new Holiday("Holy Saturday", holySaturday, year, "Matthew 27:62-66", "Mark 15:42-47"));

    Calendar ascensionDay = (Calendar)easter.clone();
    ascensionDay.add(Calendar.DATE, 39);
    holidays.add(new Holiday("Ascension Day",
        ascensionDay, year, "Matthew 28:16-20, Luke 24:50-53, Acts 1"));

    Calendar pentecost = (Calendar) easter.clone();
    pentecost.add(Calendar.DATE, 49);
    holidays.add(new Holiday("Pentecost", pentecost, year, "Acts 2"));
  }

  @Override
  protected void populateSections() {
    // Psalms
    sections.add(new Section(Arrays.asList((Book)new Psalms()),
      Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY)));

    // Old Testament
    sections.add(new Section(getOtBooks(), Main.weekdays));

    // Gospels
    sections.add(new Section(Arrays.asList(
        new Book("Matthew", 28),
        new Book("Mark", 16),
        new Book("Luke", 24),
        new Book("John", 21)),
      Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY)));

    sections.add(new Section(Arrays.asList(
        new Book("Acts", 28),
        new Book("Romans", 16),
        new Book("Hebrews", 13),
        new Book("1 Corinthians", 16),
        new Book("2 Corinthians", 13),
        new Book("James", 5),
        new Book("Galatians", 6),
        new Book("1 Peter", 5),
        new Book("Ephesians", 6),
        new Book("2 Peter", 3),
        new Book("Philippians", 4),
        new Book("1 John", 5),
        new Book("Colossians", 4),
        new Book("2 John", 1),
        new Book("3 John", 1),
        new Book("1 Thessalonians", 5),
        new Book("2 Thessalonians", 3),
        new Book("1 Timothy", 6),
        new Book("2 Timothy", 4),
        new Book("Titus", 3),
        new Book("Philemon", 1),
        new Book("Jude", 1)),
      Arrays.asList(Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY)));
  }

  private List<Book> getOtBooks() {
    List <Book> books = new ArrayList<>();
    switch (year % 2015) {
    case 0:
      books.add(new Book("Genesis", 50));
      books.add(new Book("Exodus", 40));
      books.add(new Book("Leviticus", 27));
      books.add(new Book("Numbers", 36));
      books.add(new Book("Deuteronomy", 34));
      books.add(new Book("Joshua", 24));
      books.add(new Book("Joel", 3));
      books.add(new Book("Obadiah", 1));
      books.add(new Book("Nahum", 3));
      books.add(new Book("Ecclesiastes", 12));
      books.add(new Book("Song of Songs", 8));
      break;

    case 1:
      books.add(new Book("Job", 42));
      books.add(new Book("1 Kings", 22));
      books.add(new Book("2 Kings", 25));
      books.add(new Book("1 Samuel", 31));
      books.add(new Book("2 Samuel", 24));
      books.add(new Book("Amos", 9));
      books.add(new Book("Isaiah", 39));
      books.add(new Book("Micah", 7));
      books.add(new Book("Jeremiah", 52));
      books.add(new Book("Lamentations", 5));
      break;

    case 2:
      books.add(new Book("Proverbs", 31));
      books.add(new Book("Judges", 21));
      books.add(new Book("Ruth", 4));
      books.add(new Book("1 Chronicles", 29));
      books.add(new Book("2 Chronicles", 36));
      books.add(new Book("Hosea", 14));
      books.add(new Book("Ezra", 10));
      books.add(new Book("Zephaniah", 3));
      books.add(new Book("Habakkuk", 3));
      books.add(new Book("Nehemiah", 13));
      books.add(new Book("Esther", 10));
      books.add(new Book("Ezekiel", 48));
      books.add(new Book("Daniel", 12));
      books.add(new Book("Haggai", 2));
      books.add(new Book("Zechariah", 14));
      books.add(new Book("Malachi", 4));
      break;
    }
    return books;
  }
}
