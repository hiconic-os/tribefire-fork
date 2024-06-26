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
package com.braintribe.execution.virtual;

public class VirtualThreadExecutorBuilder {

	private Integer concurrency = null;
	private Boolean waitForTasksToCompleteOnShutdown = null;
	private String threadNamePrefix = null;
	private Boolean addThreadContextToNdc = null;
	private String description = null;
	private boolean monitoring;

	private VirtualThreadExecutorBuilder() {
		//
	}

	public static VirtualThreadExecutorBuilder newPool() {
		return new VirtualThreadExecutorBuilder();
	}

	public VirtualThreadExecutorBuilder concurrency(int concurrency) {
		this.concurrency = concurrency;
		return this;
	}

	public VirtualThreadExecutorBuilder waitForTasksToCompleteOnShutdown(boolean waitForTasksToCompleteOnShutdown) {
		this.waitForTasksToCompleteOnShutdown = waitForTasksToCompleteOnShutdown;
		return this;
	}

	public VirtualThreadExecutorBuilder threadNamePrefix(String threadNamePrefix) {
		this.threadNamePrefix = threadNamePrefix;
		return this;
	}

	public VirtualThreadExecutorBuilder setAddThreadContextToNdc(boolean addThreadContextToNdc) {
		this.addThreadContextToNdc = addThreadContextToNdc;
		return this;
	}

	public VirtualThreadExecutorBuilder description(String desc) {
		this.description = desc;
		return this;
	}

	public VirtualThreadExecutorBuilder monitoring(boolean monitoring) {
		this.monitoring = monitoring;
		return this;
	}

	public VirtualThreadExecutor build() {
		if (concurrency == null) {
			throw new IllegalArgumentException("Concurrency " + concurrency + " is not set");
		}

		VirtualThreadExecutor result = new VirtualThreadExecutor(concurrency);

		applyConfiguration(result);

		return result;
	}

	private void applyConfiguration(VirtualThreadExecutor result) {
		if (waitForTasksToCompleteOnShutdown != null) {
			result.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
		}
		if (threadNamePrefix != null) {
			result.setThreadNamePrefix(threadNamePrefix);
		}
		if (addThreadContextToNdc != null) {
			result.setAddThreadContextToNdc(addThreadContextToNdc);
		}
		result.setDescription(description);
		result.setEnableMonitoring(monitoring);

		result.postConstruct();
	}
}
