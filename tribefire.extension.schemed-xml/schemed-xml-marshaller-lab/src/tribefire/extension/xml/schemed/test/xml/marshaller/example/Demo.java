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
package tribefire.extension.xml.schemed.test.xml.marshaller.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmMetaModel;

import tribefire.extension.xml.schemed.marshaller.xml.SchemedXmlMarshaller;

public class Demo {

	private static void marshall(File input, String modelName, String xmlName) {
		
		// prepare mapping model
		StaxMarshaller staxMarshaller = new StaxMarshaller();
		GmMetaModel mappingModel;
		File modelFile = new File( input, modelName);
		try ( FileInputStream in = new FileInputStream( modelFile)) {
			mappingModel = (GmMetaModel) staxMarshaller.unmarshall(in);
		} catch (Exception e) {
			System.err.println("cannot read mapping model from [" + modelFile + "]");
			return;
		}
		
		// get marshaller
		SchemedXmlMarshaller marshaller = new SchemedXmlMarshaller();
		marshaller.setMappingMetaModel(mappingModel);

		
		
		// unmarshall, aka decode
		GenericEntity assembly;
		File xmlFile = new File( input, xmlName);
		try ( FileInputStream in = new FileInputStream( xmlFile)) {
			assembly = (GenericEntity) marshaller.unmarshall(in);
		} catch (Exception e) {
			System.err.println("cannot read xml file [" + xmlFile + "]");
			return;
		}
		
		System.out.println( assembly.getGlobalId());
				
		// marshall, aka encode
		ByteArrayOutputStream out = new ByteArrayOutputStream();		
		try {
			marshaller.marshall(out, assembly);
		} catch (MarshallException e) {
			System.err.println("cannot write xml file");
			return;
		}	
				
	}
	
	public static void main(String [] args) {
		marshall( new File( args[0]), args[1], args[2]);
	}
	
	
}
