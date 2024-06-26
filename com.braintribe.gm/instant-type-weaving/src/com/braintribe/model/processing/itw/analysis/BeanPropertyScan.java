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
package com.braintribe.model.processing.itw.analysis;

import static com.braintribe.model.processing.itw.synthesis.gm.asm.DefaultMethodsSupport.isEvalMethod;
import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;
import static com.braintribe.utils.lcd.CollectionTools2.newSet;
import static java.util.Collections.emptySet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.base.EnumBase;
import com.braintribe.model.processing.itw.analysis.JavaTypeAnalysis.JtaClasses;
import com.braintribe.model.weaving.info.ProtoGmPropertyInfo;
import com.braintribe.utils.ReflectionTools;

public class BeanPropertyScan {

	private static final Logger log = Logger.getLogger(BeanPropertyScan.class);
	private static final Set<Class<? extends Annotation>> ALLOWED_BT_ANNOTATIONS = emptySet();

	private final Class<? extends GenericEntity> entityClass;
	private final JtaClasses jtaClasses;
	private final boolean requireEnumBase;

	private final Map<String, ScannedProperty> scannedProperties = newMap();
	private final Set<String> transientProperties = newSet();
	public final List<ScannedProperty> orderedProperties = newList();
	private Type evalType;

	public final Map<String, ProtoGmPropertyInfo> gmPropertyInfos = newMap();

	public BeanPropertyScan(Class<? extends GenericEntity> entityClass, JtaClasses jtaClasses, boolean requireEnumBase) {
		this.entityClass = entityClass;
		this.jtaClasses = jtaClasses;
		this.requireEnumBase = requireEnumBase;

		scan();
		validate();
	}

	public Collection<ScannedProperty> getScannedProperties() {
		return orderedProperties;
	}

	public Type getEvalType() {
		return evalType;
	}

	private void scan() throws JavaTypeAnalysisException {
		String propertyName = null;

		for (Method method : entityClass.getDeclaredMethods()) {
			/* This was a problem for GmCustomTypeInfo - has a proper method <code>GmMetaModel getDeclaringModel()</code>, but that overrides
			 * <code>ProtoGmMetaModel getDeclaringModel()</code>. For some reason java compiler puts a synthetic default method in GmCustomTypeInfo to
			 * implement this covariant override. */
			if (method.isSynthetic() || ReflectionTools.isStatic(method))
				continue;

			Boolean isGetter = isGetter(method);

			if (isGetter != null)
				propertyName = getPropertyName(method);

			if (Boolean.TRUE == isGetter) {
				aquireProperty(propertyName).getter = method;
				noteIfTransient(method, propertyName);

			} else if (Boolean.FALSE == isGetter) {
				aquireProperty(propertyName).setter = method;
				validateSetterAnnotations(method);

			} else if (isEvalMethod(method, jtaClasses.evaluatorClass)) {
				evalType = extractEvalType(method);
			}
		}

		scannedProperties.keySet().removeAll(transientProperties);

		orderedProperties.addAll(scannedProperties.values());
		orderedProperties.sort(Comparator.comparing(ScannedProperty::getPropertyName));
	}

	/** Because we ignore annotations for setters and only support getters. */
	private void validateSetterAnnotations(Method setter) {
		Annotation[] annotations = setter.getAnnotations();
		if (annotations.length == 0)
			return;

		if (containsNonAllowedAnnotation(annotations))
			log.warn("Ignoring annotations for property setter: " + setter + ", only getter annotations are supported. Found annotations: "
					+ Arrays.toString(annotations));
	}

	private boolean containsNonAllowedAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (!annotation.getClass().getName().startsWith("com.braintribe"))
				continue;

