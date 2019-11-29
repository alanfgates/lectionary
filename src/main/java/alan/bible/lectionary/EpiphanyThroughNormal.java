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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Deque;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpiphanyThroughNormal extends Period {
  private Calendar end;
  private int length;

  private Map<Integer, Calendar> easterDates;

  public EpiphanyThroughNormal(int year) {
    super(year);

    // TODO figure out how to calculate this automatically
    easterDates = new HashMap<>();
    easterDates.put(2020, new GregorianCalendar(2020, Calendar.APRIL, 12));
    easterDates.put(2021, new GregorianCalendar(2021, Calendar.APRIL, 4));
    easterDates.put(2022, new GregorianCalendar(2022, Calendar.APRIL, 17));
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

    // Dates fixed to easter
    Calendar easter = easterDates.get(year);
    if (easter == null) throw new RuntimeException("I don't know the date of easter in the year " + year);
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
    holidays.add(new Holiday("Holy Monday", holyMonday, year, "Matthew 26:1-14", "John 12:20-13:38"));

    Calendar holyTuesday = (Calendar)easter.clone();
    holyTuesday.add(Calendar.DATE, -5);
    holidays.add(new Holiday("Holy Tuesday", holyTuesday, year, "Mark 14:1-11", "John 14", "John 15"));

    Calendar holyWednesday = (Calendar)easter.clone();
    holyWednesday.add(Calendar.DATE, -4);
    holidays.add(new Holiday("Holy Wednesday", holyWednesday, year, "Luke 22:1-6", "John 16", "John 17"));

    Calendar maundyThursday = (Calendar)easter.clone();
    maundyThursday.add(Calendar.DATE, -3);
    holidays.add(new Holiday("Maundy Thursday", maundyThursday, year,
        "Matthew 26:17-75", "Mark 14:12-72", "Luke 22:7-62", "John 18"));

    Calendar goodFriday = (Calendar)easter.clone();
    goodFriday.add(Calendar.DATE, -2);
    holidays.add(new Holiday("Good Friday", goodFriday, year,
        "Matthew 27:1-61", "Mark 15:1-41", "Luke 22:63-23:56", "John 19"));

    Calendar holySaturday = (Calendar)easter.clone();
    holySaturday.add(Calendar.DATE, -1);
    holidays.add(new Holiday("Holy Saturday", holySaturday, year, "Matthew 27:62-66", "Mark 15:42-47"));

    Calendar ascensionDay = (Calendar)easter.clone();
    ascensionDay.add(Calendar.DATE, 39);
    holidays.add(new Holiday("Ascension Day",
        ascensionDay, year, "Matthew 28:16-20", "Luke 24:50-53", "Acts 1"));

    Calendar pentecost = (Calendar) easter.clone();
    pentecost.add(Calendar.DATE, 49);
    holidays.add(new Holiday("Pentecost", pentecost, year, "Acts 2"));
  }

  @Override
  protected void populateSections() {
    // Psalms
    sections.add(new Section(Collections.singletonList(new Psalms()),
      Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY)));

    List<Book> allBooks = interleave(getNtBooks(), getOtBooks());
    sections.add(new Section(allBooks, Main.readingDays));
  }


  private List<Book> getOtBooks() {
    List <Book> books = new ArrayList<>();
    switch (year % 8) {
    case 0:
      books.add(new Book("Genesis", 50));
      books.add(new Book("Judges", 21));       // 71
      books.add(new Book("Ruth", 4));          // 75
      books.add(new Book("Zechariah", 14));    // 89
      books.add(new Book("Zephaniah", 3));     // 92
      books.add(new Book("Obadiah", 1));       // 93
      break;

    case 1:
      books.add(new Book("Joshua", 24));
      books.add(new Book("Ezekiel", 48));      // 72
      books.add(new Book("Ecclesiastes", 12)); // 84
      books.add(new Book("Amos", 9));          // 93
      break;

    case 2:
      books.add(new Book("Exodus", 40));
      books.add(new Book("Ezra", 10));         // 50
      books.add(new Book("Nehemiah", 13));     // 63
      books.add(new Book("Song of Songs", 8)); // 71
      books.add(new Book("Daniel", 12));       // 83
      books.add(new Book("Malachi", 4));       // 87
      books.add(new Book("Joel", 3));          // 90
      books.add(new Book("Haggai", 2));        // 92
      break;

    case 3:
      books.add(new Book("1 Samuel", 31));
      books.add(new Book("2 Samuel", 24));     // 55
      books.add(new Book("Isaiah", 39));       // 94
      break;

    case 4:
      books.add(new Book("Leviticus", 27));
      books.add(new Book("1 Kings", 22));      // 49
      books.add(new Book("2 Kings", 25));      // 74
      books.add(new Book("Hosea", 14));        // 88
      books.add(new Book("Nahum", 3));         // 91
      books.add(new Book("Habakkuk", 3));      // 94
      break;

    case 5:
      books.add(new Book("Deuteronomy", 34));
      books.add(new Book("Esther", 10));       // 44
      books.add(new Book("Job", 42));          // 86
      books.add(new Book("Micah", 7));         // 93

      break;

    case 6:
      books.add(new Book("Numbers", 36));
      books.add(new Book("Jeremiah", 52));     // 88
      books.add(new Book("Lamentations", 5));  // 93
      break;

    case 7:
      books.add(new Book("1 Chronicles", 29));
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
    switch (year % 4) {
      case 0:
        books.add(new Book("Matthew", 28));
        books.add(new Book("Galatians", 6));      // 34
        books.add(new Book("Ephesians", 6));      // 40
        books.add(new Book("1 Timothy", 6));      // 46
        books.add(new Book("Hebrews", 13));       // 59
        books.add(new Book("Jude", 1));           // 60
        break;

      case 1:
        books.add(new Book("Mark", 16));
        books.add(new Book("Acts", 28));           // 44
        books.add(new Book("2 Timothy", 4));       // 48
        books.add(new Book("Titus", 3));           // 51
        books.add(new Book("1 Peter", 5));         // 56
        books.add(new Book("2 John", 1));          // 57
        books.add(new Book("3 John", 1));          // 58
        break;

      case 2:
        books.add(new Book("Luke", 24));
        books.add(new Book("1 Corinthians", 16));  // 40
        books.add( new Book("2 Corinthians", 13)); // 53
        books.add(new Book("Philippians", 4));     // 57
        books.add(new Book("2 Peter", 3));         // 60

        break;

      case 3:
        books.add(new Book("John", 21));
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

  private <T> List<T> interleave(List<T> list1, List<T> list2) {
    List<T> result = new ArrayList<>(list1.size() + list2.size());
    Deque<T> list1Copy = new ArrayDeque<>(list1);
    Deque<T> list2Copy = new ArrayDeque<>(list2);
    while (!list1Copy.isEmpty() && !list2Copy.isEmpty()) {
      result.add(list1Copy.remove());
      result.add(list2Copy.remove());
    }
    // pick up whatever remains
    result.addAll(list1Copy);
    result.addAll(list2Copy);
    return result;
  }
}
