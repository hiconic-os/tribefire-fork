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
package tribefire.extension.messaging.integration.test.util;

import java.util.Arrays;
import java.util.List;

import com.braintribe.model.deployment.Deployable;
import com.braintribe.model.extensiondeployment.meta.AroundProcessWith;
import com.braintribe.model.extensiondeployment.meta.PreProcessWith;
import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.DeleteMode;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.typecondition.TypeConditions;
import com.braintribe.model.generic.typecondition.basic.TypeKind;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class TestQueryUtil {
	public static GmMetaModel queryMetaModel(PersistenceGmSession cortexSession, String globalId) {
		//@formatter:off
        return cortexSession.query()
                       .abstractQuery(EntityQueryBuilder.from(GmMetaModel.T)
                                              .where()
                                              .property(GmMetaModel.globalId).eq(globalId)
                                              .tc().pattern()
                                              .typeCondition(TypeConditions.isAssignableTo(MetaData.T)).conjunction().property()
                                              .typeCondition(TypeConditions.not(TypeConditions.isKind(TypeKind.scalarType)))
                                              .close()
                                              .close()
                                              .done())
                       .first();
        //@formatter:on
	}

	public static void queryAnDeleteDeployable(PersistenceGmSession cortexSession, String externalId) {
		queryAndDelete(cortexSession, Deployable.T, Deployable.externalId, externalId);
	}

	public static void queryAnDeleteAllProcessWith(PersistenceGmSession cortexSession, String prefix) {
		Arrays.asList(ProcessWith.T, PreProcessWith.T, AroundProcessWith.T)
				.forEach(e -> queryAndDelete(cortexSession, e, GenericEntity.globalId, prefix + "*"));
	}

	public static <T extends GenericEntity> void queryAndDelete(PersistenceGmSession cortexSession, EntityType<T> type, String idField, String like) {
		try {
			//@formatter:off
            List<T> list = cortexSession.query()
                                   .abstractQuery(EntityQueryBuilder.from(type)
                                                          .where()
                                                          .property(idField).like(like+"*")
                                                          .done())
                                   .list();
            //@formatter:on
			list.forEach(v -> cortexSession.deleteEntity(v, DeleteMode.dropReferences));
			cortexSession.commit();
		} catch (Exception e) {
			// ignore
		}
	}

	private TestQueryUtil() {
	}

	public static <T extends GenericEntity> T query(PersistenceGmSession cortexSession, EntityType<GmEntityType> type, String property, String like) {
		//@formatter:off
        return cortexSession.query()
                .abstractQuery(EntityQueryBuilder.from(type)
                                       .where()
                                       .property(property)
                                       .like(like)
                                       .done()).first();
        //@formatter:on
	}
}
