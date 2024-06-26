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
package com.braintribe.marshaller.artifact.maven.settings.experts;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.braintribe.model.artifact.maven.settings.Configuration;
import com.braintribe.model.artifact.maven.settings.Server;

public class ServerExpert extends AbstractSettingsExpert {
	public static Server read(SettingsMarshallerContext context, XMLStreamReader reader) throws XMLStreamException {
		Server result = create( context, Server.T);
		reader.next();
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {	
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case ID:
							result.setId( extractString( context, reader));
						break;
						case USERNAME:
							result.setUsername(extractString( context, reader));
							break;
						case PASSWORD:
							result.setPassword(extractString(context, reader));
							break;
						case FILE_PERMISSIONS:
							result.setFilePermissions(extractString(context, reader));
							break;
						case DIRECTORY_PERMISSIONS:
							result.setDirectoryPermissions( extractString(context, reader));
							break;
						case PASSPHRASE:
							result.setPassphrase( extractString(context, reader));
							break;
						case PRIVATE_KEY : 
							result.setPrivateKey( extractString(context, reader));
							break;
						case CONFIGURATION:
							String any = extractString(context, reader);
							Configuration configuration = create( context, Configuration.T);
							configuration.setAny(any);
							result.setConfiguration( configuration);
							break;
					}
					break;
				}
				case XMLStreamConstants.END_ELEMENT : {
					return result;
				}
				default:
					break;
			}
			reader.next();
		}
		return null;
	}
}
