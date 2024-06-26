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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import com.braintribe.utils.StringTools;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * An extended {@link SimpleDateFormat} which implements {@link #toString()} and is NOT {@link #isLenient() lenient} by default, because lenient
 * parsing can be very dangerous: for example, pattern "dd.MM.yyyy HH:mm:ss.SSS" parses "2000.12.24 01:01:01.000", i.e. no exception is thrown, but,
 * of course, the parsed value is completely wrong.
 *
 * @author michael.lafite
 */

// false positive
@SuppressFBWarnings("EQ_DOESNT_OVERRIDE_EQUALS")
public class ExtSimpleDateFormat extends SimpleDateFormat {

	private static final long serialVersionUID = 1424363916540755457L;

	private String pattern;
	private final Map<String, Object> properties = new TreeMap<>();
	private String stringRepresentation;
	private boolean initialized;

	public ExtSimpleDateFormat(final String pattern) {
		super(pattern);
		init(pattern, null);
	}

	public ExtSimpleDateFormat(final String pattern, final Locale locale) {
		super(pattern, locale);
		init(pattern, locale);
	}

	private void init(final String pattern, final Locale locale) {
		this.pattern = pattern;
		setLenient(false);
		if (locale != null) {
			setProperty("locale", locale);
		}
		setInitialized();
	}

	protected boolean isInitialized() {
		return this.initialized;
	}

	protected void setInitialized() {
		this.initialized = true;
		updateStringRepresentation();
	}

	public String getPattern() {
		return this.pattern;
	}

	@Override
	public void setLenient(final boolean lenient) {
		if (lenient != isLenient()) {
			super.setLenient(lenient);
			final String propertyName = "lenient";
			if (lenient) {
				setProperty(propertyName, lenient);
			} else {
				// since lenient==false is the default setting (for this class),
				// we don't need it in the string representation --> remove property
				removeProperty(propertyName);
			}
		}
	}

	@Override
	public void applyPattern(final String pattern) {
		throw new UnsupportedOperationException("This method is not supported by " + getClass().getName() + "!");
	}

	@Override
	public void applyLocalizedPattern(final String pattern) {
		throw new UnsupportedOperationException("This method is not supported by " + getClass().getName() + "!");
	}

	@Override
	public void setDateFormatSymbols(final DateFormatSymbols newFormatSymbols) {
		super.setDateFormatSymbols(newFormatSymbols);
		setProperty("dateFormatSymbols", getDateFormatSymbols());
	}

	@Override
	public void set2DigitYearStart(final Date startDate) {
		super.set2DigitYearStart(startDate);
		setProperty("2DigitYearStart", get2DigitYearStart());
	}

	@Override
	public void setTimeZone(final TimeZone zone) {
		super.setTimeZone(zone);
		setProperty("timeZone", getTimeZone());
	}

	@Override
	public void setNumberFormat(final NumberFormat newNumberFormat) {
		super.setNumberFormat(newNumberFormat);
		setProperty("numberFormat", getNumberFormat());
	}

	@Override
	public void setCalendar(final Calendar newCalendar) {
		super.setCalendar(newCalendar);
		setProperty("calendar", getCalendar());
	}

	private Map<String, Object> getProperties() {
		return this.properties;
	}

	private void setProperty(final String propertyName, final Object propertyValue) {
		getProperties().put(propertyName, propertyValue);
		updateStringRepresentation();
	}

	private void removeProperty(final String propertyName) {
		getProperties().remove(propertyName);
		updateStringRepresentation();
	}

	private void updateStringRepresentation() {
		if (isInitialized()) {
			final StringBuilder stringBuilder = com.braintribe.utils.lcd.StringTools.newStringBuilder(ExtSimpleDateFormat.class.getSimpleName(),
					"[pattern=", getPattern());
			if (!getProperties().isEmpty()) {

				final String propertiesString = getProperties().toString();
				StringTools.append(stringBuilder, ",", StringTools.removeFirstAndLastCharacter(propertiesString));
			}
			stringBuilder.append(']');
			this.stringRepresentation = stringBuilder.toString();
		}
	}

	@Override
	public String toString() {
		return this.stringRepresentation;
	}
}
