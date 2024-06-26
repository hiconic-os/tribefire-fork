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
package tribefire.extension.xml.schemed.marshaller.commons;

import tribefire.extension.xml.schemed.model.xsd.QName;

public class QNameExpert {
	
	public static QName parse( javax.xml.namespace.QName qname) {
		if (qname == null)
			return null;
	
		QName qName = QName.T.create();
		qName.setLocalPart( qname.getLocalPart());
		String namespaceURI = qname.getNamespaceURI();
		if (namespaceURI != null && namespaceURI.length() > 0) {
			qName.setNamespaceUri( namespaceURI);
		}
		
		String prefix = qname.getPrefix();
		if (prefix != null && prefix.length() > 0) {
			qName.setPrefix( prefix);
		}
		return qName;		
	}
	
	public static QName parse( String prefix, String localPart) {
		if (localPart == null || localPart.length() == 0)
			return null;
		QName qName = QName.T.create();
	
		qName.setPrefix( prefix);
		qName.setLocalPart( localPart);				
		return qName;
	}
	
	public static QName parse( String prefix, String localPart, String namespace) {
		if (localPart == null || localPart.length() == 0)
			return null;
		QName qName = QName.T.create();
	
		qName.setPrefix( prefix);
		qName.setLocalPart( localPart);				
		qName.setNamespaceUri(namespace);
		return qName;
	}
	
	
	
	public static QName parse( String string) {
		if (string == null || string.length() == 0)
			return null;
		QName qName = QName.T.create();
		String [] parts = string.split(":");
		if (parts.length >  1) {
			qName.setPrefix( parts[0]);
			qName.setLocalPart( parts[1]);				
		}
		else {
			qName.setLocalPart( string);
		}				
		return qName;
	}

	public static String toString(QName qName) {
		String prefix = qName.getPrefix();
		if (prefix != null && prefix.length() > 0) {
			return prefix + ":" + qName.getLocalPart();
		}
		return qName.getLocalPart();
	}

}
