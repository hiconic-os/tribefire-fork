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
package com.braintribe.gwt.utils.genericmodel;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.access.IncrementalAccess;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.impl.persistence.BasicPersistenceGmSession;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.testing.model.test.technical.features.CollectionEntity;
import com.braintribe.testing.model.test.technical.features.ComplexEntity;
import com.braintribe.testing.model.test.technical.features.SimpleEntity;
import com.braintribe.testing.model.test.technical.features.SimpleTypesEntity;
import com.braintribe.testing.model.test.testtools.TestModelTestTools;

public class CompoundEntitiesFinderTest {

	private BasicPersistenceGmSession session = null;

	private final int expectedSizeForCompoundEntitiesFinderQuery1 = 3;
	private final int expectedSizeForCompoundEntitiesFinderQuery2 = 6;
	private final int expectedSizeForCompoundEntitiesFinderQuery3 = 5;

	@Before
	public void setUp() throws GmSessionException {

		final IncrementalAccess access = TestModelTestTools.newSmoodAccessMemoryOnly("test", TestModelTestTools.createTestModelMetaModel());
		this.session = new BasicPersistenceGmSession();
		this.session.setIncrementalAccess(access);

		final SimpleEntity simpleEntity1 = this.session.create(SimpleEntity.T);
		simpleEntity1.setBooleanProperty(false);
		simpleEntity1.setStringProperty("StringProperty1");

		final SimpleEntity simpleEntity2 = this.session.create(SimpleEntity.T);
		simpleEntity2.setBooleanProperty(true);
		simpleEntity2.setStringProperty("StringProperty2");

		final SimpleEntity simpleEntity3 = this.session.create(SimpleEntity.T);
		simpleEntity3.setBooleanProperty(false);
		simpleEntity3.setStringProperty("StringProperty3");

		final ComplexEntity complexEntity = this.session.create(ComplexEntity.T);
		complexEntity.setSimpleEntityProperty(simpleEntity1);
		complexEntity.setBooleanProperty(true);
		complexEntity.setStringProperty("StringPropertC1");

		final SimpleTypesEntity simpleTypesEntity = this.session.create(SimpleTypesEntity.T);
		simpleTypesEntity.setBooleanProperty(true);
		simpleTypesEntity.setDateProperty(new Date());
		simpleTypesEntity.setStringProperty("StringPropertSTE1");

		final SimpleTypesEntity simpleTypesEntity2 = this.session.create(SimpleTypesEntity.T);
		simpleTypesEntity2.setBooleanProperty(true);
		simpleTypesEntity2.setDateProperty(new Date());
		simpleTypesEntity2.setStringProperty("StringPropertSTE2");

		final CollectionEntity collectionEntity = this.session.create(CollectionEntity.T);
		final List<SimpleEntity> simpleEntityList = new ArrayList<SimpleEntity>();
		simpleEntityList.add(simpleEntity1);
		simpleEntityList.add(simpleEntity2);
		collectionEntity.setSimpleEntityList(simpleEntityList);
		this.session.commit();
	}

	@Test
	public void testCompoundEntitiesFinderQuery1() {

		final EntityQueryBasedEntitiesFinder queryBasedEntitiesFinder1 = new EntityQueryBasedEntitiesFinder();
		//@formatter:off
		final EntityQuery entityQuery1 = EntityQueryBuilder.from(SimpleEntity.class)
					.where().property("booleanProperty").eq(false)
				.done();
		//@formatter:on		
		queryBasedEntitiesFinder1.setEntityQuery(entityQuery1);

		final EntityQueryBasedEntitiesFinder queryBasedEntitiesFinder2 = new EntityQueryBasedEntitiesFinder();
		//@formatter:off
		final EntityQuery entityQuery2 = EntityQueryBuilder.from(SimpleEntity.class)
					.where().property("booleanProperty").eq(true)
				.done();
		//@formatter:on
		queryBasedEntitiesFinder2.setEntityQuery(entityQuery2);

		final CompoundEntitiesFinder compoundEntitiesFinder = new CompoundEntitiesFinder();
		final List<EntitiesFinder> entitiesFinders = new ArrayList<EntitiesFinder>();
		entitiesFinders.add(queryBasedEntitiesFinder1);
		entitiesFinders.add(queryBasedEntitiesFinder2);
		compoundEntitiesFinder.setDelegates(entitiesFinders);

		final Set<GenericEntity> foundEntities = compoundEntitiesFinder.findEntities(this.session);
		assertThat(foundEntities.size()).as("The size of the founded entities")
				.isEqualTo(this.expectedSizeForCompoundEntitiesFinderQuery1);
	}

