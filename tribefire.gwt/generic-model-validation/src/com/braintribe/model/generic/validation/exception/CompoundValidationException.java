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
package com.braintribe.model.generic.validation.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class CompoundValidationException extends Throwable implements Iterable<Throwable>{
	
	private List<Throwable> throwableList;
	
	public CompoundValidationException(){
		throwableList = new ArrayList<Throwable>();
	}
	
	public boolean addThrowable(Throwable t){
		return throwableList.add(t);
	}

	@Override
	public Iterator<Throwable> iterator() {
		return throwableList.iterator();
	}
	
	@Override
	public void printStackTrace() {
		for(Throwable t : throwableList){
			t.printStackTrace();
		}
	}
	
	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		for (Throwable t : throwableList) {
			builder.append(t.getMessage()).append("\n");
		}
		
		return builder.toString();
	}

}
