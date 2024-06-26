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
import com.braintribe.utils.lcd.StringTools;

/**
 *
 */
public class WopiTemplateDatabaseContextImpl implements WopiTemplateDatabaseContext, WopiTemplateDatabaseContextBuilder {

	private HibernateDialect hibernateDialect;
	private String tablePrefix;
	private String databaseDriver;
	private String databaseUrl;
	private String databaseUsername;
	private String databasePassword;
	private Integer minPoolSize;
	private Integer maxPoolSize;

	@Override
	public WopiTemplateDatabaseContextBuilder setHibernateDialect(HibernateDialect hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
		return this;
	}

	@Override
	public WopiTemplateDatabaseContextBuilder setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
		return this;
	}

	@Override
	public WopiTemplateDatabaseContextBuilder setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
		return this;
	}

	@Override
	public WopiTemplateDatabaseContextBuilder setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
		return this;
	}

	@Override
	public WopiTemplateDatabaseContextBuilder setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
		return this;
	}

	@Override
	public WopiTemplateDatabaseContextBuilder setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
		return this;
	}

	@Override
	public WopiTemplateDatabaseContextBuilder setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
		return this;
	}

	@Override
	public WopiTemplateDatabaseContextBuilder setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
		return this;
	}

	@Override
	public HibernateDialect getHibernateDialect() {
		return hibernateDialect;
	}

	@Override
	public String getTablePrefix() {
		return tablePrefix;
	}

	@Override
	public String getDatabaseDriver() {
		return databaseDriver;
	}

	@Override
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	@Override
	public String getDatabaseUsername() {
		return databaseUsername;
	}

	@Override
	public String getDatabasePassword() {
		return databasePassword;
	}

	@Override
	public Integer getMinPoolSize() {
		return minPoolSize;
	}

	@Override
	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	@Override
	public WopiTemplateDatabaseContext build() {
		return this;
	}

	@Override
	public String toString() {
		// TODO: rebuild toString()
		StringBuilder sb = new StringBuilder();
		sb.append("WopiDatabaseContextImpl:\n");
		sb.append("hibernateDialect: " + hibernateDialect + "\n");
		sb.append("tablePrefix: " + tablePrefix + "\n");
		sb.append("databaseDriver: " + databaseDriver + "\n");
		sb.append("databaseUrl: " + databaseUrl + "\n");
		sb.append("databaseUsername: " + databaseUsername + "\n");
		sb.append("databasePassword: " + StringTools.simpleObfuscatePassword(databasePassword) + "\n");
		sb.append("minPoolSize: " + minPoolSize + "\n");
		sb.append("maxPoolSize: " + maxPoolSize + "\n");
		return sb.toString();
	}

}
