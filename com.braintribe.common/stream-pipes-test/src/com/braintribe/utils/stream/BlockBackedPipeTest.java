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
package com.braintribe.utils.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.utils.IOTools;
import com.braintribe.utils.stream.api.StreamPipe;
import com.braintribe.utils.stream.pools.CompoundBlockPool;
import com.braintribe.utils.stream.pools.CompoundBlockPoolBuilder;

public class BlockBackedPipeTest {
	private static final String HELLO_WORLD = "Hello, world";
	
	private CompoundBlockPool blockPool;

	@Before
	public void init() {
		blockPool = CompoundBlockPoolBuilder.start() //
				.appendInMemoryBlockPool(10, 1) //
				.appendInMemoryBlockPool(2, 1) //
				.build();
	}

	@Test
	public void testMemoryOnly() throws Exception {

		StreamPipe pipe = blockPool.newPipe("test");

		try (OutputStream os = pipe.openOutputStream()) {
			os.write("Hello, world".getBytes(StandardCharsets.UTF_8));
		}

		byte[] buffer = new byte[1024];
		try (InputStream in = pipe.openInputStream()) {
			int read = IOTools.readFully(in, buffer);
			String result = new String(buffer, 0, read, StandardCharsets.UTF_8);

			assertThat(result).isEqualTo("Hello, world");
		}

	}

	@Test
	public void testOverflow() throws Exception {

		StreamPipe pipe = blockPool.newPipe("test");

		try (OutputStream os = pipe.openOutputStream()) {
			os.write("Hello, world".getBytes(StandardCharsets.UTF_8));
		}

		byte[] buffer = new byte[1024];
		try (InputStream in = pipe.openInputStream()) {
			int read = IOTools.readFully(in, buffer);
			String result = new String(buffer, 0, read, StandardCharsets.UTF_8);

			assertThat(result).isEqualTo("Hello, world");
		}

	}

	@Test
	public void testOverflowExistingInputs() throws Exception {
		blockPool = CompoundBlockPoolBuilder.start() //
				.appendInMemoryBlockPool(10, 1) //
				.appendInMemoryBlockPool(14, 1) //
				.build();

		StreamPipe pipe = blockPool.newPipe("test");

		try (OutputStream os = pipe.openOutputStream()) {
			os.write("Hello, world".getBytes(StandardCharsets.UTF_8));

			byte[] buffer = new byte["Hello, world".length()];
			try (InputStream in = pipe.openInputStream()) {
				int read = IOTools.readFully(in, buffer);
				String result = new String(buffer, 0, read, StandardCharsets.UTF_8);

				assertThat(result).isEqualTo("Hello, world");

				os.write("Hello, world".getBytes(StandardCharsets.UTF_8));

				read = IOTools.readFully(in, buffer);
				result = new String(buffer, 0, read, StandardCharsets.UTF_8);

				assertThat(result).isEqualTo("Hello, world");
			}

		}

	}

	@Test
	public void testNoWrite() throws IOException {
		StreamPipe pipe = blockPool.newPipe("test");

		try (OutputStream os = pipe.openOutputStream()) {
			// just open and close
		}

		try (InputStream in = pipe.openInputStream()) {
			byte[] bytes = IOTools.inputStreamToByteArray(in);
			assertThat(bytes).hasSize(0);
		}
	}
	
	@Test
	public void testMultipleClose() throws IOException {
		StreamPipe pipe = CompoundBlockPoolBuilder.start() //
				.appendFileBlockPool(null, 1)
				.build()
				.newPipe("");
		
		try (OutputStream os = pipe.openOutputStream()) {
			os.close();
			os.close();
			os.write(HELLO_WORLD.getBytes());
			os.close();
			os.close();
		}
		
		try (InputStream in = pipe.openInputStream()) {
			byte[] bytes = IOTools.inputStreamToByteArray(in);
			assertThat(bytes).hasSize(HELLO_WORLD.length());
			in.close();
			in.close();
		}
	}
	
