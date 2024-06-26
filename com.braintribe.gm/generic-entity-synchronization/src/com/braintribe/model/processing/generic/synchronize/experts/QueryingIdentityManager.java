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
package com.braintribe.model.processing.generic.synchronize.experts;

import java.util.Collection;

import com.braintribe.cfg.Configurable;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.generic.synchronize.GenericEntitySynchronizationException;
import com.braintribe.model.processing.generic.synchronize.api.IdentityManager;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.query.fluent.JunctionBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.query.EntityQuery;

/**
 * An abstract {@link IdentityManager} implementation that provides support for querying instances in the target session.
 */
public abstract class QueryingIdentityManager implements IdentityManager {
	
	private static final Logger logger = Logger.getLogger(QueryingIdentityManager.class);
	
	protected static GenericModelTypeReflection typeReflection = GMF.getTypeReflection();
	private boolean supportNullIdentityProperty = false;
	private boolean ignoreCache = false;
	
	public QueryingIdentityManager() {}
	
	@Configurable
	public void setSupportNullIdentityProperty(boolean supportNullIdentityProperty) {
		this.supportNullIdentityProperty = supportNullIdentityProperty;
	}
	
	@Configurable
	public void setIgnoreCache(boolean ignoreCache) {
		this.ignoreCache = ignoreCache;
	}
	
	protected GenericEntity query(PersistenceGmSession session, GenericEntity instance, EntityType<? extends GenericEntity> entityType, Collection<String> properties) throws GenericEntitySynchronizationException {
		GenericEntity existing = null;
		
		switch (properties.size()) {
		case 0:
			break;
		case 1:
			existing = query(session, buildQuery(instance, entityType, properties.iterator().next()));			
			break;
		default:
			existing = query(session, buildQuery(instance, entityType, properties));	
		}
			
		return existing;
	}

	
	protected GenericEntity query(PersistenceGmSession session, EntityQuery query) {
		if (query == null) {
			// No query given. ignore.
			return null;
		}
		
		try {
			GenericEntity result = null;
			if (!ignoreCache) {
				result = session.queryCache().entities(query).unique();
			}
			if (result == null) {
				result = session.query().entities(query).unique(); 
			}
			return result;
		} catch (GmSessionException e) {
			throw new GenericEntitySynchronizationException("Error while searching for existing entity.",e);
		}
	}

	protected EntityQuery buildQuery(GenericEntity instance, EntityType<? extends GenericEntity> entityType, Collection<String> properties) {

		JunctionBuilder<EntityQueryBuilder> where = EntityQueryBuilder.from(entityType).where().conjunction();
		for (String identityProperty : properties) {
			Object propertyValue = getPropertyValue(instance, entityType, identityProperty);
			if (propertyValue == null && !supportNullIdentityProperty) {
				logger.warn("The identity property: "+identityProperty+" of source instance" + instance + "is null but null values should be ignored due to configuration. (ignoreNullValues=true).");
				return null;
			}
			where.property(identityProperty).eq(propertyValue);
		}
		return where.close().done();
	
	}

	protected EntityQuery buildQuery(GenericEntity instance, EntityType<? extends GenericEntity> entityType, String property) {
		Object propertyValue = getPropertyValue(instance, entityType, property);
		if (propertyValue == null && !supportNullIdentityProperty) {
			logger.warn("The identity property: "+property+" of source instance: " + instance +" is null but null values should be ignored due to configuration. (ignoreNullValues=true).");
			return null;
		}
		// @formatter:off
		return  EntityQueryBuilder
					.from(entityType)
					.where()
						.property(property).eq(propertyValue)
					.done();
		// @formatter:off
	}
	
	/**
	 * @return Returns the property value of the property identified by given propertyName of the given instance.
	 */
	protected Object getPropertyValue(GenericEntity instance, EntityType<? extends GenericEntity> entityType, String propertyName) {
		Property identityProperty = entityType.findProperty(propertyName);
		if (identityProperty != null) {
			return identityProperty.get(instance);
		}
		return null;
	}	
}
