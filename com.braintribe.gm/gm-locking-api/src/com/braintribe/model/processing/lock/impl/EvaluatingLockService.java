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
package com.braintribe.model.processing.lock.impl;

import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.processing.lock.api.LockService;
import com.braintribe.model.processing.lock.api.LockServiceException;
import com.braintribe.model.processing.lock.api.model.TryLock;
import com.braintribe.model.processing.lock.api.model.Unlock;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.time.TimeSpan;

public class EvaluatingLockService implements LockService {
	
	private final Evaluator<ServiceRequest> evaluator;
	
	public EvaluatingLockService(Evaluator<ServiceRequest> evaluator) {
		this.evaluator = evaluator;
	}

	@Override
	public boolean tryLock(String identification, String holderId, String callerSignature, String machineSignature, boolean exclusive,
			TimeSpan timeout) throws LockServiceException, InterruptedException {
		

		TryLock request = TryLock.T.create();
		request.setCallerSignature(callerSignature);
		request.setExclusive(exclusive);
		request.setHolderId(holderId);
		request.setIdentfication(identification);
		request.setMachineSignature(machineSignature);
		request.setTimeout(timeout);
		
		return request.eval(evaluator).get();
	}

	@Override
	public boolean tryLock(String identification, String holderId, String callerSignature, String machineSignature, boolean exclusive,
			TimeSpan timeout, TimeSpan lockTtl) throws LockServiceException, InterruptedException {

		TryLock request = TryLock.T.create();
		request.setCallerSignature(callerSignature);
		request.setExclusive(exclusive);
		request.setHolderId(holderId);
		request.setIdentfication(identification);
		request.setLockTtl(lockTtl);
		request.setMachineSignature(machineSignature);
		request.setTimeout(timeout);
		
		return request.eval(evaluator).get();
	}

	@Override
	public void unlock(String identification, String holderId, String callerSignature, String machineSignature, boolean exclusive)
			throws LockServiceException {

		Unlock request = Unlock.T.create();
		request.setCallerSignature(callerSignature);
		request.setExclusive(exclusive);
		request.setHolderId(holderId);
		request.setIdentfication(identification);
		request.setMachineSignature(machineSignature);
		
		request.eval(evaluator).get();
	}

}
