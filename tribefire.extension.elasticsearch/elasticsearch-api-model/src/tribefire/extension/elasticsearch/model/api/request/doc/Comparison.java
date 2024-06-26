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
package tribefire.extension.elasticsearch.model.api.request.doc;

import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.elasticsearch.model.api.request.doc.conditions.Condition;

@Abstract
public interface Comparison extends Condition {

	EntityType<Comparison> T = EntityTypes.T(Comparison.class);

	String filter = "filter";
	String matchPhrase = "matchPhrase";
	String priority = "priority";

	@Priority(0.79d)
	@Name("Filter")
	@Description("Filter.")
	boolean getFilter();
	void setFilter(boolean filter);

	@Priority(0.78d)
	@Name("Match Phrase")
	@Description("Match Phrase.")
	boolean getMatchPhrase();
	void setMatchPhrase(boolean matchPhrase);

	@Priority(0.7d)
	@Name("Priority")
	@Description("Priority.")
	String getPriority();
	void setPriority(String priority);

}
