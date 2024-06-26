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
package tribefire.extension.cache.service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.LifecycleAware;
import com.braintribe.cfg.Required;
import com.braintribe.codec.marshaller.api.EntityRecurrenceDepth;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.GmSerializationOptionsBuilder;
import com.braintribe.codec.marshaller.api.IdentityManagementMode;
import com.braintribe.codec.marshaller.api.IdentityManagementModeOption;
import com.braintribe.codec.marshaller.api.Marshaller;
import com.braintribe.codec.marshaller.api.TypeExplicitness;
import com.braintribe.codec.marshaller.api.TypeExplicitnessOption;
import com.braintribe.codec.string.HashCodec;
import com.braintribe.common.lcd.NotImplementedException;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.AbsenceInformation;
import com.braintribe.model.generic.reflection.BaseType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.StandardCloningContext;
import com.braintribe.model.processing.service.api.ProceedContext;
import com.braintribe.model.processing.service.api.ServiceAroundProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.service.api.AuthorizableRequest;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.cache.model.deployment.service.HashAlgorithm;
import tribefire.extension.cache.model.status.CacheAspectStatus;

public class CacheAspect implements ServiceAroundProcessor<ServiceRequest, Object>, LifecycleAware {

	private final static Logger logger = Logger.getLogger(CacheAspect.class);

	protected static final GenericModelTypeReflection typeReflection = GMF.getTypeReflection();

	private Marshaller marshaller;

	private HashCodec hashCodec;
	private String hashAlgorithm;

	private final BaseType baseType = BaseType.INSTANCE;

	// -----------------
	// Configuration
	// -----------------

	private tribefire.extension.cache.model.deployment.service.CacheAspect deployable;
	private CacheAspectInterface<? extends CacheAspectStatus> expert;

	// -----------------
	// Local
	// -----------------

	private boolean active;

	// -----------------------------------------------------------------------
	// LIFECYCLE AWARE
	// -----------------------------------------------------------------------

	@Override
	public void postConstruct() {
		HashAlgorithm _hashAlgorithm = deployable.getHashAlgorithm();
		switch (_hashAlgorithm) {
			case MD5:
				this.hashAlgorithm = "MD5";
				break;
			case SHA1:
				this.hashAlgorithm = "SHA-1";
				break;
			case SHA256:
				this.hashAlgorithm = "SHA-256";
				break;
			case SHA384:
				this.hashAlgorithm = "SHA-384";
				break;
			case SHA512:
				this.hashAlgorithm = "SHA-512";
				break;

			default:
				throw new IllegalArgumentException("'hashAlgorithm': '" + _hashAlgorithm + "' not supported");
		}

		hashCodec = new HashCodec(this.hashAlgorithm);

		active = deployable.getUseCache();

		logger.info(() -> "Started up '" + tribefire.extension.cache.model.deployment.service.CacheAspect.T.getShortName() + "' with externalId: '"
				+ deployable.getExternalId() + "' name: '" + deployable.getName() + "'");
	}

	@Override
	public void preDestroy() {

		logger.info(() -> "Shutting down '" + tribefire.extension.cache.model.deployment.service.CacheAspect.T.getShortName() + "' with externalId: '"
				+ deployable.getExternalId() + "' name: '" + deployable.getName() + "'");
	}

	// -----------------------------------------------------------------------
	// AROUND ASPECT
	// -----------------------------------------------------------------------

	@Override
	public Object process(ServiceRequestContext requestContext, ServiceRequest request, ProceedContext proceedContext) {
		if (active) {
			String hash = calculateRequestHash(request);

			Object result = expert.retrieveCacheResult(() -> {
				Object serviceResponse = proceedContext.proceed(request);
				return serviceResponse;
			}, request, hash);

			return result;
		} else {
			Object serviceResponse = proceedContext.proceed(request);
			return serviceResponse;
		}
	}

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	public CacheAspectInterface<? extends CacheAspectStatus> expert() {
		return expert;
	}

	public void activate(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return this.active;
	}

	public tribefire.extension.cache.model.deployment.service.CacheAspect deployable() {
		return deployable;
	}

	public String calculateRequestHash(ServiceRequest request) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		//@formatter:off
		GmSerializationOptionsBuilder options = GmSerializationOptions.defaults //
						.set(EntityRecurrenceDepth.class, -1) //
						.set(TypeExplicitnessOption.class, TypeExplicitness.always) //
						.set(IdentityManagementModeOption.class, IdentityManagementMode.off) //
						.inferredRootType(baseType.getActualType(request)); //
		//@formatter:on

		ServiceRequest clone = request.clone(new StandardCloningContext() {
			@Override
			public boolean canTransferPropertyValue(EntityType<? extends GenericEntity> entityType, Property property,
					GenericEntity instanceToBeCloned, GenericEntity clonedInstance, AbsenceInformation sourceAbsenceInformation) {

				if (Resource.T.isAssignableFrom(property.getDeclaringType())) {
					throw new NotImplementedException("Handling resources is not implemented yet - it's used in request: '" + request + "'");
				}

				//@formatter:off
				return 	
						!property.isConfidential() && 
						!property.isGlobalId() && 
						!property.isIdentifier() && 
						!property.isPartition();
				//@formatter:on
			}

			@Override
			public GenericEntity supplyRawClone(EntityType<? extends GenericEntity> entityType, GenericEntity instanceToBeCloned) {
				return entityType.create();
			}
		});
		boolean supportsAuthentication = request.supportsAuthentication();
		if (supportsAuthentication) {
			AuthorizableRequest authorizedRequest = (AuthorizableRequest) clone;
			authorizedRequest.setSessionId(null);
		}

		marshaller.marshall(os, clone, options);
		String requestAsString = new String(os.toByteArray(), Charset.defaultCharset());

		String hash = hashCodec.encode(requestAsString);
		return hash;
	}
	// -----------------------------------------------------------------------
	// HELPERS
	// -----------------------------------------------------------------------

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	@Required
	public void setDeployable(tribefire.extension.cache.model.deployment.service.CacheAspect deployable) {
		this.deployable = deployable;
	}

	@Configurable
	@Required
	public void setExpert(CacheAspectInterface<? extends CacheAspectStatus> expert) {
		this.expert = expert;
	}

	@Configurable
	@Required
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}
}
