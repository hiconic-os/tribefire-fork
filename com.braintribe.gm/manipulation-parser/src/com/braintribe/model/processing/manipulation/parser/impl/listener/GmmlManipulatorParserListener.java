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
package com.braintribe.model.processing.manipulation.parser.impl.listener;

import static com.braintribe.model.processing.manipulation.parser.impl.collection.GmmlCollectionTools.addToCollection;
import static com.braintribe.model.processing.manipulation.parser.impl.collection.GmmlCollectionTools.convertToCollection;
import static com.braintribe.model.processing.manipulation.parser.impl.collection.GmmlCollectionTools.convertToMap;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;
import static com.braintribe.utils.lcd.CollectionTools2.newSet;
import static com.braintribe.utils.lcd.CommonTools.getValueOrDefault;
import static com.braintribe.utils.lcd.CommonTools.getValueOrSupplyDefault;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.braintribe.common.lcd.UnknownEnumException;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.DeleteMode;
import com.braintribe.model.generic.reflection.CollectionType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EnumType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.LinearCollectionType;
import com.braintribe.model.generic.reflection.ListType;
import com.braintribe.model.generic.reflection.MapType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.SetType;
import com.braintribe.model.processing.manipulation.parser.api.CollectionDeltaManipulator;
import com.braintribe.model.processing.manipulation.parser.api.GmmlConstants;
import com.braintribe.model.processing.manipulation.parser.api.GmmlManipulatorErrorHandler;
import com.braintribe.model.processing.manipulation.parser.api.GmmlManipulatorParserConfiguration;
import com.braintribe.model.processing.manipulation.parser.api.ProblematicEntitiesRegistry;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.AddContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.BlockContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.BlockSequenceContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.ChangeValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.ClearContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.CollectionDeltaValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.CustomInstanceContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.CustomManipulationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.DeleteManipulationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.EnumValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.InstanceAcquireContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.InstanceCreationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.InstanceLookupContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.ListDeltaValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.ManipulationBlockContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.ManipulationBlocksContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.ManipulationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.ManipulationOperationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.MapDeltaValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.PropertyEntryContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.PropertyManipulationContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.PropertyOwnerContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.QualifiedTypeAssignmentContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.QualifiedTypeContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.RemoveContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.SetDeltaValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.SingleDeltaValueContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.StatementContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.ValueAssignmentContext;
import com.braintribe.model.processing.manipulation.parser.impl.autogenerated.GmmlParser.VariableStatementContext;
import com.braintribe.model.processing.manipulation.parser.impl.collection.GmmlCollectionTools;
import com.braintribe.model.processing.manipulation.parser.impl.collection.MapDeltaManipulator;
import com.braintribe.model.processing.manipulation.parser.impl.collection.SetDeltaManipulator;
import com.braintribe.model.processing.manipulation.parser.impl.listener.error.StrictErrorHandler;
import com.braintribe.model.processing.manipulation.parser.impl.manipulator.EmptyProblematicEntitiesRegistry;
import com.braintribe.model.processing.session.api.managed.EntityManager;

public class GmmlManipulatorParserListener extends GmmlValueParserListener implements GmmlConstants {

	private final GmmlManipulatorErrorHandler errorHandler;
	private final ProblematicEntitiesRegistry problematicEntitiesRegistry;
	private final String newGlobalIdPrefix;

	private final Map<String, Object> variables;
	private final Map<String, Object> newVariables = newMap();
	
	private final Set<GenericEntity> problematicEntities = newSet();
	private final Set<GenericEntity> newlyCreatedEntities;
	private final Set<GenericEntity> previouslyCreatedEntities;
	private final EntityManager entityManager;

	private final Map<Object, Integer> problematicGlobalIdCounts = newMap();

	private final Property globalIdProperty = GenericEntity.T.getProperty(GenericEntity.globalId);

	protected GenericEntity lastEntity;
	private Object lastAssignment;

