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
package com.braintribe.devrock.repolet.launcher.builder.api;

import com.braintribe.devrock.repolet.launcher.builder.cfg.UploadReturnValueOverrideCfg;

public class UploadReturnValueOverrideContext<T extends UploadReturnValueOverrideConsumer> {
	private T consumer;
	private UploadReturnValueOverrideCfg cfg;

	public UploadReturnValueOverrideContext(T consumer) {
		this.consumer = consumer;
		this.cfg = new UploadReturnValueOverrideCfg();
	}
	
	public UploadReturnValueOverrideContext<T> code( String key, Integer value) {
		cfg.getUploadReturnValuesOverride().put(key, value);
		return this;
	}
	
	public T close() {
		this.consumer.accept(cfg);
		return this.consumer;
	}
	

}
