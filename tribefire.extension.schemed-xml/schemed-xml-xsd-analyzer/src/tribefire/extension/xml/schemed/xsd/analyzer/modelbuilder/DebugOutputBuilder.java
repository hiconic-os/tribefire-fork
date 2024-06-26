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
package tribefire.extension.xml.schemed.xsd.analyzer.modelbuilder;

import java.util.Collection;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmListType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.GmSetType;
import com.braintribe.model.meta.GmType;

public class DebugOutputBuilder {
	public static void createDebugOutput(Collection<GmType> extractedTypes) {
		// simple output for now
		System.out.println("defined [" + extractedTypes.size() + "] types");
		String prefix = "";
		for (GmType extracted : extractedTypes) {						
			handleGmType( prefix, extracted);
		}
	}


	private static void handleGmType(String prefix, GmType extracted) {
		if (extracted.isGmEntity()) {				
			handleGmEntityType( "\t", (GmEntityType) extracted);
		}
		else if (extracted.isGmEnum()) {
		}
		else if (extracted.isGmCollection()) {
			if (extracted instanceof GmListType) {							
				System.out.print(prefix + "\t\t" + "list");
				handleGmType( prefix + "\t", ((GmListType) extracted).getElementType());
			}
			else if (extracted instanceof GmSetType) {
				System.out.print(prefix + "\t\t" + "set");
				handleGmType( prefix + "\t", ((GmSetType) extracted).getElementType());
			}
			else {
				System.out.print(prefix + "\t\t unsupported " + extracted.getTypeSignature());
			}
		}
		else if (extracted.isGmSimple()) { 
			System.out.print(prefix + "\t\t simple : " + extracted.getTypeSignature());
		}
		else {
			System.out.print(prefix + "\t\t unsupported " + extracted.getTypeSignature());
		}
		
	}


	private static void handleGmEntityType(String prefix, GmEntityType entityType) {
		System.out.print( prefix + "\t" + entityType.getTypeSignature());
		System.out.println();
		for (GmProperty property : entityType.getProperties()) {
			System.out.print( prefix + "\t" + property.getName() + ":");
			GmType type = property.getType();
			handleGmType( prefix + "\t", type);
			System.out.println();
		}
		//System.out.println();
	}
}
