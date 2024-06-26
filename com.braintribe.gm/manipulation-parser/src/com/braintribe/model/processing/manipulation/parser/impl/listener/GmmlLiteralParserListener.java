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
package com.braintribe.model.processing.manipulation.parser.impl.listener;

import static com.braintribe.model.processing.manipulation.parser.impl.FragmentsBuilder.dateOffset;
import static com.braintribe.model.processing.manipulation.parser.impl.FragmentsBuilder.timeZoneOffset;
import static com.braintribe.utils.lcd.StringTools.getLastNCharacters;
import static com.braintribe.utils.lcd.StringTools.removeLastNCharacters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.braintribe.model.processing.manipulation.marshaller.ManipulationStringifier;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.BooleanValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.CalendarOffsetValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.DateOffsetContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.DateValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.DecimalValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.DoubleValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.EscBContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.EscBSContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.EscFContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.EscNContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.EscRContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.EscSQContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.EscTContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.FloatValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.IntegerDecimalRepresenationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.IntegerHexRepresentationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.LiteralValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.LongDecimalRepresenationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.LongHexRepresentationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.NullValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.PlainContentContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.StringValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.TimeZoneOffsetContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.UnicodeEscapeContext;
import com.braintribe.model.time.CalendarOffset;
import com.braintribe.model.time.DateOffsetUnit;
import com.braintribe.utils.format.lcd.FormatTool;

public abstract class GmmlLiteralParserListener extends GmmlBasicParserListener {

	private static final char MILLISECOND = 's';
	private static final char SECOND = 'S';
	private static final char MINUTE = 'm';
	private static final char HOUR = 'H';
	private static final char DAY = 'D';
	private static final char MONTH = 'M';
	private static final char YEAR = 'Y';

	@Override
	public void enterLiteralValue(LiteralValueContext ctx) {
		// noop
	}

	@Override
	public void exitLiteralValue(LiteralValueContext ctx) {
		// noop
	}

	@Override
	public void enterDateValue(DateValueContext ctx) {
		push(new ArrayList<>());
	}
	@Override
	public void exitDateValue(DateValueContext ctx) {
		List<CalendarOffset> offsetList = pop();
		Date date = FormatTool.getExpert().getDateFormat().createDateFromOffsetList(offsetList);
		push(date);
	}

	@Override
	public void enterCalendarOffsetValue(CalendarOffsetValueContext ctx) {
		// noop
	}

	@Override
	public void exitCalendarOffsetValue(CalendarOffsetValueContext ctx) {
		// noop
	}

	@Override
	public void enterDateOffset(DateOffsetContext ctx) {
		// noop
	}

	@Override
	public void exitDateOffset(DateOffsetContext ctx) {
		String dateFragment = ctx.DateOffset().getText();
		char fragmentIdentifier = dateFragment.charAt(dateFragment.length() - 1);
		dateFragment = cutLastChar(dateFragment);

		int dateInteger = Integer.parseInt(dateFragment);
		CalendarOffset offset = null;
		switch (fragmentIdentifier) {

			case YEAR:
				offset = dateOffset(dateInteger, DateOffsetUnit.year);
				break;
			case MONTH:
				offset = dateOffset(--dateInteger, DateOffsetUnit.month);
				break;
			case DAY:
				offset = dateOffset(dateInteger, DateOffsetUnit.day);
				break;
			case HOUR:
				offset = dateOffset(dateInteger, DateOffsetUnit.hour);
				break;
			case MINUTE:
				offset = dateOffset(dateInteger, DateOffsetUnit.minute);
				break;
			case SECOND:
				offset = dateOffset(dateInteger, DateOffsetUnit.second);
				break;
			case MILLISECOND:
				offset = dateOffset(dateInteger, DateOffsetUnit.millisecond);
				break;
			default:
				throw new IllegalStateException("Unidentified dateOffset identifier used: " + fragmentIdentifier);
		}
		List<CalendarOffset> offsets = peek();
		offsets.add(offset);
	}

	@Override
	public void enterTimeZoneOffset(TimeZoneOffsetContext ctx) {
		// noop
	}
	@Override
	public void exitTimeZoneOffset(TimeZoneOffsetContext ctx) {
		String dateFragment = ctx.TimeZoneOffset().getText();
		dateFragment = cutLastChar(dateFragment);
		CalendarOffset offset = timeZoneOffset(parseTimeZone(dateFragment));
		List<CalendarOffset> offsets = peek();
		offsets.add(offset);
	}

	private static int parseTimeZone(String zoneFormat) {
		int result = 0;
		// parse the sign (+ -)
		byte sign = 1;
		byte index = 0;
		char c = zoneFormat.charAt(0);
		if (c == '+') {
			sign = 1;
			index = 1;
		} else if (c == '-') {
			sign = -1;
			index = 1;
		}
		// Parse hh
		parse: {
			c = zoneFormat.charAt(index++);
			int hours = c - '0';
			c = zoneFormat.charAt(index++);
			hours = hours * 10 + (c - '0');

			if (hours > 23) {
				break parse;
			}

			// Proceed with parsing mm
			c = zoneFormat.charAt(index++);

			int minutes = c - '0';
			c = zoneFormat.charAt(index++);
			minutes = minutes * 10 + (c - '0');
			if (minutes > 59) {
				break parse;
			}

			minutes += hours * 60;
			result = minutes * sign;
		}
		if (result == 0 && !(zoneFormat.equals("0000") || zoneFormat.equals("-0000") || zoneFormat.equals("+0000"))) {
			throw new IllegalStateException("TimeZone could not be parsed");
		}
		return result;
	}

	@Override
	public void enterBooleanValue(BooleanValueContext ctx) {
		// noop
	}

