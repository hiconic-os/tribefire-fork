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
package tribefire.extension.metrics.templates.api;

import java.util.Set;
import java.util.function.Function;

import com.braintribe.logging.Logger;
import com.braintribe.model.deployment.Module;
import com.braintribe.model.descriptive.HasExternalId;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.utils.StringTools;
import com.braintribe.wire.api.scope.InstanceConfiguration;
import com.braintribe.wire.api.scope.InstanceQualification;

import tribefire.extension.metrics.templates.api.connector.MetricsTemplateConnectorContext;
import tribefire.extension.metrics.templates.util.MetricsTemplateUtil;

public class MetricsTemplateContextImpl implements MetricsTemplateContext, MetricsTemplateContextBuilder {

	private static final Logger logger = Logger.getLogger(MetricsTemplateContextImpl.class);

	private Function<String, ? extends GenericEntity> lookupFunction;
	private Function<String, ? extends HasExternalId> lookupExternalIdFunction;
	private Function<EntityType<?>, GenericEntity> entityFactory = EntityType::create;

	// -----------------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------------

	public MetricsTemplateContextImpl() {
	}

	// -----------------------------------------------------------------------
	// Common
	// -----------------------------------------------------------------------

	private String globalIdPrefix;

	private boolean addDemo;

	private String context;

	private Set<MetricsTemplateConnectorContext> connectorContexts;

	private Module metricsModule;

	// -----------------------------------------------------------------------
	// Metrics Connector
	// -----------------------------------------------------------------------

	private String serviceName;

	// -----------------------------------------------------------------------
	// -----------------------------------------------------------------------
	// -----------------------------------------------------------------------

	// -----------------------------------------------------------------------
	// Common
	// -----------------------------------------------------------------------

	@Override
	public MetricsTemplateContextBuilder setGlobalIdPrefix(String globalIdPrefix) {
		this.globalIdPrefix = globalIdPrefix;
		return this;
	}

	@Override
	public String getGlobalIdPrefix() {
		return globalIdPrefix;
	}

	@Override
	public MetricsTemplateContextBuilder setAddDemo(boolean addDemo) {
		this.addDemo = addDemo;
		return this;
	}

	@Override
	public boolean getAddDemo() {
		return addDemo;
	}

	@Override
	public MetricsTemplateContextBuilder setContext(String context) {
		this.context = context;
		return this;
	}

	@Override
	public String getContext() {
		return context;
	}

	@Override
	public MetricsTemplateContextBuilder setConnectorContexts(Set<MetricsTemplateConnectorContext> connectorContexts) {
		this.connectorContexts = connectorContexts;
		return this;
	}

	@Override
	public Set<MetricsTemplateConnectorContext> getConnectorContexts() {
		return connectorContexts;
	}

	@Override
	public MetricsTemplateContextBuilder setEntityFactory(Function<EntityType<?>, GenericEntity> entityFactory) {
		this.entityFactory = entityFactory;
		return this;
	}

	@Override
	public Module getMetricsModule() {
		return metricsModule;
	}

	@Override
	public MetricsTemplateContextBuilder setMetricsModule(Module module) {
		this.metricsModule = module;
		return this;
	}

	@Override
	public MetricsTemplateContextBuilder setLookupFunction(Function<String, ? extends GenericEntity> lookupFunction) {
		this.lookupFunction = lookupFunction;
		return this;
	}

	@Override
	public MetricsTemplateContextBuilder setLookupExternalIdFunction(Function<String, ? extends HasExternalId> lookupExternalIdFunction) {
		this.lookupExternalIdFunction = lookupExternalIdFunction;
		return this;
	}

	@Override
	public MetricsTemplateContext build() {
		return this;
	}

	@Override
	public String toString() {
		// TODO: change
		return null;
	}

	// -----------------------------------------------------------------------
	// CONTEXT METHODS
	// -----------------------------------------------------------------------

	@Override
	public <T extends GenericEntity> T create(EntityType<T> entityType, InstanceConfiguration instanceConfiguration) {

		T entity = (T) entityFactory.apply(entityType);

		String globalId = MetricsTemplateUtil.resolveContextBasedGlobalId(this, instanceConfiguration);

		InstanceQualification qualification = instanceConfiguration.qualification();

		entity.setGlobalId(globalId);

		if (entity instanceof HasExternalId) {
			HasExternalId eid = (HasExternalId) entity;

			String part1 = StringTools.camelCaseToDashSeparated(entityType.getShortName());
			String part2 = StringTools.camelCaseToDashSeparated(context);
			String part3 = StringTools.camelCaseToDashSeparated(qualification.space().getClass().getSimpleName());
			String part4 = StringTools.camelCaseToDashSeparated(qualification.name());
			String externalId = part1 + "." + part2 + "." + part3 + "." + part4;
			externalId = externalId.replace("/", "-");

			if (logger.isDebugEnabled()) {
				logger.debug("Prepared externalId: '" + externalId + "' for globalId: '" + globalId + "'");
			}

			eid.setExternalId(externalId);
		}

		return entity;
	}

	@Override
	public <T extends GenericEntity> T lookup(String globalId) {
		return (T) lookupFunction.apply(globalId);
	}

	@Override
	public <T extends HasExternalId> T lookupExternalId(String externalId) {
		return (T) lookupExternalIdFunction.apply(externalId);
	}
}
