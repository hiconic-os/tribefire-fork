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
package com.braintribe.model.processing.test.jta.model;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Bidirectional;
import com.braintribe.model.generic.annotation.meta.Color;
import com.braintribe.model.generic.annotation.meta.CompoundUnique;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Emphasized;
import com.braintribe.model.generic.annotation.meta.Max;
import com.braintribe.model.generic.annotation.meta.Min;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.NonDeletable;
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.annotation.meta.Unique;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * @author peter.gazdik
 */
@CompoundUnique("compoundUniqueSingle")
@CompoundUnique({ "compoundUnique1", "compoundUnique2" })
@SelectiveInformation("Selective INFORMATION")
@PositionalArguments({"unique", "bidi"})
@NonDeletable
public interface EntityWithAnnotations extends GenericEntity {

	EntityType<EntityWithAnnotations> T = EntityTypes.T(EntityWithAnnotations.class);

	@Unique
	String getUnique();
	void setUnique(String unique);

	@Bidirectional(type = EntityWithAnnotations.class, property = "bidi")
	EntityWithAnnotations getBidi();
	void setBidi(EntityWithAnnotations bidi);

	String getCoumpoundUniqueSingle();
	void setCoumpoundUniqueSingle(String coumpoundUniqueSingle);

	String getCoumpoundUnique1();
	void setCoumpoundUnique1(String coumpoundUnique1);

	String getCoumpoundUnique2();
	void setCoumpoundUnique2(String coumpoundUnique2);

	@Name("default name")
	String getPropertyWithName();
	void setPropertyWithName(String propertyWithName);

	@Name("default name")
	@Name(locale = "de", value = "Der Name", globalId = "md.names")
	@Name(locale = "br", value = "O Nome", globalId = "md.names")
	String getPropertyWithNames();
	void setPropertyWithNames(String propertyWithNames);

	@Description(value = "default description", globalId = "md.description")
	String getPropertyWithDescription();
	void setPropertyWithDescription(String propertyWithDescription);

	@Description("default description")
	@Description(locale = "de", value = "Unfug")
	String getPropertyWithDescriptions();
	void setPropertyWithDescriptions(String propertyWithDescriptions);

	@Min("0")
	@Max("100")
	Integer getHasLimit();
	void setHasLimit(Integer hasLimit);

	@Alias("alias")
	String getAliased();
	void setAliased(String aliased);

	@Alias("a")
	@Alias("ALIAS")
	String getAliasedMulti();
	void setAliasedMulti(String aliasedMulti);

	@Priority(666.666)
	String getPrioritized();
	void setPrioritized(String prioritized);
	
	@Emphasized
	String getEmphasized();
	void setEmphasized(String emphasized);

	@Color("#0F0")
	String getColored();
	void setColored(String colored);
	
	@Deprecated
	String getDeprecated();
	void setDeprecated(String deprecated);

}
