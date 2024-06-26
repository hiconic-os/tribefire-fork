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
package com.braintribe.gwt.gme.assemblypanel.client.model;

import java.util.function.Function;

import com.braintribe.model.generic.reflection.GenericModelException;


public class PropertyEntryTreeModel extends DelegatingTreeModel implements PropertyEntryModelInterface {
	
	protected PropertyEntry propertyEntry;
	private boolean mandatory;
	private Double priority;
	private boolean editable;
	
	protected PropertyEntryTreeModel() {}
	
	public PropertyEntryTreeModel(ObjectAndType objectAndType, Function<ObjectAndType, ? extends AbstractGenericTreeModel> modelFactory)
			throws GenericModelException {
		ObjectAndType subObjectAndType = new ObjectAndType();
		propertyEntry = (PropertyEntry)objectAndType.getObject();
		subObjectAndType.setObject(propertyEntry.getPropertyValue());
		subObjectAndType.setType(propertyEntry.getPropertyType());
		subObjectAndType.setDepth(propertyEntry.getDepth());
		subObjectAndType.setMapAsList(propertyEntry.getMapAsList());
		subObjectAndType.setMaxSize(propertyEntry.getMaxSize());
		delegate = modelFactory.apply(subObjectAndType);
		delegate.setProperty(propertyEntry.getProperty());
	}
	
	@Override
	public <X> X get(String property) {
		return (X)delegate.get(property);
	}
	
	@Override
	public PropertyEntry getPropertyEntry() {
		return propertyEntry;
	}
	
	@Override
	public AbstractGenericTreeModel getPropertyDelegate() {
		return delegate;
	}
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public boolean getMandatory() {
		return mandatory;
	}
	
	public void setPriority(Double priority) {
		this.priority = priority;
	}
	
	public Double getPriority() {
		return priority;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public boolean isEditable() {
		return editable;
	}

}
