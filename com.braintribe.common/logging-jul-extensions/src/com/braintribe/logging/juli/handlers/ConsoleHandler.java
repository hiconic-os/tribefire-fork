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

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * This is a simple extension of {@link StreamHandler} which logs to the console. This class is similar to
 * {@link java.util.logging.ConsoleHandler}, but logs to <code>System.out</code> (instead of <code>System.err</code>).
 * <p>
 * Note that this class cannot just extend {@link java.util.logging.ConsoleHandler} and
 * {@link StreamHandler#setOutputStream(java.io.OutputStream) change the OutputStream}, since that would close the
 * initial <code>OutputStream</code> which means one could no longer write to <code>System.err</code>.
 *
 * @author michael.lafite
 */
public class ConsoleHandler extends StreamHandler {

	private ReentrantLock lock = new ReentrantLock();

	/**
	 * Creates a new <code>ConsoleHandler</code> instance. This {@link StreamHandler#setOutputStream(java.io.OutputStream)
	 * sets the OutputStream} to <code>System.out</code>.
	 */
	public ConsoleHandler() {
		setOutputStream(System.out);
	}

	// /**
	// * This method merely exists to make it final so that it can be safely called from the constructor.
	// */
	// @Override
	// protected final synchronized void setOutputStream(OutputStream out) throws SecurityException {
	// super.setOutputStream(out);
	// }

	/**
	 * Similar to {@link java.util.logging.ConsoleHandler#publish(LogRecord)}.
	 */
	@Override
	public void publish(LogRecord logRecord) {
		lock.lock();
		try {
			super.publish(logRecord);
			flush();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Similar to {@link java.util.logging.ConsoleHandler#close()}.
	 */
	@Override
	public void close() {
		lock.lock();
		try {
			flush();
		} finally {
			lock.unlock();
		}
	}
}
