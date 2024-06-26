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
package com.braintribe.gwt.logging.client;

import java.util.function.Predicate;

/**
 * This Utility class helps to format a Throwable. It uses the chain of causes
 * and any message of any cause to build the string.
 * 
 * The root throwable and all causes will get a separate line with the classname
 * of the exception followed by the messages
 * 
 * @see Throwable#getCause()
 * @see Throwable#getMessage()
 * 
 * @author Dirk
 * 
 */
public class ExceptionUtil {
	
	private static final String EXCEPTION_SUFIX = "Exception:";
	
	public static String format(Throwable throwable) {
		StringBuffer builder = new StringBuffer();

		while (throwable != null) {
			if (builder.length() > 0) {
				builder.append("\ncaused by ");
			}
			builder.append(throwable.getClass().getName());
			builder.append(": ");
			builder.append(throwable.getMessage());
			Throwable cause = throwable.getCause();
			if (cause == throwable)
				break;
			throwable = cause;
		}

		return builder.toString();
	}

	public static boolean isCausedByAny(Throwable t,
			final Class<? extends Throwable>... exceptionClasses) {
		Throwable specificCause = getSpecificCause(t, new Predicate<Throwable>() {
			@Override
			public boolean test(Throwable throwable) {
				for (Class<? extends Throwable> exceptionClass : exceptionClasses) {
					if (exceptionClass == throwable.getClass())
						return true;
				}
				return false;
			}
		});
		
		return specificCause != null;
	}

	public static <T extends Throwable> T getSpecificCause(Throwable exception,
			final Class<T> exceptionClass) {
		return (T)getSpecificCause(exception, new Predicate<Throwable>() {
			@Override
			public boolean test(Throwable throwable) {
				return exceptionClass == throwable.getClass();
			}
		});
	}
	
	public static <T extends Throwable> T getSpecificCause(Throwable exception,
			Predicate<Throwable> exceptionFilter) {
		
		if (exceptionFilter.test(exception))
			return (T) exception;
		else {
			Throwable cause = exception.getCause();
			
			if (cause == null)
				return null;
			else
				return (T)getSpecificCause(cause, exceptionFilter);
		}
	}
	
	public static Throwable getLastCause(Throwable exception) {
		Throwable lastCause = null;
		while (exception != null) {
			lastCause = exception;
			exception = exception.getCause();
		}
		
		return lastCause;
	}
	
	public static String getLastMessage(Throwable throwable) {
		if (throwable == null)
			return null;
		
		String message = throwable.getMessage();
		while ((throwable = throwable.getCause()) != null) {
			if (throwable.getMessage() != null) {
				message = throwable.getMessage();
			}
		}
		
		if (message != null) {
			int index = message.indexOf(EXCEPTION_SUFIX);
			if (index != -1) {
				String initialPart = message.substring(0, index);
				if (!initialPart.contains(" ") && initialPart.length() > EXCEPTION_SUFIX.length()) {
					message = message.substring(index + EXCEPTION_SUFIX.length()).trim();
				}
			}
		}
		
		return message;
	}
	
	public static void main(String[] args) {
		Exception e = new Exception("foo", new Exception("bar", new Exception(
				"fix")));

		System.out.println(format(e));
	}
}
