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

import java.util.List;

import com.braintribe.model.generic.reflection.GenericModelType;


public abstract class DelegatingTreeModel extends AbstractGenericTreeModel {
	
	protected AbstractGenericTreeModel delegate;
	
	public void setDelegate(AbstractGenericTreeModel delegate) {
		this.delegate = delegate;
	}

	@Override
	public AbstractGenericTreeModel getDelegate() {
		return delegate;
	}
	
	@Override
	public void add(AbstractGenericTreeModel child) {
		delegate.add(child);
	}

	@Override
	public AbstractGenericTreeModel getChild(int index) {
		return delegate.getChild(index);
	}

	@Override
	public int getChildCount() {
		return delegate.getChildCount();
	}

	@Override
	public List<AbstractGenericTreeModel> getChildren() {
		return delegate.getChildren();
	}

	@Override
	public AbstractGenericTreeModel getParent() {
		return delegate.getParent();
	}

	@Override
	public int indexOf(AbstractGenericTreeModel child) {
		return delegate.indexOf(child);
	}

	@Override
	public void insert(AbstractGenericTreeModel child, int index) {
		delegate.insert(child, index);
	}

	@Override
	public boolean remove(AbstractGenericTreeModel child) {
		return delegate.remove(child);
	}
	
	@Override
	public boolean remove(int index) {
		return delegate.remove(index);
	}

	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	public void setParent(AbstractGenericTreeModel parent) {
		delegate.setParent(parent);
	}
	
	@Override
	public String getPropertyName() {
		return delegate.getPropertyName();
	}

	@Override
	public <X> X set(String property, X value) {
		return delegate.set(property, value);
	}
	
	@Override
	public void setModelObject(Object modelObject, int depth){
		delegate.setModelObject(modelObject, depth);
	}
	
	@Override
	public <X> X getModelObject() {
		return delegate.getModelObject();
	}
	
	@Override
	public boolean refersTo(Object object) {
		return delegate.refersTo(object);
	}
	
	@Override
	public <X extends GenericModelType> X getElementType() {
		return delegate.getElementType();
	}

}
