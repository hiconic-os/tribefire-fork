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
package com.braintribe.codec.marshaller.yaml;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.braintribe.cfg.Configurable;
import com.braintribe.codec.marshaller.api.CharacterMarshaller;
import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.exception.Exceptions;
import com.braintribe.gm.model.reason.Maybe;

public class YamlMarshaller implements CharacterMarshaller {
	
	private boolean writePooled;

	/**
	 * 
	 * @deprecated There is no variation of the YamlMarshaller any more. Just remove the call to this as method
	 * as it is without any effect.
	 */
	@Deprecated
	public void setV2(boolean v2) {
	}
	
	@Configurable
	public void setWritePooled(boolean writePooled) {
		this.writePooled = writePooled;
	}

	@Override
	public void marshall(OutputStream out, Object value) throws MarshallException {
		marshall(out, value, GmSerializationOptions.defaultOptions);
	}

	@Override
	public void marshall(OutputStream out, Object value, GmSerializationOptions options) throws MarshallException {
		try {
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			marshall(writer, value, options);
			writer.flush();
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "Error while marshalling:" + value, MarshallException::new);
		}
	}
	
	@Override
	public void marshall(Writer writer, Object value, GmSerializationOptions options) throws MarshallException {
		if (writePooled)
			new PooledStatefulYamlMarshaller(options, writer, value).write();
		else
			new StatefulYamlMarshaller(options, writer, value).write();
	}
	
	@Override
	public Maybe<Object> unmarshallReasoned(InputStream in, GmDeserializationOptions options) {
		try {
			return unmarshallReasoned(new InputStreamReader(in, "UTF-8"), options);
		} catch (UnsupportedEncodingException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	@Override
	public Maybe<Object> unmarshallReasoned(Reader reader, GmDeserializationOptions options) {
		StatefulYamlUnmarshaller decoder = new StatefulYamlUnmarshaller(reader, options);
		return decoder.decodeReasoned();
	}
	
	static String logStringify(Object value) {
		return value.toString();
	}
	
	@Override
	public Object unmarshall(InputStream in) throws MarshallException {
		return unmarshall(in, GmDeserializationOptions.defaultOptions);
	}

	@Override
	public Object unmarshall(InputStream in, GmDeserializationOptions options) throws MarshallException {
		try {
			return unmarshall(new InputStreamReader(in, "UTF-8"), options);
		} catch (UnsupportedEncodingException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public Object unmarshall(Reader reader, GmDeserializationOptions options) throws MarshallException {
		StatefulYamlUnmarshaller decoder = new StatefulYamlUnmarshaller(reader, options);
		return decoder.decode();
	}
}
