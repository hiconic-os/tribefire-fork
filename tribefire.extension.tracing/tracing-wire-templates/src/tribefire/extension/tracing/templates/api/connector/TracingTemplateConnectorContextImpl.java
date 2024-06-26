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
 */
public abstract class TracingTemplateConnectorContextImpl implements TracingTemplateConnectorContext, TracingTemplateConnectorContextBuilder {

	private Set<String> entityTypeInclusions;
	private Set<String> entityTypeHierarchyInclusions;
	private Set<String> entityTypeExclusions;
	private Set<String> entityTypeHierarchyExclusions;
	private Set<String> userInclusions;
	private Set<String> userExclusions;
	private Set<DefaultAttribute> defaultAttributes;
	private Map<String, String> customAttributes;
	private Boolean defaultTracingEnabled;
	private String componentName;
	private String tenant;
	private String serviceName;
	private Level addAttributesFromNotificationsMessage;
	private Level addAttributesFromNotificationsDetailsMessage;

	@Override
	public TracingTemplateConnectorContextBuilder setEntityTypeInclusions(Set<String> entityTypeInclusions) {
		this.entityTypeInclusions = entityTypeInclusions;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setEntityTypeHierarchyInclusions(Set<String> entityTypeHierarchyInclusions) {
		this.entityTypeHierarchyInclusions = entityTypeHierarchyInclusions;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setEntityTypeExclusions(Set<String> entityTypeExclusions) {
		this.entityTypeExclusions = entityTypeExclusions;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setEntityTypeHierarchyExclusions(Set<String> entityTypeHierarchyExclusions) {
		this.entityTypeHierarchyExclusions = entityTypeHierarchyExclusions;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setUserInclusions(Set<String> userInclusions) {
		this.userInclusions = userInclusions;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setUserExclusions(Set<String> userExclusions) {
		this.userExclusions = userExclusions;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setDefaultAttributes(Set<DefaultAttribute> defaultAttributes) {
		this.defaultAttributes = defaultAttributes;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setCustomAttributes(Map<String, String> customAttributes) {
		this.customAttributes = customAttributes;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setDefaultTracingEnabled(Boolean defaultTracingEnabled) {
		this.defaultTracingEnabled = defaultTracingEnabled;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setComponentName(String componentName) {
		this.componentName = componentName;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setTenant(String tenant) {
		this.tenant = tenant;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setServiceName(String serviceName) {
		this.serviceName = serviceName;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setAddAttributesFromNotificationsMessage(Level addAttributesFromNotificationsMessage) {
		this.addAttributesFromNotificationsMessage = addAttributesFromNotificationsMessage;
		return this;
	}

	@Override
	public TracingTemplateConnectorContextBuilder setAddAttributesFromNotificationsDetailsMessage(Level addAttributesFromNotificationsDetailsMessage) {
		this.addAttributesFromNotificationsDetailsMessage = addAttributesFromNotificationsDetailsMessage;
		return this;
	}

	@Override
	public Set<String> getEntityTypeInclusions() {
		return entityTypeInclusions;
	}

	@Override
	public Set<String> getEntityTypeHierarchyInclusions() {
		return entityTypeHierarchyInclusions;
	}

	@Override
	public Set<String> getEntityTypeExclusions() {
		return entityTypeExclusions;
	}

	@Override
	public Set<String> getEntityTypeHierarchyExclusions() {
		return entityTypeHierarchyExclusions;
	}

	@Override
	public Set<String> getUserInclusions() {
		return userInclusions;
	}

	@Override
	public Set<String> getUserExclusions() {
		return userExclusions;
	}

	@Override
	public Set<DefaultAttribute> getDefaultAttributes() {
		return defaultAttributes;
	}

	@Override
	public Map<String, String> getCustomAttributes() {
		return customAttributes;
	}

	@Override
	public Boolean getDefaultTracingEnabled() {
		return defaultTracingEnabled;
	}

	@Override
	public String getComponentName() {
		return componentName;
	}

	@Override
	public String getTenant() {
		return tenant;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public Level getAddAttributesFromNotificationsMessage() {
		return addAttributesFromNotificationsMessage;
	}

	@Override
	public Level getAddAttributesFromNotificationsDetailsMessage() {
		return addAttributesFromNotificationsDetailsMessage;
	}

	@Override
	public String toString() {
		return "TracingTemplateConnectorContextImpl [entityTypeInclusions=" + entityTypeInclusions + ", entityTypeHierarchyInclusions="
				+ entityTypeHierarchyInclusions + ", entityTypeExclusions=" + entityTypeExclusions + ", entityTypeHierarchyExclusions="
				+ entityTypeHierarchyExclusions + ", userInclusions=" + userInclusions + ", userExclusions=" + userExclusions + ", defaultAttributes="
				+ defaultAttributes + ", customAttributes=" + customAttributes + ", defaultTracingEnabled=" + defaultTracingEnabled + ", componentName=" + componentName
				+ ", tenant=" + tenant + ", serviceName=" + serviceName + ", addAttributesFromNotificationsMessage=" + addAttributesFromNotificationsMessage
				+ ", addAttributesFromNotificationsDetailsMessage=" + addAttributesFromNotificationsDetailsMessage + "]";
	}
}
