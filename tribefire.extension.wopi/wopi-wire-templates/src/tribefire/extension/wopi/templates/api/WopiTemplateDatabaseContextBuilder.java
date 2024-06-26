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
package tribefire.extension.wopi.templates.api;

import com.braintribe.model.accessdeployment.hibernate.HibernateDialect;

/**
 * Builder for setting up Database Template Context (HibernateAccess)
 * 
 *
 */
public interface WopiTemplateDatabaseContextBuilder {

	WopiTemplateDatabaseContextBuilder setHibernateDialect(HibernateDialect hibernateDialect);

	WopiTemplateDatabaseContextBuilder setTablePrefix(String tablePrefix);

	WopiTemplateDatabaseContextBuilder setDatabaseDriver(String databaseDriver);

	WopiTemplateDatabaseContextBuilder setDatabaseUrl(String databaseUrl);

	WopiTemplateDatabaseContextBuilder setDatabaseUsername(String databaseUsername);

	WopiTemplateDatabaseContextBuilder setDatabasePassword(String databasePassword);

	WopiTemplateDatabaseContextBuilder setMinPoolSize(Integer minPoolSize);

	WopiTemplateDatabaseContextBuilder setMaxPoolSize(Integer maxPoolSize);

	WopiTemplateDatabaseContext build();
}