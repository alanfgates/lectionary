package alan.bible.lectionary;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

public class Main {

  public static void main(String[] args) {
    Calendar today = new GregorianCalendar();
    DailyReadings daily = new DailyReadings();
    Map<SpecialReadings.CalendarDay, Reading> holidays = SpecialReadings.calculateSpecialReadings(today.get(Calendar.YEAR));
    while (daily.hasNext()) {
      if (today.get(Calendar.MONTH) == Calendar.JANUARY && today.get(Calendar.DAY_OF_MONTH) == 1) {
        holidays = SpecialReadings.calculateSpecialReadings(today.get(Calendar.YEAR));
      }
      Reading reading = holidays.get(new SpecialReadings.CalendarDay(today));
      if (reading == null) reading = daily.nextReading(today);
      System.out.println(reading.toString());
      today.add(Calendar.DATE, 1);
    }
  }

  private static void usage() {
    System.out.println("Usage: lectionary <year>");
    throw new RuntimeException();
  }
}
