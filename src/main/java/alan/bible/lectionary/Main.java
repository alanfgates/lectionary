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
import java.util.List;

public class Main {

  public static void main(String[] args) {
    if (args.length != 1) usage();

    int year = Integer.parseInt(args[0]);

    LiturgicalCalendar calendar = new LiturgicalCalendar(year);
    List<Period> periods = new ArrayList<>();
    EasterPentecost easter = new EasterPentecost(calendar);
    periods.add(easter);
    HolyWeek holyWeek = new HolyWeek(calendar, easter);
    periods.add(holyWeek);
    periods.add(new Advent(calendar));
    periods.add(new ChristmasEpiphanyLent(calendar, holyWeek));
    for (Period period : periods) period.determineReadings();
    calendar.printReadings(System.out);
  }

  private static void usage() {
    System.out.println("Usage: lectionary <year>");
    throw new RuntimeException();
  }
}
