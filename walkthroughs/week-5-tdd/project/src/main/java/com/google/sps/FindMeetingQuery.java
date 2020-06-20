// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;

public final class FindMeetingQuery {

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        
      // Collects data from the request: meeting duration and the attendees
      int duration = (int) request.getDuration();
      Collection<String> attendees = request.getAttendees();

      // This is the collection of time ranges that we will return that fits everyones schedule.
      Collection<TimeRange> availableTimes = new ArrayList<>();
      Collection<TimeRange> potentialTimes = new ArrayList<>();

      int startTime = 0;
      
      // Cycles through the day, incrementally to check the potential time range.
      while (startTime + duration < TimeRange.END_OF_DAY) {

        TimeRange potentialMeetTime = TimeRange.fromStartDuration(startTime, duration);

        for (Event event : events) {
          TimeRange eventMeetTime = event.getWhen();
          Collection<String> eventAttendees = event.getAttendees();
          if (!eventMeetTime.overlaps(potentialMeetTime)) {
            if (potentialTimeConflicts(eventAttendees)) {
              availableTimes.add(potentialMeetTime);
            }
          } 
        }
        startTime++;
      }    

      // Adds for the last remaining block of time to add to the available times list
      TimeRange lastTimeBlock =  TimeRange.fromStartEnd(startTime, TimeRange.END_OF_DAY, true);
      if (lastTimeBlock.duration() >= request.getDuration()) {
        availableTimes.add(lastTimeBlock);
      }
      return availableTimes;
    }

    /*
     * TODO: Implement function that checks if the attendees needs to attend the meeting
     */
    public boolean potentialTimeConflicts(Collection<String> eventAttendees) { 
        return (Collections.disjoint(eventAttendees, attendees));
    }

}
