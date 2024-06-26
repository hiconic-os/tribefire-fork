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
public interface TracingTemplateConnectorContextBuilder {

	TracingTemplateConnectorContextBuilder setEntityTypeInclusions(Set<String> entityTypeInclusions);

	TracingTemplateConnectorContextBuilder setEntityTypeHierarchyInclusions(Set<String> entityTypeHierarchyInclusions);

	TracingTemplateConnectorContextBuilder setEntityTypeExclusions(Set<String> entityTypeExclusions);

	TracingTemplateConnectorContextBuilder setEntityTypeHierarchyExclusions(Set<String> entityTypeHierarchyExclusions);

	TracingTemplateConnectorContextBuilder setUserInclusions(Set<String> userInclusions);

	TracingTemplateConnectorContextBuilder setUserExclusions(Set<String> userExclusions);

	TracingTemplateConnectorContextBuilder setDefaultAttributes(Set<DefaultAttribute> defaultAttributes);

	TracingTemplateConnectorContextBuilder setCustomAttributes(Map<String, String> customAttributes);

	TracingTemplateConnectorContextBuilder setDefaultTracingEnabled(Boolean defaultTracingEnabled);

	TracingTemplateConnectorContextBuilder setComponentName(String componentName);

	TracingTemplateConnectorContextBuilder setTenant(String tenant);

	TracingTemplateConnectorContextBuilder setServiceName(String serviceName);

	TracingTemplateConnectorContextBuilder setAddAttributesFromNotificationsMessage(Level setAddAttributesFromNotificationsMessage);

	TracingTemplateConnectorContextBuilder setAddAttributesFromNotificationsDetailsMessage(Level addAttributesFromNotificationsDetailsMessage);

	TracingTemplateConnectorContext build();

}