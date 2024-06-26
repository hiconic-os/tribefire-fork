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

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Immutable version of {@link ExtSimpleDateFormat} that cannot be modified after initialization, i.e. all
 * <code>set/apply</code> methods throw an {@link UnsupportedOperationException}.
 *
 * @author michael.lafite
 */

public class ImmutableExtSimpleDateFormat extends ExtSimpleDateFormat {

	private static final long serialVersionUID = 4343404207569300340L;

	private static final String ERROR_MESSAGE = "Instances of " + ImmutableExtSimpleDateFormat.class.getName()
			+ " cannot be changed after initialization. Use " + ExtSimpleDateFormat.class.getName() + " instead.";

	public ImmutableExtSimpleDateFormat(final String pattern) {
		super(pattern);
	}

	public ImmutableExtSimpleDateFormat(final String pattern, final Locale locale) {
		super(pattern, locale);
	}

	@Override
	public void set2DigitYearStart(final Date startDate) {
		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}

	@Override
	public void applyPattern(final String pattern) {
		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}

	@Override
	public void applyLocalizedPattern(final String pattern) {
		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}

	@Override
	public void setDateFormatSymbols(final DateFormatSymbols newFormatSymbols) {
		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}

	@Override
	public void setCalendar(final Calendar newCalendar) {
		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}

	@Override
	public void setNumberFormat(final NumberFormat newNumberFormat) {
		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}

	@Override
	public void setTimeZone(final TimeZone zone) {
		throw new UnsupportedOperationException(ERROR_MESSAGE);
	}

	@Override
	public void setLenient(final boolean lenient) {
		if (isInitialized()) {
			throw new UnsupportedOperationException(ERROR_MESSAGE);
		} else {
			super.setLenient(lenient);
		}
	}
}
