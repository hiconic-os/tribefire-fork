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
package com.braintribe.model.access.smood.distributed.test.concurrent.worker;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.access.smood.distributed.test.wire.contract.DistributedSmoodAccessTestContract;


public class WorkerFactory {

	protected int iterations = -1;
	protected DistributedSmoodAccessTestContract space = null;

	public Worker provideWorker() {
		Worker worker = new Worker(this.iterations, this.space);
		return worker;
	}

	@Configurable
	@Required
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	public int getIterations() {
		return iterations;
	}

	public DistributedSmoodAccessTestContract getSpace() {
		return space;
	}
	@Configurable
	@Required
	public void setSpace(DistributedSmoodAccessTestContract space) {
		this.space = space;
	}

}
