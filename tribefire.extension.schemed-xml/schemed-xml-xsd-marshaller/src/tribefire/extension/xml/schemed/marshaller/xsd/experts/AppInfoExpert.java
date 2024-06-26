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
package tribefire.extension.xml.schemed.marshaller.xsd.experts;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import tribefire.extension.xml.schemed.model.xsd.AppInfo;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public class AppInfoExpert extends AbstractSchemaExpert {
	public static AppInfo read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		AppInfo appInfo = AppInfo.T.create();
		attach(appInfo, declaringSchema);

		Map<QName, String> attributes = readAttributes(reader);
		appInfo.setSource( attributes.get( new QName(SOURCE)));	
				
		reader.next();
		
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT :			
				break;
				case XMLStreamConstants.END_ELEMENT : {
					return appInfo;
				}				
				default: 
					break;
				}
				reader.next();
		}
		return appInfo;
	}
	
	public static void write( XMLStreamWriter writer, Namespace namespace, AppInfo appinfo) throws XMLStreamException {
		if (appinfo == null)
			return;
		String prefix = namespace.getPrefix();		
		writer.writeStartElement( prefix != null ? prefix + ":" + DOCUMENTATION : DOCUMENTATION);
		
		String source = appinfo.getSource();
		if (source != null)
			writer.writeAttribute( SOURCE, source);

		writer.writeCharacters( appinfo.getContents());
		
		writer.writeEndElement();
	}
}
