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
package tribefire.extension.xml.schemed.demo;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmMetaModel;

import tribefire.extension.xml.schemed.marshaller.xml.SchemedXmlMarshaller;

/**
 * stupid little example just to show how you bascially call both marshalling and unmarshalling functions of the schemed-xml-marshaller
 * 
 * Note that calling the main function of this example currently doesn't work as it requires the assembly's types in the classpath. 
 * 
 * @author pit
 *
 */
public class Demo {
	private static Logger log = Logger.getLogger(Demo.class);

	private static void marshall(File input, String mappingName, String xmlName) {
		
		// prepare mapping model : load it from the XML dump
		StaxMarshaller staxMarshaller = new StaxMarshaller();
		GmMetaModel mappingModel;
		File modelFile = new File( input, mappingName);
		try ( FileInputStream in = new FileInputStream( modelFile)) {
			mappingModel = (GmMetaModel) staxMarshaller.unmarshall(in);
		} catch (Exception e) {
			log.error( "cannot read mapping model from [" + modelFile + "]", e);
			return;
		}
		
		// get & initialize marshaller
		SchemedXmlMarshaller marshaller = new SchemedXmlMarshaller();
		marshaller.setMappingMetaModel(mappingModel);

		
		// unmarshall, aka decode
		GenericEntity assembly;
		File xmlFile = new File( input, xmlName);
	
		try ( FileInputStream in = new FileInputStream( xmlFile)) {
			assembly = (GenericEntity) marshaller.unmarshall(in);
		} catch (MarshallException e) {
			log.error("cannot unmarshall xml file [" + xmlFile + "]" + e);
			return;
		}
		catch (IOException e) {
			log.error("cannot read xml file [" + xmlFile + "]", e);
			return;
		}
		
		// let's just print out the signature of the main (container-) type of the assembly
		System.out.println( assembly.entityType().getTypeSignature());
				
		// marshall, aka encode
		File ouputDir = new File( input, "output");
		ouputDir.mkdir();
		File xmlOutputFile = new File( ouputDir, xmlName); 
				
		try (OutputStream out = new FileOutputStream(xmlOutputFile)) {
			marshaller.marshall(out, assembly);
		} catch (MarshallException e) {
			log.error("cannot marshall", e);
			return;
		}	
		catch (IOException e) {
			log.error("cannot write xml file", e);
			return;
		}
				
	}
	
	public static void main(String [] args) {
		marshall( new File( "res/Swift/pain"), "com.braintribe.fin.swift.Pain-001.001.03-mapping#1.0.xml", "pain.1.001.001.03.xml");
	}
	
	
}
