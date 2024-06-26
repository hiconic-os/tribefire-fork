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
package com.braintribe.model.access.collaboration.persistence.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.api.Marshaller;
import com.braintribe.model.generic.tools.GmValueCodec;

/**
 * @author peter.gazdik
 */
public class GmValueMarshaller implements Marshaller {

	public static final GmValueMarshaller INSTANCE = new GmValueMarshaller();

	private GmValueMarshaller() {
	}

	@Override
	public void marshall(OutputStream out, Object value, GmSerializationOptions options) throws MarshallException {
		marshall(out, value);
	}

	@Override
	public Object unmarshall(InputStream in, GmDeserializationOptions options) throws MarshallException {
		return unmarshall(in);
	}

	@Override
	public void marshall(OutputStream out, Object value) throws MarshallException {
		String valueAsGmString = GmValueCodec.objectToGmString(value);

		try {
			Writer writer = new OutputStreamWriter(out, "UTF-8");
			writer.write(valueAsGmString);
			writer.flush();

		} catch (IOException e) {
			throw new MarshallException("Error while marshalling value: " + valueAsGmString, e);
		}
	}

	@Override
	public Object unmarshall(InputStream in) throws MarshallException {
		try {
			InputStreamReader isReader = new InputStreamReader(in, "UTF-8");
			BufferedReader reader = new BufferedReader(isReader);
			String valueAsGmString = reader.readLine();

			return GmValueCodec.objectFromGmString(valueAsGmString);

		} catch (IOException e) {
			throw new MarshallException("Error while unmarshalling GM value from input stream", e);
		}
	}

}