			if (!ALLOWED_BT_ANNOTATIONS.contains(annotation.annotationType()))
				return true;
		}

		return false;
	}

	private void noteIfTransient(Method method, String propertyName) {
		if (isTransient(method))
			transientProperties.add(propertyName);
	}

	private Type extractEvalType(Method method) throws JavaTypeAnalysisException {
		Type returnType = method.getGenericReturnType();
		if (method.getReturnType() != jtaClasses.evalContextClass || !(returnType instanceof ParameterizedType))
			throwInvalidMethodException(method);

		ParameterizedType pt = (ParameterizedType) returnType;
		Type type = pt.getActualTypeArguments()[0];

		if (type instanceof WildcardType) {
			WildcardType wt = (WildcardType) type;
			Type[] upperBounds = wt.getUpperBounds();

			if (upperBounds.length != 1)
				throwInvalidMethodException(method);

			type = upperBounds[0];
		}

		if (type instanceof Class)
			return type;

		if (!(type instanceof ParameterizedType))
			throwInvalidMethodException(method);

		pt = (ParameterizedType) type;
		Type rawType = pt.getRawType();
		if (rawType != List.class && rawType != Set.class && rawType != Map.class)
			throwInvalidMethodException(method);

		return type;
	}

	private void throwInvalidMethodException(Method method) throws JavaTypeAnalysisException {
		throw new JavaTypeAnalysisException("Invalid return type of 'eval' method: " + method);
	}

	protected static boolean hasCorrectModifiers(Method method) {
		return method.getModifiers() == (Modifier.PUBLIC | Modifier.ABSTRACT);
	}

	private ScannedProperty aquireProperty(String propertyName) {
		ScannedProperty scannedProperty = scannedProperties.get(propertyName);
		if (scannedProperty == null) {
			scannedProperty = new ScannedProperty(entityClass, propertyName, requireEnumBase);
			scannedProperties.put(propertyName, scannedProperty);
		}

		return scannedProperty;
	}

	/**
	 * Returns {@code Boolean.TRUE} if it is a getter, {@code Boolean.FALSE} if it is a setter and {@code null} otherwise.
	 */
	public static Boolean isGetter(Method method) {
		String methodName = method.getName();

		if (methodName.length() <= 3)
			return null;

		String accessKindCandidate = methodName.substring(0, 3);

		boolean isGetter = "get".equals(accessKindCandidate);
		boolean isSetter = "set".equals(accessKindCandidate);
		boolean isAccessor = isGetter || isSetter;

		if (!isAccessor)
			return null;

		int actualParamCount = method.getParameterTypes().length;
		int expectedParamCount = isGetter ? 0 : 1;

		if (!hasCorrectModifiers(method) || actualParamCount != expectedParamCount)
			throw new IllegalArgumentException(
					"Invalid " + accessKindCandidate + "ter method:\n\t" + method + "\nof classs:\n\t" + method.getDeclaringClass().getName());

		return isGetter ? Boolean.TRUE : Boolean.FALSE;
	}

	public static String getPropertyName(Method getterOrSetter) {
		String methodName = getterOrSetter.getName();
		String commonPart = methodName.substring(4);
		return Character.toLowerCase(methodName.charAt(3)) + commonPart;
	}

	private boolean isTransient(Method method) {
		return method.getDeclaredAnnotation(jtaClasses.transientAnnotationClass) != null;
	}

	private void validate() {
		if (requireEnumBase && evalType != null)
			validatePossibleEnum(evalType, jtaClasses, entityClass, "eval()");
	}

	/* package */ static void validatePossibleEnum(Type type, JtaClasses jtaClasses, Class<?> entityClass, String evalOrPropertyName) {
		if (isInvalidEnum(type, jtaClasses))
			throw new IllegalArgumentException("Illegal enum [" + type.getTypeName() + "] for EntityType [" + entityClass.getName() + "."
					+ evalOrPropertyName + "]. Valid enum type must implement " + EnumBase.class.getName() + ".");
	}

	private static boolean isInvalidEnum(Type type, JtaClasses jtaClasses) {
		if (!(type instanceof Class))
			return false;

		Class<?> c = (Class<?>) type;
		if (!c.isEnum())
			return false;

		return !Arrays.asList(c.getInterfaces()).contains(jtaClasses.enumBaseClass);
	}

}
