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

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Advent extends Period {
  private Calendar startOfAdvent;

  public Advent(int year) {
    super(year);
  }

  @Override
  protected Calendar begin() {
    if (startOfAdvent == null) startOfAdvent = startOfAdvent(year);
    return (Calendar)startOfAdvent.clone();
  }

  @Override
  protected Calendar end() {
    return new GregorianCalendar(year - 1, Calendar.DECEMBER, 24);
  }

  @Override
  protected int length() {
    Calendar startAdvent = new GregorianCalendar(year - 1, Calendar.NOVEMBER, 30);
    switch (startAdvent.get(Calendar.DAY_OF_WEEK)) {
    case Calendar.SUNDAY: return 25;
    case Calendar.MONDAY: return 26;
    case Calendar.TUESDAY: return 27;
    case Calendar.WEDNESDAY: return 28;
    case Calendar.THURSDAY: return 22;
    case Calendar.FRIDAY: return 23;
    case Calendar.SATURDAY: return 24;
    default: throw new RuntimeException("Huh?");
    }
  }

  @Override
  protected void populateHolidays() {
    Calendar firstSunday = begin();
    holidays.add(new Holiday("First Sunday in Advent", firstSunday, year, "John 1:1-18"));
    Calendar secondSunday = (Calendar)firstSunday.clone();
    secondSunday.add(Calendar.DATE, 7);
    holidays.add(new Holiday("Second Sunday in Advent", secondSunday, year, "Luke 1:1-25"));
    Calendar thirdSunday = (Calendar)firstSunday.clone();
    thirdSunday.add(Calendar.DATE, 14);
    holidays.add(new Holiday("Third Sunday in Advent", thirdSunday, year, "Luke 1:26-56"));
    Calendar fourthSunday = (Calendar)firstSunday.clone();
    fourthSunday.add(Calendar.DATE, 21);
    holidays.add(new Holiday("Fourth Sunday in Advent", fourthSunday, year, "Luke 1:57-80"));

  }

  @Override
  protected void populateSections() {
    if (year % 2 == 0) sections.add(new Section(Arrays.asList(new Book("Isaiah", 66, 40)), Main.weekdays));
    else sections.add(new Section(Arrays.asList(new Book("Revelation", 22)), Main.weekdays));
  }

  /**
   * Static so that EpiphanyThroughNormal can use it to find it's end date
   * @param year
   * @return
   */
  static Calendar startOfAdvent(int year) {
    Calendar startAdvent = new GregorianCalendar(year - 1, Calendar.NOVEMBER, 30);
    switch (startAdvent.get(Calendar.DAY_OF_WEEK)) {
    case Calendar.SUNDAY:
      break;
    case Calendar.MONDAY:
      startAdvent.add(Calendar.DATE, -1);
      break;
    case Calendar.TUESDAY:
      startAdvent.add(Calendar.DATE, -2);
      break;
    case Calendar.WEDNESDAY:
      startAdvent.add(Calendar.DATE, -3);
      break;
    case Calendar.THURSDAY:
      startAdvent.add(Calendar.DATE, 3);
      break;
    case Calendar.FRIDAY:
      startAdvent.add(Calendar.DATE, 2);
      break;
    case Calendar.SATURDAY:
      startAdvent.add(Calendar.DATE, 1);
      break;
    default: throw new RuntimeException("Huh?");
    }
    return startAdvent;
  }
}
