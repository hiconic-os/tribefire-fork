// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.utils.date;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

/**
 * Provides a nanosecond-precision clock implementation. This could be used to do time 
 * measurement ({@link Clock#instant()}) and printing a duration between two instants
 * with {@link com.braintribe.utils.StringTools#prettyPrintDuration(java.time.Duration, boolean, java.time.temporal.ChronoUnit)}.
 */
public class NanoClock extends Clock {
    
	private final Clock clock;
    private final long initialNanos;
    private final Instant initialInstant;
    
    public final static Clock INSTANCE = new NanoClock(); 

    public NanoClock() {
        this(Clock.systemUTC());
    }

    public NanoClock(final Clock clock) {
        this.clock = clock;
        initialInstant = clock.instant();
        initialNanos = System.nanoTime();
    }

    @Override
    public ZoneId getZone() {
        return clock.getZone();
    }

    @Override
    public Instant instant() {
        return initialInstant.plusNanos(System.nanoTime() - initialNanos);
    }

    @Override
    public Clock withZone(final ZoneId zone) {
        return new NanoClock(clock.withZone(zone));
    }

}
