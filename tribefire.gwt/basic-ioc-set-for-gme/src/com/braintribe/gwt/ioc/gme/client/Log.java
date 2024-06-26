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
package com.braintribe.gwt.ioc.gme.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.logging.client.LogConfig;
import com.braintribe.gwt.logging.client.LogConsole;
import com.braintribe.gwt.logging.client.LogEventBuffer;
import com.braintribe.gwt.logging.client.LogListener;
import com.braintribe.gwt.logging.ui.gxt.client.LogWindow;
import com.braintribe.provider.SingletonBeanProvider;

/**
 * This is the IoC configuration for the Logging.
 * @author Dirk
 *
 */
class Log {
	
	private static Supplier<LogEventBuffer> logEventBuffer = new SingletonBeanProvider<LogEventBuffer>() {
		@Override
		public LogEventBuffer create() throws Exception {
			LogEventBuffer bean = new LogEventBuffer();
			return bean;
		}
	};
	
	protected static Supplier<LogWindow> logWindow = new SingletonBeanProvider<LogWindow>() {
		@Override
		public LogWindow create() throws Exception {
			LogWindow bean = new LogWindow();
			bean.setLogEventBuffer(logEventBuffer.get());
			return bean;
		}
	};

	private static Supplier<LogConsole> logConsole = new SingletonBeanProvider<LogConsole>() {
		@Override
		public LogConsole create() throws Exception {
			LogConsole bean = new LogConsole();
			return bean;
		}
	};

	private static Supplier<List<LogListener>> logListeners = new SingletonBeanProvider<List<LogListener>>() {
		@Override
		public List<LogListener> create() throws Exception {
			List<LogListener> bean = new ArrayList<LogListener>();
			bean.add(logEventBuffer.get());
			//bean.add(logWindow.get());
			bean.add(logConsole.get());
			return bean;
		}
	};
	
	protected static Supplier<LogConfig> logConfig = new SingletonBeanProvider<LogConfig>() {
		@Override
		public LogConfig create() throws Exception {
			LogConfig bean = new LogConfig();
			bean.setLogListeners(logListeners.get());
			bean.setErrorDialogLogEventBuffer(logEventBuffer.get());
			bean.setProfilingEnabled(Runtime.profilingEnablement.get());
			bean.setLogLevel(Runtime.logLevel.get());
			//ErrorDialog.addExceptionFilterAction(sessionTimeoutExceptionFilter.get(), sessionTimeoutAction.get());
			return bean;
		}
	};
	
	/*private static Supplier<SessionTimeoutExceptionFilter> sessionTimeoutExceptionFilter = new SingletonBeanProvider<SessionTimeoutExceptionFilter>() {
		@Override
		public SessionTimeoutExceptionFilter create() throws Exception {
			SessionTimeoutExceptionFilter bean = publish(new SessionTimeoutExceptionFilter());
			return bean;
		}
	};
	
	private static Supplier<SessionTimeoutAction> sessionTimeoutAction = new SingletonBeanProvider<SessionTimeoutAction>() {
		@Override
		public SessionTimeoutAction create() throws Exception {
			SessionTimeoutAction bean = publish(new SessionTimeoutAction());
			return bean;
		}
	};*/
	
}
