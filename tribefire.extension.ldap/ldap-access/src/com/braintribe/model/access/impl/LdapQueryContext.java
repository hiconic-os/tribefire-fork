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
package com.braintribe.model.access.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.TypeCode;
import com.braintribe.model.ldap.LdapAttribute;
import com.braintribe.model.processing.accessory.impl.BasicModelAccessory;
import com.braintribe.model.processing.meta.cmd.builders.EntityMdResolver;
import com.braintribe.model.processing.meta.cmd.builders.ModelMdResolver;

public class LdapQueryContext {
	
	protected static Logger logger = Logger.getLogger(LdapQueryContext.class);
	
	protected String entityTypeSignature = null;
	protected EntityType<GenericEntity> entityType = null;
	
	protected ModelMdResolver metaDataBuilder = null; 
	protected EntityMdResolver entityMetaDataBuilder = null;
	
	protected Set<String> ldapObjectClasses = null;
	protected Map<String,String> propertyToAttributeMap = new HashMap<String,String>();
	protected Map<String,Property> attributeToPropertyMap = new HashMap<String,Property>();
	protected Property idProperty = null;
	protected List<Property> propertyList = null;

	private BasicModelAccessory modelAccessory;

	
	public LdapQueryContext(ModelMdResolver metaData, BasicModelAccessory modelAccessory) {
		this.metaDataBuilder = metaData;
		this.modelAccessory = modelAccessory;
	}

	public void setEntityType(EntityType<GenericEntity> entityType) throws Exception {
		this.entityType = entityType;
		this.propertyList = this.entityType.getProperties();
		this.entityMetaDataBuilder = this.metaDataBuilder.entityType(this.entityType);

		for (Property property : this.propertyList) {
			LdapAttribute ldapAttribute = this.entityMetaDataBuilder.property(property).meta(LdapAttribute.T).exclusive();
			if (ldapAttribute != null) {
				String propertyName = property.getName();
				String attributeName = ldapAttribute.getAttributeName();
				this.propertyToAttributeMap.put(propertyName, attributeName);
				this.attributeToPropertyMap.put(attributeName.toLowerCase(), property);
			}
			
		}
		GenericModelType idType = modelAccessory.getIdType(entityType.getTypeSignature());
		this.idProperty = entityType.getIdProperty();
		
		if (idType.getTypeCode() != TypeCode.stringType) {
			logger.debug(() -> "Id property "+idProperty.getName()+" of type "+this.entityType+" is not of type String.");
			throw new Exception("The entity type "+this.entityType+" has no ID property of type String.");
		}
	}
}
