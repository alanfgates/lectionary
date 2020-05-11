package alan.bible.lectionary;

import java.util.Objects;

class Book {
  private final String name;
  private int nextChapter;
  private int numChapters;

  Book(String name, int numChapters) {
    this.name = name;
    this.numChapters = numChapters;
    nextChapter = 1;
  }

  String getName() {
    return name;
  }

  String getNextChapter() {
    if (hasNextChapter()) return Integer.toString(nextChapter++);
    else return "";
  }

  boolean hasNextChapter() {
    return nextChapter <= numChapters;
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
