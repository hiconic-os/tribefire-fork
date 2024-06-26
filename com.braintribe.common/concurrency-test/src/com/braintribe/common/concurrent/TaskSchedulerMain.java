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

import static com.braintribe.utils.SysPrint.spOut;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This is not a real test, just main method to show task scheduling works.
 * <p>
 * <ul>
 * <li>We start {@link PeriodicJob} every 500 ms.
 * <li>Each run sleeps 250 and prints before/after.
 * <li>1st job runs 500-750 ms after job scheduled.
 * <li>2nd job runs 1000-1250 ms after job scheduled.
 * <li>Main thread calls shutdown 1100ms after job scheduled.
 * <li>Job is then either interrupted, or runs till the end (depending on {@link #INTERRUPT_TASKS_ON_SHUTDOWN}), and no job starts after that.
 * </ul>
 * 
 * @author peter.gazdik
 */
public class TaskSchedulerMain {

	private static final boolean INTERRUPT_TASKS_ON_SHUTDOWN = false;

	public static void main(String[] args) {
		new TaskSchedulerMain().run();
		spOut("Ending Main Thread");
	}

	private void run() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		TaskScheduler scheduler = taskScheduler(executor);

		scheduler.scheduleAtFixedRate("Job1", new PeriodicJob("Job1"), 500, 500, TimeUnit.MILLISECONDS) //
				.interruptOnCancel(INTERRUPT_TASKS_ON_SHUTDOWN) //
				.done();

		spOut("Main is going to sleep");
		try {
			Thread.sleep(1100);
		} catch (InterruptedException e) {
			throw new RuntimeException("", e);
		}

		spOut("Main woke up and will shut down.");

		scheduler.awaitTermination();

		try {
			executor.shutdownNow();
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private TaskScheduler taskScheduler(ScheduledExecutorService executor) {
		TaskSchedulerImpl bean = new TaskSchedulerImpl();
		bean.setName("Platform-Task-Scheduler");
		bean.setExecutor(executor);

		return bean;
	}

	private class PeriodicJob implements Runnable {

		private final String name;

		private int run = 0;

		public PeriodicJob(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			print("[" + name + "] #" + ++run + " STARTED");

			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				print("[" + name + "] #" + run + " INTERRUPTED!!!!");
				return;
			}

			print("[" + name + "] #" + run + " finished");
		}

		private void print(String s) {
			System.out.println(s);
		}

	}

}
