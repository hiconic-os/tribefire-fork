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
package com.braintribe.spring.loader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.braintribe.logging.Logger;


/**
 * string based resource provider 
 * 
 * @author pit
 *
 */
public class ResourceProvider extends GenericResourceLoader implements Supplier<String> {
	private static Logger log = Logger.getLogger(ResourceProvider.class);
	private String encoding = null;
	private String url = null;	
	
	@Required
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	@Required
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	@Override
	public String get() throws RuntimeException {
		try {
			
			Resource urlResource = null;
			try {
				URL urlR = getClass().getClassLoader().getResource(url);
				urlResource = new UrlResource( urlR.toURI().toString());
			} catch (Exception e) {
				String msg = "cannot retrieve resource denoted by URL [" + url + "]";
				log.error( msg, e);
			}
			if (urlResource == null)
				urlResource = getResourceByPath( url);
		
			InputStream in = urlResource.getURL().openStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			byte[] buf = new byte[ 1024];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
			    out.write(buf, 0, count);
			}					
			return out.toString( encoding);
		} catch (Exception e) {
			String msg="cannot provide contents of URL [" + url + "]"; 
			throw new RuntimeException( msg, e);
		} 
			
	}
		
	
}
