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
package com.braintribe.thread.impl;

import java.util.List;

import com.braintribe.thread.api.ThreadContextScope;

public class ContextBoundRunnable implements Runnable {

	private Runnable delegate;
	private List<ThreadContextScope> scopes;

	public ContextBoundRunnable(Runnable delegate, List<ThreadContextScope> scopes) {
		this.delegate = delegate;
		this.scopes = scopes;
	}

	@Override
	public void run() {

		int i = 0;
		try {
			for (; i < scopes.size(); ++i) {
				scopes.get(i).push();
			}
		} catch (Exception e) {
			throw new ThreadContextScopingRuntimeException("Could not set the context in step: " + i + ", which is " + scopes.get(i), e);
		}
		try {
			this.delegate.run();
		} finally {
			int j = i - 1;
			try {
				for (; j >= 0; --j) {
					scopes.get(j).pop();
				}
			} catch (Exception e) {
				throw new ThreadContextScopingRuntimeException("Could not remove the context in step " + j + ", which is " + scopes.get(j), e);
			}
		}

	}

	@Override
	public String toString() {
		return this.delegate.toString();
	}
	@Override
	public int hashCode() {
		return this.delegate.hashCode();
	}
	@Override
	public boolean equals(Object o) {
		return this.delegate.equals(o);
	}

}