	@Test
	public void testReadWithOffset() throws Exception {
		blockPool = CompoundBlockPoolBuilder.start() //
				.appendInMemoryBlockPool(10, 1) //
				.appendInMemoryBlockPool(14, 1) //
				.build();

		StreamPipe pipe = blockPool.newPipe("test");
		
		try (OutputStream os = pipe.openOutputStream()) {
			os.write("Hello, world".getBytes(StandardCharsets.UTF_8));
			os.flush();
			readAFewBytesWithOffset(pipe); // This uses SupplierBackedSequenceInputStream because the pipe's output stream is NOT closed
		}

		readAFewBytesWithOffset(pipe); // This uses PipeInputStream because the pipe's output stream IS already closed
	}
	
	@Test
	public void testReadSingleBytes() throws Exception {
		File dir = Files.createTempDirectory("block-backed-pipe-test").toFile();

		blockPool = CompoundBlockPoolBuilder.start() //
				.appendInMemoryBlockPool(10, 1) //
				.appendFileBlockPool(dir, 1) //
				.build();
		
		StreamPipe pipe = blockPool.newPipe("test");
		
		try (OutputStream os = pipe.openOutputStream()) {
			for (byte b: HELLO_WORLD.getBytes()) {
				os.write(b);
			}
			os.flush();
			readAFewBytesOneByOne(pipe); // This uses SupplierBackedSequenceInputStream because the pipe's output stream is NOT closed
		}
		
		readAFewBytesOneByOne(pipe); // This uses PipeInputStream because the pipe's output stream IS already closed
	}
	
	@Test
	public void testReadSingleBytesWithFileBlock() throws Exception {
		File dir = Files.createTempDirectory("block-backed-pipe-test").toFile();
		
		blockPool = CompoundBlockPoolBuilder.start() //
				.appendFileBlockPool(dir, 1) //
				.build();
		
		StreamPipe pipe = blockPool.newPipe("test");
		
		try (OutputStream os = pipe.openOutputStream()) {
			for (byte b: HELLO_WORLD.getBytes()) {
				os.write(b);
			}
			os.flush();
			readAFewBytesOneByOne(pipe); // This uses SupplierBackedSequenceInputStream because the pipe's output stream is NOT closed
		}
		
		readAFewBytesOneByOne(pipe); // This uses PipeInputStream because the pipe's output stream IS already closed
	}

	private void readAFewBytesWithOffset(StreamPipe pipe) throws IOException {
		byte[] buffer = new byte[10];
		try (InputStream in = pipe.openInputStream()) {
			int read =  in.read(buffer, 0, 2);
			assertThat(read).isEqualTo(2);
			
			assertThat(buffer[0]).isEqualTo((byte)'H');
			assertThat(buffer[1]).isEqualTo((byte)'e');
			
			read =  in.read(buffer, 2, 8);
			assertThat(read).isEqualTo(8);
			
			assertThat(new String(buffer)).isEqualTo("Hello, wor");
		}
	}
	
	private void readAFewBytesOneByOne(StreamPipe pipe) throws IOException {
		try (InputStream in = pipe.openInputStream()) {
			assertThat(in.read()).isEqualTo((byte)'H');
			assertThat(in.read()).isEqualTo((byte)'e');
			assertThat(in.read()).isEqualTo((byte)'l');
			assertThat(in.read()).isEqualTo((byte)'l');
			assertThat(in.read()).isEqualTo((byte)'o');
			assertThat(in.read()).isEqualTo((byte)',');
			assertThat(in.read()).isEqualTo((byte)' ');
			assertThat(in.read()).isEqualTo((byte)'w');
			assertThat(in.read()).isEqualTo((byte)'o');
			assertThat(in.read()).isEqualTo((byte)'r');
			assertThat(in.read()).isEqualTo((byte)'l');
			assertThat(in.read()).isEqualTo((byte)'d');
			// don't test EOF because that would block in first case when pipe is still open
		}
	}

}
