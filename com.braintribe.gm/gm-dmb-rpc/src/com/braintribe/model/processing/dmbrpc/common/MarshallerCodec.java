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
package com.braintribe.model.processing.dmbrpc.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.codec.marshaller.api.EntityVisitorOption;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.api.Marshaller;
import com.braintribe.crypto.Cryptor;
import com.braintribe.crypto.Decryptor;
import com.braintribe.crypto.Encryptor;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.resource.CallStreamCapture;
import com.braintribe.model.resource.source.TransientSource;
import com.braintribe.utils.IOTools;

public class MarshallerCodec<T> implements Codec<T, byte[]> {
	private Marshaller marshaller;
	private Class<T> valueClass;
	private Encryptor encryptor;
	private Decryptor decryptor;
	private List<TransientSource> transientSources = new ArrayList<>();
	private List<CallStreamCapture> callStreamCaptures = new ArrayList<>();

	public MarshallerCodec(Marshaller marshaller, Class<T> valueClass) {
		this(marshaller, valueClass, null);
	}

	public MarshallerCodec(Marshaller marshaller, Class<T> valueClass, Cryptor cryptor) {
		this.marshaller = marshaller;
		this.valueClass = valueClass;
		if (cryptor instanceof Encryptor) {
			encryptor = (Encryptor) cryptor;
		}
		if (cryptor instanceof Decryptor) {
			decryptor = (Decryptor) cryptor;
		}
	}

	@Override
	public byte[] encode(T value) throws CodecException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		OutputStream out = null;
		try {
			out = encryptor != null ? encryptor.wrap(baos) : baos;
		} catch (Exception e) {
			throw new CodecException("error while wrapping stream", e);
		}

		try {
			marshaller.marshall(out, value, GmSerializationOptions.deriveDefaults().set(EntityVisitorOption.class, this::visitEntity).build());
		} catch (MarshallException e) {
			throw new CodecException("error while marshalling value", e);
		} finally {
			IOTools.closeQuietly(out);
		}

		return baos.toByteArray();

	}

	private void visitEntity(GenericEntity entity) {
		if (entity instanceof TransientSource) {
			TransientSource transientSource = (TransientSource) entity;
			transientSources.add(transientSource);
		} else if (entity instanceof CallStreamCapture) {
			CallStreamCapture callStreamCapture = (CallStreamCapture) entity;
			callStreamCaptures.add(callStreamCapture);
		}
	}

	public List<TransientSource> getTransientSources() {
		return transientSources;
	}

	public List<CallStreamCapture> getCallStreamCaptures() {
		return callStreamCaptures;
	}

	@Override
	public T decode(byte[] encodedValue) throws CodecException {

		ByteArrayInputStream bais = new ByteArrayInputStream(encodedValue);

		InputStream in = null;
		try {
			in = decryptor != null ? decryptor.wrap(bais) : bais;
		} catch (Exception e) {
			throw new CodecException("error while wrapping stream", e);
		}

		try {
			@SuppressWarnings("unchecked")
			T value = (T) marshaller.unmarshall(in);
			return value;
		} catch (MarshallException e) {
			throw new CodecException("error while unmarshalling value", e);
		} finally {
			IOTools.closeQuietly(in);
		}

	}

	@Override
	public Class<T> getValueClass() {
		return valueClass;
	}
}
