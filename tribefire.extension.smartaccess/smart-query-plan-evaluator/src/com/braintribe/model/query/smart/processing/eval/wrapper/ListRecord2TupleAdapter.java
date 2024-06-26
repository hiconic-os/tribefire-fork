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
package com.braintribe.model.query.smart.processing.eval.wrapper;

import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.record.ListRecord;

public class ListRecord2TupleAdapter implements Tuple {

	private final ListRecord listRecord;
	private final int maxIndex;

	public ListRecord2TupleAdapter(ListRecord listRecord) {
		this.listRecord = listRecord;
		this.maxIndex = listRecord.getValues().size();
	}

	@Override
	public Object getValue(int index) {
		if ((index >= 0) && (index < maxIndex)) {
			return listRecord.getValues().get(index);

		}

		return null;
	}

	@Override
	public Tuple detachedCopy() {
		DetachedTuple result = new DetachedTuple(this.listRecord);
		return result;
	}

	@Override
	public String toString() {
		return "Tuple" + (listRecord.getValues());
	}

	protected static class DetachedTuple extends ListRecord2TupleAdapter {
		DetachedTuple(ListRecord record) {
			super(record);
		}

		@Override
		public Tuple detachedCopy() {
			return this;
		}
	}

}
