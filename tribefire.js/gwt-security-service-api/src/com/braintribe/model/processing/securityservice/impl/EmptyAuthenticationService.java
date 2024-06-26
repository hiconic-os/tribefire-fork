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
package com.braintribe.model.processing.securityservice.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.securityservice.api.AuthenticationService;
import com.braintribe.model.processing.securityservice.api.exceptions.AuthenticationException;
import com.braintribe.model.securityservice.UserAuthenticationResponse;
import com.braintribe.model.securityservice.credentials.Credentials;
import com.braintribe.model.securityservice.credentials.TokenCredentials;
import com.braintribe.model.securityservice.credentials.TokenWithCorrelationIdCredentials;
import com.braintribe.model.securityservice.credentials.TokenWithPasswordCredentials;
import com.braintribe.model.securityservice.credentials.TokenWithUserNameCredentials;
import com.braintribe.model.securityservice.credentials.UserPasswordCredentials;
import com.braintribe.model.securityservice.credentials.identification.UserIdentification;
import com.braintribe.model.securityservice.credentials.identification.UserNameIdentification;
import com.braintribe.model.securityservice.messages.UnsupportedCredentialsMessage;

public class EmptyAuthenticationService implements AuthenticationService {

	private Set<String> supportedCredentials = Collections.<String>emptySet();
	private Set<String> supportedUserIdentifications = Collections.<String>emptySet();
	
	public void setSupportedCredentials(Set<Class<? extends Credentials>> supportedCredentials) {
		
		if (supportedCredentials == null) {
			this.supportedCredentials = Collections.<String>emptySet();
		} else {
			Set<String> typeSignatures = new HashSet<String>();
			for (Class<? extends Credentials> type : supportedCredentials) {
				EntityType<? extends Credentials> entityType = GMF.getTypeReflection().getEntityType(type);
				if (entityType != null) {
					typeSignatures.add(entityType.getTypeSignature());
				}
			}
			this.supportedCredentials = typeSignatures;
		}
		
	}
	
	public void setSupportedUserIdentifications(Set<Class<? extends UserIdentification>> supportedUserIdentifications) {

		if (supportedUserIdentifications == null) {
			this.supportedUserIdentifications = Collections.<String>emptySet();
		} else {
			Set<String> typeSignatures = new HashSet<String>();
			for (Class<? extends UserIdentification> type : supportedUserIdentifications) {
				EntityType<? extends UserIdentification> entityType = GMF.getTypeReflection().getEntityType(type);
				if (entityType != null) {
					typeSignatures.add(entityType.getTypeSignature());
				}
			}
			this.supportedUserIdentifications = typeSignatures;
		}
		
	}
	
	@Override
	public UserAuthenticationResponse authenticate(Credentials credentials) throws AuthenticationException {
		
		if (credentials instanceof UserPasswordCredentials) {

			return authenticate((UserPasswordCredentials)credentials);
			
		} else if (credentials instanceof TokenWithCorrelationIdCredentials) {
			
			return authenticate((TokenWithCorrelationIdCredentials)credentials);
			
		} else if (credentials instanceof TokenWithPasswordCredentials) {
			
			return authenticate((TokenWithPasswordCredentials)credentials);
			
		}
		
		return createUnsupportedCredentialsResponse("Given credentials [ "+(credentials != null ? credentials.getClass() : "null")+" ] are not supported by "+this.getClass().getSimpleName());
	}

	/**
	 * <p>
	 * Offers a convenience for specializations supporting {@link TokenWithUserNameCredentials}.
	 * 
	 * <p>
	 * Subclasses can override this method in order to handle {@code userName} and {@code token} properties extracted
	 * from an incoming {@link TokenWithUserNameCredentials}.
	 * 
	 * @param userName
	 *            property extracted from an incoming {@link TokenWithUserNameCredentials}
	 * @param password
	 *            property extracted from an incoming {@link TokenWithUserNameCredentials}
	 * @return An {@link UserAuthenticationResponse}
	 * @throws AuthenticationException
	 *             To be thrown by specializations of this class in case of authentication failures.
	 */
	protected UserAuthenticationResponse authenticateWithPassword(String userName, String password) throws AuthenticationException {
		return createUnsupportedCredentialsResponse("Given credentials are not supported by " + this.getClass().getSimpleName());
	}

	/**
	 * <p>
	 * Offers a convenience for specializations supporting {@link TokenCredentials}.
	 * 
	 * <p>
	 * Subclasses can override this method in order to handle the {@code token} property extracted from an incoming
	 * {@link TokenCredentials}.
	 * 
	 * @param token
	 *            property extracted from an incoming {@link TokenWithUserNameCredentials}
	 * @return An {@link UserAuthenticationResponse}
	 * @throws AuthenticationException
	 *             To be thrown by specializations of this class in case of authentication failures.
	 */
	protected UserAuthenticationResponse authenticateWithToken(String token) throws AuthenticationException {
		return createUnsupportedCredentialsResponse("Given credentials are not supported by " + this.getClass().getSimpleName());
	}

