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
package com.braintribe.gwt.gme.constellation.client;

import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.gwt.gmsession.client.GwtPersistenceGmSession;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.meta.cmd.CmdResolverImpl;
import com.braintribe.model.processing.meta.cmd.ResolutionContextBuilder;
import com.braintribe.model.processing.meta.cmd.context.SelectorContextAspect;
import com.braintribe.model.processing.meta.cmd.context.aspects.UseCaseAspect;
import com.braintribe.model.processing.meta.oracle.BasicModelOracle;
import com.braintribe.model.processing.meta.oracle.ModelOracle;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.processing.session.api.managed.ModelAccessory;
import com.braintribe.model.processing.session.impl.managed.AbstractModelAccessory;
import com.braintribe.model.processing.session.impl.managed.BasicManagedGmSession;
import com.braintribe.model.processing.session.impl.persistence.TransientPersistenceGmSession;
import com.braintribe.provider.Holder;

/**
 * Session used for transient operations.
 * @author michel.docouto
 *
 */
public class TransientGmSession extends TransientPersistenceGmSession {
	private static Logger logger = Logger.getLogger(TransientGmSession.class);
	
	private GmMetaModel transientMetaModel;
	private Map<Class<? extends SelectorContextAspect<?>>, Supplier<?>> dynamicAspectProviders;
	
	@Configurable
	public void setDynamicAspectProviders(Map<Class<? extends SelectorContextAspect<?>>, Supplier<?>> dynamicAspectProviders) {
		this.dynamicAspectProviders = dynamicAspectProviders;
	}
	
	public void configureGmMetaModel(GmMetaModel transientMetaModel) {
		this.transientMetaModel = transientMetaModel;
		if (transientMetaModel != null)
			setModelAccessory(createModelAccessory());
		else {
			modelAccessory = null;
		}
	}
	
	public GmMetaModel getTransientGmMetaModel() {
		return transientMetaModel;
	}
	
	@Override
	protected ModelAccessory createModelAccessory() {
		if (transientMetaModel != null)
			return new TransientModelAccessory();
		
		return null;
	}
	
	private class TransientModelAccessory extends AbstractModelAccessory {
		private BasicManagedGmSession modelSession;

		@Override
		public CmdResolver getCmdResolver() {
			if (cmdResolver == null) {
				ResolutionContextBuilder rcb = new ResolutionContextBuilder(getOracle());
				rcb.addDynamicAspectProviders(dynamicAspectProviders);
				rcb.addDynamicAspectProvider(UseCaseAspect.class, GwtPersistenceGmSession::defaultGmeUseCase);
				rcb.setSessionProvider(new Holder<>(new Object()));

				cmdResolver = new CmdResolverImpl(rcb.build());
			}
			
			cmdResolver.getMetaData().useCase("tmp");

			return cmdResolver;
		}
				
		@Override
		public ManagedGmSession getModelSession() {
			if (modelSession == null) {
				modelSession = new BasicManagedGmSession();
				modelSession.setModelAccessory(this);
				
				try {
					modelSession.merge().adoptUnexposed(false).doFor(transientMetaModel);
				} catch (GmSessionException e) {
					logger.error("error while filling model session of ModelAccessory", e);
				}
			}

			return modelSession;
		}

		@SuppressWarnings("unusable-by-js")
		@Override
		public GmMetaModel getModel() {
			return transientMetaModel;
		}

		@Override
		public ModelOracle getOracle() {
			if (modelOracle == null) {
				modelOracle = new BasicModelOracle(getModel());
			}
			return modelOracle;
		}
	}

}
