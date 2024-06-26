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
package com.braintribe.logging;

import java.awt.Component;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.SwingUtilities;

public class DebugUtil {

	private static final Logger logger = Logger.getLogger(DebugUtil.class);

	protected static int debugDelay = 0;
	protected static boolean profiling = false;
	protected static long startTime = System.currentTimeMillis();

	public static int getDebugDelay() {
		return debugDelay;
	}

	public static void setDebugDelay(final int debugDelay) {
		DebugUtil.debugDelay = debugDelay;
	}

	public static boolean isProfiling() {
		return profiling;
	}

	public static void setProfiling(final boolean profiling) {
		DebugUtil.profiling = profiling;
	}

	public static void debugDelay(final int ticks) {
		assertNotAwt("debugDelay()");

		if (ticks == 0) {
			return;
		}
		if (debugDelay <= 0) {
			return;
		}

		try {
			Thread.sleep(debugDelay * ticks);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public static void assertInAwt(final String what) {
		if (!SwingUtilities.isEventDispatchThread()) {
			throw new RuntimeException("Must only be called in AWT thread: " + what);
		}
	}

	public static void assertNotAwt(final String what) {
		if (SwingUtilities.isEventDispatchThread()) {
			/* DEACTIVATED DUE TO SYNCHRONIZATION PROBLEMS throw new
			 * RuntimeException("Must not be called in AWT thread: "+what); */
			logger.warn(what + " should not be called in AWT");
		}
	}

	public static long profileIn(@SuppressWarnings("unused") final String name) {
		if (!profiling) {
			return 0;
		}

		final long t = System.currentTimeMillis();

		return t;
	}

	public static void profileOut(final String name, final long inTime) {
		if (!profiling || inTime <= 0) {
			return;
		}

		final long t = System.currentTimeMillis() - inTime;

		logger.debug("PROFILING: " + name + ": " + t + " ms");
	}

	public static void profileTimeSinceStart(final String name) {
		profileOut("TOTAL TIME TO: " + name, startTime);
	}

	public static String debugName(final Object obj) {
		if (obj == null) {
			return "<null>";
		}
		if (obj instanceof Component) {
			return obj.getClass().getName() + "#" + System.identityHashCode(obj);
		}
		return obj.toString();
	}

	public static void dumpClassPath(ClassLoader cl, final PrintStream out) {
		while (cl != null) {
			out.println(cl.getClass().getName());

			if (cl instanceof URLClassLoader) {
				final URLClassLoader ucl = (URLClassLoader) cl;
				final URL[] urls = ucl.getURLs();

				for (int i = 0; i < urls.length; i++) {
					System.out.println("\t" + urls[i]);
				}
			}

			cl = cl.getParent();
		}
	}

	public static void showStackTrace() {
		final Throwable t = new Throwable();
		final StackTraceElement stacktrace[] = t.getStackTrace();
		System.out.println("#### Stacktrace");
		int index = 0;
		for (final StackTraceElement traceElement : stacktrace) {
			if (index > 0) {
				System.out.println(traceElement);
			}
			index++;
		}
	}

}
