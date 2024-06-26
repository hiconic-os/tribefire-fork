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
package com.braintribe.gwt.gm.resource.streamingprocessing.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.braintribe.codec.Codec;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.fileapi.client.File;
import com.braintribe.gwt.fileapi.client.FileList;
import com.braintribe.gwt.fileapi.client.ProgressHandler;
import com.braintribe.gwt.genericmodel.client.codec.dom4.GmXmlCodec;
import com.braintribe.gwt.gm.resource.api.client.ResourceBuilder;
import com.braintribe.gwt.gm.resource.api.client.ResourcesFromFilesBuilder;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resourceapi.persistence.UploadResourceResponse;

public class RestBasedResourceProcessing implements ResourceBuilder {
	private Codec<UploadResourceResponse, String> resultCodec;
	private ManagedGmSession session;
	//private String streamBaseUrl;
	//private Supplier<String> sessionProvider;
	//private XMLHttpRequest2 xhr;
	
	@SuppressWarnings("unused")
	public void setStreamBaseUrl(String streamBaseUrl) {
		//this.streamBaseUrl = streamBaseUrl;
	}
	
	@SuppressWarnings("unused")
	public void setSessionProvider(Supplier<String> sessionProvider) {
		//this.sessionProvider = sessionProvider;
	}
	
	public Codec<UploadResourceResponse, String> getResultCodec() {
		if (resultCodec == null) {
			resultCodec = new GmXmlCodec<UploadResourceResponse>();
		}

		return resultCodec;
	}
	
	@Override
	public void configureGmSession(ManagedGmSession gmSession) {
		session = gmSession;
	}
	
	@Override
	public ResourcesFromFilesBuilder fromFiles() {
		return new ResourcesFromFilesBuilder() {
			private ProgressHandler progressHandler;
			private List<File> files = new ArrayList<File>();
			
			@Override
			public ResourcesFromFilesBuilder withProgressHandler(ProgressHandler progressHandler) {
				this.progressHandler = progressHandler;
				return this;
			}
			
			@Override
			public Future<List<Resource>> build() {
				return buildFromFiles(files, progressHandler);
			}
			
			@Override
			public ResourcesFromFilesBuilder addSource(File file) {
				files.add(file);
				return this;
			}
			
			@Override
			public ResourcesFromFilesBuilder addFiles(FileList fileList) {
				for (int i = 0; i < fileList.getLength(); i++) {
					File file = fileList.item(i);
					files.add(file);
				}
				return this;
			}
			
			@Override
			public ResourcesFromFilesBuilder addFiles(File... files) {
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					this.files.add(file);
				}
				return this;
			}
			
			@Override
			public ResourcesFromFilesBuilder addFiles(Iterable<File> fileList) {
				for (File file: fileList)
					files.add(file);
				return this;
			}
		};
	}
	
	@Override
	public void abortUpload() {
		//if (xhr != null)
			//xhr.abort();
	}
	
	protected Future<List<Resource>> buildFromFiles(List<File> fileList, ProgressHandler progressHandler) {
		Future<List<Resource>> future = new Future<>();
		session.resources().create().withProgressHandler(progressHandler).store(fileList).andThen(result -> {
			session.merge().adoptUnexposed(true).suspendHistory(true).doFor(result, future);
		}).onError(future::onFailure);
		
		return future;
	}
}