	@Override
	public void exitBooleanValue(BooleanValueContext ctx) {
		push(Boolean.valueOf(ctx.Boolean().getText()));
	}

	@Override
	public void enterStringValue(StringValueContext ctx) {
		push(new StringBuilder());
	}

	@Override
	public void exitStringValue(StringValueContext ctx) {
		push(pop().toString());
	}

	@Override
	public void enterPlainContent(PlainContentContext ctx) {
		// noop
	}
	@Override
	public void exitPlainContent(PlainContentContext ctx) {
		appendStringFragment(ctx.getText());
	}

	@Override
	public void enterEscB(EscBContext ctx) {
		// noop
	}

	@Override
	public void exitEscB(EscBContext ctx) {
		appendStringFragment("\b");
	}

	@Override
	public void enterEscBS(EscBSContext ctx) {
		// noop
	}

	@Override
	public void exitEscBS(EscBSContext ctx) {
		appendStringFragment("\\");
	}

	@Override
	public void enterEscF(EscFContext ctx) {
		// noop
	}

	@Override
	public void exitEscF(EscFContext ctx) {
		appendStringFragment("\f");
	}

	@Override
	public void enterEscN(EscNContext ctx) {
		// noop
	}

	@Override
	public void exitEscN(EscNContext ctx) {
		appendStringFragment("\n");
	}

	@Override
	public void enterEscR(EscRContext ctx) {
		// noop
	}

	@Override
	public void exitEscR(EscRContext ctx) {
		appendStringFragment("\r");
	}

	@Override
	public void enterEscSQ(EscSQContext ctx) {
		// noop
	}

	@Override
	public void exitEscSQ(EscSQContext ctx) {
		appendStringFragment("\'");
	}

	@Override
	public void enterEscT(EscTContext ctx) {
		// noop
	}

	@Override
	public void exitEscT(EscTContext ctx) {
		appendStringFragment("\t");
	}

	@Override
	public void enterUnicodeEscape(UnicodeEscapeContext ctx) {
		// noop
	}

	/**
	 * PGA: The implementation uses the magical number 32. I got it from the static initializer of {@link ManipulationStringifier}
	 */
	@Override
	public void exitUnicodeEscape(UnicodeEscapeContext ctx) {
		String s = ctx.UnicodeEscape().getText();
		s = s.substring(2);
		int value = Integer.parseInt(s, 16);
		if (value < 32) {
			appendStringFragment(Character.toString((char) (byte) value));

		} else {
			appendStringFragment(s);
		}
	}

	private void appendStringFragment(String s) {
		StringBuilder builder = peek();
		builder.append(s);
	}

	@Override
	public void enterDecimalValue(DecimalValueContext ctx) {
		// noop
	}

	@Override
	public void exitDecimalValue(DecimalValueContext ctx) {

		String value = ctx.DecimalLiteral().getText();
		value = cutLastChar(value);

		BigDecimal bigDecimalValue = new BigDecimal(value);

		push(bigDecimalValue);
	}

	@Override
	public void enterFloatValue(FloatValueContext ctx) {
		// noop
	}

	@Override
	public void exitFloatValue(FloatValueContext ctx) {
		String value = ctx.FloatLiteral().getText();
		value = cutLastChar(value);

		Float floatValue = Float.parseFloat(value);

		push(floatValue);
	}

	@Override
	public void enterDoubleValue(DoubleValueContext ctx) {
		// noop
	}

	@Override
	public void exitDoubleValue(DoubleValueContext ctx) {
		String value = ctx.DoubleLiteral().getText();

		if (getLastNCharacters(value, 1).toUpperCase().equals("D"))
			value = cutLastChar(value);

		Double doubleValue = Double.parseDouble(value);

		push(doubleValue);
	}

	private String cutLastChar(String value) {
		return removeLastNCharacters(value, 1);
	}

	@Override
	public void enterLongDecimalRepresenation(LongDecimalRepresenationContext ctx) {
		// noop
	}
	@Override
	public void exitLongDecimalRepresenation(LongDecimalRepresenationContext ctx) {

		String value = ctx.LongBase10Literal().getText();
		value = cutLastChar(value);

		Long longValue = Long.parseLong(value);

		push(longValue);
	}

	@Override
	public void enterLongHexRepresentation(LongHexRepresentationContext ctx) {
		// noop
	}

	@Override
	public void exitLongHexRepresentation(LongHexRepresentationContext ctx) {

		String value = ctx.LongBase16Literal().getText();
		if (value.charAt(0) != '0') {
			value = value.charAt(0) + value.substring(3, value.length() - 1);
		} else {
			value = value.substring(2, value.length() - 1);
		}
		Long longValue = Long.parseLong(value, 16);

		push(longValue);
	}

	@Override
	public void enterIntegerDecimalRepresenation(IntegerDecimalRepresenationContext ctx) {
		// noop
	}

	@Override
	public void exitIntegerDecimalRepresenation(IntegerDecimalRepresenationContext ctx) {

		String value = ctx.IntegerBase10Literal().getText();
		Integer integerValue = Integer.parseInt(value);

		push(integerValue);
	}

	@Override
	public void enterIntegerHexRepresentation(IntegerHexRepresentationContext ctx) {
		// noop
	}

	@Override
	public void exitIntegerHexRepresentation(IntegerHexRepresentationContext ctx) {

		String value = ctx.IntegerBase16Literal().getText();
		if (value.charAt(0) != '0') {
			value = value.charAt(0) + value.substring(3);
		} else {
			value = value.substring(2);
		}

		Integer integerValue = Integer.parseInt(value, 16);

		push(integerValue);
	}

	@Override
	public void enterNullValue(NullValueContext ctx) {
		// noop
	}

	@Override
	public void exitNullValue(NullValueContext ctx) {
		push(null);
	}

}
