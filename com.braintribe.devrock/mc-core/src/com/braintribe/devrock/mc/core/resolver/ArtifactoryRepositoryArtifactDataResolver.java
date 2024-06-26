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
package com.braintribe.devrock.mc.core.resolver;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.json.JsonStreamMarshaller;
import com.braintribe.devrock.mc.api.commons.ArtifactAddressBuilder;
import com.braintribe.devrock.mc.api.resolver.ArtifactDataResolution;
import com.braintribe.devrock.mc.core.commons.PartReflectionCommons;
import com.braintribe.devrock.model.artifactory.FileItem;
import com.braintribe.devrock.model.artifactory.FolderInfo;
import com.braintribe.exception.Exceptions;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.ReasonException;
import com.braintribe.gm.model.reason.essential.NotFound;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;
import com.braintribe.model.artifact.consumable.PartReflection;
import com.braintribe.model.resource.Resource;
import com.braintribe.utils.lcd.LazyInitialized;
import com.braintribe.utils.paths.UniversalPath;

public class ArtifactoryRepositoryArtifactDataResolver extends HttpRepositoryArtifactDataResolver {
	private LazyInitialized<String> restUrl = new LazyInitialized<>( this::determineRestUrl);
	private static JsonStreamMarshaller marshaller = new JsonStreamMarshaller();
	private static GmDeserializationOptions options = GmDeserializationOptions.defaultOptions.derive().setInferredRootType( FolderInfo.T).build();
	
	private String determineRestUrl() {
		try {
			URI uri = new URI( root);		
			UniversalPath path = UniversalPath.empty().pushSlashPath( uri.getPath());
			UniversalPath artifactoryContextUrl = path.pop();
			UniversalPath resturl = artifactoryContextUrl.push("api").push("storage").push( path.getName());
			uri.resolve( "/" + resturl.toSlashPath());			
			URI newUri = new URI( uri.getScheme() + "://" + uri.getAuthority() +  resturl.toSlashPath());
			return newUri.toString();
		} catch (URISyntaxException e) {
			throw new IllegalStateException("invalid root URL [" + root + "]", e);
		}
		
	}

	@Override
	public Maybe<ArtifactDataResolution> getPartOverview( CompiledArtifactIdentification compiledArtifactIdentification) {
		return resolve(ArtifactAddressBuilder.build().root( restUrl.get()).compiledArtifact(compiledArtifactIdentification));		
	}

	@Override
	public List<PartReflection> getPartsOf(CompiledArtifactIdentification compiledArtifactIdentification) {
		Maybe<ArtifactDataResolution> overview = getPartOverview(compiledArtifactIdentification);
		if (overview.isSatisfied()) {
			Resource resource = overview.get().getResource();
			try (InputStream in = new BufferedInputStream( resource.openStream())) {
				FolderInfo info = (FolderInfo) marshaller.unmarshall(in, options);
				List<String> files = new ArrayList<>( info.getChildren().size());
				for (FileItem item : info.getChildren()) {
					files.add( item.getUri());
				}
				return PartReflectionCommons.transpose( compiledArtifactIdentification, repositoryId, files);
			}
			catch (IOException | NoSuchElementException e) {
				return Collections.emptyList(); // no such file 
			}
			catch (Exception e) {
				throw Exceptions.unchecked( e, "error while unmarshalling json result for [" +  compiledArtifactIdentification + "]");
			}					
		}
		else if (overview.whyUnsatisfied() instanceof NotFound) {
			return Collections.emptyList();	
		}
		else {
			throw new ReasonException(overview.whyUnsatisfied());
		}
	}
	
	
	
	

}
