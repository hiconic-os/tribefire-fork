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
package com.braintribe.codec.json.genericmodel;

import java.io.File;
import java.io.FilenameFilter;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.KnownIssue;
import com.braintribe.utils.IOTools;


public class GenericModelDataIOTest {
	@Test
	@Category(KnownIssue.class)
	public void testDumps() throws Exception {
		File dumpsDir = new File("./dumps");
		File files[] = dumpsDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".json");
			}
		});
		
		GenericModelJsonStringCodec<Object> codec = new GenericModelJsonStringCodec<Object>();
		
		long s,d;
		for (File file: files) {
			s = System.currentTimeMillis();
			String encodedJson = IOTools.slurp(file, "UTF-8");
			d = System.currentTimeMillis() - s;
			System.out.printf("%s read in %d ms\n", file.getName(), d);
			s = System.currentTimeMillis();
			Object value = codec.decode(encodedJson);
			d = System.currentTimeMillis() - s;
			System.out.printf("%s decoded in %d ms\n", file.getName(), d);
			s = System.currentTimeMillis();
			codec.encode(value);
			d = System.currentTimeMillis() - s;
			System.out.printf("%s encoded in %d ms\n", file.getName(), d);
		}
		{
			File file = files[0];
			String encodedJson = IOTools.slurp(file, "UTF-8");
			Object value = codec.decode(encodedJson);
			String reencodedJson = codec.encode(value);
			System.out.println(reencodedJson);
		}
	}
}