	@Test
	public void testCompoundEntitiesFinderQuery2() {

		final EntityQueryBasedEntitiesFinder queryBasedEntitiesFinder1 = new EntityQueryBasedEntitiesFinder();
		//@formatter:off
		final EntityQuery entityQuery1 = EntityQueryBuilder.from(SimpleEntity.class)
					.where().property("booleanProperty").eq(false)
				.done();
		//@formatter:on
		queryBasedEntitiesFinder1.setEntityQuery(entityQuery1);

		final EntityTypeBasedEntitiesFinder typeBasedEntityFinder1 = new EntityTypeBasedEntitiesFinder();
		final HashSet<Class<? extends GenericEntity>> entityTypes = new HashSet<Class<? extends GenericEntity>>();
		entityTypes.add(SimpleEntity.class);
		entityTypes.add(ComplexEntity.class);
		typeBasedEntityFinder1.setEntityTypes(entityTypes);

		final EntityTypeBasedEntitiesFinder typeBasedEntityFinder2 = new EntityTypeBasedEntitiesFinder();
		final HashSet<Class<? extends GenericEntity>> entityTypes2 = new HashSet<Class<? extends GenericEntity>>();
		entityTypes2.add(SimpleEntity.class);
		entityTypes2.add(SimpleTypesEntity.class);
		typeBasedEntityFinder2.setEntityTypes(entityTypes2);

		final CompoundEntitiesFinder compoundEntitiesFinder = new CompoundEntitiesFinder();
		final List<EntitiesFinder> entitiesFinders = new ArrayList<EntitiesFinder>();
		entitiesFinders.add(queryBasedEntitiesFinder1);
		entitiesFinders.add(typeBasedEntityFinder1);
		entitiesFinders.add(typeBasedEntityFinder2);
		compoundEntitiesFinder.setDelegates(entitiesFinders);

		final Set<GenericEntity> foundEntities = compoundEntitiesFinder.findEntities(this.session);
		assertThat(foundEntities.size()).as("The size of the founded entities")
				.isEqualTo(this.expectedSizeForCompoundEntitiesFinderQuery2);
	}

	@Test
	public void testCompoundEntitiesFinderQuery3() {

		final EntityQueryBasedEntitiesFinder queryBasedEntitiesFinder1 = new EntityQueryBasedEntitiesFinder();
		//@formatter:off
		final EntityQuery entityQuery1 = EntityQueryBuilder.from(SimpleEntity.class)
					.where().property("booleanProperty").eq(false)
				.done();
		//@formatter:on
		queryBasedEntitiesFinder1.setEntityQuery(entityQuery1);

		final EntityTypeBasedEntitiesFinder typeBasedEntityFinder1 = new EntityTypeBasedEntitiesFinder();
		final HashSet<Class<? extends GenericEntity>> entityTypes = new HashSet<Class<? extends GenericEntity>>();
		entityTypes.add(SimpleEntity.class);
		entityTypes.add(ComplexEntity.class);
		typeBasedEntityFinder1.setEntityTypes(entityTypes);

		final CompoundEntitiesFinder compoundEntitiesFinder1 = new CompoundEntitiesFinder();
		final List<EntitiesFinder> entitiesFinders = new ArrayList<EntitiesFinder>();
		entitiesFinders.add(queryBasedEntitiesFinder1);
		entitiesFinders.add(typeBasedEntityFinder1);
		compoundEntitiesFinder1.setDelegates(entitiesFinders);

		final EntityTypeBasedEntitiesFinder typeBasedEntityFinder2 = new EntityTypeBasedEntitiesFinder();
		final HashSet<Class<? extends GenericEntity>> entityTypes2 = new HashSet<Class<? extends GenericEntity>>();
		entityTypes2.add(SimpleEntity.class);
		entityTypes2.add(CollectionEntity.class);
		typeBasedEntityFinder2.setEntityTypes(entityTypes2);

		final CompoundEntitiesFinder compoundEntitiesFinder2 = new CompoundEntitiesFinder();
		final List<EntitiesFinder> entitiesFinders2 = new ArrayList<EntitiesFinder>();
		entitiesFinders2.add(typeBasedEntityFinder2);
		entitiesFinders2.add(compoundEntitiesFinder1);
		compoundEntitiesFinder2.setDelegates(entitiesFinders2);

		final Set<GenericEntity> foundEntities = compoundEntitiesFinder2.findEntities(this.session);
		assertThat(foundEntities.size()).as("The size of the founded entities")
				.isEqualTo(this.expectedSizeForCompoundEntitiesFinderQuery3);
	}
}
