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
package com.braintribe.model.processing.aop.impl.context;

import java.util.Collections;
import java.util.List;

import com.braintribe.model.processing.aop.api.aspect.Advice;
import com.braintribe.model.processing.aop.api.aspect.Caller;
import com.braintribe.model.processing.aop.api.context.AroundContext;
import com.braintribe.model.processing.aop.api.interceptor.AroundInterceptor;

/**
 * @author gunther.schenk
 */
public class AroundContextImpl<I,O> extends AbstractContextImpl<I> implements AroundContext<I, O>{
	
	private List<AroundInterceptor<I,O>> interceptors;
	private Caller<I, O> actualCaller;
	private int interceptorIndex = 0;
	private boolean isRequestOverridden = false;

	public void setInterceptorIndex(int interceptorIndex) {
		this.interceptorIndex = interceptorIndex;
	}
	
	// **************************************************************************
	// Interface methods
	// **************************************************************************

	@Override
	public O proceed(I request) {
		overrideRequest(request);
		return proceed();
	}
	
	@Override
	public O proceed() {
		O result = null;
		if (interceptorIndex < getInterceptors().size()) {
			AroundInterceptor<I, O> interceptor = getInterceptors().get(interceptorIndex);
			try {
				interceptorIndex++;
				result = interceptor.run(this);
				commitIfNecessary();
			} finally {
				interceptorIndex--;
			}
		} else {
			result = actualCaller.call(request, this);
		}
		
		return result;
	}

	public List<AroundInterceptor<I, O>> getInterceptors() {
		if (interceptors == null)
			return Collections.emptyList();
		return interceptors;
	}
	
	public void setInterceptors(List<AroundInterceptor<I, O>> interceptors) {
		this.interceptors = interceptors;
		
	}
	
	public void setActualCaller(Caller<I, O> caller) {
		this.actualCaller = caller;
	}

	@Override
	public Advice getAdvice() {
		return Advice.around;
	}

	@Override
	public void overrideRequest(I request) {
		this.request = request;
		this.isRequestOverridden = true;
	}
	
	@Override
	public String getProceedIdentification() {
		return String.valueOf(interceptorIndex);
	}
	
	public boolean isRequestOverridden() {
		return isRequestOverridden;
	}
}
