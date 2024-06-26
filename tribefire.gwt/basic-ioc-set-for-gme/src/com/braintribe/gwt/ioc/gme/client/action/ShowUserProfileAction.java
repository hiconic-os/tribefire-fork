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
package com.braintribe.gwt.ioc.gme.client.action;

import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.async.client.AsyncCallbacks;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.gme.constellation.client.GIMADialog;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.gwt.gmview.client.GmEntityView;
import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.accessapi.ModelEnvironment;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.RootPathElement;
import com.braintribe.model.generic.validation.ValidationKind;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;
import com.braintribe.model.user.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ShowUserProfileAction extends Action {
	
	private ExplorerConstellation explorerConstellation;
	private String userAccessId = "auth";
	private String useCase = "userProfile";
	private Supplier<? extends GmEntityView> propertyPanelProvider;
	private Supplier<GIMADialog> gimaDialogProvider;
	private ModelEnvironmentDrivenGmSession gmSession;
	private ModelEnvironment userModelEnvironment;
	private Function<String, Future<ModelEnvironment>> modelEnvironmentProvider;
	private Supplier<Future<User>> userProvider;
	
	/**
	 * Configures the required provider for the user.
	 */
	@Required
	public void setUserProvider(Supplier<Future<User>> userProvider) {
		this.userProvider = userProvider;
	}
	
	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}
	
	@Required
	public void setPropertyPanelProvider(Supplier<? extends GmEntityView> propertyPanelProvider) {
		this.propertyPanelProvider = propertyPanelProvider;
	}
	
	@Required
	public void setGimaDialogProvider(Supplier<GIMADialog> gimaDialogProvider) {
		this.gimaDialogProvider = gimaDialogProvider;
	}
	
	@Required
	public void setGmSession(ModelEnvironmentDrivenGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	/**
	 * Configures the provider used for loading the model environment.
	 */
	@Required
	public void setModelEnvironmentProvider(Function<String, Future<ModelEnvironment>> modelEnvironmentProvider) {
		this.modelEnvironmentProvider = modelEnvironmentProvider;
	}
	
	/**
	 * Configures the userAccessId. Defaults to "auth".
	 */
	@Configurable
	public void setUserAccessId(String userAccessId) {
		this.userAccessId = userAccessId;
	}
	
	/**
	 * Configures an optional useCase to be used by this action. Defaults to "userProfile".
	 */
	@Configurable
	public void setUseCase(String useCase) {
		this.useCase = useCase;
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		GlobalState.showSuccess(LocalizedText.INSTANCE.loadingUser());
		if (userModelEnvironment == null)
			getUserModelEnvironment();
		else
			getUser();
	}
	
	private AsyncCallback<ModelEnvironment> getUserModelEnvironmentCallback() {
		return AsyncCallbacks.of(modelEnvironment -> {
			userModelEnvironment = modelEnvironment;
			modelEnvironmentProvider.apply(explorerConstellation.getGmSession().getAccessId()); //resetting the accessId
			gmSession.configureModelEnvironment(modelEnvironment, com.braintribe.processing.async.api.AsyncCallback.of( //
					v -> getUser(), //
					e -> {
						e.printStackTrace();
						GlobalState.showSuccess(LocalizedText.INSTANCE.userNotAvailable());
					}));
		}, Throwable::printStackTrace);
	}
	
	private void getUserModelEnvironment() {
		AsyncCallback<ModelEnvironment> callback = getUserModelEnvironmentCallback();
		try {
			modelEnvironmentProvider.apply(userAccessId).get(callback);
		} catch (RuntimeException e) {
			callback.onFailure(e);
		}
	}
	
	private void getUser() {
		userProvider.get().andThen(user -> {
			if (user == null) {
				GlobalState.showSuccess(LocalizedText.INSTANCE.userNotAvailable());
				return;
			}
			
			if (user.getPassword() != null)
				user.setPassword(null);
			ModelPath modelPath = new ModelPath();
			RootPathElement rootPathElement = new RootPathElement(user.entityType(), user);
			modelPath.add(rootPathElement);
			explorerConstellation.onEditEntity(modelPath, false, useCase, propertyPanelProvider, gimaDialogProvider, ValidationKind.none);
		}).onError(e -> {
			e.printStackTrace();
			GlobalState.showSuccess(LocalizedText.INSTANCE.userNotAvailable());
		});
	}

}
