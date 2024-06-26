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
package com.braintribe.model.processing.gcp.connect.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.stream.StreamSupport;

import com.braintribe.logging.Logger;
import com.braintribe.model.processing.gcp.connect.GcpBucket;
import com.braintribe.model.processing.gcp.connect.GcpStorage;
import com.braintribe.model.processing.session.api.managed.NotFoundException;
import com.google.api.gax.paging.Page;
import com.google.cloud.ReadChannel;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;

public class GcpStorageImpl implements GcpStorage {

	private final static Logger logger = Logger.getLogger(GcpStorageImpl.class);

	private Storage storage;

	public GcpStorageImpl(Storage storage) {
		this.storage = storage;
	}

	@Override
	public GcpBucket get(String bucketName) {
		Bucket bucket = null;
		try {
			bucket = storage.get(bucketName);
			if (bucket != null) {
				return new GcpBucketImpl(bucket);
			}
		} catch (Exception e) {
			logger.error("Could not lookup bucket " + bucketName);
		}
		return null;
	}

	@Override
	public GcpBucket getOrCreate(String bucketName) {

		Bucket bucket = null;
		try {
			bucket = storage.get(bucketName);
			if (bucket != null) {
				return new GcpBucketImpl(bucket);
			}
		} catch (Exception e) {
			logger.error("Could not lookup bucket " + bucketName);
		}
		bucket = storage.create(BucketInfo.of(bucketName));
		return new GcpBucketImpl(bucket);
	}

	@Override
	public void deleteBlob(String bucketName, String key) {
		BlobId blobId = BlobId.of(bucketName, key);
		storage.delete(blobId);
	}

	@Override
	public InputStream openInputStream(String bucketName, String key, Long start) throws IOException {
		Blob blob = storage.get(BlobId.of(bucketName, key));
		if (blob == null) {
			throw new NotFoundException("Could not find the blob " + key + " in bucket " + bucketName);
		}
		ReadChannel reader = blob.reader();
		if (start != null) {
			reader.seek(start);
		}
		InputStream rawInputStream = Channels.newInputStream(reader);
		return rawInputStream;
	}

	@Override
	public WritableByteChannel openWriteChannel(String bucketName, String key, String mimeType) {
		BlobId blobId = BlobId.of(bucketName, key);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
		WriteChannel writer = storage.writer(blobInfo);
		return writer;
	}

	@Override
	public ReadableByteChannel openReadChannel(String bucketName, String key, Long start) throws IOException {
		Blob blob = storage.get(BlobId.of(bucketName, key));
		if (blob == null) {
			throw new NotFoundException("Could not find the blob " + key + " in bucket " + bucketName);
		}
		ReadChannel reader = blob.reader();
		if (start != null && start > 0) {
			reader.seek(start);
		}
		return reader;
	}

	@Override
	public boolean deleteBucket(String bucketName) {
		GcpBucket bucket = get(bucketName);
		if (bucket == null) {
			return true;
		}
		return bucket.delete();
	}

	@Override
	public int getBucketCount(int max) {
		int totalCount = 0;

		Page<Bucket> buckets = storage.list();
		do {
			Iterable<Bucket> blobIterator = buckets.iterateAll();
			long count = StreamSupport.stream(blobIterator.spliterator(), false).count();
			totalCount += (int) count;

			buckets = buckets.getNextPage();

			if (totalCount > max) {
				break;
			}
		} while (buckets != null);
		return totalCount;
	}

}
