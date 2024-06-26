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
package com.braintribe.logging.jul;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * Provides helper methods to set custom JUL configurations.
 *
 * @author michael.lafite
 */
public class JulConfigurationHelper {

	private static final String LOGGING_PROPERTIES_PACKAGEPREFIX = "/" + JulConfigurationHelper.class.getPackage().getName().replace('.', '/') + "/";
	public static final String SIMPLE_CONSOLE_ONLY_LOGGING_PROPERTIES = LOGGING_PROPERTIES_PACKAGEPREFIX + "simple-console-only-logging.properties";

	private JulConfigurationHelper() {
		// no instantiation required
	}

	/**
	 * Configures the JUL {@link LogManager} to use a simple, console-only logging configuration which logs single-line messages to
	 * <code>System.out</code>, instead of two-liners to <code>System.err</code> (which is the JUL default). <br>
	 * This delegates to {@link #setCustomConfiguration(InputStream)}.
	 */
	public static void setSimpleConsoleOnlyLoggingConfiguration() {
		try (InputStream inputStream = JulConfigurationHelper.class.getResourceAsStream(SIMPLE_CONSOLE_ONLY_LOGGING_PROPERTIES)) {
			setCustomConfiguration(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Error while reading logging configuration!", e);
		}
	}

	/**
	 * Same as {@link #setSimpleConsoleOnlyLoggingConfiguration()}, but only sets the new configuration if there is no
	 * {@link #isCustomJulConfiguration() custom configuration} yet.
	 */
	public static void setSimpleConsoleOnlyLoggingConfigurationUnlessAlreadyConfigured() {
		if (!isCustomJulConfiguration()) {
			setSimpleConsoleOnlyLoggingConfiguration();
		}
	}

	/**
	 * Configures the JUL {@link LogManager} to use the custom logging configuration read from the passed <code>inputStream</code>. This overrides
	 * previous configuration settings (if any). This method does NOT close the stream.
	 */
	public static void setCustomConfiguration(InputStream inputStream) {
		if (inputStream == null) {
			throw new IllegalArgumentException("The passed input stream must not be null!");
		}
		try {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Error while reading logging confiuration from input stream!", e);
		}
	}

	/**
	 * Same as {@link #setCustomConfiguration(InputStream)}, but only sets the new configuration if there is no {@link #isCustomJulConfiguration()
	 * custom configuration} yet.
	 */
	public static void setCustomConfigurationUnlessAlreadyConfigured(InputStream inputStream) {
		if (!isCustomJulConfiguration()) {
			setCustomConfiguration(inputStream);
		}
	}

	/**
	 * Checks whether the current JUL configuration is a custom configuration (and not the default one).
	 */
	public static boolean isCustomJulConfiguration() {
		return !isDefaultJulConfiguration();
	}

	/**
	 * Checks whether the current JUL configuration is the default one.
	 */
	public static boolean isDefaultJulConfiguration() {
		boolean result = false;

		if (System.getProperty("java.util.logging.config.file") == null && System.getProperty("java.util.logging.config.class") == null) {

			LogManager manager = LogManager.getLogManager();

			// see $JAVA_HOME/conf/logging.properties
			if ("%h/java%u.log".equals(manager.getProperty("java.util.logging.FileHandler.pattern")) //
					&& "50000".equals(manager.getProperty("java.util.logging.FileHandler.limit")) //
					&& "1".equals(manager.getProperty("java.util.logging.FileHandler.count")) //
					&& "100".equals(manager.getProperty("java.util.logging.FileHandler.maxLocks"))) {
				// pretty safe to say this is the default JUL configuration
				result = true;

				// in Java 8 one could also check for "com.xyz.foo.level", but this (correctly) is commented out in more recent versions
			}
		}
		return result;
	}

}
