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
package com.braintribe.web.multipart.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;

import com.braintribe.web.multipart.api.PartHeader;

public class DelegatingPartHeader implements PartHeader {

	private PartHeader delegate;

	@Override
	public String getFileName() {
		return delegate.getFileName();
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public boolean isFile() {
		return delegate.isFile();
	}

	@Override
	public String getContentType() {
		return delegate.getContentType();
	}

	@Override
	public String getFormDataContentDisposition() {
		return delegate.getFormDataContentDisposition();
	}

	@Override
	public Set<String> getFormDataContentDispositionParameterNames() {
		return delegate.getFormDataContentDispositionParameterNames();
	}

	@Override
	public String getFormDataContentDispositionParameter(String name) {
		return delegate.getFormDataContentDispositionParameter(name);
	}

	@Override
	public Set<String> getHeaderNames() {
		return delegate.getHeaderNames();
	}

	@Override
	public String getHeader(String name) {
		return delegate.getHeader(name);
	}

	@Override
	public Collection<String> getHeaders(String name) {
		return delegate.getHeaders(name);
	}
	
	@Override
	public Stream<Entry<String, List<String>>> getHeaders() {
		return delegate.getHeaders();
	}

	public DelegatingPartHeader(PartHeader delegate) {
		this.delegate = delegate;
	}

	@Override
	public String getTransferEncoding() {
		return delegate.getTransferEncoding();
	}

}
