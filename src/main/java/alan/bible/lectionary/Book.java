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

import java.util.Objects;

class Book {
  private final String name;
  private final String[] chapters;
  private int nextChapter;

  Book(String name, int numChapters) {
    this(name, numChapters, 1);
  }

  Book(String name, int numChapters, int startChapter) {
    this.name = name;
    chapters = new String[numChapters - startChapter + 1];
    for (int i = startChapter; i <= numChapters; i++) {
      chapters[i - startChapter] = Integer.toString(i);
    }
    nextChapter = 0;
  }

  /**
   * For subclasses that want to break themselves up not by normal chapters (like Psalms, where
   * we need to break Psalm 119 up into sections)
   * @param name name of the book
   * @param chapters chapter strings
   */
  protected Book(String name, String[] chapters) {
    this.name = name;
    this.chapters = chapters;
    nextChapter = 0;
  }

  String getName() {
    return name;
  }

  int getNumChapters() {
    return chapters.length;
  }

  String getNextChapter() {
    if (hasNextChapter()) return chapters[nextChapter++];
    else return "";
  }

  boolean hasNextChapter() {
    return nextChapter < chapters.length;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return name.equals(book.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
