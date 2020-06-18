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
import java.util.List;
import java.util.Collection;

public final class FindMeetingQuery {

    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        
        //Collects data from the request: meeting duration and the attendees
        int duration = (int) request.getDuration();
        Collection<String> attendees = request.getAttendees();
        Collection<String> opAttendees = request.getOptionalAttendees();

        //This is the collection of time ranges that we willl return that fits everyones schedule
        Collection<TimeRange> availableTimes = new ArrayList<>();
        Collection<TimeRange> unavailableTimes = new ArrayList<>();

        int startTime = 0;
        int endTime = startTime + duration;
        boolean addTime = false;

        while( startTime < TimeRange.END_OF_DAY ) {

            TimeRange unavailableMeetTime = TimeRange.fromStartDuration(startTime, duration);

            /** 
             * This loop goes through all the events and checks if it conflicts with the potential meeting time.
             * It will add it to a list of unavailble times.
             * From this I plan to reverse those values and collect the actaul availble meeting times.
             */
            for (Event event : events) {
                TimeRange eventMeetTime = event.getWhen();
                Collection<String> eventAttendees = event.getAttendees();

                if (eventMeetTime.overlaps(unavailableMeetTime) == true) {
                    //Still a iffy on the process of the disjoint.
                    if(Collections.disjoint(eventAttendees, attendees) == false) {
                        unavailableTimes.add(unavailableMeetTime);
                    }
                } 

            }
            startTime = startTime + duration;
        }

        //Resets startTime for next process of gathering surrounding time blocks
        startTime = 0;

        /**
         * This function loops through the unavailable time blocks and finds the surrounding
         * time blocks around it. (AKA the actual available times)
         */
        for (TimeRange time : unavailableTimes) {
            endTime = time.start();
            //Creates a new Time range with correct
            TimeRange overlap = TimeRange.fromStartEnd(startTime, endTime, false);
            if (overlap.duration() >= request.getDuration()) { 
                availableTimes.add(overlap);
            }
            startTime = endTime + time.duration();
        }

        //Checks for the last remaining block of time to add to the available times list
        TimeRange lastTimeBlock =  TimeRange.fromStartEnd(startTime, TimeRange.END_OF_DAY, true);
        if (lastTimeBlock.duration() >= request.getDuration()) {
            availableTimes.add(lastTimeBlock);
        }

        return availableTimes;
    }

}
