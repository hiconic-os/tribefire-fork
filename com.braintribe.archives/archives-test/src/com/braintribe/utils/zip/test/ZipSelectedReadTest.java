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
package com.braintribe.utils.zip.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.utils.archives.Archives;
import com.braintribe.utils.archives.ArchivesException;
import com.braintribe.utils.archives.zip.ZipContext;
import com.braintribe.utils.archives.zip.impl.ArchivesHelper;

public class ZipSelectedReadTest {
	private File source = new File( "res/reading/source/packed.zip");
	
	@Test
	public void test() {
		
		ZipContext zC;
		try {
			zC = Archives.zip().from(source);
		} catch (ArchivesException e1) {
			Assert.fail( "");
			return;
		}
		for (String header : zC.getHeaders()) {
			InputStream in = null;
			ByteArrayOutputStream out = null;
			try {
				in = zC.get(header);
				out = new ByteArrayOutputStream();
				ArchivesHelper.pump(in, out);
				System.out.println("pumped " + header);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
	}

}
