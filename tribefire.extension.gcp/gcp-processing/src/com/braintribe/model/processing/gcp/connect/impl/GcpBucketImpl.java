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

import com.braintribe.model.processing.gcp.connect.GcpBucket;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Bucket.BucketSourceOption;

public class GcpBucketImpl implements GcpBucket {

	private Bucket bucket;
	
	public GcpBucketImpl(Bucket bucket) {
		this.bucket = bucket;
	}
	
	@Override
	public boolean delete() {
		return bucket.delete(BucketSourceOption.metagenerationMatch());
	}

	@Override
	public String getName() {
		return bucket.getName();
	}

}
