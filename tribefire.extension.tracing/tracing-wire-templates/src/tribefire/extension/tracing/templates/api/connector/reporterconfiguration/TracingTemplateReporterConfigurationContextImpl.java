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
package tribefire.extension.tracing.templates.api.connector.reporterconfiguration;

import tribefire.extension.tracing.templates.api.connector.senderconfiguration.TracingTemplateSenderConfigurationContext;

public class TracingTemplateReporterConfigurationContextImpl
		implements TracingTemplateReporterConfigurationContext, TracingTemplateReporterConfigurationContextBuilder {

	private int flushInterval;

	private int maxQueueSize;

	private boolean logSpans;

	private TracingTemplateSenderConfigurationContext senderConfigurationContext;

	@Override
	public TracingTemplateReporterConfigurationContextBuilder setFlushInterval(int flushInterval) {
		this.flushInterval = flushInterval;
		return this;
	}

	@Override
	public TracingTemplateReporterConfigurationContextBuilder setMaxQueueSize(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
		return this;
	}

	@Override
	public TracingTemplateReporterConfigurationContextBuilder setSenderConfigurationContext(
			TracingTemplateSenderConfigurationContext senderConfigurationContext) {
		this.senderConfigurationContext = senderConfigurationContext;
		return this;
	}

	@Override
	public int getFlushInterval() {
		return flushInterval;
	}

	@Override
	public int getMaxQueueSize() {
		return maxQueueSize;
	}

	@Override
	public TracingTemplateSenderConfigurationContext getSenderConfigurationContext() {
		return senderConfigurationContext;
	}

	@Override
	public TracingTemplateReporterConfigurationContext build() {
		return this;
	}

	@Override
	public String toString() {
		return "TracingTemplateReporterConfigurationContextImpl [flushInterval=" + flushInterval + ", maxQueueSize=" + maxQueueSize + ", logSpans="
				+ logSpans + ", senderConfigurationContext=" + senderConfigurationContext + "]";
	}

}
