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
package com.braintribe.model.processing.generic.synchronize.experts;

import java.util.Collections;

import com.braintribe.cfg.Configurable;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.processing.core.expert.api.GmExpertRegistry;
import com.braintribe.model.processing.core.expert.impl.ConfigurableGmExpertRegistry;
import com.braintribe.model.processing.generic.synchronize.GenericEntitySynchronizationException;
import com.braintribe.model.processing.generic.synchronize.api.IdentityManager;
import com.braintribe.model.processing.generic.synchronize.api.SynchronizationContext;
import com.braintribe.model.processing.generic.synchronize.experts.ResourceUploadExpert.UploadInfo;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.source.FileUploadSource;
import com.braintribe.model.resource.source.UploadSource;
import com.braintribe.model.resource.source.UrlUploadSource;

/**
 * An {@link IdentityManager} that takes care of uploading and identifying resources that have an
 * {@link FileUploadSource} set.
 */
public class ResourceIdentityManager extends QueryingIdentityManager {

	private GmExpertRegistry experRegistry =
			new ConfigurableGmExpertRegistry()
			.add(ResourceUploadExpert.class, FileUploadSource.class, ResourceUploadExpert.fileUploadExpert)
			.add(ResourceUploadExpert.class, UrlUploadSource.class, ResourceUploadExpert.urlUploadExpert);


	@Configurable
	public void setExperRegistry(GmExpertRegistry experRegistry) {
		this.experRegistry = experRegistry;
	}
	
	@Override
	public boolean isResponsible(GenericEntity instanceToBeCloned, EntityType<? extends GenericEntity> entityType, SynchronizationContext context) {
		if (instanceToBeCloned instanceof Resource) {
			return (((Resource) instanceToBeCloned).getResourceSource() instanceof UploadSource);
		}
		return false;
	}

	@Override
	public GenericEntity findEntity(GenericEntity instanceToBeCloned, EntityType<? extends GenericEntity> entityType, SynchronizationContext context) throws GenericEntitySynchronizationException {
		
		// First search whether we find an existing resource with same globalId
		GenericEntity existing = query(context.getSession(), instanceToBeCloned, entityType, Collections.singleton(GenericEntity.globalId));
		if (existing != null) {
			return existing;
		}

		// We can assume to get an Resource with a LocalFileSource set. 
		Resource resource = (Resource) instanceToBeCloned;	
		UploadSource source = (UploadSource) resource.getResourceSource();
		
		
		ResourceUploadExpert<UploadSource> uploadExpert = experRegistry.getExpert(ResourceUploadExpert.class).forInstance(source);
		UploadInfo uploadInfo = uploadExpert.getUploadInfo(source);
		
		
		String resourceName = resource.getName();
		if (resourceName == null) {
			resourceName = uploadInfo.getDefaultResourceName(); //default
			if (resourceName == null) {
				resourceName = "Uploaded Resource"; //default default
			}
		}
		// Haven't found an existing one, thus we create/upload a new one based on the localFile.
		PersistenceGmSession session = context.getSession();
		try {
			return session
					.resources()
					.create()
						.name(resourceName)
						.store(uploadInfo.getInputStreamProvider());
						
		} catch (Exception e) {
			throw new GenericEntitySynchronizationException("Error while uploading resource from upload source: "+source,e);
		} 
	}
	
	@Override
	public boolean canTransferProperty(GenericEntity instanceToBeCloned, GenericEntity clonedInstance, EntityType<? extends GenericEntity> entityType, Property property, SynchronizationContext context) {
		return false;
	}
	
}
