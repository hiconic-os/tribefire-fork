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
package com.braintribe.model.processing.gcp.connect;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public interface GcpStorage {

	GcpBucket get(String bucketName);
	GcpBucket getOrCreate(String bucketName);
	int getBucketCount(int max);
	boolean deleteBucket(String bucketName);

	void deleteBlob(String bucketName, String key);

	InputStream openInputStream(String bucketName, String key, Long start) throws IOException;
	WritableByteChannel openWriteChannel(String bucketName, String key, String mimeType);
	ReadableByteChannel openReadChannel(String bucketName, String key, Long start) throws IOException;

}
