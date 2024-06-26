// ============================================================================
// BRAINTRIBE TECHNOLOGY GMBH - www.braintribe.com
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2018 - All Rights Reserved
// It is strictly forbidden to copy, modify, distribute or use this code without written permission
// To this file the Braintribe License Agreement applies.
// ============================================================================

package com.braintribe.build.artifact.representations.artifact.pom.marshaller.experts;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.braintribe.model.artifact.Exclusion;

public class ExclusionExpert extends AbstractPomExpert implements HasPomTokens {
	
	public static Exclusion read(PomReadContext context, XMLStreamReader reader) throws XMLStreamException  {
		
		Exclusion exclusion = Exclusion.T.create();
		reader.next();
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {
					
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case GROUPID : {
							exclusion.setGroupId( extractString(context, reader));
							break;
						}
						case ARTIFACTID: {
							exclusion.setArtifactId( extractString(context, reader));
							break;
						}
					}
					break;
				}
				case XMLStreamConstants.END_ELEMENT : {
					return exclusion;
				}
				default:
					break;
			}
			reader.next();
		}
		return null;
	}
		
}
