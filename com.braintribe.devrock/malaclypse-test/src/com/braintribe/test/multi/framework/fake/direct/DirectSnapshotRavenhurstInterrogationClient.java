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
package com.braintribe.test.multi.framework.fake.direct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.braintribe.build.artifact.retrieval.multi.ravenhurst.interrogation.RepositoryInterrogationClient;
import com.braintribe.build.artifact.retrieval.multi.ravenhurst.interrogation.RepositoryInterrogationException;
import com.braintribe.model.artifact.Identification;
import com.braintribe.model.ravenhurst.Artifact;
import com.braintribe.model.ravenhurst.Part;
import com.braintribe.model.ravenhurst.interrogation.RavenhurstBundle;
import com.braintribe.model.ravenhurst.interrogation.RavenhurstRequest;
import com.braintribe.model.ravenhurst.interrogation.RavenhurstResponse;
import com.braintribe.test.multi.framework.SnapshotTuple;

public class DirectSnapshotRavenhurstInterrogationClient extends AbstractDirectSnapshotClientBase implements RepositoryInterrogationClient {
	
	

	public DirectSnapshotRavenhurstInterrogationClient(String key, boolean expansive, SnapshotTuple... snapshotTuples) {
		super(key, expansive, snapshotTuples);	
	}

	@Override
	public RavenhurstResponse interrogate(RavenhurstRequest request) throws RepositoryInterrogationException {		
		RavenhurstResponse response = RavenhurstResponse.T.create();
		StringBuilder builder = new StringBuilder();
		
		for (SnapshotTuple tuple : tuples) {
			if (builder.length() > 0)
				builder.append("\n");
			String value = tuple.getArtifact();
			builder.append( value);			

			int gp = value.indexOf(':');
			int vs = value.indexOf( '#');
			Artifact artifact = Artifact.T.create();
			artifact.setGroupId( value.substring(0, gp));
			artifact.setArtifactId( value.substring(gp+1, vs));
			artifact.setVersion( value.substring(vs+1));
			response.getTouchedArtifacts().add( artifact);
		}
		
		response.setPayload(builder.toString());
		
		return response;
	}
	@Override
	public RavenhurstResponse extractIndex(RavenhurstRequest request) throws RepositoryInterrogationException {
		RavenhurstResponse response = RavenhurstResponse.T.create();
		return response;
	}

	@Override
	public List<Part> extractPartList(RavenhurstBundle bundle, Artifact artifact) throws RepositoryInterrogationException {
		url = bundle.getRepositoryUrl();
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		List<Part> parts = new ArrayList<Part>();
		for (SnapshotTuple tuple : tuples) {
			if (match(artifact, tuple.getArtifact())) {
				
				
				for (int i = 0; i < tuple.getBuilds().length; i++) {
					int build = tuple.getBuilds()[i];
					Date timestamp = tuple.getTimestamps()[i];
					String pathPrefix = url + "/" + artifact.getGroupId().replace('.', '/') + "/" + artifact.getArtifactId() + "/" + artifact.getVersion();
					String namePrefix = artifact.getArtifactId() + "-" + artifact.getVersion() + "-" + format.format( timestamp) + "-" + build;
					
					String [] values = restricted_extensions;
					if (expansive) {
						values = expansive_extensions;
					}
					for (String extension : values) {
						Part part = Part.T.create();
						part.setSource( pathPrefix + "/" + namePrefix + extension);
						part.setName( namePrefix + extension);
						parts.add(part);
						// hashes 
						part = Part.T.create();
						part.setSource( pathPrefix + "/" + namePrefix + extension + ".md5");
						part.setName( namePrefix + extension + ".md5");
						parts.add(part);
						part = Part.T.create();
						part.setSource( pathPrefix + "/" + namePrefix + extension + ".sha1");
						part.setName( namePrefix + extension + ".sha1");
						parts.add(part);	
					}
					// metadata 
					Part part = Part.T.create();
					part.setSource( pathPrefix + "/" + "maven-metadata.xml");
					part.setName( "maven-metadata.xml");
					parts.add(part);
					
					part = Part.T.create();
					part.setSource( pathPrefix + "/" + "maven-metadata.xml.md5");
					part.setName( "maven-metadata.xml.md5");
					parts.add(part);
					
					part = Part.T.create();
					part.setSource( pathPrefix + "/" + "maven-metadata.xml.sha1");
					part.setName( "maven-metadata.xml.sha1");
					parts.add(part);
				}
			}
		}
		return parts;
	}

	@Override
	public List<String> extractVersionList(RavenhurstBundle bundle, Identification artifact) throws RepositoryInterrogationException {
		List<String> versions = new ArrayList<String>();
		for (SnapshotTuple tuple : tuples) {
			String suspect = tuple.getArtifact();
			if (match( artifact, suspect)) {
				int p = suspect.indexOf('#');
				String vrs = suspect.substring(p+1);
				versions.add( vrs);
			}
		}
		return versions;
	}

}