	public GmmlManipulatorParserListener(EntityManager entityManager, GmmlManipulatorParserConfiguration configuration) {
		this.entityManager = entityManager;
		this.errorHandler = resolveErrorHandler(configuration);
		this.problematicEntitiesRegistry = resolveRegistry(configuration);
		this.newGlobalIdPrefix = resolvePrefix(configuration);
		this.variables = configuration.variables();
		this.newlyCreatedEntities = newSet();
		this.previouslyCreatedEntities = configuration.previouslyCreatedEntities();
	}

	private GmmlManipulatorErrorHandler resolveErrorHandler(GmmlManipulatorParserConfiguration configuration) {
		return getValueOrSupplyDefault(configuration.errorHandler(), () -> StrictErrorHandler.INSTANCE);
	}

	private ProblematicEntitiesRegistry resolveRegistry(GmmlManipulatorParserConfiguration configuration) {
		return getValueOrDefault(configuration.problematicEntitiesRegistry(), EmptyProblematicEntitiesRegistry.INSTANCE);
	}

	private String resolvePrefix(GmmlManipulatorParserConfiguration configuration) {
		return "gmml://" + getValueOrDefault(configuration.stageName(), "stage@" + System.identityHashCode(this)) + ".";
	}

	/**
	 * Returns the variables resolved during parsing, as well as all unresolvable variables mapped to themselves, thus providing the information they
	 * are used..
	 * 
	 * NOTE that the change is done on the original map, thus this call has side effects and should only be done once the parsing is over.
	 */
	public Map<String, Object> getAllUsedVariables() {
		return variables;
	}

	public Map<String, Object> getNewVariables() {
		return newVariables;
	}

	public Object getLastAssignment() {
		return lastAssignment;
	}

	public Set<GenericEntity> getProblematicEntities() {
		return problematicEntities;
	}

