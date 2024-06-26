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
package tribefire.extension.js.core.impl;

import java.util.Optional;

import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.Property;
import com.braintribe.model.artifact.Solution;

public interface JsDependencyFiltering {
	static boolean filter(Solution depender, Dependency dependency) {
		String scope = Optional.ofNullable(dependency.getScope()).orElse("compile");
		
		switch (scope) {
		case "provided":
		case "test":
			return false;
		default:
			break;
		}
		
		if (dependency.getOptional())
			return false;
		
		String type = Optional.ofNullable(dependency.getType()).orElse("jar");
		String classifier = dependency.getClassifier();
		
		if (classifier != null)
			return false;
		
		switch (type) {
		case "pom":
		case "jar":
			break;
		default:
			return false;
		}
		
		// js tagged if depender solution is jsinterop marked
		boolean jsinterop = false;
		
		for (Property property: depender.getProperties()) {
			if (property.getName().equals("jsinterop") && Boolean.TRUE.toString().equals(property.getValue())) {
				jsinterop = true;
				break;
			}
		}
		
		return !jsinterop || dependency.getTags().contains("js");
	}
}
