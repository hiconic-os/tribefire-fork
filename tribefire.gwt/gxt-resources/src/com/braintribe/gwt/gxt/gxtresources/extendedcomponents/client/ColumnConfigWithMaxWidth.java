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
package com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

/**
 * Extension for the ColumnConfig which adds a max width for the column.
 * @author michel.docouto
 *
 */
public class ColumnConfigWithMaxWidth<M, N> extends ColumnConfig <M, N> {
	
	private int maxWidth;

	public ColumnConfigWithMaxWidth(ValueProvider<? super M, N> valueProvider, int width) {
		super(valueProvider, width);
	}
	
	/**
	 * Configures the maximum width for the column.
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	
	public int getMaxWidth() {
		return maxWidth;
	}

}
