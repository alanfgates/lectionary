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

class Holiday {
  private final String name;
  private final String[] reading;
  private final Calendar date;
  private final int year;

  Holiday(String name, Calendar date, int year, String... reading) {
    this.name = name;
    this.reading = reading;
    this.date = date;
    this.year = year;
  }

  public String getName() {
    return name;
  }

  boolean readToday(Calendar today) {
    return today.equals(date);
  }

  String todaysReading() {
    /*
    if (reading.length == 1) return reading[0];
    else if (reading.length == 2) return reading[(year - Main.START_YEAR) % 2];
    else if (reading.length == 3) return reading[(year - Main.START_YEAR) % 3];
    else if (reading.length == 4) return reading[(year - Main.START_YEAR) % 4];
    else throw new RuntimeException("Help!");
    */
    return reading[year % reading.length];
  }
}
