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
package com.braintribe.marshaller.maven.metadata.experts;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.braintribe.marshaller.maven.metadata.HasTokens;
import com.braintribe.model.artifact.meta.MavenMetaData;
import com.braintribe.model.artifact.processing.version.VersionProcessor;
import com.braintribe.model.artifact.version.Version;

public class MavenMetaDataExpert extends AbstractMavenMetaDataExpert implements HasTokens {
	
	public static MavenMetaData read( XMLStreamReader reader) throws XMLStreamException {
		MavenMetaData mavenMetaData = MavenMetaData.T.create();
		
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {
					
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case tag_groupId : {
							mavenMetaData.setGroupId( extractString( reader));
							break;
						}
						case tag_artifactId: {
							mavenMetaData.setArtifactId( extractString( reader));
							break;
						}
						case tag_version: {
							String versionAsString = extractString( reader);
							mavenMetaData.setVersion( VersionProcessor.createFromString(versionAsString));
							break;
						}
						case tag_versioning : {
							mavenMetaData.setVersioning( VersioningExpert.extract( reader));
							break;
						}
						case tag_plugins : {
							mavenMetaData.setPlugins( PluginsExpert.extract( reader));
							break;
						}
					}
					break;
				}
				case XMLStreamConstants.COMMENT : {									
					mavenMetaData.setMcComment( reader.getText());
					break;
				}
				case XMLStreamConstants.END_ELEMENT : {
					return mavenMetaData;
				}
				default:
					break;
			}
			reader.next();
		}
		return null;
	}
	
	public static void write(XMLStreamWriter writer, MavenMetaData value) throws XMLStreamException {
		writer.writeStartElement( tag_metaData);
		if (value.getMcComment() != null) {
			writer.writeComment( value.getMcComment());
		}
		write(writer, tag_groupId, value.getGroupId());
		write( writer, tag_artifactId, value.getArtifactId());
		Version version = value.getVersion();
		if (version != null) {
			write( writer, tag_version, VersionProcessor.toString( version));
		}
		VersioningExpert.write(writer, value.getVersioning());
		PluginsExpert.write( writer, value.getPlugins());
		writer.writeEndElement();
		
	}
}
