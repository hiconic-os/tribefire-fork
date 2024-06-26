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
package tribefire.extension.ldap.initializer.wire.space;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.ldap.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.ldap.initializer.wire.contract.LdapInitializerModuleModelsContract;
import tribefire.extension.ldap.templates.api.LdapTemplateContext;
import tribefire.extension.ldap.templates.wire.contract.LdapMetaDataContract;
import tribefire.extension.ldap.templates.wire.contract.LdapTemplatesContract;

@Managed
public class LdapInitializerModuleModelsSpace extends AbstractInitializerSpace implements LdapInitializerModuleModelsContract {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private LdapTemplatesContract ldapTemplates;

	@Import
	private LdapMetaDataContract ldapMetaData;

	@Import
	private LdapInitializerModuleSpace ldapInitializer;

	@Override
	public void metaData() {
		LdapTemplateContext context = ldapInitializer.defaultContext();
		ldapMetaData.metaData(context);
	}

	@Override
	public GmMetaModel deploymentModel() {
		LdapTemplateContext context = ldapInitializer.defaultContext();
		return ldapMetaData.deploymentModel(context);
	}

	@Override
	public GmMetaModel serviceModel() {
		LdapTemplateContext context = ldapInitializer.defaultContext();
		return ldapMetaData.serviceModel(context);
	}

	@Override
	public void registerModels() {
		LdapTemplateContext context = ldapInitializer.defaultContext();
		ldapMetaData.registerModels(context);
	}
}