	// #####################################################
	// ## . . . . . . Types/Variable/Instances . . . . . .##
	// #####################################################

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		// System.out.println("enter " + ctx.getClass().getSimpleName().replace("Context", ""));
		// super.exitEveryRule(ctx);
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		// System.out.println("exit " + ctx.getClass().getSimpleName().replace("Context", ""));
		// super.exitEveryRule(ctx);
	}

	@Override
	public void enterQualifiedTypeAssignment(QualifiedTypeAssignmentContext ctx) {
		// noop
	}

	@Override
	public void exitQualifiedTypeAssignment(QualifiedTypeAssignmentContext ctx) {
		Object type = pop();
		String variableName = pop();

		putVariable(variableName, type);
		push(type);
	}

	@Override
	public void enterQualifiedType(QualifiedTypeContext ctx) {
		// noop
	}

	@Override
	public void exitQualifiedType(QualifiedTypeContext ctx) {
		String typeSignature = pop();

		Object type = GMF.getTypeReflection().findType(typeSignature);

		if (type == null) {
			errorHandler.typeNotFound(typeSignature);
			type = missing;
		}

		push(type);
	}

	@Override
	public void enterValueAssignment(ValueAssignmentContext ctx) {
		// noop
	}

	@Override
	public void exitValueAssignment(ValueAssignmentContext ctx) {
		Object value = pop();
		String variableName = pop();

		putVariableAndUpdateLastAssignment(variableName, value);
		push(value);
	}

	@Override
	public void enterInstanceCreation(InstanceCreationContext ctx) {
		// noop
	}

	@Override
	public void exitInstanceCreation(InstanceCreationContext ctx) {
		Object maybeType = pop();
		String variableName = pop();

		GenericEntity entity = createNewInstance(maybeType, variableName);

		putVariableAndUpdateLastAssignment(variableName, entity);
		push(entity);

		notifyEntity(entity);
	}

	@Override
	public void enterInstanceLookup(InstanceLookupContext ctx) {
		// noop
	}

	@Override
	public void exitInstanceLookup(InstanceLookupContext ctx) {
		String globalId = pop();
		/* Object maybeType = */pop();
		String variableName = pop();

		if (problematicEntitiesRegistry.isProblematic(globalId))
			errorHandler.problematicEntityReferenced(globalId);

		try {
			GenericEntity entity = entityManager.findEntityByGlobalId(globalId);
			if (entity == null) {
				errorHandler.entityNotFound(globalId);
				entity = missing;
			}

			putVariableAndUpdateLastAssignment(variableName, entity);
			push(entity);

			notifyEntity(entity);

		} catch (IllegalStateException e) {
			throw e;

		} catch (Exception e) {
			throw new IllegalStateException("Error while resolving entity with globalId: " + globalId, e);
		}
	}

	@Override
	public void enterInstanceAcquire(InstanceAcquireContext ctx) {
		// noop
	}

	@Override
	public void exitInstanceAcquire(InstanceAcquireContext ctx) {
		String globalId = pop();
		Object maybeType = pop();
		String variableName = pop();

		if (problematicEntitiesRegistry.isProblematic(globalId))
			errorHandler.problematicEntityReferenced(globalId);

		GenericEntity entity = entityManager.findEntityByGlobalId(globalId);
		if (entity == null)
			entity = createNewInstance(maybeType, variableName);

		putVariableAndUpdateLastAssignment(variableName, entity);
		push(entity);

		notifyEntity(entity);
	}

	private GenericEntity createNewInstance(Object maybeType, String variableName) {
		// if type==missing --> lenient
		if (maybeType != missing) {
			GenericModelType type = (GenericModelType) maybeType;

			if (type.isEntity()) {
				GenericEntity entity = entityManager.createRaw((EntityType<?>) type);
				newlyCreatedEntities.add(entity);

				return entity;

			} else {
				errorHandler.variableNotEntityType(variableName);
			}
		}

		return missing;
	}

	protected void putVariableAndUpdateLastAssignment(String variableName, Object variableValue) {
		putVariable(variableName, variableValue);

		lastAssignment = variableValue;
	}

	private void putVariable(String variableName, Object variableValue) {
		if (variableValue == missing)
			variableValue = variableName;
		
		variables.put(variableName, variableValue);
		newVariables.put(variableName, variableValue);
	}

	private void notifyEntity(GenericEntity entity) {
		if (!insideVariableAssignmentValue)
			lastEntity = entity;
	}

	@Override
	public void enterVariableStatement(VariableStatementContext ctx) {
		// noop
	}

	@Override
	public void exitVariableStatement(VariableStatementContext ctx) {
		pop();
	}

	@Override
	protected <T> T requireVariable(String name) {
		T value = (T) variables.getOrDefault(name, noValue);

		if (name.equals(value))
			return (T) missing;

		if (value == noValue)
			throw new IllegalStateException("Variable not defined: " + name);

		return value;
	}

	// #####################################################
	// #. . . . . . . . . Manipulations . . . . . . . . . ##
	// #####################################################

	@Override
	public void enterManipulationBlock(ManipulationBlockContext ctx) {
		// noop
	}

	@Override
	public void exitManipulationBlock(ManipulationBlockContext ctx) {
		// noop
	}

	@Override
	public void enterManipulation(ManipulationContext ctx) {
		// noop
	}

	@Override
	public void exitManipulation(ManipulationContext ctx) {
		// noop
	}

	@Override
	public void enterDeleteManipulation(DeleteManipulationContext ctx) {
		// noop
	}

	/**
	 * Performs a delete operation on the {@link EntityManager}. If this entity was created within this file, we can use
	 * {@link DeleteMode#ignoreReferences} mode, otherwise we use {@link DeleteMode#dropReferences}. For this reason, when parsing a snippet which was
	 * just attached to the already parsed file, make sure you provide the created entities in given
	 * {@link GmmlManipulatorParserConfiguration#previouslyCreatedEntities() parser configuration}.
	 */
	@Override
	public void exitDeleteManipulation(DeleteManipulationContext ctx) {
		GenericEntity entity = pop();
		if (entity == missing)
			return;

		DeleteMode mode = canIgnoreReferencesOf(entity) ? DeleteMode.ignoreReferences : DeleteMode.dropReferences;
		entityManager.deleteEntity(entity, mode);
	}

	private boolean canIgnoreReferencesOf(GenericEntity entity) {
		return newlyCreatedEntities.remove(entity) || previouslyCreatedEntities.contains(entity);
	}

	@Override
	public void enterCustomManipulation(CustomManipulationContext ctx) {
		// noop
	}

	@Override
	public void exitCustomManipulation(CustomManipulationContext ctx) {
		pop(); // FullyQualifiedIdentifier
	}

	@Override
	public void enterPropertyEntry(PropertyEntryContext ctx) {
		// noop
	}

	@Override
	public void enterCustomInstance(CustomInstanceContext ctx) {
		// noop
	}

	@Override
	public void exitCustomInstance(CustomInstanceContext ctx) {
		// noop
	}

	@Override
	public void enterPropertyManipulation(PropertyManipulationContext ctx) {
		// noop
	}

	@Override
	public void exitPropertyEntry(PropertyEntryContext ctx) {
		// noop
	}

	@Override
	public void exitPropertyManipulation(PropertyManipulationContext ctx) {
		BiConsumer<GenericEntity, Property> consumer = pop();
		String propertyName = ctx.StandardIdentifier().getText();

		withProperty(lastEntity, propertyName, consumer);
	}

	@Override
	public void enterPropertyOwner(PropertyOwnerContext ctx) {
		// noop
	}

	@Override
	public void exitPropertyOwner(PropertyOwnerContext ctx) {
		lastEntity = pop();
	}

	@Override
	public void enterAdd(AddContext ctx) {
		// noop
	}

	@Override
	public void exitAdd(AddContext ctx) {
		CollectionDeltaManipulator deltaManipulator = pop();

		pushPropertyManipulationApplier((entity, property) -> {
			withCollectionProperty(entity, property, (collection, collectionType) -> {
				switch (collectionType.getCollectionKind()) {
					case list:
						deltaManipulator.addToList((List<Object>) collection, (ListType) collectionType);
						return;
					case set:
						deltaManipulator.addToSet((Set<Object>) collection, (SetType) collectionType);
						return;
					case map:
						deltaManipulator.addToMap((Map<Object, Object>) collection, (MapType) collectionType);
						return;
					default:
						throw new UnknownEnumException(collectionType.getCollectionKind());
				}
			});
		});
	}

	@Override
	public void enterRemove(RemoveContext ctx) {
		// noop
	}

	@Override
	public void exitRemove(RemoveContext ctx) {
		CollectionDeltaManipulator deltaManipulator = pop();

		pushPropertyManipulationApplier((entity, property) -> {
			withCollectionProperty(entity, property, (value, collectionType) -> {
				switch (collectionType.getCollectionKind()) {
					case list:
						deltaManipulator.removeFromList((List<Object>) value, (ListType) collectionType);
						return;
					case set:
						deltaManipulator.removeFromSet((Set<Object>) value, (SetType) collectionType);
						return;
					case map:
						deltaManipulator.removeFromMap((Map<Object, Object>) value, (MapType) collectionType);
						return;
					default:
						throw new UnknownEnumException(collectionType.getCollectionKind());
				}
			});
		});
	}

	private void withCollectionProperty(GenericEntity entity, Property property, BiConsumer<Object, CollectionType> consumer) {
		Object value = property.get(entity);

		GenericModelType propertyType = property.getType();
		if (propertyType.isBase())
			propertyType = GMF.getTypeReflection().getType(value);

		if (!propertyType.isCollection()) {
			errorHandler.propertyNotCollection(entity, property);
			problematicEntities.add(entity);
		}

		consumer.accept(value, (CollectionType) propertyType);
	}

	@Override
	public void enterStatement(StatementContext ctx) {
		// noop
	}

	@Override
	public void exitStatement(StatementContext ctx) {
		// noop
	}

	@Override
	public void enterManipulationBlocks(ManipulationBlocksContext ctx) {
		// noop
	}

	@Override
	public void exitManipulationBlocks(ManipulationBlocksContext ctx) {
		// noop
	}

	@Override
	public void enterManipulationOperation(ManipulationOperationContext ctx) {
		// noop
	}

	@Override
	public void exitManipulationOperation(ManipulationOperationContext ctx) {
		// noop
	}

	@Override
	public void enterClear(ClearContext ctx) {
		// noop
	}

	@Override
	public void exitClear(ClearContext ctx) {
		pushPropertyManipulationApplier((entity, property) -> {
			Object value = property.get(entity);

			if (value instanceof Collection)
				((Collection<?>) value).clear();
			else if (value instanceof Map)
				((Map<?, ?>) value).clear();
			else
				handleCannotClearError(entity, property, value);
		});
	}

	private void handleCannotClearError(GenericEntity entity, Property property, Object value) {
		errorHandler.propertyValueNotCollectionCannotClear(entity, property, value);
		problematicEntities.add(entity);
	}

	@Override
	public void enterChangeValue(ChangeValueContext ctx) {
		// noop
	}

	@Override
	public void exitChangeValue(ChangeValueContext ctx) {
		Object value = pop();

		pushPropertyManipulationApplier((entity, property) -> {
			if (value == missing)
				return;
			
			try {
				Object typeSafeValue = ensureTypeSafety(property.getType(), value);

				setEntityProperty(entity, property, typeSafeValue);

			} catch (RuntimeException e) {
				errorHandler.propertySettingFailed(entity, property, value, e);
				problematicEntities.add(entity);
			}
		});
	}

	private void setEntityProperty(GenericEntity entity, Property property, Object actualValue) {
		Object originalValue = property.getDirectUnsafe(entity);

		try {
			property.set(entity, actualValue);

		} catch (RuntimeException e) {
			property.setDirectUnsafe(entity, originalValue);

			if (property.isGlobalId())
				onGlobalIdError(entity, actualValue, originalValue, e);
			else
				throw e;
		}
	}

	private void onGlobalIdError(GenericEntity entity, Object globalId, Object originalGlobalId, RuntimeException e) {
		errorHandler.globalIdSettingFailed(entity, globalId, e);
		problematicEntities.add(entity);

		String newGlobalId = tryReallyHardToSetNewGlobalId(entity, globalId, originalGlobalId);

		errorHandler.globalIdAdjusted(entity, globalId, newGlobalId, e);
	}

	private String tryReallyHardToSetNewGlobalId(GenericEntity entity, Object globalId, Object originalGlobalId) {
		while (true) {
			/* Q: Alright there mate, isn't this eternal cycle a little dangerous? */
			/* A: Not really mate. This most likely handles the situation of duplicate globalId being used. But in any case, the new globalId is
			 * created reflecting also the stage name, and we keep a counter to always create a higher one, so we should be safe. In the most unlikely
			 * event of parsing multiple GMML files with same stage name, which also create entities with the same globalId, we could have a conflict,
			 * and thus multiple attempts would be needed. Not many though, I hope. */

			String newGlobalId = getNewGlobalId(globalId);
			try {
				entity.setGlobalId(newGlobalId);
				return newGlobalId;
			} catch (RuntimeException ignored) {
				globalIdProperty.setDirectUnsafe(entity, originalGlobalId);
			}
		}
	}

	private String getNewGlobalId(Object globalId) {
		if (globalId == null)
			return newGlobalIdPrefix + "null#" + getAndIncreaseProblemCounter(globalId);

		if (!(globalId instanceof String))
			return newGlobalIdPrefix + globalId.getClass().getName() + "#" + getAndIncreaseProblemCounter(globalId) + ".[" + globalId + "]";

		return newGlobalIdPrefix + "copy#" + getAndIncreaseProblemCounter(globalId) + "." + globalId;
	}

	private Integer getAndIncreaseProblemCounter(Object globalId) {
		return problematicGlobalIdCounts.compute(globalId, (k, v) -> v == null ? 1 : v + 1);
	}

	private Object ensureTypeSafety(GenericModelType type, Object unsafeValue) {
		// In non-collection case we rely on the type check happening inside the setter
		if (unsafeValue == null || !type.isCollection())
			return unsafeValue;

		switch (type.getTypeCode()) {
			case listType:
			case setType: {
				LinearCollectionType linearCollectionType = (LinearCollectionType) type;
				Collection<Object> collection = convertToCollection(unsafeValue);
				Collection<Object> result = linearCollectionType.createPlain();

				collection.forEach(element -> addToCollection(result, linearCollectionType, element, errorHandler));
				return result;
			}
			case mapType: {
				MapType mapType = (MapType) type;
				Map<Object, Object> map = convertToMap(unsafeValue);
				Map<Object, Object> result = mapType.createPlain();

				map.forEach((key, value) -> GmmlCollectionTools.putToMap(result, mapType, key, value, errorHandler));
				return result;
			}

			default:
				throw new IllegalStateException("This is unreachable.");
		}
	}

	private void pushPropertyManipulationApplier(BiConsumer<GenericEntity, Property> applier) {
		push(applier);
	}

	@Override
	public void visitErrorNode(ErrorNode arg0) {
		// noop
	}

	@Override
	public void visitTerminal(TerminalNode arg0) {
		// noop
	}

	@Override
	public void enterEnumValue(EnumValueContext ctx) {
		// noop
	}

	@Override
	public void exitEnumValue(EnumValueContext ctx) {
		Object type = pop();
		String enumConstantName = ctx.StandardIdentifier().getText();
		Object enumValue;

		if (type == missing) {
			enumValue = missing;

		} else {
			GenericModelType castedType = (GenericModelType) type;

			if (!(type instanceof GenericModelType)) {
				errorHandler.typeNotGenericModelType(type);
				enumValue = missing;

			} else {
				EnumType enumType = (EnumType) castedType;
				enumValue = enumType.findEnumValue(enumConstantName);

				if (enumValue == null) {
					errorHandler.enumConstantNotFound(enumType, enumConstantName);
					enumValue = missing;
				}
			}
		}

		push(enumValue);
	}

	@Override
	public void enterBlock(BlockContext ctx) {
		// noop
	}

	@Override
	public void exitBlock(BlockContext ctx) {
		// noop
	}

	@Override
	public void enterBlockSequence(BlockSequenceContext ctx) {
		// noop
	}

	@Override
	public void exitBlockSequence(BlockSequenceContext ctx) {
		// noop
	}

	// #####################################################
	// ## . . . . . . Collection Delta Values . . . . . . ##
	// #####################################################

	@Override
	public void enterCollectionDeltaValue(CollectionDeltaValueContext ctx) {
		// noop
	}

	@Override
	public void exitCollectionDeltaValue(CollectionDeltaValueContext ctx) {
		// noop
	}

	@Override
	public void enterListDeltaValue(ListDeltaValueContext ctx) {
		// noop
	}

	@Override
	public void exitListDeltaValue(ListDeltaValueContext ctx) {
		throw new UnsupportedOperationException("List delta value is not supported yet!");
	}

	@Override
	public void enterSetDeltaValue(SetDeltaValueContext ctx) {
		// noop
	}

	@Override
	public void exitSetDeltaValue(SetDeltaValueContext ctx) {
		Set<Object> set = pop();
		push(new SetDeltaManipulator(set, errorHandler));
	}

	@Override
	public void enterSingleDeltaValue(SingleDeltaValueContext ctx) {
		// noop
	}

	@Override
	public void exitSingleDeltaValue(SingleDeltaValueContext ctx) {
		Object value = pop();
		push(new SetDeltaManipulator(Collections.singleton(value), errorHandler));
	}

	@Override
	public void enterMapDeltaValue(MapDeltaValueContext ctx) {
		// noop
	}

	@Override
	public void exitMapDeltaValue(MapDeltaValueContext ctx) {
		Map<Object, Object> map = pop();
		push(new MapDeltaManipulator(map, errorHandler));
	}

	// #####################################################
	// ## . . . . . Handling Collection Values . . . . . .##
	// #####################################################

	@Override
	protected void addToList(List<Object> list, Object e) {
		list.add(GmmlConstants.getValueOrNullIfMissing(e));
	}

	@Override
	protected void addToSet(Set<Object> set, Object e) {
		if (e != missing)
			set.add(e);
	}

	@Override
	protected void putToMap(Map<Object, Object> map, Object key, Object value) {
		if (key != missing)
			map.put(key, value);
	}

	// #####################################################
	// ## . . . . Property Manipulation Helpers . . . . . ##
	// #####################################################

	private void withProperty(Object maybeEntity, String propertyName, BiConsumer<GenericEntity, Property> consumer) {
		if (!(maybeEntity instanceof GenericEntity)) {
			errorHandler.cannotResolvePropertyOfNonEntity(maybeEntity, propertyName);
			return;
		}

		GenericEntity entity = (GenericEntity) maybeEntity;

		Property property = entity.entityType().findProperty(propertyName);
		if (property == null) {
			errorHandler.propertyNotFound(entity, propertyName);
			problematicEntities.add(entity);
			return;
		}

		consumer.accept(entity, property);
	}

}
