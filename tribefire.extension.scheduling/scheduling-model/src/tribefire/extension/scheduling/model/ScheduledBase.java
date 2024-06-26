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
package tribefire.extension.scheduling.model;

import java.util.Date;

import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.scheduling.model.action.Action;
import tribefire.extension.scheduling.model.context.Context;

@SelectiveInformation("${scheduledDate}")
public interface ScheduledBase extends StandardStringIdentifiable {

	EntityType<ScheduledBase> T = EntityTypes.T(ScheduledBase.class);

	String scheduledDate = "scheduledDate";
	String action = "action";
	String context = "context";

	Date getScheduledDate();
	void setScheduledDate(Date scheduledDate);

	Action getAction();
	void setAction(Action action);

	Context getContext();
	void setContext(Context context);

	String getDescription();
	void setDescription(String description);
}
