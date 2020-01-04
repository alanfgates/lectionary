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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

abstract class Period {

  protected final LiturgicalCalendar calendar;
  private final int numWeeks;

  /**
   * Holidays in this period, must be filled out by the subclass.  The key is the date of the holiday.
   */
  protected Map<Calendar, Holiday> holidays;

  /**
   * Sections in this period, must be filled out by the subclass.
   */
  protected List<Section> sections;

  /**
   * @param calendar liturgical calendar for this year
   * @param numWeeks number of weeks in this period.
   */
  Period(LiturgicalCalendar calendar, int numWeeks) {
    this.calendar = calendar;
    this.numWeeks = numWeeks;
    holidays = new HashMap<>();
    sections = new ArrayList<>();
  }

  /**
   * Find the readings for this Period
   */
  final void determineReadings() {
    populateHolidays();
    // Put each holiday on the calendar
    for (Map.Entry<Calendar, Holiday> entry : holidays.entrySet()) {
      calendar.addReading(entry.getKey(), entry.getValue().todaysReading());
    }

    populateSections();

    Calendar firstDay = beginDate();

    for (int weekNum = 0; weekNum < numWeeks; weekNum++) {
      Calendar firstDayOfCurrentWeek = (Calendar)firstDay.clone();
      firstDayOfCurrentWeek.add(Calendar.DATE, 7 * weekNum);

      // Keep them separated by section so I can interleave them.
      List<List<String>> readings = new ArrayList<>();
      for (Section section : sections) readings.add(getThisWeeksReadings(section, weekNum));

      // Figure out how many total readings we need to do this week.
      int totalReadings = 0;
      for (List<String> r : readings) totalReadings += r.size();

      // If we have any holidays this week, subtract that day from number of days in the week, as those will already have readings
      int daysThisWeek = 7;
      for (int i = 0; i < 7; i++) {
        Calendar today = (Calendar)firstDayOfCurrentWeek.clone();
        today.add(Calendar.DATE, i);
        if (holidays.containsKey(today)) daysThisWeek--;
      }

      int readingsPerDay = totalReadings / daysThisWeek;
      int readingsPerDayRemainder = totalReadings % daysThisWeek;

      InterleavingIterator<String> nextReading = new InterleavingIterator<>(readings);
      // Assign the readings to days
      for (int i = 0; i < 7; i++) {
        Calendar today = (Calendar)firstDayOfCurrentWeek.clone();
        today.add(Calendar.DATE, i);
        if (holidays.containsKey(today)) {
          // If this holiday happens before we've cleared the remainder I need to bump up the remainder, or we'll miss a chapter later
          // when we do the if (i < readingsPerDayRemainder)
          if (i < readingsPerDayRemainder) readingsPerDayRemainder++;
          continue;
        }
        for (int j = 0; j < readingsPerDay; j++) {
          assert nextReading.hasNext();
          calendar.addReading(today, nextReading.next());
        }
        if (i < readingsPerDayRemainder) {
          assert nextReading.hasNext();
          calendar.addReading(today, nextReading.next());
        }
      }
      assert !nextReading.hasNext();
    }
  }

  /**
   * Get the day the period begins on
   * @return begin day
   */
  abstract protected Calendar beginDate();

  /**
   * Get the day the period ends on
   * @return end
   */
  abstract protected Calendar endDate();

  /**
   * Fill out the Holidays list.
   */
  abstract protected void populateHolidays();

  /**
   * Fill out the sections list
   */
  abstract protected void populateSections();

  private List<String> getThisWeeksReadings(Section section, int weekNum) {
    int baseReadingsPerWeek = section.getTotalReadings() / numWeeks;
    int readingsPerWeekRemainder = section.getTotalReadings() % numWeeks;
    int numReadingsThisWeek = baseReadingsPerWeek + (weekNum > readingsPerWeekRemainder ? 0 : 1);
    List<String> thisWeeksReadings = new ArrayList<>(numReadingsThisWeek);
    for (int i = 0; i < numReadingsThisWeek; i++) thisWeeksReadings.add(section.getNextReading());
    return thisWeeksReadings;
  }

  private static class InterleavingIterator<T> implements Iterator<T> {
    private List<Iterator<T>> iters;
    private int nextToRead;


    InterleavingIterator(List<List<T>> lists) {
      iters = new ArrayList<>();
      for (List<T> inner : lists) iters.add(inner.iterator());
      nextToRead = 0;
    }

    @Override
    public boolean hasNext() {
      for (Iterator<T> iter : iters) if (iter.hasNext()) return true;
      return false;
    }

    @Override
    public T next() {
      if (!hasNext()) return null;
      while (!iters.get(nextToRead).hasNext()) incrementNextToRead();
      T tmp = iters.get(nextToRead).next();
      incrementNextToRead();
      return tmp;
    }

    private void incrementNextToRead() {
      nextToRead++;
      if (nextToRead >= iters.size()) nextToRead = 0;
    }
  }

}
