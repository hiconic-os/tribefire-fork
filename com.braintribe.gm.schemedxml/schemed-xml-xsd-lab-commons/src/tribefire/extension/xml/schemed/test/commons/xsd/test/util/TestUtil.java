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
package tribefire.extension.xml.schemed.test.commons.xsd.test.util;

import java.io.File;

public class TestUtil {
	
	/**
	 * recursive file delete 
	 * @param file - top directory to be deleted
	 */
	public static void delete( File file) {
		if (file == null || file.exists() == false)
			return;
		for (File child : file.listFiles()) {
			if (child.isDirectory()) {
				delete( child);
			} 
			child.delete();			
		}
	}

	/**
	 * make sure that the directory exists AND IS EMPTY
	 * @param output - the directory to ensure
	 */
	public static void ensure(File output) {	
		if (output.exists())
			delete( output);
		output.mkdirs();
	}
}
