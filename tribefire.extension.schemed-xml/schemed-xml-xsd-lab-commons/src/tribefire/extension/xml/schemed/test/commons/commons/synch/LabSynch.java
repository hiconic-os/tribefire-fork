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
package tribefire.extension.xml.schemed.test.commons.commons.synch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.braintribe.utils.IOTools;

/**
 * synchronizes the analyzer-map and the marshaller-map...
 * 
 * basically for now: as soon as a test fails, run the sync. If the test isn't added yet, do so, run the sync, run the test again..
 * if all's well, better move the tests to the round trip runner.  
 * 
 * @author pit
 *
 */
public class LabSynch {
	private static File workRoot = new File("d:/works/git/com.braintribe.gm.schemedxml");
	
	private static File marshallerRoot = new File( workRoot, "schemed-xml-marshaller-lab");
	private static File analyzerRoot = new File( workRoot, "schemed-xml-xsd-analyzer-lab");
	
	
	public static void processTuples( List<SyncTuple> tuples) {
		tuples.stream().forEach( t -> process(t));
	}
	
	public static void process( List<String> tuples) {
		tuples.stream().forEach( s -> {
			SyncTuple t = SyncTuple.fromString(s);
			process( t);	
		});
	}
	
	public static void process(String specification) {
		SyncTuple t = SyncTuple.fromString( specification);
		process( t);
	}
	
	public static void process( File file) throws IOException {
		String slurp = IOTools.slurp(file, "UTF-8");
		String [] lines = slurp.trim().split( "\n");
		process( Arrays.asList( lines));
		
	}
	
	public static void process(SyncTuple tuple ) {
		System.out.println( "processing [" + tuple.toString() + "]");
		
		File sourceRoot = new File( analyzerRoot, tuple.getSource());
		File targetRoot = new File( marshallerRoot, tuple.getTarget());
		
		if (!sourceRoot.exists()) {
			System.err.println( "no source [" + sourceRoot.getAbsolutePath() + "]");
			return;
		}
		
		if (!targetRoot.exists()) {
			targetRoot.mkdirs();
		}
		
		tuple.getNamesToCopy().forEach( n -> {
			File source = new File( sourceRoot, n);
			File target = new File( targetRoot, n);
			
			System.out.println("transferring [" + n + "], [" + source.getAbsolutePath() + "] -> [" + target.getAbsolutePath() + "]");
			if (!source.exists()) {
				System.err.println( "no source [" + source.getAbsolutePath() + "]");
			}
			else {
				try {
					Files.copy( source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					System.err.println("cannot copy [" + source.getAbsolutePath() + "] to [" + target.getAbsolutePath() + "] as " + e.getMessage());
				}
			}
		}); 
	}
	
	public static void main( String [] args) {
		List<String> tuples = new ArrayList<>();
		tuples.add( "namespaces_attributes,res/namespace/output,res/generics/namespaces/flat/input,AttributeQualifiedNamespaceFlatModel-1.0.jar,com.braintribe.xsd.AttributeQualifiedNamespaceFlatModel-mapping.model.xml");		
		tuples.add( "namespaces_elements,res/namespace/output,res/generics/namespaces/flat/input,ElementQualifiedNamespaceFlatModel-1.0.jar,com.braintribe.xsd.ElementQualifiedNamespaceFlatModel-mapping.model.xml");
		tuples.add( "namespaces_full,res/namespace/output,res/generics/namespaces/flat/input,QualifiedNamespaceFlatModel-1.0.jar,com.braintribe.xsd.QualifiedNamespaceFlatModel-mapping.model.xml");
		tuples.add( "namespaces_none,res/namespace/output,res/generics/namespaces/flat/input,NoNamespaceFlatModel-1.0.jar,com.braintribe.xsd.NoNamespaceFlatModel-mapping.model.xml");
		tuples.add( "namespaces_unqualified,res/namespace/output,res/generics/namespaces/flat/input,UnqualifiedNamespaceFlatModel-1.0.jar,com.braintribe.xsd.UnqualifiedNamespaceFlatModel-mapping.model.xml");
		
		tuples.add( "simple_type_import,res/simpleTypeImport/output,res/generics/simpleTypeImport/flat/input,SimpleTypeImportFlatModel-1.0.jar,com.braintribe.xsd.SimpleTypeImportFlatModel-mapping.model.xml");
		tuples.add( "redefining_type_import,res/redefiningTypeImport/output,res/generics/redefiningTypeImport/flat/input,RedefiningTypeImportFlatModel-1.0.jar,com.braintribe.xsd.RedefiningTypeImportFlatModel-mapping.model.xml");		
		tuples.add( "common_type_import,res/commonTypeImport/output,res/generics/commonTypeImport/flat/input,CommonTypeImportFlatModel-1.0.jar,com.braintribe.xsd.CommonTypeImportFlatModel-mapping.model.xml");
		
		process( tuples);
		
	}
}
