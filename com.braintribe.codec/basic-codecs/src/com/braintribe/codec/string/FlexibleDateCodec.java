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
package com.braintribe.codec.string;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

public class FlexibleDateCodec implements Codec<Date, String> {
	private static Pattern pattern = Pattern.compile("(?:[yMdHmsSZ]\\(([-+]?\\d*)\\))");
	@Override
	public Date decode(String encodedValue) throws CodecException {
		Matcher matcher = pattern.matcher(encodedValue);
		Calendar calendar = Calendar.getInstance();
		Set<Integer> explicitFields = new HashSet<Integer>(); 
		while (matcher.find()) {
			char code = matcher.group(0).charAt(0);
			int argument = Integer.parseInt(matcher.group(1));
			switch (code) {
			case 'y':
				calendar.set(Calendar.YEAR, argument);
				explicitFields.add(Calendar.YEAR);
				break;
			case 'M':
				calendar.set(Calendar.MONTH, argument - 1);
				explicitFields.add(Calendar.MONTH);
				break;
			case 'd':
				calendar.set(Calendar.DAY_OF_MONTH, argument);
				explicitFields.add(Calendar.DAY_OF_MONTH);
				break;
			case 'H':
				calendar.set(Calendar.HOUR_OF_DAY, argument);
				explicitFields.add(Calendar.HOUR_OF_DAY);
				break;
			case 'm':
				calendar.set(Calendar.MINUTE, argument);
				explicitFields.add(Calendar.MINUTE);
				break;
			case 's':
				calendar.set(Calendar.SECOND, argument);
				explicitFields.add(Calendar.SECOND);
				break;
			case 'S':
				calendar.set(Calendar.MILLISECOND, argument);
				explicitFields.add(Calendar.MILLISECOND);
				break;
			case 'Z':
				int millies = (argument / 100 * 60 + argument % 100) * 60 * 1000;
				calendar.set(Calendar.ZONE_OFFSET, millies);
				break;
			default:
				throw new CodecException("unknown code " + code);
			}
		}
		int scales[] = {Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND};
		boolean foundExplicit = false;
		for (Integer field: scales) {
			if (explicitFields.contains(field))
				foundExplicit = true;
			else if (foundExplicit) {
				switch (field) {
				case Calendar.DAY_OF_MONTH:
					calendar.set(Calendar.DAY_OF_MONTH, 1);
					break;
				default:
					calendar.set(field, 0);
					break;
				}
			}
		}
		
		return calendar.getTime();
	}
	
	@Override
	public String encode(Date value) throws CodecException {
		throw new UnsupportedOperationException("not yet implemented");
	}
	
	@Override
	public Class<Date> getValueClass() {
		return Date.class;
	}

}
