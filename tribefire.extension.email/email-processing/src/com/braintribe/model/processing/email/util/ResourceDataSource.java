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
package com.braintribe.model.processing.email.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import com.braintribe.common.lcd.NotSupportedException;
import com.braintribe.exception.Exceptions;
import com.braintribe.model.resource.Resource;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.stream.api.StreamPipe;
import com.braintribe.utils.stream.api.StreamPipeFactory;

public class ResourceDataSource implements DataSource {

	private Resource resource;

	public ResourceDataSource(StreamPipeFactory pipeStreamFactory, Resource resource) {
		StreamPipe pipe = pipeStreamFactory.newPipe("attachment-" + resource.getName());
		try (OutputStream os = pipe.acquireOutputStream(); InputStream in = resource.openStream()) {
			IOTools.transferBytes(in, os);

			this.resource = Resource.createTransient(pipe::openInputStream);
			this.resource.setName(resource.getName());
			this.resource.setMimeType(resource.getMimeType());
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "Could not access attachment resource.");
		}
	}
	public ResourceDataSource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new BufferedInputStream(resource.openStream());
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		throw new NotSupportedException("Saving Resources is not supported here!");
	}

	@Override
	public String getContentType() {
		return resource.getMimeType();
	}

	@Override
	public String getName() {
		return resource.getName();
	}

}
