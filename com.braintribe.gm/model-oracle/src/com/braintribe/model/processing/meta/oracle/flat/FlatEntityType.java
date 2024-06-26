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
package com.braintribe.model.processing.meta.oracle.flat;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.info.GmEntityTypeInfo;

/**
 * @author peter.gazdik
 */
public class FlatEntityType extends FlatCustomType<GmEntityType, GmEntityTypeInfo> {

	private volatile Map<String, FlatProperty> flatProperties;
	private ReentrantLock flatPropertiesLock = new ReentrantLock();

	public FlatEntityType(GmEntityType type, FlatModel flatModel) {
		super(type, flatModel);
	}

	@Override
	public boolean isEntity() {
		return true;
	}

	public Map<String, FlatProperty> acquireFlatProperties() {
		if (flatProperties == null) {
			flatPropertiesLock.lock();
			try {
				if (flatProperties == null) {
					flatProperties = FlatPropertiesFactory.buildFor(this);
				}
			} finally {
				flatPropertiesLock.unlock();
			}
		}

		return flatProperties;
	}

}
