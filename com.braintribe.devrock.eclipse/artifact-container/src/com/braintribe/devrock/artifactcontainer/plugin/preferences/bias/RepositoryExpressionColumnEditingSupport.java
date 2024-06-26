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
package com.braintribe.devrock.artifactcontainer.plugin.preferences.bias;

import org.eclipse.jface.viewers.TableViewer;

public class RepositoryExpressionColumnEditingSupport extends AbstractColumnEditingSupport {
	
	private TableViewer viewer;
	
	public RepositoryExpressionColumnEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected Object getValue(Object element) {
		RepositoryExpression expression = (RepositoryExpression) element;
		return expression.getId();			
	}

	@Override
	protected void setValue(Object element, Object value) {
		RepositoryExpression expression = (RepositoryExpression) element;
		expression.setId( (String) value);		
		viewer.refresh( element);

	}

}
