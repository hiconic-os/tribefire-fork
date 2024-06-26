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
package tribefire.extension.tracing.connector.logging;

import java.util.Collection;

import com.braintribe.logging.Logger;
import com.braintribe.logging.Logger.LogLevel;

import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SpanExporter;

/**
 * Configurable logging reporter
 *
 */
public class ConfigurableLoggingExporter implements SpanExporter {

	private final static Logger logger = Logger.getLogger(ConfigurableLoggingExporter.class);

	private LogLevel logLevel;
	private boolean logAttributes;

	public ConfigurableLoggingExporter(final LogLevel logLevel, final boolean logAttributes) {
		this.logLevel = logLevel;
		this.logAttributes = logAttributes;
	}

	@Override
	public CompletableResultCode export(Collection<SpanData> spans) {
		if (spans == null || spans.isEmpty()) {
			return CompletableResultCode.ofSuccess();
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (SpanData span : spans) {
			
			sb.append(span.getName());
			sb.append(": (TraceId: ");
			sb.append(span.getTraceId());
			sb.append(") (SpanId: ");
			sb.append(span.getSpanId());
			sb.append(") (SpanContext: ");
			sb.append(span.getSpanContext());
			sb.append(") (");
			sb.append((span.getEndEpochNanos() - span.getStartEpochNanos()) / 1000 / 1000);
			sb.append("ms) ");
			if (logAttributes) {
				sb.append("attributes: ");
				sb.append(span.getAttributes());
			}
		}
		
		logger.log(logLevel, "Span reported: " + sb.toString());
		
		return CompletableResultCode.ofSuccess();
	}


	@Override
	public CompletableResultCode flush() {
		return CompletableResultCode.ofSuccess();
	}


	@Override
	public CompletableResultCode shutdown() {
		return CompletableResultCode.ofSuccess();
	}
}
