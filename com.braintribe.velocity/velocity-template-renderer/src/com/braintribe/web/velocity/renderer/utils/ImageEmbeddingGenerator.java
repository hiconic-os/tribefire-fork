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
package com.braintribe.web.velocity.renderer.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.braintribe.logging.Logger;
import com.braintribe.utils.Base64;

public class ImageEmbeddingGenerator {
	private static Logger log = Logger.getLogger(ImageEmbeddingGenerator.class);

	public String embedImage( URL imageResource) {
		try {
			InputStream in = imageResource.openStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			byte[] buf = new byte[ 1024];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
			    out.write(buf, 0, count);
			}					
			byte [] bytes = out.toByteArray();
			String base64 = Base64.encodeBytes(bytes, Base64.DONT_BREAK_LINES);			
			return "data:image/gif;base64," + base64;
		} catch (MalformedURLException e) {
			String msg = "Cannot open resource stream as " + e;
			log.error( msg, e);
		} catch (IOException e) {
			String msg = "Cannot read from resource stream as " + e;
			log.error( msg, e);
		}
		return null;
		
	}
}
