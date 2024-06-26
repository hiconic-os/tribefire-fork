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
package com.braintribe.gwt.gmview.codec.client;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.model.time.TimeSpan;

/**
 * This codec is responsible for transforming a {@link TimeSpan} into String,
 * for visualization only purpose.
 * 
 */
public class TimeSpanRendererCodec implements Codec<TimeSpan, String> {
	private static final String EMPTY_STRING = "";

	@Override
	public TimeSpan decode(String encodedValue) throws CodecException {
		throw new CodecException("Decode is not supported");
	}

	@Override
	public String encode(TimeSpan timeSpan) throws CodecException {
		if (timeSpan == null)
			return EMPTY_STRING;
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(timeSpan.getValue());
		if (timeSpan.getUnit() != null) {
			//builder.append(" ").append(getShortTimeUnit(timeSpan.getUnit()));
			builder.append(" ").append(timeSpan.getUnit().toString());
		}
		return builder.toString();
	}

	/*
	public String getShortTimeUnit (TimeUnit timeUnit) {
		if (timeUnit != null) {
			SafeHtmlBuilder builder = new SafeHtmlBuilder();			
			if (timeUnit == TimeUnit.milliSecond) {
				builder.appendEscaped("ms");
			} else if (timeUnit == TimeUnit.microSecond) {				
				builder.appendEscaped("\u00B5").appendEscaped("s");
			} else if (timeUnit == TimeUnit.nanoSecond)  {
				builder.appendEscaped("ns");
			} else if (timeUnit == TimeUnit.second) { 
				builder.appendEscaped("s");
			} else if (timeUnit == TimeUnit.hour) {
				builder.appendEscaped("h");
			} else if (timeUnit == TimeUnit.minute) {
				builder.appendEscaped("m");
			} else if (timeUnit == TimeUnit.day) {
				builder.appendEscaped("d");
			} else if (timeUnit == TimeUnit.planckTime) {
				builder.appendEscaped("pt");
			}	
			return builder.toSafeHtml().asString();
		}
		return EMPTY_STRING;
	}
	*/
		
	@Override
	public Class<TimeSpan> getValueClass() {
		return TimeSpan.class;
	}
}
