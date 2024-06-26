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
package com.braintribe.web.multipart.streams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;

import com.braintribe.utils.IOTools;

public class BackedUpChunkedOutputStream extends ChunkedOutputStream {
	private final File backupFile;
	private final OutputStream backupOut;
	private final InputStream backupIn;

	public BackedUpChunkedOutputStream(OutputStream delegate, boolean proprietaryMode) {
		this(delegate, IOTools.SIZE_64K, proprietaryMode);
	}

	public BackedUpChunkedOutputStream(OutputStream delegate, int chunkSize, boolean proprietaryMode) {
		super(delegate, chunkSize, proprietaryMode);

		try {
			backupFile = Files.createTempFile("chunked_backup", null).toFile();
			backupOut = new BufferedOutputStream(new FileOutputStream(backupFile));
			backupIn = new BufferedInputStream(new FileInputStream(backupFile));
		} catch (IOException e) {
			throw new UncheckedIOException("Could not create backup file for chunked output stream", e);
		}
	}

	@Override
	protected void addBytesToCurrentChunk(byte[] b, int off, int numOfBytesToWriteInTotal) {
		try {
			backupOut.write(b, off, numOfBytesToWriteInTotal);
		} catch (IOException e) {
			throw new UncheckedIOException("Could not write to backup of chunked output stream", e);
		}
	}

	@Override
	protected void writeOutCurrentChunkContent() throws IOException {
		backupOut.flush();
		
		IOTools.transferBytes(backupIn, delegate, IOTools.BUFFER_SUPPLIER_64K);
	}
	
	@Override
	public void close() throws IOException {
		super.close();
		backupIn.close();
		backupOut.close();
		backupFile.delete();
	}
}
