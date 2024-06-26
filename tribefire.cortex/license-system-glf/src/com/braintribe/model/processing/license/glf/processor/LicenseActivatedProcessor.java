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
package com.braintribe.model.processing.license.glf.processor;

import java.util.Collections;
import java.util.List;

import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.EntityProperty;
import com.braintribe.model.generic.manipulation.PropertyManipulation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.license.License;
import com.braintribe.model.processing.license.LicenseManager;
import com.braintribe.model.processing.license.glf.LicenseLoaderUtil;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.sp.api.AfterStateChangeContext;
import com.braintribe.model.processing.sp.api.ProcessStateChangeContext;
import com.braintribe.model.processing.sp.api.StateChangeProcessor;
import com.braintribe.model.processing.sp.api.StateChangeProcessorException;
import com.braintribe.model.processing.sp.api.StateChangeProcessorMatch;
import com.braintribe.model.processing.sp.api.StateChangeProcessorRule;
import com.braintribe.model.processing.sp.api.StateChangeProcessorSelectorContext;
import com.braintribe.model.processing.sp.api.StateChangeProcessors;
import com.braintribe.model.stateprocessing.api.StateChangeProcessorCapabilities;

public class LicenseActivatedProcessor implements StateChangeProcessor<License, GenericEntity>, StateChangeProcessorRule, StateChangeProcessorMatch {

	protected static Logger logger = Logger.getLogger(LicenseActivatedProcessor.class);

	protected String myProcessorId = LicenseActivatedProcessor.class.getSimpleName();

	protected LicenseManager licenseManager = null;
	
	@Override
	public List<StateChangeProcessorMatch> matches(StateChangeProcessorSelectorContext context) {

		if (context.isForLifecycle()) {
			return Collections.emptyList();			
		}
		
		EntityType<?> contextType = context.getEntityType();
		boolean isCorrectType = License.T.isAssignableFrom(contextType);
		if (!isCorrectType)
			return Collections.emptyList();


		EntityProperty property = context.getEntityProperty();
		String propertyName = property.getPropertyName();
		boolean isCorrectProperty = propertyName.equals(License.active);
		if (!isCorrectProperty)
			return Collections.emptyList();


		PropertyManipulation propManipulation = context.getManipulation();
		if (!(propManipulation instanceof ChangeValueManipulation)) {
			if (logger.isTraceEnabled())
				logger.trace("The manipulation is "+propManipulation);

			return Collections.emptyList();
		}

		ChangeValueManipulation cvManipulation = (ChangeValueManipulation) propManipulation;
		Object newValueObject = cvManipulation.getNewValue();
		if (newValueObject == null)
			return Collections.emptyList();

		if (!(newValueObject instanceof Boolean))
			return Collections.emptyList();

		Boolean newValue = (Boolean) newValueObject;
		
		if (logger.isTraceEnabled())
			logger.trace("Active is "+newValue);

		if (newValue.booleanValue()) {
			if (logger.isDebugEnabled())
				logger.debug("License activated");
			
			return Collections.<StateChangeProcessorMatch>singletonList( this);
		}

		return Collections.emptyList();
	}

	@Override
	public String getProcessorId() {
		return this.myProcessorId;
	}

	@Override
	public StateChangeProcessor<? extends GenericEntity, ? extends GenericEntity> getStateChangeProcessor() {
		return this;
	}

	@Override
	public StateChangeProcessorCapabilities getCapabilities() {
		return StateChangeProcessors.afterOnlyCapabilities();
	}

	@Override
	public void onAfterStateChange(AfterStateChangeContext<License> context, GenericEntity customContext) throws StateChangeProcessorException {
		License license = context.getProcessEntity();
		PersistenceGmSession session = context.getSession();

		List<License> otherLicenses = null;
		try {
			otherLicenses = LicenseLoaderUtil.getLicenses(session, license);
		} catch(Exception e) {
			logger.error("Could not find other licenses ("+license.getId()+")", e);
			return;
		}
		if ((otherLicenses != null) && (otherLicenses.size() > 0)) {
			for (License otherLicense : otherLicenses) {
				otherLicense.setActive(false);
			}
		}
		try {
			session.commit();
		} catch (Exception e) {
			logger.error("Could not deactive other licenses ("+license.getId()+")", e);
		}
		
		this.licenseManager.reloadLicense();
	}

	@Override
	public void processStateChange(ProcessStateChangeContext<License> context, GenericEntity customContext) throws StateChangeProcessorException {
		//Intentionally left empty
	}

	@Required
	public void setLicenseManager(LicenseManager licenseManager) {
		this.licenseManager = licenseManager;
	}

	@Override
	public String getRuleId() {
		return myProcessorId;
	}

	@Override
	public StateChangeProcessor<? extends GenericEntity, ? extends GenericEntity> getStateChangeProcessor(String processorId) {
		return this;
	}

}
