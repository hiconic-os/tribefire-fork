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
package com.braintribe.model.processing.dataio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.Marshaller;
import com.braintribe.model.generic.reflection.GenericModelException;
import com.braintribe.utils.FileTools;
import com.braintribe.utils.file.api.PathValueStore;

/**
 * File system based {@link PathValueStore} which stores data in files relative to the configured {@link #setRootDir(File) root directory}, encoded
 * with a configured {@link #setMarshaller(Marshaller) marshaller}.
 * 
 * @author peter.gazdik
 */
public class FileBasedGmPathValueStore implements PathValueStore {

	private File rootDir;
	private Marshaller marshaller;
	private GmSerializationOptions serializationOptions = GmSerializationOptions.defaultOptions;
	private GmDeserializationOptions deserializationOptions = GmDeserializationOptions.defaultOptions;
	private String descriptor = "";

	@Required
	public void setRootDir(File rootDir) {
		if (!rootDir.exists())
			FileTools.ensureFolderExists(rootDir);
		else if (!rootDir.isDirectory())
			throw new IllegalArgumentException("File is not a directory:");

		this.rootDir = rootDir;
	}

	public File getRootDir() {
		return rootDir;
	}

	@Required
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	@Configurable
	public void setSerializationOptions(GmSerializationOptions serializationOptions) {
		this.serializationOptions = serializationOptions;
	}

	@Configurable
	public void setDeserializationOptions(GmDeserializationOptions deserializationOptions) {
		this.deserializationOptions = deserializationOptions;
	}

	@Configurable
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public boolean hasEntry(String filePath) {
		return fileForPath(filePath).exists();
	}

	/** Reads GM value for a slash-separated file-path (slashes work everywhere) */
	@Override
	public <T> T read(String filePath) {
		File file = fileForPath(filePath);

		if (!file.exists())
			return null;

		try (InputStream inputStream = new FileInputStream(file)) {
			return (T) marshaller.unmarshall(inputStream, deserializationOptions);

		} catch (Exception e) {
			throw new GenericModelException("Reading " + descriptor + " failed.", e);
		}
	}

	@Override
	public void write(String filePath, Object value) {
		File file = fileForPath(filePath);

		if (!file.exists())
			FileTools.ensureFolderExists(file.getParentFile());

		try (OutputStream outputStream = new FileOutputStream(file)) {
			marshaller.marshall(outputStream, value, serializationOptions);

		} catch (Exception e) {
			throw new GenericModelException("Storing " + descriptor + " failed.", e);
		}
	}

	private File fileForPath(String filePath) {
		return new File(rootDir, filePath);
	}

}
