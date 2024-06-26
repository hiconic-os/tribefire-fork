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
package com.braintribe.gwt.gme.assemblypanel.client.model.factory;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import com.braintribe.gwt.gm.storage.api.ColumnData;
import com.braintribe.gwt.gme.assemblypanel.client.AbstractAssemblyPanel;
import com.braintribe.gwt.gme.assemblypanel.client.AssemblyPanel;
import com.braintribe.gwt.gme.assemblypanel.client.model.AbstractGenericTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.model.ObjectAndType;
import com.braintribe.gwt.gme.assemblypanel.client.model.ValueTreeModel;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.TypeCode;
import com.braintribe.model.meta.data.prompt.CondensationMode;
import com.braintribe.model.meta.data.prompt.Inline;
import com.braintribe.model.meta.data.prompt.Outline;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class ModelFactory implements Function<ObjectAndType, AbstractGenericTreeModel> {
	private Map<TypeCode, Function<ObjectAndType, ? extends AbstractGenericTreeModel>> typeSpecificFactories;
	private EntityTreeModelFactory entityTreeModelFactory;
	private boolean ignoreInlineMetadata = false;
	private Set<Class<?>> simplifiedEntityTypes;
	private EntityType<?> entityTypeForPreparingProperties;
	public static int MAX_DEPTH = 1;
	private AbstractAssemblyPanel assemblyPanel;

	public ModelFactory() {
	}

	@Configurable @Required
	public void setTypeSpecificFactories(
			Map<TypeCode, Function<ObjectAndType, ? extends AbstractGenericTreeModel>> typeSpecificFactories) {
		this.typeSpecificFactories = typeSpecificFactories;
		entityTreeModelFactory = (EntityTreeModelFactory) typeSpecificFactories.get(TypeCode.entityType);
		
		typeSpecificFactories.values().stream().filter(modelFactory -> modelFactory instanceof Consumer).forEach(modelFactory -> {
			try {
				((Consumer<ModelFactory>) modelFactory).accept(this);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Configures whether to ignore the {@link Inline} and {@link Outline} metaData.
	 * Defaults to false.
	 */
	@Configurable
	public void setIgnoreInlineMetadata(boolean ignoreInlineMetadata) {
		this.ignoreInlineMetadata = ignoreInlineMetadata;
	}

	/**
	 * Configures a set of {@link EntityType}s that act as simplified by default.
	 */
	@Configurable
	public void setSimplifiedEntityTypes(Set<Class<?>> simplifiedEntityTypes) {
		this.simplifiedEntityTypes = simplifiedEntityTypes;
	}
	
	public void configureAssemblyPanel(AbstractAssemblyPanel assemblyPanel) {
		this.assemblyPanel = assemblyPanel;
	}
	
	public String getUseCase() {
		return assemblyPanel.getUseCase();
	}
	
	public PersistenceGmSession getGmSession() {
		return assemblyPanel.getGmSession();
	}

	/**
	 * Returns a set of {@link EntityType}s that act as simplified by default.
	 */
	public Set<Class<?>> getSimplifiedEntityTypes() {
		return simplifiedEntityTypes;
	}

	/**
	 * Configures the {@link EntityType} that will have its properties prepared as tree property models.
	 */
	public void configureEntityTypeForPreparingProperties(EntityType<?> entityTypeForPreparingProperties) {
		this.entityTypeForPreparingProperties = entityTypeForPreparingProperties;
	}
	
	/**
	 * Returns the {@link EntityType} that will have its properties prepared as tree property models.
	 */
	public EntityType<?> getEntityTypeForPreparingProperties() {
		return entityTypeForPreparingProperties;
	}
	
	public ColumnData getColumnData() {
		return assemblyPanel.getColumnData();
	}

	/**
	 * Marks the entity type as condensed.
	 */
	public void markEntityTypeAsCondensed(EntityType<?> entityType, String collectionPropertyName, CondensationMode condensationMode) {
		entityTreeModelFactory.markEntityTypeAsCondensed(entityType, collectionPropertyName, condensationMode);
	}

	/**
	 * Unmarks the entity type as condensed.
	 */
	public void unmarkEntityTypeAsCondensed(EntityType<?> entityType) {
		entityTreeModelFactory.unmarkEntityTypeAsCondensed(entityType);
	}

	/**
	 * Returns the collection property name used for the condensation. Null if the entityType is not condensed.
	 */
	public String getCondensedProperty(GenericEntity entity, EntityType<?> entityType) {
		return entityTreeModelFactory.getCondensedProperty(entity, entityType);
	}

	/**
	 * Returns the condensation mode used for the condensation. Null if the entityType is not condensed.
	 */
	public CondensationMode getCondensationMode(EntityType<?> entityType) {
		return entityTreeModelFactory.getCondensationMode(entityType);
	}

	@Override
	public AbstractGenericTreeModel apply(ObjectAndType objectAndType) {
		GenericModelType type = objectAndType.getType();

		Function<ObjectAndType, ? extends AbstractGenericTreeModel> factory = typeSpecificFactories.get(type.getTypeCode());

		if (factory == null) 
			return new ValueTreeModel(objectAndType);

		return factory.apply(objectAndType);
	}

	public boolean isIgnoreInlineMetadata() {
		return ignoreInlineMetadata;
	}
	
	/**
	 * Configures whether we can uncondense auto condensations. If false, then we do not prepare all properties for the entity, since they are not used.
	 * It is set to false when the {@link AssemblyPanel} where this is used is not displaying actions.
	 */
	public void configureCanUncondense(boolean canUncondense) {
		entityTreeModelFactory.configureCanUncondense(canUncondense);
	}
}
