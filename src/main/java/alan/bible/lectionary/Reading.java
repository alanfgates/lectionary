package alan.bible.lectionary;

import java.util.Calendar;
import java.util.Locale;

class Reading {
  private final String date;
  private final String holiday;
  private final String scripture;

  public Reading(Calendar date, String scripture) {
    this(date, null, scripture);
  }

  public Reading(Calendar date, String holiday, String scripture) {
    this.date = dateToString(date);
    this.holiday = holiday;
    this.scripture = scripture;
  }

  @Override
  public String toString() {
    return date + " " + (holiday == null ? "" : holiday + " ") + scripture;
  }

  private static String dateToString(Calendar date) {
    StringBuilder buf = new StringBuilder();
    if (date.get(Calendar.MONTH) == Calendar.JANUARY && date.get(Calendar.DAY_OF_MONTH) == 1) {
      buf.append(date.get(Calendar.YEAR)).append("\n");
    }
    if (date.get(Calendar.DAY_OF_MONTH) == 1) {
      buf.append(date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
          .append("\n");
    }
    buf.append(date.get(Calendar.DAY_OF_MONTH))
        .append(" ")
        .append(date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()))
        .append(' ');
    return buf.toString();
  }
}
