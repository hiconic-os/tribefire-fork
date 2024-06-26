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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import com.braintribe.common.lcd.Pair;
import com.braintribe.gwt.gme.assemblypanel.client.AssemblyPanel;
import com.braintribe.gwt.gme.assemblypanel.client.model.AbstractGenericTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.model.CondensedEntityTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.model.EntityTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.model.ObjectAndType;
import com.braintribe.gwt.gmview.util.client.GMEMetadataUtil;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.meta.data.prompt.CondensationMode;

public class EntityTreeModelFactory implements Function<ObjectAndType, AbstractGenericTreeModel>, Consumer<ModelFactory> {
	
	private ModelFactory modelFactory;
	private final Map<EntityType<?>, Pair<String, CondensationMode>> condensedTypes = new HashMap<>();
	private final Set<EntityType<?>> uncondensedTypes = new HashSet<EntityType<?>>();
	private final boolean displayAllPropertiesInMultiplex;
	private boolean canUncondense;
	private EntityTreeModel entityTreeModelToUseAsBase;
	
	/**
	 * Instantiates the Entity model factory.
	 * @param displayAllPropertiesInMultiplex - Configures whether we display All properties in the Multiplex Dimension of the Assembly Panel.
	 * Set to false so only complex properties and object typed ones are shown.
	 */
	public EntityTreeModelFactory(boolean displayAllPropertiesInMultiplex) {
		this.displayAllPropertiesInMultiplex = displayAllPropertiesInMultiplex;
	}
	
	/**
	 * Configures whether we can uncondense auto condensations. If false, then we do not prepare all properties for the entity, since they are not used.
	 * It is set to false when the {@link AssemblyPanel} where this is used is not displaying actions.
	 */
	public void configureCanUncondense(boolean canUncondense) {
		this.canUncondense = canUncondense;
	}
	
	@Override
	public void accept(ModelFactory modelFactory) {
		this.modelFactory = modelFactory;
	}
	
	/**
	 * Marks the entity type as condensed.
	 */
	public void markEntityTypeAsCondensed(EntityType<?> entityType, String collectionPropertyName, CondensationMode condensationMode) {
		condensedTypes.put(entityType, new Pair<>(collectionPropertyName, condensationMode));
		uncondensedTypes.remove(entityType);
	}
	
	/**
	 * Unmarks the entity type as condensed.
	 */
	public void unmarkEntityTypeAsCondensed(EntityType<?> entityType) {
		uncondensedTypes.add(entityType);
	}

	@Override
	public AbstractGenericTreeModel apply(ObjectAndType objectAndType) {
		EntityType<?> entityType = objectAndType.getType();
		Pair<String, CondensationMode> pair = condensedTypes.get(entityType);
		String condensedPropertyName = pair == null ? null : pair.getFirst();
		GenericEntity entity = null;
		if (objectAndType.getObject() instanceof GenericEntity)
			entity = (GenericEntity) objectAndType.getObject();
		if (condensedPropertyName == null)
			condensedPropertyName = getCondensedPropertyFromMetaModel(entity, entityType);
		
		Property condensedProperty = null;
		if (condensedPropertyName != null) {
			EntityType<?> actualEntityType = entityType;
			if (entity != null)
				actualEntityType = entity.entityType();
			condensedProperty = actualEntityType.findProperty(condensedPropertyName);
			if (condensedProperty == null)
				condensedPropertyName = null;
		}
		
		EntityType<?> baseType = getBaseTypeForProperties();
		EntityTreeModel entityTreeModel;
		if (baseType != null && entityTreeModelToUseAsBase != null && baseType.equals(entityTreeModelToUseAsBase.getBaseTypeForProperties())
				&& baseType.equals(objectAndType.getType())) {
			entityTreeModel = new EntityTreeModel(objectAndType, entityTreeModelToUseAsBase);
		} else {
			entityTreeModel = new EntityTreeModel(objectAndType, modelFactory, displayAllPropertiesInMultiplex, baseType,
					canUncondense(entityType) ? null : condensedPropertyName);
			if (baseType != null && baseType.equals(objectAndType.getType()))
				entityTreeModelToUseAsBase = entityTreeModel;
			else if (entityTreeModelToUseAsBase != null)
				entityTreeModelToUseAsBase = null;
		}
		if (condensedPropertyName == null || uncondensedTypes.contains(entityType))
			return entityTreeModel;
		return new CondensedEntityTreeModel(null, entityTreeModel, condensedProperty, modelFactory.getUseCase(), objectAndType.getDepth());
	}
	
	/**
	 * Returns the collection property name used for the condensation. Null if the entityType is not condensed.
	 */
	public String getCondensedProperty(GenericEntity entity, EntityType<?> entityType) {
		String condensedPropertyName = null;
		if (!uncondensedTypes.contains(entityType)) {
			Pair<String, CondensationMode> pair = condensedTypes.get(entityType);
			condensedPropertyName = pair == null ? null : pair.getFirst();
			if (condensedPropertyName == null)
				condensedPropertyName = getCondensedPropertyFromMetaModel(entity, entityType);
			
			if (condensedPropertyName != null) {
				Property property = entityType.findProperty(condensedPropertyName);
				if (property == null)
					condensedPropertyName = null;
			}
		}
		return condensedPropertyName;
	}
	
	/**
	 * Returns the condensation mode used for the condensation. Null if the entityType is not condensed.
	 */
	public CondensationMode getCondensationMode(EntityType<?> entityType) {
		if (!uncondensedTypes.contains(entityType)) {
			Pair<String, CondensationMode> pair = condensedTypes.get(entityType);
			return pair == null ? null : pair.getSecond();
		}
		
		return null;
	}
	
	/**
	 * Returns whether we display All properties in the Multiplex Dimension of the Assembly Panel.
	 * False means only complex properties and object typed ones are shown.
	 */
	public boolean isDisplayAllPropertiesInMultiplex() {
		return displayAllPropertiesInMultiplex;
	}
	
	private String getCondensedPropertyFromMetaModel(GenericEntity entity, EntityType<?> entityType) {
		GMEMetadataUtil.CondensationBean bean = GMEMetadataUtil.getEntityCondensationProperty(GMEMetadataUtil.getEntityCondensations(entity, entityType,
				modelFactory.getGmSession().getModelAccessory().getMetaData(), modelFactory.getUseCase()), false);
		
		if (bean != null) {
			markEntityTypeAsCondensed(entityType, bean.getProperty(), bean.getMode());
			return bean.getProperty();
		}
		
		return null;
	}
	
	private EntityType<?> getBaseTypeForProperties() {
		return modelFactory.getEntityTypeForPreparingProperties();
	}
	
	private boolean canUncondense(EntityType<?> entityType) {
		CondensationMode mode = getCondensationMode(entityType);
		if (CondensationMode.forced.equals(mode))
			return false;
		
		if (CondensationMode.auto.equals(mode))
			return canUncondense;
		
		return true;
	}

}
