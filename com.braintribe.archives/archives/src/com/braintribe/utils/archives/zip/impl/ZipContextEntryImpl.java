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
package com.braintribe.utils.archives.zip.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.braintribe.utils.archives.ArchivesException;
import com.braintribe.utils.archives.zip.ZipContextEntry;

/**
 * an implementation of {@link ZipContextEntry}
 * 
 * TODO : change implementation NOT to keep the input streams for each entry, but rather note where to get the input
 * stream.<br/>
 * CAVEAT: take into account that some zip entries have NO input stream (for instance directory entries) and therefore
 * none can be provided for it
 * 
 * @author pit
 *
 */
public class ZipContextEntryImpl implements ZipContextEntry {

	private ZipEntry zipEntry;
	private InputStream stream;

	public ZipContextEntryImpl() {
	}

	public ZipContextEntryImpl(ZipEntry zipEntry, InputStream stream) {
		this.zipEntry = zipEntry;
		this.stream = stream;
	}

	@Override
	public ZipEntry getZipEntry() {
		return zipEntry;
	}

	@Override
	public void setZipEntry(ZipEntry zipEntry) {
		this.zipEntry = zipEntry;
	}

	@Override
	public InputStream getPayload() {
		return stream;
	}

	public void setPayload(InputStream stream) {
		this.stream = stream;
	}
	/**
	 * write an existing entry to the stream
	 * 
	 * @param outstream
	 *            - the {@link ZipOutputStream} to write to
	 * @throws ArchivesException
	 *             - if it cannot be written
	 */
	public void write(ZipOutputStream outstream) throws ArchivesException {
		try {
			outstream.putNextEntry(zipEntry);
			if (stream != null) {
				ArchivesHelper.pump(stream, outstream);
			}
			outstream.closeEntry();
		} catch (IOException e) {
			String msg = String.format("cannot write zip entry name: '[%s]', comment: '[%s]' size: '[%d]' to stream", zipEntry.getName(),
					zipEntry.getComment(), zipEntry.getSize());
			throw new ArchivesException(msg);
		}
	}
}
