package alan.bible.lectionary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

class DailyReadings {

  private Iterator<String> readings;
  private Psalms psalms;

  DailyReadings() {
    psalms = new Psalms();
    List<Book> ot = new ArrayList<>();
    List<Book> nt = new ArrayList<>();
    ot.add(new Book("Genesis", 50));
    ot.add(new Book("Joshua", 24));
    ot.add(new Book("Job", 42));
    ot.add(new Book("Isaiah", 66));
    ot.add(new Book("Exodus", 40));
    ot.add(new Book("Judges", 21));
    ot.add(new Book("Proverbs", 31));
    ot.add(new Book("Jeremiah", 52));
    ot.add(new Book("Leviticus", 27));
    ot.add(new Book("Ruth", 4));
    ot.add(new Book("Lamentations", 5));
    ot.add(new Book("Ecclesiastes", 12));
    ot.add(new Book("Numbers", 36));
    ot.add(new Book("1 Samuel", 31));
    ot.add(new Book("Song of Songs", 8));
    ot.add(new Book("Ezekiel", 48));
    ot.add(new Book("Deuteronomy", 34));
    ot.add(new Book("2 Samuel", 24));
    ot.add(new Book("Daniel", 12));
    ot.add(new Book("1 Kings", 22));
    ot.add(new Book("Hosea", 14));
    ot.add(new Book("2 Kings", 25));
    ot.add(new Book("Joel", 3));
    ot.add(new Book("1 Chronicles", 29));
    ot.add(new Book("Amos", 9));
    ot.add(new Book("2 Chronicles", 36));
    ot.add(new Book("Obadiah", 1));
    ot.add(new Book("Ezra", 10));
    ot.add(new Book("Jonah", 4));
    ot.add(new Book("Nehemiah", 13));
    ot.add(new Book("Micah", 7));
    ot.add(new Book("Esther", 10));
    ot.add(new Book("Nahum", 3));
    ot.add(new Book("Habakkuk", 3));
    ot.add(new Book("Zephaniah", 3));
    ot.add(new Book("Haggai", 2));
    ot.add(new Book("Zechariah", 14));
    ot.add(new Book("Malachi", 4));

    nt.addAll(addToNt());
    nt.addAll(addToNt());
    nt.addAll(addToNt());

    readings = new Iterator<String>() {
      private int otBook = 0;
      private int ntBook = 0;
      private boolean otNext = true;
      private String next;

      @Override
      public boolean hasNext() {
        if (next != null) return true;
        next = next();
        return next != null;
      }

      @Override
      public String next() {
        if (next != null) {
          String tmp = next;
          next = null;
          return tmp;
        }
        if (otBook == ot.size() && ntBook == nt.size()) return null;

        if (otNext && otBook < ot.size()) {
          Book ob = ot.get(otBook);
          if (ob.hasNextChapter()) {
            otNext = false;
            return ob.getName() + " " + ob.getNextChapter();
          }
          otBook++;
          return next();
        } else if (ntBook < nt.size()) {
          Book nb = nt.get(ntBook);
          if (nb.hasNextChapter()) {
            otNext = true;
            return nb.getName() + " " + nb.getNextChapter();
          }
          ntBook++;
          return next();
        } else {
          // This means we tried to read from one that we were out of, so switch and read from the other
          otNext = !otNext;
          return next();
        }
      }
    };
  }

  boolean hasNext() {
    return readings.hasNext();
  }

  Reading nextReading(Calendar day) {
    if (day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      return psalms.nextPsalm(day);
    } else {
      String r = readings.next();
      return r == null ? null : new Reading(day, r);
    }
  }

  private static List<Book> addToNt() {
    List<Book> nt = new ArrayList<>();
    nt.add(new Book("Matthew", 28));
    nt.add(new Book("Acts", 28));
    nt.add(new Book("Romans", 16));
    nt.add(new Book("Hebrews", 13));
    nt.add(new Book("Revelation", 22));
    nt.add(new Book("Mark", 16));
    nt.add(new Book("1 Corinthians", 16));
    nt.add(new Book("James", 5));
    nt.add(new Book("Luke", 24));
    nt.add(new Book("2 Corinthians", 13));
    nt.add(new Book("1 Peter", 5));
    nt.add(new Book("John", 21));
    nt.add(new Book("Galatians", 6));
    nt.add(new Book("2 Peter", 3));
    nt.add(new Book("Ephesians", 6));
    nt.add(new Book("1 John", 5));
    nt.add(new Book("Philippians", 4));
    nt.add(new Book("2 John", 1));
    nt.add(new Book("Colossians", 4));
    nt.add(new Book("3 John", 1));
    nt.add(new Book("1 Thessalonians", 5));
    nt.add(new Book("Jude", 1));
    nt.add(new Book("2 Thessalonians", 3));
    nt.add(new Book("1 Timothy", 6));
    nt.add(new Book("2 Timothy", 4));
    nt.add(new Book("Titus", 3));
    nt.add(new Book("Philemon", 1));
    return nt;
  }

  private static class Psalms {
    private static final String[] LETTERS_119 = {
        "א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט", "י", "כ", "ל", "מ", "נ", "ס", "ע", "פ", "צ", "ק", "ר", "ש", "ת"
    };
    private int nextPsalm;
    private int next119;

    Psalms() {
      nextPsalm = 1;
      next119 = 0;
    }

    Reading nextPsalm(Calendar date) {
      if (nextPsalm > 150) nextPsalm = 1;
      if (nextPsalm == 119) {
        if (next119 == LETTERS_119.length) {
          nextPsalm = 120;
        } else {
          return new Reading(date, "Psalm 119 " + LETTERS_119[next119++]);
        }
      }
      return new Reading(date, "Psalm " + nextPsalm++);
    }
  }


}
