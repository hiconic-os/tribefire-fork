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
package tribefire.extension.tracing.templates.api.connector;

import java.util.Map;
import java.util.Set;

import com.braintribe.model.notification.Level;

import tribefire.extension.tracing.model.deployment.service.DefaultAttribute;

/**
 * 
 *
 */
public interface TracingTemplateConnectorContext {

	Set<String> getEntityTypeInclusions();

	Set<String> getEntityTypeHierarchyInclusions();

	Set<String> getEntityTypeExclusions();

	Set<String> getEntityTypeHierarchyExclusions();

	Set<String> getUserInclusions();

	Set<String> getUserExclusions();

	Set<DefaultAttribute> getDefaultAttributes();

	Map<String, String> getCustomAttributes();

	Boolean getDefaultTracingEnabled();

	String getComponentName();

	String getTenant();

	String getServiceName();

	Level getAddAttributesFromNotificationsMessage();

	Level getAddAttributesFromNotificationsDetailsMessage();

	static TracingTemplateConnectorContextBuilder builder() {
		throw new IllegalStateException("'" + TracingTemplateConnectorContextBuilder.class.getName()
				+ "' needs to implement builder method. Either unintentionally used this base interface or the actual implementation is not overriding this method");
	}
}