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
package tribefire.extension.messaging.test.comparison.model;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ComplexWithCollectionOfSimple extends GenericEntity {
    EntityType<ComplexWithCollectionOfSimple> T = EntityTypes.T(ComplexWithCollectionOfSimple.class);

    String name = "name";
    String listSimple = "listSimple";
    String mapSimple = "mapSimple";
    String setSimple = "setSimple";

    List<Simple> getListSimple();
    void setListSimple(List<Simple> listSimple);

    Map<String,Simple> getMapSimple();
    void setMapSimple(Map<String,Simple> mapSimple);

    Set<Simple> getSetSimple();
    void setSetSimple(Set<Simple> setSimple);
}