	/**
	 * <p>
	 * Offers a convenience for specializations supporting {@link TokenWithUserNameCredentials}.
	 * 
	 * <p>
	 * Subclasses can override this method in order to handle {@code userName} and {@code token} properties extracted
	 * from an incoming {@link TokenWithUserNameCredentials}.
	 * 
	 * @param userName
	 *            property extracted from an incoming {@link TokenWithUserNameCredentials}
	 * @param token
	 *            property extracted from an incoming {@link TokenWithUserNameCredentials}
	 * @return An {@link UserAuthenticationResponse}
	 * @throws AuthenticationException
	 *             To be thrown by specializations of this class in case of authentication failures.
	 */
	protected UserAuthenticationResponse authenticateWithTokenAndUserName(String userName, String token) throws AuthenticationException {
		return createUnsupportedCredentialsResponse("Given credentials are not supported by " + this.getClass().getSimpleName());
	}

	/**
	 * <p>
	 * Offers a convenience for specializations supporting {@link TokenWithPasswordCredentials}.
	 * 
	 * <p>
	 * Subclasses can override this method in order to handle {@code userName}, {@code token} and {@code password}
	 * properties extracted from an incoming {@link TokenWithPasswordCredentials}.
	 * 
	 * @param userName
	 *            property extracted from an incoming {@link TokenWithPasswordCredentials}
	 * @param token
	 *            property extracted from an incoming {@link TokenWithPasswordCredentials}
	 * @param password
	 *            property extracted from an incoming {@link TokenWithPasswordCredentials}
	 * @return An {@link UserAuthenticationResponse}
	 * @throws AuthenticationException
	 *             To be thrown by specializations of this class in case of authentication failures.
	 */
	protected UserAuthenticationResponse authenticateWithTokenAndPassword(String userName, String token, String password) throws AuthenticationException {
		return createUnsupportedCredentialsResponse("Given credentials are not supported by " + this.getClass().getSimpleName());
	}

	/**
	 * <p>
	 * Offers a convenience for specializations supporting {@link TokenWithCorrelationIdCredentials}.
	 * 
	 * <p>
	 * Subclasses can override this method in order to handle {@code userName}, {@code token} and {@code correlationId}
	 * properties extracted from an incoming {@link TokenWithCorrelationIdCredentials}.
	 * 
	 * @param userName
	 *            property extracted from an incoming {@link TokenWithCorrelationIdCredentials}
	 * @param token
	 *            property extracted from an incoming {@link TokenWithCorrelationIdCredentials}
	 * @param correlationId
	 *            property extracted from an incoming {@link TokenWithCorrelationIdCredentials}
	 * @return An {@link UserAuthenticationResponse}
	 * @throws AuthenticationException
	 *             To be thrown by specializations of this class in case of authentication failures.
	 */
	protected UserAuthenticationResponse authenticateWithTokenAndCorrelationId(String userName, String token, String correlationId) throws AuthenticationException {
		return createUnsupportedCredentialsResponse("Given credentials are not supported by " + this.getClass().getSimpleName());
	}

	protected UserAuthenticationResponse authenticate(UserPasswordCredentials credentials) throws AuthenticationException {

		UserIdentification userIdentification = credentials.getUserIdentification();
		
		if (!(userIdentification instanceof UserNameIdentification)) {
			return createUnsupportedCredentialsResponse("Given identification [ "+(userIdentification != null ? userIdentification.getClass() : "null")+" ] is not supported by "+this.getClass().getSimpleName());
		}
		
		return authenticateWithPassword(((UserNameIdentification)userIdentification).getUserName(), credentials.getPassword());
		
	}
	
	protected UserAuthenticationResponse authenticate(TokenWithCorrelationIdCredentials credentials) throws AuthenticationException {
		return authenticateWithTokenAndCorrelationId(credentials.getUserName(), credentials.getToken(), credentials.getCorrelationId());
	}
	
	protected UserAuthenticationResponse authenticate(TokenWithPasswordCredentials credentials) throws AuthenticationException {
		return authenticateWithTokenAndPassword(credentials.getUserName(), credentials.getToken(), credentials.getPassword());
	}
	
	protected Set<String> getSupportedCredentials() {
		return supportedCredentials;
	}
	
	protected Set<String> getSupportedUserIdentifications() {
		return supportedUserIdentifications;
	}
	
	protected UserAuthenticationResponse createUnsupportedCredentialsResponse(String message) {
		
		UnsupportedCredentialsMessage unsupportedCredentialsMessage = UnsupportedCredentialsMessage.T.create();
		unsupportedCredentialsMessage.setSupportedCredentials(getSupportedCredentials());
		unsupportedCredentialsMessage.setSupportedUserIdentifications(getSupportedUserIdentifications());
		unsupportedCredentialsMessage.setMessage(message);
		
		UserAuthenticationResponse response = UserAuthenticationResponse.T.create();

		response.setStatusMessage(unsupportedCredentialsMessage);
		
		return response;
	}

}
