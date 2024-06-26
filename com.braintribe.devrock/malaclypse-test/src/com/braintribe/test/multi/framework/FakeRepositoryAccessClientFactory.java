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
package com.braintribe.test.multi.framework;

import java.util.function.Function;

import com.braintribe.build.artifact.retrieval.multi.retrieval.access.RepositoryAccessClient;
import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.test.multi.framework.fake.direct.DirectRepositoryAccessClient;
import com.braintribe.test.multi.framework.fake.direct.DirectSnapshotRepositoryAccessClient;

public class FakeRepositoryAccessClientFactory extends AbstractFakeClientFactoryBase implements Function<String, RepositoryAccessClient> {

	private Function<String, RepositoryAccessClient> delegate;	
	@Configurable @Required
	public void setDelegate(Function<String, RepositoryAccessClient> delegate) {
		this.delegate = delegate;
	}
	
	
	@Override
	public RepositoryAccessClient apply(String index) throws RuntimeException {
		
		String [] fakeContent = getContentsForKey( index);
		if (fakeContent != null) {
			return new DirectRepositoryAccessClient(index, getExpansive(index), fakeContent);
		}
		SnapshotTuple [] tuples = getTuplesForKey(index);
		if (tuples != null) {
			return new DirectSnapshotRepositoryAccessClient(index, getExpansive(index), tuples);
		}		
		return delegate.apply(index);
		
	}

	

}
