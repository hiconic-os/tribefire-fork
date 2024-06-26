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
import tribefire.extension.xml.schemed.model.xsd.Attribute;
import tribefire.extension.xml.schemed.model.xsd.AttributeGroup;
import tribefire.extension.xml.schemed.model.xsd.Block;
import tribefire.extension.xml.schemed.model.xsd.Choice;
import tribefire.extension.xml.schemed.model.xsd.ComplexContent;
import tribefire.extension.xml.schemed.model.xsd.ComplexType;
import tribefire.extension.xml.schemed.model.xsd.Final;
import tribefire.extension.xml.schemed.model.xsd.Group;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.Sequence;
import tribefire.extension.xml.schemed.model.xsd.SimpleContent;

public class ComplexTypeExpert extends AbstractSchemaExpert {
	
	public static ComplexType read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		// wind to next event
		ComplexType complexType = ComplexType.T.create();
		attach(complexType, declaringSchema);
		Map<QName, String> attributes = readAttributes(reader);
		complexType.setName( attributes.get( new QName(NAME)));
		
		String value = attributes.get( new QName(ABSTRACT));
		if (value != null) {
			complexType.setAbstract( Boolean.valueOf(value));
			complexType.setAbstractSpecified( true);
		}
		
		value = attributes.get( new QName(MIXED));
		if (value != null) {
			complexType.setMixed( Boolean.valueOf(value));
			complexType.setMixedSpecified( true);
		}
		
		value = attributes.get( new QName(BLOCK));
		if (value != null) {
			complexType.setBlock( Block.valueOf(value));			
		}
		else {
			complexType.setBlock( Block.none);
		}
		value = attributes.get( new QName(FINAL));
		if (value != null) {
			complexType.setFinal( Final.valueOf(value));		
		}
		else {
			complexType.setFinal( Final.none);;
		}
		
		readAnyAttributes( complexType.getAnyAttributes(), attributes, NAME, ABSTRACT, MIXED, BLOCK, FINAL);
		
		reader.next();
		
		while (reader.hasNext()) {
			
			switch (reader.getEventType()) {
			
				case XMLStreamConstants.START_ELEMENT :												
					String tag = reader.getName().getLocalPart();
					switch (tag) {
					case SEQUENCE:			
						Sequence sequence2 = SequenceExpert.read( declaringSchema, reader);
						complexType.setSequence(sequence2);
						complexType.getNamedItemsSequence().add( sequence2);
						break;
					case CHOICE:		
						Choice choice2 = ChoiceExpert.read(declaringSchema, reader);
						complexType.setChoice(choice2);
						complexType.getNamedItemsSequence().add( choice2);
						break;						
					case ATTRIBUTE:
						Attribute attribute2 = AttributeExpert.read( declaringSchema, reader);
						complexType.getAttributes().add( attribute2);
						complexType.getNamedItemsSequence().add( attribute2);
						break;
					case ATTRIBUTE_GROUP:
						AttributeGroup attributeGroup = AttributeGroupExpert.read( declaringSchema, reader);
						complexType.getAttributeGroups().add( attributeGroup);
						complexType.getNamedItemsSequence().add( attributeGroup);
						break;
					case SIMPLE_CONTENT:
						SimpleContent simpleContent = SimpleContentExpert.read( declaringSchema, reader);
						complexType.setSimpleContent( simpleContent);
						complexType.getNamedItemsSequence().add( simpleContent);
						break;
					case COMPLEX_CONTENT:
						ComplexContent complexContent = ComplexContentExpert.read(declaringSchema, reader);
						complexType.setComplexContent( complexContent);
						complexType.getNamedItemsSequence().add( complexContent);
						break;
					case ANNOTATION:
						Annotation annotation = AnnotationExpert.read( declaringSchema, reader);
						complexType.setAnnotation(annotation);
						complexType.getNamedItemsSequence().add( annotation);
						break;
					case ALL:
						All all = AllExpert.read( declaringSchema, reader);
						complexType.setAll(all);				
						complexType.getNamedItemsSequence().add( all);
						break;
					case GROUP:
						Group group = GroupExpert.read( declaringSchema, reader);
						complexType.setGroup( group);
						complexType.getNamedItemsSequence().add( group);
						break;				
					default:
						skip(reader);
						break;
					}				
				break;
				
				case XMLStreamConstants.END_ELEMENT : {
					return complexType;
				}		
			
				default: 
					break;
			}
			reader.next();
		}
		return complexType;
	}

	public static void write(XMLStreamWriter writer, Namespace namespace, ComplexType complexType) throws XMLStreamException {
		if (complexType == null)
			return;
		String prefix = namespace.getPrefix();		
		writer.writeStartElement( prefix != null ? prefix + ":" + COMPLEX_TYPE : COMPLEX_TYPE);
		
		String name = complexType.getName();
		if (name != null) {
			writer.writeAttribute(NAME, name);
		}
		
		if (complexType.getAbstract() || complexType.getAbstractSpecified()) {
			writer.writeAttribute(ABSTRACT, Boolean.toString( complexType.getAbstract()));
		}
		if (complexType.getMixed() || complexType.getMixedSpecified()) {
			writer.writeAttribute(MIXED, Boolean.toString( complexType.getMixed()));
		}
		if (complexType.getBlock() != Block.none) {
			writer.writeAttribute(BLOCK, complexType.getBlock().toString());
		}
		if (complexType.getFinal() != Final.none) {
			writer.writeAttribute(FINAL, complexType.getFinal().toString());
		}
		
		writeAnyAttributes(writer, complexType.getAnyAttributes());
		
		for (GenericEntity ge : complexType.getNamedItemsSequence()) {
			if (ge instanceof ComplexContent) {
				ComplexContentExpert.write(writer, namespace, (ComplexContent) ge);				
			}
			else if (ge instanceof Sequence) {
				SequenceExpert.write(writer, namespace, (Sequence) ge);				
			}
			else if (ge instanceof Choice) {
				ChoiceExpert.write(writer, namespace, (Choice) ge);				
			}
			else if (ge instanceof All) {
				AllExpert.write(writer, namespace, (All) ge);
			}
			else if (ge instanceof Group) {
				GroupExpert.write(writer, namespace, (Group) ge);
			}
			else if (ge instanceof SimpleContent) {
				SimpleContentExpert.write(writer, namespace, (SimpleContent) ge);				
			}
			else if (ge instanceof Attribute) {
				AttributeExpert.write(writer, namespace, (Attribute) ge);				
			}
			else if (ge instanceof AttributeGroup) {
				AttributeGroupExpert.write(writer, namespace, (AttributeGroup) ge);								
			}
			else if (ge instanceof Annotation) {
				AnnotationExpert.write(writer, namespace, (Annotation) ge);
			}
			else {
				throw new IllegalStateException("unknown type [" + ge.getClass() + "] encountered");
			}
		}
	
		
				
		writer.writeEndElement();
		
	}
}
