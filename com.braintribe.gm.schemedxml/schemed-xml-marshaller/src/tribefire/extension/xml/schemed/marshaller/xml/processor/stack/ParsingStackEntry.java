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
package tribefire.extension.xml.schemed.marshaller.xml.processor.stack;

import java.util.Collection;

import javax.xml.namespace.QName;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.CollectionType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.meta.GmEntityType;


/**
 * an entry for the stack that is run while decoding
 *  
 * @author pit
 *
 */
public class ParsingStackEntry {
	// cache element 
	private PropertyMappingMetaDataCacheElement propertyMapping;
	// string buffer for characters
	private StringBuffer stringBuffer;
	// QName 
	private QName qname;
	// generic entity instance
	private GenericEntity genericEntity;
	// GmEntityType 
	private GmEntityType gmEntityType;
	// EntityType instance 
	private EntityType<GenericEntity> entityType;
	// actual value
	private Object value;
	// parent
	private ParsingStackEntry parent;
	
	private boolean toBeIgnored = false;
	private boolean any = false;
	private boolean undefined = false;
	private boolean notTextContent = false;	
	
	/**
	 * gets the attached {@link PropertyMappingMetaDataCacheElement}
	 * @return - the {@link PropertyMappingMetaDataCacheElement}
	 */
	public PropertyMappingMetaDataCacheElement getPropertyMapping() {
		return propertyMapping;
	}
	/**
	 * attaches the cache element 
	 * @param properyMapping - the {@link PropertyMappingMetaDataCacheElement}
	 */
	public void setPropertyMapping(PropertyMappingMetaDataCacheElement properyMapping) {
		this.propertyMapping = properyMapping;
	}
	
	public StringBuffer getStringBuffer() {
		if (stringBuffer == null)
			stringBuffer = new StringBuffer();
		return stringBuffer;
	}
	public void setStringBuffer(StringBuffer stringBuffer) {
		this.stringBuffer = stringBuffer;
	}
	
	public QName getQname() {
		return qname;
	}
	public void setQname(QName qname) {
		this.qname = qname;
	}
	public GenericEntity getGenericEntity() {
		return genericEntity;
	}
	public void setGenericEntity(GenericEntity genericEntity) {
		this.genericEntity = genericEntity;
	}
	
	public GmEntityType getGmEntityType() {
		return gmEntityType;
	}
	public void setGmEntityType(GmEntityType entityType) {
		this.gmEntityType = entityType;
	}
		
	public EntityType<GenericEntity> getEntityType() {
		return entityType;
	}
	public void setEntityType(EntityType<GenericEntity> entityType) {
		this.entityType = entityType;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	public boolean isToBeIgnored() {
		return toBeIgnored;
	}
	public void setToBeIgnored(boolean toBeIgnored) {
		this.toBeIgnored = toBeIgnored;
	}
	
	public boolean isNotTextContent() {
		return notTextContent;
	}
	public void setNotTextContent(boolean notTextContent) {
		this.notTextContent = notTextContent;
	}
	
	public boolean isAnyType() {
		return any;
	}
	public void setAnyType(boolean any) {
		this.any = any;
	}		
	
	public boolean isUndefined() {
		return undefined;
	}
	public void setUndefined(boolean undefined) {
		this.undefined = undefined;
	}
	public ParsingStackEntry getParent() {
		return parent;
	}
	public void setParent(ParsingStackEntry parent) {
		this.parent = parent;
	}
	
	public void attachChild(ParsingStackEntry entry) {								
		String name = entry.propertyMapping.gmPropertyName;
		
		Property property;
		if (entityType != null) {
			property = entityType.getProperty( name);
		} 
		else if (parent.entityType != null) {
			property = parent.entityType.getProperty(name);
		}
		else {
			EntityType<GenericEntity> ettype = parent.genericEntity.entityType();
			property = ettype.getProperty(name);
		}		
		
		assignValue(entry, property);
	}
	
	public void attachToSelf() {
		String name = propertyMapping.gmPropertyName;
		Property property = entityType != null ? entityType.getProperty( name) : parent.entityType.getProperty(name);
		assignValue( this, property);
	}
	
	private void assignValue(ParsingStackEntry entry, Property property) {
		if (
				propertyMapping != null &&
				propertyMapping.isUndefined
			) {
			getStringBuffer().append( entry.getValue());
			return;
		}
		if (entry.propertyMapping.apparentXsdType.equalsIgnoreCase("anyType")) {
			if (!entry.propertyMapping.isMultiple) {			
				property.set(genericEntity, entry.genericEntity);
			}
			else {
				Collection<Object> collection = property.get( genericEntity);
				collection.add( entry.genericEntity);
			}			
		} 
		else {			
			if (property.getType() instanceof CollectionType == false) {
				property.set(genericEntity, entry.value);	
			}
			else {				
				Collection<Object> collection = property.get( genericEntity);
				collection.add( entry.value);
			}
		}
	}
	
}
