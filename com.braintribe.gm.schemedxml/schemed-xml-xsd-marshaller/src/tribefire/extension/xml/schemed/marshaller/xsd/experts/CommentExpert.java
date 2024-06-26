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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import tribefire.extension.xml.schemed.model.xsd.Comment;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public class CommentExpert extends AbstractSchemaExpert{
	public static Comment read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {	
		Comment choice = Comment.T.create();
		attach(choice, declaringSchema);
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
		choice.setComment( buffer.toString());
		return choice;
	}
	
	public static void write(XMLStreamWriter writer, Namespace namespace, Comment comment) throws XMLStreamException{
			writer.writeComment( comment.getComment());
	}
}
