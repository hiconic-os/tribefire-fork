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
package com.braintribe.devrock.zed.commons;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import com.braintribe.devrock.zed.api.context.CommonZedCoreContext;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.data.TypeReferenceEntity;
import com.braintribe.zarathud.model.data.ZedEntity;

/**
 * collection of common simple functions for ZED
 * 
 * @author pit
 *
 */
public class Commons {
	/**
	 * @param entityType
	 * @return
	 * @throws ZedException
	 */
	public static <T extends GenericEntity> T create(CommonZedCoreContext context, EntityType<T> entityType) throws ZedException {
		if (context.session() != null) {
			try {
				return (T) context.session().create(entityType);
			} catch (RuntimeException e) {
				String msg ="instance provider cannot provide new instance of type [" + entityType.getTypeSignature() + "]";				
				throw new ZedException(msg, e);
			}
		} 
		else {
			return (T) entityType.create();
		}
	}
	
	public static TypeReferenceEntity ensureTypeReference( CommonZedCoreContext context, ZedEntity z) {
		if (z instanceof TypeReferenceEntity) 
			return (TypeReferenceEntity) z;
		
		TypeReferenceEntity tr = create(context, TypeReferenceEntity.T);
		tr.setReferencedType(z);
		return tr;
	}
	
	/**
	 * returns a 'comparable' URL so the originating jar can be identified 
	 * @param resourceUrl - the explicit URL from the {@link URLClassLoader}
	 * @return - an URL that specifies the jar level only (not the internal file)
	 */
	public static URL extractComparableResource(URL resourceUrl, String resourceName) {
		String asE = resourceUrl.toExternalForm();
		String originatingResource = null;
		int p = asE.indexOf('!');
		if (p > 0) {
			originatingResource = asE.substring(4, p); 
		}
		else {
			String urlSuffix = resourceName.replace('.', '/') + ".class";
			int len = urlSuffix.length();
			originatingResource = asE.substring(0, (asE.length() - len));
		}
				
		try {
			return new URL( originatingResource);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("cannot build trimmed URL from [" + asE + "]", e);
		}	
	}
	
	public static Artifact getOwningArtifact( Map<URL,Artifact> map, URL suspect) {
		Artifact artifact = null;
		String scannedUrl = suspect.toString();
		if (scannedUrl.startsWith( "jar:")) {
			scannedUrl = scannedUrl.substring( 4);
		}		
		for (Map.Entry<URL, Artifact> entry : map.entrySet()) {
			String artifactUrl = entry.getKey().toString();
			if (scannedUrl.startsWith(artifactUrl)) {
				artifact = entry.getValue();
				break;
			}
		}
		return artifact;
	}
}
