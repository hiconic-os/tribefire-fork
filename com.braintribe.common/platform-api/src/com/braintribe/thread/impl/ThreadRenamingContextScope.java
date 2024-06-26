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

import java.util.function.Supplier;

import com.braintribe.logging.ThreadRenamer;
import com.braintribe.thread.api.ThreadContextScope;

public class ThreadRenamingContextScope implements ThreadContextScope {

	private ThreadRenamer threadRenamer;
	private Supplier<String> nameSupplier;

	public ThreadRenamingContextScope(ThreadRenamer threadRenamer, Supplier<String> nameSupplier) {
		this.threadRenamer = threadRenamer;
		this.nameSupplier = nameSupplier;
	}

	@Override
	public void push() {
		threadRenamer.push(nameSupplier);
	}

	@Override
	public void pop() {
		threadRenamer.pop();
	}

}
