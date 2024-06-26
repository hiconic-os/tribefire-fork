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
package tribefire.extension.messaging.model.comparison;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface PropertyModel extends GenericEntity {
    EntityType<PropertyModel> T = EntityTypes.T(PropertyModel.class);
    String property = "property";
    String isIndex = "isIndex";
    String indexType = "indexType";

    String getProperty();
    void setProperty(String property);

    boolean getIsIndex();
    void setIsIndex(boolean index);

    CollectionType getIndexType();
    void setIndexType(CollectionType indexType);

    default String getIndex(){
        return this.getProperty().substring(1, this.getProperty().length()-1);
    }

    static PropertyModel indexProperty(String property, CollectionType type) {
        PropertyModel p = PropertyModel.T.create();
        p.setProperty(property);
        p.setIsIndex(true);
        p.setIndexType(type);
        return p;
    }

    static PropertyModel listIndexProperty(String property) {
        PropertyModel p = PropertyModel.T.create();
        p.setProperty(property);
        p.setIsIndex(true);
        p.setIndexType(CollectionType.MAP);
        return p;
    }

    static PropertyModel mapIndexProperty(String property) {
        PropertyModel p = PropertyModel.T.create();
        p.setProperty(property);
        p.setIsIndex(true);
        p.setIndexType(CollectionType.MAP);
        return p;
    }

    static PropertyModel regularProperty(String property) {
        PropertyModel p = PropertyModel.T.create();
        p.setProperty(property);
        p.setIsIndex(false);
        return p;
    }
}
