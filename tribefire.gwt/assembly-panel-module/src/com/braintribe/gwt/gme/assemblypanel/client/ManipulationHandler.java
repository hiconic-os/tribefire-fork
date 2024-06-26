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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.gwt.gme.assemblypanel.client.model.AbstractGenericTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.model.ListEntryTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.model.MapKeyAndValueTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.model.MapKeyOrValueEntryTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.model.PropertyEntryModelInterface;
import com.braintribe.gwt.gme.assemblypanel.client.model.SetEntryTreeModel;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.transaction.NestedTransaction;

/**
 * Class containing operations that require a {@link NestedTransaction}.
 * @author michel.docouto
 *
 */
public class ManipulationHandler {
	
	private PersistenceGmSession gmSession;
	private Comparator<AbstractGenericTreeModel> listEntryComparator;
	
	public ManipulationHandler(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	public void configureGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	/**
	 * Reorders the list elements.
	 */
	public void replaceInList(int index, List<AbstractGenericTreeModel> models) {
		AbstractGenericTreeModel model = models.get(0);
		if (!(model instanceof ListEntryTreeModel))
			return;
		
		AbstractGenericTreeModel parentModel = models.get(0).getParent();
		AbstractGenericTreeModel collectionTreeModel = parentModel.getDelegate();
		if (parentModel instanceof PropertyEntryModelInterface)
			collectionTreeModel = ((PropertyEntryModelInterface) parentModel).getPropertyDelegate();
		
		NestedTransaction nestedTransaction = gmSession.getTransaction().beginNestedTransaction();
		List<Object> list = collectionTreeModel.getModelObject();
		
		List<Object> objectsToAdd = new ArrayList<>();
		Collections.sort(models, getListEntryComparator());
		for (AbstractGenericTreeModel modelToReplace : models) {
			int oldIndex = ((ListEntryTreeModel) modelToReplace).getListEntryIndex();
			objectsToAdd.add(0, list.remove(oldIndex));
		}
		
		for (Object objectToAdd : objectsToAdd)
			list.add(index++, objectToAdd);
		
		nestedTransaction.commit();
	}
	
	/**
	 * Replaces the value in the {@link SetEntryTreeModel}.
	 */
	public void replaceInSet(SetEntryTreeModel setEntryTreeModel, Object newValue) {
		NestedTransaction nestedTransaction = gmSession.getTransaction().beginNestedTransaction();
		
		Set<Object> set = (setEntryTreeModel.getParent()).getModelObject();
		set.remove(setEntryTreeModel.getDelegate().getModelObject());
		set.add(newValue);
		
		nestedTransaction.commit();
	}
	
	/**
	 * Replaces the value in the {@link MapKeyOrValueEntryTreeModel}.
	 */
	public void replaceInMap(final MapKeyOrValueEntryTreeModel mapKeyOrValue, final Object newKeyOrValue) {
		MapKeyAndValueTreeModel mapKeyAndValueTreeModel = (MapKeyAndValueTreeModel) mapKeyOrValue.getParent();
		final Map<Object, Object> map = (mapKeyAndValueTreeModel.getParent()).getModelObject();
		
		if (!mapKeyOrValue.isKey()) {
			map.put(mapKeyAndValueTreeModel.getMapKeyEntryTreeModel().getDelegate().getModelObject(), newKeyOrValue);
			return;
		}
		
		NestedTransaction nestedTransaction = gmSession.getTransaction().beginNestedTransaction();
		Object value = map.remove(mapKeyOrValue.getDelegate().getModelObject());
		map.put(newKeyOrValue, value);
		nestedTransaction.commit();
	}
	
	private Comparator<AbstractGenericTreeModel> getListEntryComparator() {
		if (listEntryComparator == null)
			listEntryComparator = (arg0, arg1) -> ((Integer) ((ListEntryTreeModel) arg1).getListEntryIndex()).compareTo(((ListEntryTreeModel) arg0).getListEntryIndex());
		
		return listEntryComparator;
	}
	
}
