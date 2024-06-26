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

import com.braintribe.model.generic.GenericEntity;

import tribefire.extension.xml.schemed.model.xsd.All;
import tribefire.extension.xml.schemed.model.xsd.Annotation;
import tribefire.extension.xml.schemed.model.xsd.Any;
import tribefire.extension.xml.schemed.model.xsd.Choice;
import tribefire.extension.xml.schemed.model.xsd.Element;
import tribefire.extension.xml.schemed.model.xsd.Group;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.Sequence;

public class SequenceExpert extends AbstractSchemaExpert {

	public static Sequence read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		Sequence sequence = Sequence.T.create();
		attach(sequence, declaringSchema);
		
		Map<QName, String> attributes = readAttributes(reader);		
		
		String value = attributes.get( new QName(MIN_OCCURS));
		if (value != null) {
			sequence.setMinOccurs(Integer.valueOf(value));
			sequence.setMinOccursSpecified(true);
		}
	
		value = attributes.get( new QName(MAX_OCCURS));
		if (value != null) {
			if (value.equalsIgnoreCase( UNBOUNDED)) {
				sequence.setMaxOccurs(-1);
				sequence.setMaxOccursSpecified(true);
			}
			else {
			sequence.setMaxOccurs(Integer.valueOf(value));
			}
		}
				
		reader.next();
		
		while (reader.hasNext()) {
			
			switch (reader.getEventType()) {
			
				case XMLStreamConstants.START_ELEMENT :												
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case ELEMENT:	
							Element element = ElementExpert.read( declaringSchema, reader);
							sequence.getElements().add( element);		
							sequence.getNamedItemsSequence().add( element);
							break;						
						case GROUP:
							Group group = GroupExpert.read( declaringSchema, reader);
							sequence.getGroups().add( group);
							sequence.getNamedItemsSequence().add( group);
							break;
						case CHOICE:							
							Choice choice2 = ChoiceExpert.read( declaringSchema, reader);
							sequence.getChoices().add(choice2);							
							sequence.getNamedItemsSequence().add( choice2);
							break;
						case SEQUENCE:							
						Sequence sequence2 = SequenceExpert.read( declaringSchema, reader);
							sequence.getSequences().add(sequence2);
							sequence.getNamedItemsSequence().add( sequence2);
							break;
						case ANNOTATION:
							Annotation annotation = AnnotationExpert.read( declaringSchema, reader);
							sequence.setAnnotation(annotation);				
							sequence.getNamedItemsSequence().add( annotation);
							break;
						case ANY:
							Any any = AnyExpert.read( declaringSchema, reader);
							//sequence.setA
							sequence.getNamedItemsSequence().add( any);
							break;
							default:
								skip(reader);
							break;
					}				
				break;				
				case XMLStreamConstants.END_ELEMENT : {
					return sequence;
				}		
			
				default: 
					break;
			}
			reader.next();
		}
		return sequence;
	}

	
	public static void write(XMLStreamWriter writer, Namespace namespace, Sequence sequence) throws XMLStreamException{
		if (sequence == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + SEQUENCE : SEQUENCE);
		// write attributes
		int min = sequence.getMinOccurs();
		if (min != 1 || sequence.getMinOccursSpecified()) {
			writer.writeAttribute( MIN_OCCURS, "" + min);
		}
		int max = sequence.getMaxOccurs();
		if (max < 0 ) {
			writer.writeAttribute( MAX_OCCURS, UNBOUNDED);
		}
		else if (max != 1 || sequence.getMaxOccursSpecified()) {
			writer.writeAttribute( MAX_OCCURS, "" + max);
		}
		
		for (GenericEntity ge : sequence.getNamedItemsSequence()) {
			if (ge instanceof Element) {
				ElementExpert.write(writer, namespace, (Element) ge);				
			}
			else if (ge instanceof Choice) {			
				ChoiceExpert.write(writer, namespace, (Choice) ge);
			}
			else if (ge instanceof Sequence) {
				SequenceExpert.write(writer, namespace, (Sequence) ge);				
			}
			else if (ge instanceof All) {
				AllExpert.write(writer, namespace, (All) ge);
			}
			else if (ge instanceof Annotation) {
				AnnotationExpert.write(writer, namespace, (Annotation) ge);
			}
			else if (ge instanceof Any) {
				AnyExpert.write(writer, namespace,  (Any) ge);
			}
			else {
				throw new IllegalStateException("unknown type [" + ge.getClass() + "] encountered");
			}
			
		}
		writer.writeEndElement();
	}
}
