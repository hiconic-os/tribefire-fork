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
package com.braintribe.model.access.crud.support.read;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.braintribe.model.access.crud.api.DataReader;
import com.braintribe.model.access.crud.api.DataReadingContext;
import com.braintribe.model.access.crud.api.DataReadingException;
import com.braintribe.model.access.crud.api.read.EntityReader;
import com.braintribe.model.access.crud.api.read.EntityReadingContext;
import com.braintribe.model.access.crud.api.read.PopulationReader;
import com.braintribe.model.access.crud.api.read.PopulationReadingContext;
import com.braintribe.model.access.crud.api.read.PropertyReader;
import com.braintribe.model.access.crud.api.read.PropertyReadingContext;
import com.braintribe.model.generic.GenericEntity;

public class DispatchingReader<T extends GenericEntity> implements EntityReader<T>,PopulationReader<T>,PropertyReader<T,Object> {
	
	private List<DispatchEntry<EntityReader<T>, EntityReadingContext<T>>> entityReaders = new ArrayList<>();
	private List<DispatchEntry<PopulationReader<T>, PopulationReadingContext<T>>> populationReaders = new ArrayList<>();
	private List<DispatchEntry<PropertyReader<T,?>, PropertyReadingContext<T>>> propertyReaders = new ArrayList<>();
	
	// ***************************************************************************************************
	// Registration
	// ***************************************************************************************************

	public DispatchingReader<T> registerEntityReader (Predicate<EntityReadingContext<T>> predicate, EntityReader<T> delegate) {
		entityReaders.add(new DispatchEntry<EntityReader<T>, EntityReadingContext<T>>(predicate, delegate));
		return this;
	}
	public DispatchingReader<T> registerPopulationReader (Predicate<PopulationReadingContext<T>> predicate, PopulationReader<T> delegate) {
		populationReaders.add(new DispatchEntry<PopulationReader<T>, PopulationReadingContext<T>>(predicate, delegate));
		return this;
	}
	public DispatchingReader<T> registerPropertyReader (Predicate<PropertyReadingContext<T>> predicate, PropertyReader<T,?> delegate) {
		propertyReaders.add(new DispatchEntry<PropertyReader<T,?>, PropertyReadingContext<T>>(predicate, delegate));
		return this;
	}	

	// ***************************************************************************************************
	// EntityReader
	// ***************************************************************************************************

	@Override
	public T getEntity(EntityReadingContext<T> context) {
		return getDelegate(entityReaders,context).getEntity(context);
	}
		
	// ***************************************************************************************************
	// PopulationReader
	// ***************************************************************************************************

	@Override
	public Iterable<T> findEntities(PopulationReadingContext<T> context) {
		return getDelegate(populationReaders,context).findEntities(context);
	}
	
	// ***************************************************************************************************
	// PropertyLoader
	// ***************************************************************************************************

	@Override
	public Object getPropertyValue(PropertyReadingContext<T> context) {
		return getDelegate(propertyReaders, context).getPropertyValue(context);
	}
	
	// ***************************************************************************************************
	// Helper
	// ***************************************************************************************************	

	protected <L extends DataReader<T>, C extends DataReadingContext<T>> L getDelegate(List<DispatchEntry<L, C>> readers, C context) {
		for (DispatchEntry<L, C> entry : readers) {
			if (entry.getPredicate().test(context)) {
				return entry.getDelegate();
			}
		}
		throw new DataReadingException("No matching delegate reader found in registry.");
	}
	
	// ***************************************************************************************************
	// Registry
	// ***************************************************************************************************
	
	public class DispatchEntry<L extends DataReader<T>, C extends DataReadingContext<T>> {
		
		private Predicate<C> predicate;
		private L delegate;
		
		public DispatchEntry(Predicate<C> predicate, L delegate) {
			super();
			this.predicate = predicate;
			this.delegate = delegate;
		}
		
		public Predicate<C> getPredicate() {
			return predicate;
		}
		public L getDelegate() {
			return delegate;
		}
		
	}
	
}
