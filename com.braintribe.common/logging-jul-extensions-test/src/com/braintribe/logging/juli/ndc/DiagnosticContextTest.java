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
package com.braintribe.logging.juli.ndc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.logging.StreamHandler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.braintribe.logging.Logger;
import com.braintribe.logging.juli.formatters.simple.SimpleFormatter;

public class DiagnosticContextTest {

	protected ByteArrayOutputStream logBuffer = null;
	protected StreamHandler streamHandler = null;

	@Before
	public void initialize() throws Exception {

		if (this.streamHandler == null) {
			this.logBuffer = new ByteArrayOutputStream();
			this.streamHandler = new StreamHandler(this.logBuffer, new SimpleFormatter("%4$-7s %7$-33s '%5$s' %6$s [%9$s - %10$s]%n"));

			java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DiagnosticContextTest.class.getName());
			logger.addHandler(this.streamHandler);
		}
	}

	@Ignore
	protected String getLastLogLine() throws Exception {
		this.streamHandler.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(this.logBuffer.toByteArray()), "UTF-8"));
		String line = null;
		String lastLine = null;
		while ((line = br.readLine()) != null) {
			lastLine = line;
		}
		br.close();
		this.logBuffer.reset();
		if (lastLine != null) {
			lastLine = lastLine.trim();
		}
		return lastLine;
	}

	@Test
	public void testNdcSingleElement() throws Exception {

		Logger logger = Logger.getLogger(DiagnosticContextTest.class);
		logger.removeContext();
		logger.clearMdc();

		logger.pushContext("test");
		logger.info("Test Message");

		String lastLine = this.getLastLogLine();

		Assert.assertEquals(true, lastLine.endsWith("[test - ]"));
	}

	@Test
	public void testNdcMultipleElements() throws Exception {

		Logger logger = Logger.getLogger(DiagnosticContextTest.class);
		logger.removeContext();
		logger.clearMdc();

		logger.pushContext("hello");
		logger.pushContext("world");
		logger.info("Test Message");

		String lastLine = this.getLastLogLine();

		Assert.assertEquals(true, lastLine.endsWith("[world,hello - ]"));
	}

	@Test
	public void testMdcSingleElement() throws Exception {

		Logger logger = Logger.getLogger(DiagnosticContextTest.class);
		logger.removeContext();
		logger.clearMdc();

		logger.put("hello", "world");
		logger.info("Test Message");

		String lastLine = this.getLastLogLine();

		Assert.assertEquals(true, lastLine.endsWith("[ - hello=world]"));
	}

	@Test
	public void testMdcMultipleElements() throws Exception {

		Logger logger = Logger.getLogger(DiagnosticContextTest.class);
		logger.removeContext();
		logger.clearMdc();

		logger.put("key1", "value1");
		logger.put("key2", "value2");
		logger.info("Test Message");

		String lastLine = this.getLastLogLine();

		Assert.assertEquals(true, lastLine.contains("key1=value1"));
		Assert.assertEquals(true, lastLine.contains("key2=value2"));
	}
}
