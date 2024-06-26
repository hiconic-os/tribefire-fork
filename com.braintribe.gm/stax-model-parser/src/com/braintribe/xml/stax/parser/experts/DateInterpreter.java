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
package com.braintribe.xml.stax.parser.experts;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.braintribe.codec.CodecException;
import com.braintribe.logging.Logger;

/**
 * helper to support the two xsd date related types 
 * xsd:dateTime
 * xsd:date
 * xsd:time
 * 
 * @author pit
 *
 */
public class DateInterpreter {
	
	private static Logger log = Logger.getLogger(DateInterpreter.class);	
	
	public DateInterpreter() {
	
	}
	
	public Date parseDateTime( String dateAsString) throws CodecException {
		try {
			DatatypeFactory df = DatatypeFactory.newInstance();
			XMLGregorianCalendar dateTime = df.newXMLGregorianCalendar(dateAsString);			
			return dateTime.toGregorianCalendar().getTime();
		} catch (Exception e) {
			String msg = "cannot parse datetime from [" + dateAsString + "]";
			log.error( msg, e);
			throw new CodecException( msg, e);
		}
	}
	
	public String formatDateTime( Date date) throws CodecException {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			DatatypeFactory df = DatatypeFactory.newInstance();
			XMLGregorianCalendar dateTime = df.newXMLGregorianCalendar(calendar);
			return dateTime.toString();
			
		} catch (Exception e) {
			String msg = "cannot print dateTime from [" + date + "]";
			log.error( msg, e);
			throw new CodecException( msg, e);
		}
	}
	
	public Date parseDate( String dateAsString) throws CodecException {
		try {
			DatatypeFactory df = DatatypeFactory.newInstance();
			XMLGregorianCalendar dateTime = df.newXMLGregorianCalendar(dateAsString);			
			return dateTime.toGregorianCalendar().getTime();
		} catch (Exception e) {
			String msg = "cannot parse date from [" + dateAsString + "]";
			log.error( msg, e);
			throw new CodecException( msg, e);
		}
	}
	
	public String formatDate( Date date) throws CodecException {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			DatatypeFactory df = DatatypeFactory.newInstance();
			
			XMLGregorianCalendar dateTime = df.newXMLGregorianCalendarDate(calendar.get( Calendar.YEAR), calendar.get( Calendar.MONDAY) + 1, calendar.get( Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
			return dateTime.toString();
		} catch (Exception e) {
			String msg = "cannot print date from [" + date + "]";
			log.error( msg, e);
			throw new CodecException( msg, e);
		}
	}
	
	public Date parseTime( String timeAsString) throws CodecException {
		try {
			DatatypeFactory df = DatatypeFactory.newInstance();
			XMLGregorianCalendar dateTime = df.newXMLGregorianCalendar(timeAsString);			
			return dateTime.toGregorianCalendar().getTime();
		} catch (Exception e) {
			String msg = "cannot parse date from [" + timeAsString + "]";
			log.error( msg, e);
			throw new CodecException( msg, e);
		}
	}
	
	public String formatTime( Date date) throws CodecException {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			DatatypeFactory df = DatatypeFactory.newInstance();
			
			XMLGregorianCalendar dateTime = df.newXMLGregorianCalendarTime(calendar.get( Calendar.HOUR), calendar.get( Calendar.MINUTE), calendar.get( Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
			return dateTime.toString();
		} catch (Exception e) {
			String msg = "cannot print date from [" + date + "]";
			log.error( msg, e);
			throw new CodecException( msg, e);
		}
	}
	
	
	
}
