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
package com.braintribe.logging.juli.handlers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * <p>
 * This is a simple extension of {@link StreamHandler} which logs to a file configured by the system property
 * <b>logging.staticfilehandler.filepath</b>
 *
 * @author christina.wilpernig, dirk.scheffler
 */
public class StaticFileHandler extends StreamHandler {

	public static final String SYSTEM_PROPERTY_FILEPATH = "logging.staticfilehandler.filepath";
	private OutputStream out;
	private ReentrantLock lock = new ReentrantLock();

	/**
	 * Creates a new <code>StaticFileHandler</code> instance. This
	 * {@link StreamHandler#setOutputStream(java.io.OutputStream) sets the OutputStream} to the {@link FileOutputStream}
	 * configured by <b>logging.staticfilehandler.filepath</b>
	 */
	public StaticFileHandler() {
		String fileName = System.getProperty(SYSTEM_PROPERTY_FILEPATH);
		try {
			out = new FileOutputStream(fileName);
			setOutputStream(out);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void publish(LogRecord record) {
		lock.lock();
		try {
			super.publish(record);
			flush();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void close() {
		lock.lock();
		try {
			flush();
			out.close();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} finally {
			lock.unlock();
		}
	}
}
