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
package com.braintribe.devrock.zed.registry;

import java.util.HashMap;
import java.util.Map;

import com.braintribe.devrock.zed.api.context.CommonZedCoreContext;
import com.braintribe.devrock.zed.api.core.ArtifactRegistry;
import com.braintribe.devrock.zed.commons.Commons;
import com.braintribe.model.artifact.essential.ArtifactIdentification;
import com.braintribe.zarathud.model.data.Artifact;

public class BasicArtifactRegistry implements ArtifactRegistry {
	
	private Artifact runtimeArtifact;
	private Artifact unknownArtifact;
	private Map<String, Artifact> namedUnknownArtifacts = new HashMap<>();
	

	@Override
	public Artifact artifact(CommonZedCoreContext context, String signature) {
		return acquireArtifact(context, signature);
	}


	@Override
	public Artifact runtimeArtifact(CommonZedCoreContext context) {	
		return acquireJavaArtifact(context);
	}


	@Override
	public Artifact unknownArtifact(CommonZedCoreContext context) {
		return acquireUnknownSourceArtifact(context);
	}

	

	@Override
	public Artifact unknownArtifact(CommonZedCoreContext context, String name) {	
		return acquireUnknownSourceArtifact(context, name);
	}


	private boolean isJavaType( String signature) {
		//TODO : find a better way to do that.. 
		if (
				signature.startsWith( "java.") ||
				signature.startsWith( "java/") ||
				signature.startsWith( "javax.") ||
				signature.startsWith( "javax/")) {
			return true;
		}
		return false;
	}

	
	private Artifact acquireArtifact(CommonZedCoreContext context, String signature) {
		if (isJavaType(signature)) {
			return acquireJavaArtifact(context);
		}
		else if (signature.equalsIgnoreCase("void")) {
			return acquireJavaArtifact( context);
		}
		return acquireUnknownSourceArtifact( context);
	}

	private Artifact acquireJavaArtifact(CommonZedCoreContext context) {
		if (runtimeArtifact == null) {
			runtimeArtifact = Commons.create( context, Artifact.T);
			runtimeArtifact.setArtifactId("rt");
		}
		return runtimeArtifact;
	}

	private Artifact acquireUnknownSourceArtifact(CommonZedCoreContext context) {
		if (unknownArtifact == null) {
			unknownArtifact = Commons.create( context, Artifact.T);
			unknownArtifact.setArtifactId("unknown");
			unknownArtifact.setIsIncomplete(true);
		}
		return unknownArtifact;	
	}
	
	private Artifact acquireUnknownSourceArtifact(CommonZedCoreContext context, String name) {
		Artifact namedUnknownArtifact = this.namedUnknownArtifacts.get(name);
		if (namedUnknownArtifact == null) {
			namedUnknownArtifact = Commons.create( context, Artifact.T);
			ArtifactIdentification artifactIdentification = ArtifactIdentification.parse(name);
			namedUnknownArtifact.setGroupId( artifactIdentification.getGroupId());
			namedUnknownArtifact.setArtifactId( artifactIdentification.getArtifactId());
			namedUnknownArtifact.setVersion( "0.0.0");
			namedUnknownArtifact.setIsIncomplete(true);
			this.namedUnknownArtifacts.put ( name, namedUnknownArtifact);
		}
		return namedUnknownArtifact;	
	}

	
}
