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
package tribefire.extension.tracing.model.deployment.connector;

import java.util.Map;
import java.util.Set;

import com.braintribe.model.deployment.connector.Connector;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.DeployableComponent;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.notification.Level;

import tribefire.extension.tracing.model.deployment.service.DefaultAttribute;

/**
 * Abstract base type for all tracing connectors
 * 
 *
 */
@Abstract
@DeployableComponent
public interface TracingConnector extends Connector {

	final EntityType<TracingConnector> T = EntityTypes.T(TracingConnector.class);

	String entityTypeInclusions = "entityTypeInclusions";
	String entityTypeHierarchyInclusions = "entityTypeHierarchyInclusions";
	String entityTypeExclusions = "entityTypeExclusions";
	String entityTypeHierarchyExclusions = "entityTypeHierarchyExclusions";
	String userInclusions = "userInclusions";
	String userExclusions = "userExclusions";
	String defaultAttributes = "defaultAttributes";
	String customAttributes = "customAttributes";
	String defaultTracingEnabled = "defaultTracingEnabled";
	String componentName = "componentName";
	String addAttributesFromNotificationsMessage = "addAttributesFromNotificationsMessage";
	String addAttributesFromNotificationsDetailsMessage = "addAttributesFromNotificationsDetailsMessage";

	String tenant = "tenant";
	String serviceName = "serviceName";

	@Name("Entity Type Inclusions")
	@Description("Entity Types to be included in the tracing. Empty means accepting all entity types")
	Set<String> getEntityTypeInclusions();
	void setEntityTypeInclusions(Set<String> entityTypeInclusions);

	@Name("Entity Type Hierarchy Inclusions")
	@Description("Entity Types Hierarchy to be included in the tracing. Empty means accepting all entity types")
	Set<String> getEntityTypeHierarchyInclusions();
	void setEntityTypeHierarchyInclusions(Set<String> entityTypeHierarchyInclusions);

	@Name("Entity Type Exclusions")
	@Description("Entity Types to be excluded in the tracing. Empty means accepting all entity types")
	Set<String> getEntityTypeExclusions();
	void setEntityTypeExclusions(Set<String> entityTypeExclusions);

	@Name("Entity Type Hierarchy Exclusions")
	@Description("Entity Types Hierarchy to be excluded in the tracing. Empty means accepting all entity types")
	Set<String> getEntityTypeHierarchyExclusions();
	void setEntityTypeHierarchyExclusions(Set<String> entityTypeHierarchyExclusions);

	@Name("User Inclusions")
	@Description("Users to be included in the tracing. Empty means accepting all users")
	Set<String> getUserInclusions();
	void setUserInclusions(Set<String> userInclusions);

	@Name("User Exclusions")
	@Description("Users to be excluded in the tracing. Empty means excepting all users")
	Set<String> getUserExclusions();
	void setUserExclusions(Set<String> userExclusions);

	@Name("Default Attributes")
	@Description("Default attributes that will be added on tracing; empty/null means all default Attributes")
	Set<DefaultAttribute> getDefaultAttributes();
	void setDefaultAttributes(Set<DefaultAttribute> defaultAttributes);

	@Name("Custom Attribute")
	@Description("Custom attribute to be added to each span")
	Map<String, String> getCustomAttributes();
	void setCustomAttributes(Map<String, String> customAttributes);

	@Name("Default Tracing Enabled")
	@Description("Defines if tracing is enabled or disabled by default (on redeployment of deployable, on restart)")
	@Initializer("true")
	boolean getDefaultTracingEnabled();
	void setDefaultTracingEnabled(boolean defaultTracingEnabled);

	@Mandatory
	@Name("Component Name")
	@Description("Component Name used as 'component' attribute")
	@Initializer("'tribefire'")
	String getComponentName();
	void setComponentName(String componentName);

	@Name("Add Attributes from Notifications Message")
	@Description("In case of a notification add the messages as attribute")
	Level getAddAttributesFromNotificationsMessage();
	void setAddAttributesFromNotificationsMessage(Level addAttributesFromNotificationsMessage);

	@Name("Add Attributes from Notifications Details Message")
	@Description("In case of a notification add the details messages as attribute")
	Level getAddAttributesFromNotificationsDetailsMessage();
	void setAddAttributesFromNotificationsDetailsMessage(Level addAttributesFromNotificationsDetailsMessage);

	// -----------------------------------------------------------------------
	// NOT CONFIGURABLE AT RUNTIME
	// -----------------------------------------------------------------------

	@Name("Tenant")
	@Description("Optional tenant information; will be the attribute 'TENANT'")
	String getTenant();
	void setTenant(String tenant);

	@Mandatory
	@Name("Service Name")
	@Description("Service name as the main span grouping element")
	@Initializer("'default'")
	String getServiceName();
	void setServiceName(String serviceName);

}
