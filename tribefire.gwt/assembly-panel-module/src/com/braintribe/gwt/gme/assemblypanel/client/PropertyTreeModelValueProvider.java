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
package com.braintribe.gwt.gme.assemblypanel.client;

import com.braintribe.gwt.gme.assemblypanel.client.model.AbstractGenericTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.model.TreePropertyModel;
import com.braintribe.model.generic.reflection.Property;
import com.sencha.gxt.core.client.ValueProvider;

public class PropertyTreeModelValueProvider implements ValueProvider<AbstractGenericTreeModel, Object> {
	private final Property property;

	public PropertyTreeModelValueProvider(Property property) {
		this.property = property;
	}

	@Override
	public Object getValue(AbstractGenericTreeModel model) {
		TreePropertyModel treePropertyModel = getTreePropertyModel(model);
		return treePropertyModel == null ? null : treePropertyModel.getValue();
	}

	@Override
	public void setValue(AbstractGenericTreeModel model, Object value) {
		TreePropertyModel treePropertyModel = getTreePropertyModel(model);
		if (treePropertyModel != null)
			treePropertyModel.setValue(value);
	}

	@Override
	public String getPath() {
		return property.getName();
	}
	
	public Property getProperty() {
		return property;
	}
	
	private TreePropertyModel getTreePropertyModel(AbstractGenericTreeModel model) {
		return model.getDelegate().getTreePropertyModel(property);
	}
	
}
