// ============================================================================
// BRAINTRIBE TECHNOLOGY GMBH - www.braintribe.com
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2018 - All Rights Reserved
// It is strictly forbidden to copy, modify, distribute or use this code without written permission
// To this file the Braintribe License Agreement applies.
// ============================================================================

package com.braintribe.model.build;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.braintribe.model.build.AbstractCreateModelDeclaration.AbstractCommandLineParameters;

public class CreateModelDeclarationFromGradle {

	public static void main(String[] args) {

		Set<String> mandatoryProperties = Stream
				.of(AbstractCommandLineParameters.ARTIFACT, AbstractCommandLineParameters.DEPENDENCIES, AbstractCommandLineParameters.CLASSES_FOLDER)
				.collect(Collectors.toCollection(HashSet::new));

		Map<String, String> parameters = AbstractCommandLineParameters.parseParameterValues(Arrays.asList(args), mandatoryProperties);

		ModelDeclrationContext context = new ModelDeclrationContext();
		context.classesFolder = parameters.get(AbstractCommandLineParameters.CLASSES_FOLDER);
		context.artifact = parameters.get(AbstractCommandLineParameters.ARTIFACT);
		context.modelRevision = parameters.get(AbstractCommandLineParameters.MODEL_REVISION);
		context.dependencies = parameters.get(AbstractCommandLineParameters.DEPENDENCIES);

		CreateModelDeclaration.createModelDeclaration(context);
	}
}
