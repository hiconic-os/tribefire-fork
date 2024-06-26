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
package com.braintribe.gwt.browserfeatures.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;

public class Console {
	
	private static DateTimeFormat dtf = DateTimeFormat.getFormat("HH:mm:ss:SSSS");
	
	public static final void logWithTime(Object obj){
		_logWithTime(obj, dtf.format(new Date()));
	}
	
	private static native final void _logWithTime(Object obj, String date) /*-{
		console.log(date + " " + obj);
	}-*/;
	
	public static void log(Object obj) {
		_log(obj);
	}
	
	private static native final void _log(Object obj) /*-{
		console.log(obj);
	}-*/;
	
	public static native final void error(Object obj) /*-{
		console.error(obj);
	}-*/;
	
	public static final void time(String process){
		if(GWT.isProdMode())
			_time(process);
	}
	
	public static final void timeEnd(String process){
		if(GWT.isProdMode())
			_timeEnd(process, false);
	}
	
//	public static final void clearAndTimeEnd(String process){
//		if(GWT.isProdMode()) _timeEnd(process, true);
//	};
	
	private static native final void _time(String process) /*-{
		console.time(process);
	}-*/;
	
	private static native final void _timeEnd(String process, boolean clear) /*-{
		if(clear)
			console.clear();
		console.timeEnd(process);
	}-*/;
	
	public static native final void debugger() /*-{
		debugger;
	}-*/;

	public static native final void clearAndLog(Object msg) /*-{
		console.clear();
		console.log(msg);
	}-*/;

}
