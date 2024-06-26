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
package tribefire.extension.scheduling.templates.api;

import tribefire.extension.scheduling.SchedulingConstants;
import tribefire.extension.templates.api.TemplateContextImpl;

public class SchedulingTemplateContextImpl extends TemplateContextImpl<SchedulingTemplateContext>
		implements SchedulingTemplateContext, SchedulingTemplateContextBuilder {

	private String accessId = SchedulingConstants.ACCESS_ID;
	private Long pollingIntervalMs;
	private String databaseUrl;
	private String databaseUser;
	private String databasePassword;
	private String databaseConnectionGlobalId;

	@Override
	public SchedulingTemplateContext build() {
		return this;
	}

	@Override
	public int hashCode() {
		return getIdPrefix().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SchedulingTemplateContext) {
			return ((SchedulingTemplateContext) obj).getIdPrefix().equals(this.getIdPrefix());
		}
		return super.equals(obj);
	}

	@Override
	public SchedulingTemplateContextBuilder setAccessId(String accessId) {
		if (accessId != null) {
			this.accessId = accessId;
		}
		return this;
	}

	@Override
	public SchedulingTemplateContextBuilder setPollingIntervalMs(Long pollingInterval) {
		this.pollingIntervalMs = pollingInterval;
		return this;
	}

	@Override
	public String getAccessId() {
		return accessId;
	}

	@Override
	public Long getPollingIntervalMs() {
		return pollingIntervalMs;
	}

	@Override
	public SchedulingTemplateContextBuilder setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
		return this;
	}

	@Override
	public SchedulingTemplateContextBuilder setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
		return this;
	}

	@Override
	public SchedulingTemplateContextBuilder setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
		return this;
	}

	@Override
	public SchedulingTemplateContextBuilder setDatabaseConnectionGlobalId(String databaseConnectionGlobalId) {
		this.databaseConnectionGlobalId = databaseConnectionGlobalId;
		return this;
	}

	@Override
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	@Override
	public String getDatabaseUser() {
		return databaseUser;
	}

	@Override
	public String getDatabasePassword() {
		return databasePassword;
	}

	@Override
	public String getDatabaseConnectionGlobalId() {
		return databaseConnectionGlobalId;
	}

}
