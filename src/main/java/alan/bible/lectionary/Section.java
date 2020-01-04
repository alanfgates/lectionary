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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class Section {
  private final Book[] books;
  private final int totalReadings;
  private int currentBook;

  Section(List<Book> books) {
    this.books = books.toArray(new Book[0]);
    currentBook = 0;
    int tmpTotalReadings = 0;
    for (Book book : books) tmpTotalReadings += book.getNumChapters();
    totalReadings = tmpTotalReadings;

    /*
    int baseReadingsPerWeek = totalReadings / numWeeks;
    int readingsPerWeekRemainder = totalReadings % numWeeks;

    readings = new HashMap<>(numWeeks);
    for (int i = 0; i < numWeeks; i++) {
      int numReadingsThisWeek = baseReadingsPerWeek + (i > readingsPerWeekRemainder ? 0 : 1);
      List<String> thisWeeksReadings = new ArrayList<>(numReadingsThisWeek);
      for (int j = 0; j < numReadingsThisWeek; j++) thisWeeksReadings.add(getNextReading());
      readings.put(i, thisWeeksReadings);
    }
    */

  }

  int getTotalReadings() {
    return totalReadings;
  }

  String getNextReading() {
    while (currentBook < books.length) {
      if (books[currentBook].hasNextChapter()) {
        return books[currentBook].getName() + " " + books[currentBook].getNextChapter();
      } else {
        currentBook++;
      }
    }
    return "";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Section section = (Section) o;
    return totalReadings == section.totalReadings &&
        Arrays.equals(books, section.books);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(totalReadings);
    result = 31 * result + Arrays.hashCode(books);
    return result;
  }
}
