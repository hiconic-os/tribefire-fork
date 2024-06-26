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
package tribefire.extension.messaging.model.deployment.event.rule;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import tribefire.extension.messaging.model.InterceptionTarget;
import tribefire.extension.messaging.model.comparison.AddEntries;
import tribefire.extension.messaging.model.comparison.DiffType;
import tribefire.extension.messaging.model.comparison.TypesProperty;
import tribefire.extension.messaging.model.deployment.event.DiffLoader;

import java.util.Set;

public interface ProducerDiffEventRule extends ProducerEventRule{
    EntityType<ProducerDiffEventRule> T = EntityTypes.T(ProducerDiffEventRule.class);

    String diffType = "diffType";
    String diffLoader = "diffLoader";
    String listedPropertiesOnly = "listedPropertiesOnly";
    String addEntries = "addEntries";
    String extractionPropertyPaths = "extractionPropertyPaths";

    @Name("Type of Diff to be calculated")
    @Description("This only refers to InterceptionTarget.DIFF and is not a mandatory field. If not set DiffType.ChangesOnly shall apply")
    DiffType getDiffType();
    void setDiffType(DiffType diffType);

    @Initializer("enum(tribefire.extension.messaging.model.deployment.event.DiffLoader,QUERY)")
    DiffLoader getDiffLoader();
    void setDiffLoader(DiffLoader diffLoader);

    @Name("Only listed properties should be scanned")
    @Description("Defines if only the properties from the 'fieldsToInclude' should be scanned for diff")
    @Initializer("true")
    Boolean getListedPropertiesOnly();
    void setListedPropertiesOnly(Boolean listedPropertiesOnly);

    @Name("Entries to add to diff")
    @Description("Defines the entries to be added to diff")
    AddEntries getAddEntries();
    void setAddEntries(AddEntries addEntries);

    @Name("Extraction path")
    @Description("Property path to extract entry that is to be compared")
    Set<TypesProperty> getExtractionPropertyPaths();
    void setExtractionPropertyPaths(Set<TypesProperty> extractionPropertyPaths);

    @Override
    @Initializer("enum(tribefire.extension.messaging.model.InterceptionTarget,DIFF)")
    default InterceptionTarget getInterceptionTarget(){
        return InterceptionTarget.DIFF;
    }

    default String getExtractionPathMatchingByType(GenericEntity entity){
        String ts = entity.entityType().getTypeSignature();
        //@formatter:off
        return this.getExtractionPropertyPaths().stream()
                .filter(e->e.getEntityType().getTypeSignature().equals(ts))
                .findFirst()
                .map(TypesProperty::getProperty)
                .orElse(null);
        //@formatter:on
    }
}
