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

import static java.util.Arrays.fill;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * <p>
 * Alters the name of the current {@link Thread} by appending a supplied {@code String}.
 * 
 * <p>
 * e.g.:
 * 
 * <pre>
 * threadRenamer.push(() -> "custom name");
 * try {
 * 	// Your code
 * } finally {
 * 	threadRenamer.pop();
 * }
 * </pre>
 * 
 */
public class ThreadRenamer {

	private static final Logger logger = Logger.getLogger(ThreadRenamer.class);
	
	private static AtomicLong threadIdCounter = new AtomicLong(0);
	
	// configurable
	private boolean oneLiner;
	private String oneLinerSeparator = "->";

	// cached
	// This is protected so that the ThreadRenamerTest can access it; not nice, but better than using reflection
	protected ThreadLocal<Stack<String>> nameStack; 

	// constants
	public static final ThreadRenamer NO_OP = new ThreadRenamer(false);
	private static final char tab = '\t';
	private static final char nl = '\n';

	public ThreadRenamer(boolean enabled) {
		this(enabled, true, null);
	}

	public ThreadRenamer(boolean enabled, boolean oneLiners, String oneLinerSeparator) {
		if (enabled) {
			this.nameStack = ThreadLocal.withInitial(Stack::new);
			this.oneLiner = oneLiners;
			if (oneLinerSeparator != null) {
				this.oneLinerSeparator = oneLinerSeparator;
			}
		}
	}

	public void push(Supplier<String> nameSupplier) {

		if (nameStack != null) {

			Thread thread = Thread.currentThread();

			nameStack.get().push(thread.getName());

			// We are expecting here to have a nameSupplier (i.e., non-null) that also returns a non-null value.
			// In any case, we would not want to have a problem here, would we?
			
			String suppliedName = null;
			if (nameSupplier != null) {
				suppliedName = nameSupplier.get();
			}
			if (suppliedName == null) {
				suppliedName = "ai"; //An acronym for absent information 
			}

			Long threadId = threadIdCounter.incrementAndGet();
			String threadIdString = Long.toString(threadId.longValue(), 36);
			String threadContextString = suppliedName + "#" + threadIdString; 
			
			logger.pushContext(threadContextString);
			thread.setName(newThreadName(thread.getName(), threadContextString));

		}

	}

	public void pop() {

		if (nameStack == null) {
			return;
		}

		Stack<String> names = nameStack.get();

		//Intentionally throwing a StackEmptyException so that we know that pop() was called more often than push()
		
		String previousName = names.pop();
		logger.popContext();
		
		if (names.isEmpty()) {
			nameStack.remove();
		}

		Thread.currentThread().setName(previousName);

	}

	protected String newThreadName(String originalName, String suppliedName) {
		if (oneLiner) {
			return originalName.concat(oneLinerSeparator).concat(suppliedName);
		} else {
			char[] tabs = new char[nameStack.get().size()];
			fill(tabs, tab);
			return new StringBuilder(originalName).append(nl).append(tabs).append(suppliedName).toString();
		}
	}

}
