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
package com.braintribe.common.scoping;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import com.braintribe.common.lcd.function.CheckedRunnable;
import com.braintribe.common.lcd.function.CheckedSupplier;

public interface Scoping {
	<T> T call(Callable<T> callable) throws Exception;
	
	<T> T execute(Supplier<T> supplier);
	
	<T, E extends Throwable> T executeThrowing(CheckedSupplier<T, E> supplier) throws E;
	
	void run(Runnable runnable);
	
	<E extends Throwable> void runThrowing(CheckedRunnable<E> runnable) throws E;
	
	static Scoping with(Runnable before, Runnable after) {
		Objects.requireNonNull(before, "Argument before may not be null");
		Objects.requireNonNull(after, "Argument after may not be null");
		return new GenericScoping(before, after);
	}
}

class GenericScoping implements Scoping {
	private Runnable before;
	private Runnable after;
	
	public GenericScoping(Runnable before, Runnable after) {
		this.before = before;
		this.after = after;
	}

	@Override
	public <E extends Throwable> void runThrowing(CheckedRunnable<E> runnable) throws E {
		before.run();
		try {
			runnable.run();
		}
		finally {
			after.run();
		}
	}
	
	@Override
	public void run(Runnable runnable) {
		before.run();
		try {
			runnable.run();
		}
		finally {
			after.run();
		}
	}
	
	@Override
	public <T, E extends Throwable> T executeThrowing(CheckedSupplier<T, E> supplier) throws E {
		before.run();
		try {
			return supplier.get();
		}
		finally {
			after.run();
		}
	}
	
	@Override
	public <T> T execute(Supplier<T> supplier) {
		before.run();
		try {
			return supplier.get();
		}
		finally {
			after.run();
		}
	}
	
	@Override
	public <T> T call(Callable<T> callable) throws Exception {
		before.run();
		try {
			return callable.call();
		}
		finally {
			after.run();
		}
	}
}
