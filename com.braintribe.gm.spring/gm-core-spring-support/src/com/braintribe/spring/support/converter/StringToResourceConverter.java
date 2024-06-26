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

import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.convert.converter.Converter;

import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.source.FileUploadSource;

/**
 * A converter that converts a local File into a {@link Resource} with a 
 * LocalFileSource containing the path of the local file.
 *
 */
public class StringToResourceConverter implements Converter<String, Resource>{

	private Converter<String, File> stringToFileConverter;
	
	@Required
	public void setStringToFileConverter(Converter<String, File> stringToFileConverter) {
		this.stringToFileConverter = stringToFileConverter;
	}
	

	@Override
	public Resource convert(String localFilePath) {
		File localFile = stringToFileConverter.convert(localFilePath);
		return createResource(localFile);
	}
	
	public static Resource createResource(File localFile) {
		Resource resource = Resource.T.create();
		FileUploadSource source = FileUploadSource.T.create();
		source.setLocalFilePath(localFile.getAbsolutePath());
		resource.setResourceSource(source);
		return resource;
	}
	
	
	
}
