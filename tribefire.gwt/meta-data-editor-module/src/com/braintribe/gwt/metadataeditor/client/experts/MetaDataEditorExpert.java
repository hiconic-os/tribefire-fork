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
package com.braintribe.gwt.metadataeditor.client.experts;

import java.util.Collection;

import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.processing.async.api.AsyncCallback;

public interface MetaDataEditorExpert extends MetaDataEditorBaseExpert {

	void provide(GenericEntity value, CallbackMetaData callback);
    void provide(GenericEntity value, CallbackEntityType callback);
    void provide(GenericEntity value, GmMetaModel editingModel, GenericEntity entity, CallbackMetaData callback);
	void provide(GenericEntity value, CallbackExpertResultType callback);
    void provide(GenericEntity value, GmMetaModel editingModel, GenericEntity entity, CallbackExpertResultType callback);
	
	public static abstract class CallbackMetaData implements AsyncCallback<Collection<MetaData>> {
		@Override
		public void onFailure(Throwable t) {
			ErrorDialog.show("MetaDataEditorExpert-Failure", t);
		}
	}
	
	public static abstract class CallbackEntityType implements AsyncCallback<Collection<EntityType<?>>> {
		@Override
		public void onFailure(Throwable t) {
			ErrorDialog.show("MetaDataEditorExpert-Failure", t);
		}
	}

	public static abstract class CallbackExpertResultType implements AsyncCallback<Collection<MetaDataEditorExpertResultType>> {
		@Override
		public void onFailure(Throwable t) {
			ErrorDialog.show("MetaDataEditorExpert-Failure", t);
		}
	}

}
