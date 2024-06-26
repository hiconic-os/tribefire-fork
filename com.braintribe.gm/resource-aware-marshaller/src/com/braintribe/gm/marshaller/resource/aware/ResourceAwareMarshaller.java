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
package com.braintribe.gm.marshaller.resource.aware;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.marshaller.api.EntityVisitorOption;
import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.api.Marshaller;
import com.braintribe.codec.marshaller.api.MarshallerRegistry;
import com.braintribe.exception.Exceptions;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.resource.source.TransientSource;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.stream.api.StreamPipe;
import com.braintribe.utils.stream.api.StreamPipeFactory;
import com.braintribe.utils.stream.api.StreamPipes;
import com.braintribe.web.multipart.api.FormDataWriter;
import com.braintribe.web.multipart.api.MutablePartHeader;
import com.braintribe.web.multipart.api.PartReader;
import com.braintribe.web.multipart.api.PartWriter;
import com.braintribe.web.multipart.api.SequentialFormDataReader;
import com.braintribe.web.multipart.impl.MultipartSubFormat;
import com.braintribe.web.multipart.impl.Multiparts;

public class ResourceAwareMarshaller implements Marshaller {

	private Marshaller marshaller;
	private String gmDataMimeType = "application/octet-stream";
	private StreamPipeFactory streamPipeFactory;
	
	@Configurable
	public void setStreamPipeFactory(StreamPipeFactory streamPipeFactory) {
		this.streamPipeFactory = streamPipeFactory;
	}
	
	@Required
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}
	
	@Required
	public void setGmDataMimeType(String mimeType) {
		this.gmDataMimeType = mimeType;
	}
	
	private StreamPipeFactory getStreamPipeFactory() {
		if (streamPipeFactory == null) {
			streamPipeFactory = StreamPipes.simpleFactory();
		}

		return streamPipeFactory;
	}
	
	@Override
	public void marshall(OutputStream out, Object value, GmSerializationOptions options) throws MarshallException {
		new StatefulResourceAwareMarshaller(options, out).marshall(value);
	}

	@Override
	public Object unmarshall(InputStream in, GmDeserializationOptions options) throws MarshallException {
		return new StatefulResourceAwareUnmarshaller(options, in).unmarshall();
	}
	
	private class StatefulResourceAwareMarshaller {

		private GmSerializationOptions options;
		private OutputStream out;
		private List<TransientSource> transientSources = new ArrayList<>();

		public StatefulResourceAwareMarshaller(GmSerializationOptions options, OutputStream out) {
			this.options = options.derive().set(EntityVisitorOption.class, this::onEntityMarshalled).build();
			
			this.out = out;
		}
		
		private void onEntityMarshalled(GenericEntity e) {
			if (e.type() == TransientSource.T) {
				transientSources.add((TransientSource) e);
			}
		}

		public void marshall(Object value) {
			try (FormDataWriter formDataWriter = Multiparts.chunkedFormDataWriter(out)) {
			
				MutablePartHeader partHeader = Multiparts.newPartHeader();
				
				partHeader.setName("gm-data");
				partHeader.setContentType(gmDataMimeType);
				
				PartWriter gmDataPart = formDataWriter.openPart(partHeader);
				
				try (OutputStream partOut = gmDataPart.outputStream()) {
					marshaller.marshall(partOut, value, options);
				}
				
				for (TransientSource transientSource: transientSources) {
					MutablePartHeader resourcePartHeader = Multiparts.newPartHeader();
					
					resourcePartHeader.setName("transient-source");
					resourcePartHeader.addHeader("globalId", transientSource.getGlobalId());
					resourcePartHeader.setContentType("application/octet-stream");
					
					PartWriter resourcePart = formDataWriter.openPart(resourcePartHeader);
					
					try (OutputStream resourceOut = resourcePart.outputStream(); InputStream in = transientSource.openStream()) {
						IOTools.transferBytes(in, resourceOut, IOTools.BUFFER_SUPPLIER_64K);
					}
				}
			} catch (IOException e) {
				throw new MarshallException(e);
			} catch (Exception e1) {
				throw Exceptions.unchecked(e1);
			}
		}
	}
	
	private class StatefulResourceAwareUnmarshaller {
		
		private GmDeserializationOptions options;
		private InputStream in;
		private List<TransientSource> transientSources = new ArrayList<>();
		private Map<String, TransientSource> transientSourceByGlobalId = new LinkedHashMap<>();
		
		public StatefulResourceAwareUnmarshaller(GmDeserializationOptions options, InputStream in) {
			this.in = in;
			this.options = options.derive().set(EntityVisitorOption.class, this::onEntityUnmarshalled).build();
		}
		
		private void onEntityUnmarshalled(GenericEntity e) {
			if (e.type() == TransientSource.T) {
				transientSources.add((TransientSource) e);
			}
		}
		
		public Object unmarshall() {
			try (SequentialFormDataReader formDataReader = Multiparts.buildFormDataReader(in).subFormat(MultipartSubFormat.chunked).sequential()) {

				PartReader gmDataReader = formDataReader.next();
				
				String mimeType = gmDataReader.getContentType();

				Object gmData;
				
				try (InputStream partIn = gmDataReader.openStream()) {
					gmData = marshaller.unmarshall(partIn, options);
				}
				
				indexTransientSources();

				PartReader resourceReader = null;
				
				while ((resourceReader = formDataReader.next()) != null) {
					StreamPipe resourcePipe = getStreamPipeFactory().newPipe("resource");
					
					try (InputStream resourceIn = resourceReader.openStream(); OutputStream out = resourcePipe.acquireOutputStream()) {
						IOTools.transferBytes(resourceIn, out, IOTools.BUFFER_SUPPLIER_64K);
					}
					
					String globalId = resourceReader.getHeader("globalId");
					TransientSource transientSource = transientSourceByGlobalId.get(globalId);
					transientSource.setInputStreamProvider(resourcePipe::openInputStream);
				}
				
				return gmData;
				
			} catch (Exception e) {
				throw new MarshallException(e);
			}
			
		}

		private void indexTransientSources() {
			for (TransientSource transientSource: transientSources) {
				transientSourceByGlobalId.put(transientSource.getGlobalId(), transientSource);
			}
		}
		
	}

}
