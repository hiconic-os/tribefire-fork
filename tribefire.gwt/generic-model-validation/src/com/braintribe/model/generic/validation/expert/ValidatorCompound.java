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
package com.braintribe.model.generic.validation.expert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.braintribe.model.meta.data.MetaData;

public class ValidatorCompound implements Iterable<Validator<MetaData>> {
	
	private List<Validator<MetaData>> validatorList = new ArrayList<>();
	
	public void setValidatorList(List<Validator<MetaData>> validatorList) {
		this.validatorList = validatorList;
	}
	
	public List<Validator<MetaData>> getValidatorList() {
		return validatorList;
	}
	
	public boolean add(Validator<MetaData> validator) {
		return this.validatorList.add(validator);
	}
	
	public Validator<MetaData> remove(int index) {
		return this.validatorList.remove(index);
	}
	
	public Validator<MetaData> set(int index, Validator<MetaData> element) {
		return this.validatorList.set(index, element);
	}

	@Override
	public Iterator<Validator<MetaData>> iterator() {
		return validatorList.iterator();		
	}

}
