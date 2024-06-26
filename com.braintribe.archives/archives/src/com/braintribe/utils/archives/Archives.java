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
package com.braintribe.utils.archives;

import java.util.zip.ZipFile;

import com.braintribe.utils.archives.zip.ZipContext;
import com.braintribe.utils.archives.zip.ZipContextEntry;
import com.braintribe.utils.archives.zip.impl.ZipContextEntryImpl;
import com.braintribe.utils.archives.zip.impl.ZipContextImpl;

/**
 * the starting point of the voyage into the land of archives <br/>
 * current implementation supports ZIP file via {@link ZipFile}
 * @author pit
 *
 */
public class Archives {
	
	/**
	 * the starting point: creates a new {@link ZipContext} and returns it
	 * @return - the created {@link ZipContext}
	 */
	public static ZipContext zip() {
		return new ZipContextImpl();
	}
	
	/**
	 * create an empty {@link ZipContextEntry} and return it
	 * @return - the created {@link ZipContextEntry}
	 */
	public static ZipContextEntry entry() {
		return new ZipContextEntryImpl();
	}
	
}
