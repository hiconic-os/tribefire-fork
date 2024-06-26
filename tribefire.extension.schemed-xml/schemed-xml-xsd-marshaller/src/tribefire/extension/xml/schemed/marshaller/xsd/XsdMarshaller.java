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
package tribefire.extension.xml.schemed.marshaller.xsd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.braintribe.logging.Logger;

import tribefire.extension.xml.schemed.marshaller.xsd.experts.SchemaExpert;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public class XsdMarshaller implements HasSchemaTokens {
	private static Logger log = Logger.getLogger(XsdMarshaller.class);
	private static XMLInputFactory inputFactory;
	private static XMLOutputFactory outputFactory;
	private boolean indenting = true;
	
	static {
		inputFactory = XMLInputFactory.newInstance();

		boolean debug = log.isDebugEnabled();
		try {
			inputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false); // This disables DTDs entirely for that factory
		} catch(Exception e) {
			if (debug) log.debug("Could not set feature "+XMLInputFactory.SUPPORT_DTD+"=false", e);
		}

		try {
			inputFactory.setProperty("javax.xml.stream.isSupportingExternalEntities", false); // disable external entities
		} catch(Exception e) {
			if (debug) log.debug("Could not set feature javax.xml.stream.isSupportingExternalEntities=false", e);
		}
		
		outputFactory = XMLOutputFactory.newInstance();
	}
	
	/**
	 * unmarshall the file 
	 * @param file
	 * @return
	 * @throws XMLStreamException
	 */
	public Schema unmarshall( File file) throws XMLStreamException {	
		try (InputStream in = new FileInputStream( file)) {
			XMLStreamReader reader = inputFactory.createXMLStreamReader( in);
			return unmarshall(reader);
		} catch (Exception e) {
			throw new XMLStreamException(e);
		} 
	}
	
	/**
	 * unmarshall the input stream 
	 * @param in
	 * @return
	 * @throws XMLStreamException
	 */
	public Schema unmarshall(InputStream in) throws XMLStreamException {
		XMLStreamReader reader = inputFactory.createXMLStreamReader( in);
		return unmarshall(reader);
	}
	
	public Schema unmarshall( URL url) throws XMLStreamException {
		try {
			InputStream in = url.openStream();
			XMLStreamReader reader = inputFactory.createXMLStreamReader( in);
			return unmarshall(reader);
		} catch (IOException e) {
			throw new XMLStreamException( "cannot open stream to [" + url.toString() + "]", e);
		}
	}
	
	/**
	 * unmarshall the POM as defined in the String passed  
	 * @param in
	 * @return
	 * @throws XMLStreamException
	 */
	public Schema unmarshall( String in) throws XMLStreamException {
		try (InputStream stream = new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8));) {
			return unmarshall(stream);
		} catch (IOException e) {
			throw new XMLStreamException( e);
		}
	}
	public Schema unmarshall( XMLStreamReader reader) throws XMLStreamException {
		Schema schema = null;
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
			case XMLStreamConstants.START_ELEMENT : 				
				String tag = reader.getName().getLocalPart();{
					if (tag.equalsIgnoreCase( SCHEMA )) {
						schema = SchemaExpert.read(reader);
					}
					break;
				}
			case XMLStreamConstants.END_ELEMENT : {
				return schema;
			}
			case XMLStreamConstants.END_DOCUMENT: {
				return schema;
			}
			default: 
				break;
			}
			reader.next();
		}	
		return schema;
	}
	

	public void marshall(File file, Schema value) throws XMLStreamException {
		try (OutputStream out = new FileOutputStream( file)) {
			marshall( out, value);
		}
		catch (Exception e) {
			throw new XMLStreamException(e);
		}
	}
	
	public String marshall( Schema value) throws XMLStreamException {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			marshall(out, value);
			return out.toString();
		} catch (IOException e) {
			throw new XMLStreamException(e);
		}
	}
	
	public void marshall( OutputStream out, Schema value) throws XMLStreamException {
		XMLStreamWriter writer;
		writer = !indenting ? outputFactory.createXMLStreamWriter(out, "UTF-8") : new IndentingXmlStreamWriter(out, "UTF-8");
		marshall( writer, value);
	}
	
	public void marshall( XMLStreamWriter writer, Schema value) throws XMLStreamException {
		writer.writeStartDocument();
		SchemaExpert.write( writer, value);
		writer.writeEndDocument();
		writer.flush();
		writer.close();
	}
	
	
	
}
