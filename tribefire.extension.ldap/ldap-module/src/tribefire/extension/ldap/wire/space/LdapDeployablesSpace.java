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
package tribefire.extension.ldap.wire.space;

import com.braintribe.model.access.impl.LdapAccess;
import com.braintribe.model.access.impl.LdapUserAccess;
import com.braintribe.model.ldapauthenticationdeployment.LdapAuthentication;
import com.braintribe.model.ldapconnectiondeployment.LdapConnection;
import com.braintribe.model.processing.bootstrapping.TribefireRuntime;
import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.ldap.service.HealthCheckProcessor;
import com.braintribe.model.processing.securityservice.ldap.LdapAuthenticationService;
import com.braintribe.provider.Holder;
import com.braintribe.transport.ssl.SslSocketFactoryProvider;
import com.braintribe.transport.ssl.impl.EasySslSocketFactoryProvider;
import com.braintribe.transport.ssl.impl.StrictSslSocketFactoryProvider;
import com.braintribe.utils.ldap.LdapConnectionStack;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.module.wire.contract.SystemUserRelatedContract;
import tribefire.module.wire.contract.TribefirePlatformContract;

@Managed
public class LdapDeployablesSpace implements WireSpace {

	@Import
	protected TribefirePlatformContract tfPlatform;

	@Import
	protected SystemUserRelatedContract systemUserRelated;

	/** ldap.authentication.service */
	@Managed
	public LdapAuthenticationService service(ExpertContext<LdapAuthentication> context) {

		LdapAuthentication deployable = context.getDeployable();

		LdapAuthenticationService bean = new LdapAuthenticationService();
		bean.setSessionFactory(systemUserRelated.sessionFactory());
		bean.setLdapAccessId(deployable.getLdapAccess().getExternalId());
		return bean;
	}

	/** ldap.access */
	@Managed
	public LdapAccess access(ExpertContext<com.braintribe.model.ldapaccessdeployment.LdapAccess> context) {

		LdapAccess bean = new LdapAccess();

		com.braintribe.model.ldapaccessdeployment.LdapAccess deployable = context.getDeployable();

		com.braintribe.utils.ldap.LdapConnection ldapConnectionStack = context.resolve(deployable.getLdapConnection(), LdapConnection.T);

		bean.setMetaModelProvider(new Holder<>(deployable.getMetaModel()));
		bean.setLdapConnectionStack(ldapConnectionStack);
		bean.setBase(deployable.getBase());
		bean.setSearchPageSize(deployable.getSearchPageSize());

		return bean;

	}

	/** ldap.user.access */
	@Managed
	public LdapUserAccess userAccess(ExpertContext<com.braintribe.model.ldapaccessdeployment.LdapUserAccess> context) {

		LdapUserAccess bean = new LdapUserAccess();

		com.braintribe.model.ldapaccessdeployment.LdapUserAccess deployable = context.getDeployable();

		com.braintribe.utils.ldap.LdapConnection ldapConnectionStack = context.resolve(deployable.getLdapConnection(), LdapConnection.T);

		bean.setMetaModelProvider(new Holder<>(deployable.getMetaModel()));
		bean.setGroupBase(deployable.getGroupBase());
		bean.setGroupIdAttribute(deployable.getGroupIdAttribute());
		bean.setGroupMemberAttribute(deployable.getGroupMemberAttribute());
		bean.setGroupNameAttribute(deployable.getGroupNameAttribute());
		bean.setGroupObjectClasses(deployable.getGroupObjectClasses());
		bean.setGroupsAreRoles(deployable.getGroupsAreRoles());
		bean.setLdapConnectionStack(ldapConnectionStack);
		bean.setMemberAttribute(deployable.getMemberAttribute());
		bean.setRoleIdAttribute(deployable.getRoleIdAttribute());
		bean.setRoleNameAttribute(deployable.getRoleNameAttribute());
		bean.setUserBase(deployable.getUserBase());
		bean.setUserDescriptionAttribute(deployable.getUserDescriptionAttribute());
		bean.setUserEmailAttribute(deployable.getUserEmailAttribute());
		bean.setUserFilter(deployable.getUserFilter());
		bean.setUserFirstNameAttribute(deployable.getUserFirstNameAttribute());
		bean.setUserIdAttribute(deployable.getUserIdAttribute());
		bean.setUserLastLoginAttribute(deployable.getUserLastLoginAttribute());
		bean.setUserLastNameAttribute(deployable.getUserLastNameAttribute());
		bean.setUserMemberOfAttribute(deployable.getUserMemberOfAttribute());
		bean.setUserNameAttribute(deployable.getUserNameAttribute());
		bean.setUserObjectClasses(deployable.getUserObjectClasses());
		bean.setSearchPageSize(deployable.getSearchPageSize());

		return bean;

	}

	/** ldap.connection */
	@Managed
	public LdapConnectionStack connection(ExpertContext<LdapConnection> context) {

		LdapConnection deployable = context.getDeployable();

		LdapConnectionStack bean = new LdapConnectionStack();
		bean.setConnectionUrl(deployable.getConnectionUrl());
		bean.setUsername(deployable.getUsername());
		bean.setPassword(deployable.getPassword());
		bean.setInitialContextFactory(deployable.getInitialContextFactory());
		bean.setReferralFollow(deployable.getReferralFollow());
		bean.setUseTLSExtension(deployable.getUseTLSExtension());
		bean.setConnectTimeout(deployable.getConnectTimeout());
		bean.setDnsTimeoutInitial(deployable.getDnsTimeoutInitial());
		bean.setDnsTimeoutRetries(deployable.getDnsTimeoutRetries());
		bean.setEnvironmentSettings(deployable.getEnvironmentSettings());
		bean.setSslSocketFactoryProvider(sslSocketFactoryProvider());

		return bean;

	}

	@Managed
	public SslSocketFactoryProvider sslSocketFactoryProvider() {
		SslSocketFactoryProvider bean = TribefireRuntime.getAcceptSslCertificates() ? new EasySslSocketFactoryProvider()
				: new StrictSslSocketFactoryProvider();

		return bean;
	}

	@Managed
	public HealthCheckProcessor healthCheckProcessor(ExpertContext<com.braintribe.model.ldapaccessdeployment.HealthCheckProcessor> context) {

		com.braintribe.model.ldapaccessdeployment.HealthCheckProcessor deployable = context.getDeployable();

		HealthCheckProcessor bean = new HealthCheckProcessor();
		bean.setSessionFactory(systemUserRelated.sessionFactory());
		bean.setDeployRegistry(tfPlatform.deployment().deployRegistry());
		bean.setTimeWarnThreshold(deployable.getTimeWarnThreshold());
		return bean;

	}
}
