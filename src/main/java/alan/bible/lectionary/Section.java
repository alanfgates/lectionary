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

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Section {
  private final Book[] books;
  private final Set<Integer> daysRead;
  private int currentBook;
  private transient int chaptersRemaining;

  Section(List<Book> books, List<Integer> daysRead) {
    this.books = books.toArray(new Book[books.size()]);
    this.daysRead = new HashSet<>(daysRead);
    chaptersRemaining = 0;
    currentBook = 0;
    for (Book book : books) chaptersRemaining += book.getNumChapters();
  }

  boolean readToday(Calendar today, int weeksLeftInPeriod) {
    if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
      // Only return true if we're behind schedule and need to make up time.
      return chaptersRemaining / daysRead.size() >= weeksLeftInPeriod;

    } else {
      return (daysRead.contains(today.get(Calendar.DAY_OF_WEEK)));
    }
  }

  /**
   * For advent reading a single extra on Saturdays won't help us out.  In that case, read a second
   * time on Saturday.
   * @param weeksLeftInPeriod weeks left in this period
   * @return whether we should read another chapter.
   */
  boolean doubleDip(Calendar today, int weeksLeftInPeriod) {
    if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
      return chaptersRemaining / (daysRead.size() + 1) >= weeksLeftInPeriod;
    }
    return false;
  }

  /**
   * Get today's reading.  Assumes you've already called {@link #readToday} and gotten a true.
   * @return A StringBuilder containing the reading from this section for today.
   */
  StringBuilder todaysReading() {
    while (currentBook < books.length) {
      if (books[currentBook].hasNextChapter()) {
        chaptersRemaining--;
        return new StringBuilder(books[currentBook].getName())
            .append(' ')
            .append(books[currentBook].getNextChapter());
      } else {
        currentBook++;
      }
    }
    return new StringBuilder();
  }
}
