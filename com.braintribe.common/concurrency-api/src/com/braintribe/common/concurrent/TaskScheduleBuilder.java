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
package com.braintribe.common.concurrent;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface TaskScheduleBuilder {

	/**
	 * Specifies whether to interrupt the task when canceling it on shutdown of the {@link TaskScheduler}.
	 * <p>
	 * This value is passed to the corresponding {@link ScheduledFuture#cancel(boolean)}.
	 */
	TaskScheduleBuilder interruptOnCancel(boolean interrupt);

	/**
	 * Specifies a time to wait for this task on cancel.
	 * <p>
	 * This is relevant when {@link TaskScheduler} is being cancelled w
	 */
	TaskScheduleBuilder waitOnCancel(long timeout, TimeUnit unit);

	/**
	 * Optionally define an error handler.
	 * <p>
	 * This also determines whether the task will be invoked again after an exception is thrown.
	 * <p>
	 * Default implementation, where the error handler simply re-throws the exception, immediately halts after first error.
	 * <p>
	 * Is you want to continue, the error handler's method must not exit with an exception.
	 */
	TaskScheduleBuilder errorHandler(TaskErrorHandler errorHandler);

	/**
	 * Final confirmation to schedules given task.
	 * 
	 * @throws RejectedExecutionException
	 *             if the backing {@link TaskScheduler} was already {@link TaskScheduler#shutdown() shut down}.
	 */
	ScheduledTask done();

}
