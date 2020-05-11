package alan.bible.lectionary;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Track days with special readings
class SpecialReadings {

  private static Map<Integer, Map<CalendarDay, Reading>> calculatedYears = new HashMap<>();

  static Map<CalendarDay, Reading> calculateSpecialReadings(int year) {
    return calculatedYears.computeIfAbsent(year, SpecialReadings::calculateYear);
  }

  private static Map<CalendarDay, Reading> calculateYear(int year) {
    Map<CalendarDay, Reading> specialReadings = new HashMap<>();
    Calendar startOfAdvent = new GregorianCalendar(year, Calendar.DECEMBER, 25);

    switch (startOfAdvent.get(Calendar.DAY_OF_WEEK)) {
      case Calendar.SUNDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.SATURDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.FRIDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.THURSDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.WEDNESDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.TUESDAY: startOfAdvent.add(Calendar.DATE, -1);
      case Calendar.MONDAY: startOfAdvent.add(Calendar.DATE, -1);
    }
    // First Sunday in Advent
    startOfAdvent.add(Calendar.DATE, -21);
    specialReadings.put(new CalendarDay(startOfAdvent), new Reading(startOfAdvent, "First Sunday of Advent", "John 1:1-18"));
    // Second Sunday in Advent
    Calendar secondSundayInAdvent = (Calendar)startOfAdvent.clone();
    secondSundayInAdvent.add(Calendar.DATE, 7);
    specialReadings.put(new CalendarDay(secondSundayInAdvent), new Reading(secondSundayInAdvent, "Second Sunday of Advent", "Luke 1:1-25"));
    // Third Sunday in Advent
    Calendar thirdSundayInAdvent = (Calendar)startOfAdvent.clone();
    thirdSundayInAdvent.add(Calendar.DATE, 14);
    specialReadings.put(new CalendarDay(thirdSundayInAdvent), new Reading(thirdSundayInAdvent, "Third Sunday of Advent", "Luke 1:26-56"));
    // Third Sunday in Advent
    Calendar fourthSundayInAdvent = (Calendar)startOfAdvent.clone();
    fourthSundayInAdvent.add(Calendar.DATE, 21);
    specialReadings.put(new CalendarDay(fourthSundayInAdvent), new Reading(fourthSundayInAdvent, "Fourth Sunday of Advent", "Luke 1:57-80"));
    // Christmas
    Calendar christmas = new GregorianCalendar(year, Calendar.DECEMBER, 25);
    specialReadings.put(new CalendarDay(christmas), new Reading(christmas, "Christmas", "Matthew 1:18-25, Luke 2:1-20"));
    Calendar holyInnocents = new GregorianCalendar(year, Calendar.DECEMBER, 28);
    specialReadings.put(new CalendarDay(holyInnocents), new Reading(holyInnocents, "Holy Innocents", "Matthew 2:13-23"));
    // We're jumping back 12 months here, but that's ok, we're filling out the current calendar year, not calculating a coherent liturgical year
    Calendar epiphany = new GregorianCalendar(year, Calendar.JANUARY, 6);
    specialReadings.put(new CalendarDay(epiphany), new Reading(epiphany, "Epiphany", "Matthew 2:1-12"));
    Calendar presentation = new GregorianCalendar(year, Calendar.FEBRUARY, 2);
    specialReadings.put(new CalendarDay(presentation), new Reading(presentation, "The Presentation", "Luke 2:21-25"));
    Calendar annunciation = new GregorianCalendar(year, Calendar.MARCH, 25);
    specialReadings.put(new CalendarDay(annunciation), new Reading(annunciation, "The Annunciation", "Luke 1:26-38"));

    Calendar easter = calculateEaster(year);
    Calendar ashWednesday = (Calendar)easter.clone();
    ashWednesday.add(Calendar.DATE, -46);
    specialReadings.put(new CalendarDay(ashWednesday), new Reading(ashWednesday, "Ash Wednesday",
        oneOf(year, "Matthew 3:13-4:11", "Mark 1:9-13", "Luke 3:21,22, 4:1-13")));

    Calendar palmSunday = (Calendar)easter.clone();
    palmSunday.add(Calendar.DATE, -7);
    specialReadings.put(new CalendarDay(palmSunday), new Reading(palmSunday, "Palm Sunday",
        oneOf(year, "Psalm 24, Zechariah 9, Matthew 21:1-9, John 12:12-19",
            "Psalm 24, Zechariah 9, Mark 11:1-10, John 12:12-19",
            "Psalm 24, Zechariah 9, Luke 19:28-48, John 12:12-19")));

    Calendar holyMonday = (Calendar)palmSunday.clone();
    holyMonday.add(Calendar.DATE, 1);
    specialReadings.put(new CalendarDay(holyMonday), new Reading(holyMonday, "Monday of Holy Week", oneOf(year, "Matthew 26:1-16", "John 12:20-13:38")));

    Calendar holyTuesday = (Calendar)palmSunday.clone();
    holyTuesday.add(Calendar.DATE, 2);
    specialReadings.put(new CalendarDay(holyTuesday), new Reading(holyTuesday, "Tuesday of Holy Week", oneOf(year, "Mark 14:1-11", "John 14", "John 16")));

    Calendar holyWednesday = (Calendar)palmSunday.clone();
    holyWednesday.add(Calendar.DATE, 3);
    specialReadings.put(new CalendarDay(holyWednesday), new Reading(holyWednesday, "Wednesday of Holy Week", oneOf(year, "Luke 22:1-6", "John 15", "John 17")));

    Calendar maundyThursday = (Calendar)palmSunday.clone();
    maundyThursday.add(Calendar.DATE, 4);
    specialReadings.put(new CalendarDay(maundyThursday), new Reading(maundyThursday, "Maundy Thursday",
        oneOf(year, "Matthew 26:17-75", "Mark 14:12-72", "Luke 22:7-62", "John 18")));

    Calendar goodFriday = (Calendar)palmSunday.clone();
    goodFriday.add(Calendar.DATE, 5);
    specialReadings.put(new CalendarDay(goodFriday), new Reading(goodFriday, "Good Friday",
        oneOf(year, "Matthew 27:1-61", "Mark 15:1-41", "Luke 22:63-23:56", "John 19")));

    Calendar holySaturday = (Calendar)palmSunday.clone();
    holySaturday.add(Calendar.DATE, 6);
    specialReadings.put(new CalendarDay(holySaturday), new Reading(holySaturday, "Holy Saturday", oneOf(year, "Matthew 27:62-66", "Mark 15:42-47")));

    specialReadings.put(new CalendarDay(easter), new Reading(easter, "Easter", oneOf(year, "Matthew 28:1-15", "Mark 16:1-8", "Luke 24:1-49", "John 20")));

    Calendar ascensionDay = (Calendar)easter.clone();
    ascensionDay.add(Calendar.DATE, 39);
    specialReadings.put(new CalendarDay(ascensionDay), new Reading(ascensionDay, "Ascension Day", oneOf(year, "Matthew 28:16-20", "Luke 24:50-53", "Acts 1")));

    Calendar pentecost = (Calendar) easter.clone();
    pentecost.add(Calendar.DATE, 49);
    specialReadings.put(new CalendarDay(pentecost), new Reading(pentecost, "Pentecost", "Acts 2"));

    return specialReadings;
  }

  static private Calendar calculateEaster(int year) {
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

  static String oneOf(int year, String... options) {
    return options[year % options.length];
  }

  static class CalendarDay {
    private final int month;
    private final int day;

    CalendarDay(Calendar date) {
      month = date.get(Calendar.MONTH);
      day = date.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CalendarDay that = (CalendarDay) o;
      return month == that.month &&
          day == that.day;
    }

    @Override
    public int hashCode() {
      return Objects.hash(month, day);
    }
  }
}


