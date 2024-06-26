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
package com.braintribe.spring.support.converter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * 
 * @deprecated please use {@link StringToUrlConverter} since this one cannot handle relative urls
 */
@Deprecated
public class UrlConverter implements Converter<String, URL> {
	
	public URL convert(String source) {
		if (source == null || source.trim().length() == 0) {
			return null;
		} else {
			try {				
				return new URL( source);
			} catch (MalformedURLException e) {
				File file = new File( source);
				if (file.exists()) {
					try {
						return file.toURI().toURL();
					} catch (MalformedURLException e1) {
						throw new IllegalArgumentException("[" + source + "] is not a valid format for an URL", e);
					}
				} else {
					throw new IllegalArgumentException("can't find file [" + file.getAbsolutePath() + "] ", e);
				}

			}
		}
		
	}

}
