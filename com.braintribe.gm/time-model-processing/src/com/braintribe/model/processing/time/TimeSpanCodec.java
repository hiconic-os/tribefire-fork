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
package com.braintribe.model.processing.time;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.model.time.TimeSpan;
import com.braintribe.model.time.TimeUnit;

public class TimeSpanCodec implements Codec<TimeSpan, Double> {
	private TimeUnit unit = TimeUnit.milliSecond;
	
	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

	@Override
	public Double encode(TimeSpan span) throws CodecException {
		if (span == null)
			return null;
		
		return TimeSpanConversion.fromTimeSpan(span).unit(unit).toValue();
	}

	@Override
	public TimeSpan decode(Double encodedValue) throws CodecException {
		if (encodedValue == null)
			return null;
		
		return TimeSpanConversion.fromValue(encodedValue, unit).toTimeSpan();
	}

	@Override
	public Class<TimeSpan> getValueClass() {
		return TimeSpan.class;
	}
}
