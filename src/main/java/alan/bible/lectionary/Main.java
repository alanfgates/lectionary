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

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gates on 12/28/14.
 */
public class Main {

  public static int START_YEAR = 2015;
  public static int MAX_YEAR = 2020;

  public static void main(String[] args) {
    if (args.length != 1) usage();

    int year = Integer.valueOf(args[0]);
    if (year < START_YEAR || year > MAX_YEAR) usage();

    Period advent = new Advent(year);
    Period epiphanyPlus = new EpiphanyThroughNormal(year);
    advent.determineReadings(System.out);
    epiphanyPlus.determineReadings(System.out);
  }

  private static void usage() {
    System.out.println("Usage: lectionary <year>");
    System.out.println("Year must be between 2015 and 2019");
    throw new RuntimeException();
  }

  static List<Integer> weekdays = Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY,
      Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY);
}
