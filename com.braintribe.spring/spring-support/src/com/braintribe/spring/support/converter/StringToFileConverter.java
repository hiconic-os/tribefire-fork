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
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.core.convert.converter.Converter;


import com.braintribe.utils.lcd.FileTools;

public class StringToFileConverter implements Converter<String, File>{
	
	private File rootDir;
	protected Supplier<File> rootDirProvider = null;
	
	private Map<String, Supplier<File>> rootDirProviderMap;
	
	public void setRootDir(File rootDir) {
		this.rootDir = rootDir;
	}

	public void setRootDirProviderMap(Map<String, Supplier<File>> rootDirProviderMap) {
		this.rootDirProviderMap = rootDirProviderMap;
	}

	public void setRootDirProvider(Supplier<File> rootDirProvider) {
		this.rootDirProvider = rootDirProvider;
		
		if ((rootDirProvider != null) && (this.rootDir == null)) {
			try {
				this.rootDir = rootDirProvider.get();
			} catch (RuntimeException e) {
				throw new RuntimeException("Error while trying to get the rootDir file from the provider "+rootDirProvider, e);
			}
		}
	}

	@Override
	public File convert(String source) {
		if (source == null || source.trim().length() == 0) {
			return null;

		} else {
			source = FileTools.sanitizePath(source);
			
			File fileByPrefix = fromRootDirProvider(source);
			if (fileByPrefix != null) {
				return fileByPrefix;
			}
			
			File candidateFile = new File(source);
			return candidateFile.isAbsolute()? candidateFile: new File(rootDir, source);
		}
		
	}
	
	/**
	 * Tries to create a {@link File} object from the {@code source} parameter using the 
	 * root directory provider configured through {@link #setRootDirProviderMap(Map)} for its prefix.
	 * <p>
	 * Will return {@code null} if:
	 * <ul>
	 *   <li>No root directory provider was configured to {@link #rootDirProviderMap}</li>
	 *   <li>No prefix is found in the {@code source} parameter</li>
	 *   <li>The prefix found in the {@code source} parameter does not map to a configured root directory provider</li>
	 * </ul>
	 * 
	 * @param source
	 * @return {@link File} assembled by combining the {@code source} parameter with the root directory configured for its prefix.
	 */
	private File fromRootDirProvider(String source) {
		
		if (isEmpty(rootDirProviderMap)) 
			return null;
		
		int prefixIndex = source.indexOf(":");
		if (prefixIndex == -1)
			return null;

		String prefix = source.substring(0, prefixIndex).toLowerCase();
		Supplier<File> rootDirProvider = rootDirProviderMap.get(prefix);
		if (rootDirProvider == null)
			return null;

		String specific = source.substring(prefixIndex + 1);

		try {
			return new File(rootDirProvider.get(), specific);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("root directory provider [ "+rootDirProvider+" ] configured with the prefix "
					+ "[ "+prefix+" ] was unable to provide a root directory", e);
		}
	}
	
	private <K, V> boolean isEmpty(Map<K, V> map) {
		return (map == null || map.isEmpty());
	}

}
