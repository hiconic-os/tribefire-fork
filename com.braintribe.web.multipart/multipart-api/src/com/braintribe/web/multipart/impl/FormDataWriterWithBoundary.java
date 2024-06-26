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

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.braintribe.exception.Exceptions;
import com.braintribe.utils.ArrayTools;
import com.braintribe.web.multipart.api.PartHeader;
import com.braintribe.web.multipart.api.PartWriter;
import com.braintribe.web.multipart.streams.BasicDelegateOutputStream;

abstract public class FormDataWriterWithBoundary extends AbstractFormDataWriter {
	private final byte CRLFPlusBoundaryAsBytes[];
	private final String boundary;


	protected FormDataWriterWithBoundary(OutputStream outputStream, String boundary) {
		super(outputStream);

		this.boundary = boundary;

		if (boundary != null) {
			try {
				byte coreBoundaryAsBytes[] = boundary.getBytes("ISO-8859-1");
				byte boundaryAsBytes[] = (byte[]) ArrayTools.merge(MULTIPART_HYPHENS, coreBoundaryAsBytes);
				CRLFPlusBoundaryAsBytes = (byte[]) ArrayTools.merge(HTTP_LINEBREAK, MULTIPART_HYPHENS, coreBoundaryAsBytes);
				out.write(boundaryAsBytes);
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("charset ISO-8859-1 not supported", e);
			} catch (IOException e) {
				throw Exceptions.unchecked(e, "Could not write initial boundary to output stream");
			}
		}else {
			CRLFPlusBoundaryAsBytes = null;
		}
	}
	
	public String getBoundary() {
		return boundary;
	}

	protected void writePartClosingBoundary(OutputStream out) {
		try {
			out.write(CRLFPlusBoundaryAsBytes);
			out.flush();
		} catch (IOException e) {
			throw Exceptions.unchecked(e, "Could not write boundary closing the part");
		}
	}
	
	protected class DirectPartWriter extends DelegatingPartHeader implements PartWriter {

		public DirectPartWriter(PartHeader delegate) {
			super(delegate);
		}

		@Override
		public OutputStream outputStream() {
			return new BasicDelegateOutputStream(out) {
				private boolean closed;

				@Override
				public void close() throws IOException {
					if (!closed) {
						out.flush();
						freeCurrentPartWriter();
						writePartClosingBoundary(out);
						closed = true;
					}
				}
			};
		}

	}

}
