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
package com.braintribe.gwt.gme.assemblypanel.client;

import java.util.Comparator;

import com.braintribe.gwt.gme.assemblypanel.client.model.AbstractGenericTreeModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class PriorityColumnConfig {
	private static Comparator<PriorityColumnConfig> comparator;
	
	private Double priority;
	private ColumnConfig<AbstractGenericTreeModel, ?> columnConfig;
	
	public PriorityColumnConfig(Double priority, ColumnConfig<AbstractGenericTreeModel, ?> columnConfig) {
		this.priority = priority;
		this.columnConfig = columnConfig;
	}
	
	public Double getPriority() {
		return priority;
	}
	
	public void setPriority(Double priority) {
		this.priority = priority;
	}
	
	public ColumnConfig<AbstractGenericTreeModel, ?> getColumnConfig() {
		return columnConfig;
	}
	
	public void setColumnConfig(ColumnConfig<AbstractGenericTreeModel, ?> columnConfig) {
		this.columnConfig = columnConfig;
	}
	
	public static Comparator<PriorityColumnConfig> getPriorityComparator() {
		if (comparator != null)
			return comparator;
		
		comparator = (o1, o2) -> {
			int priorityComparison;
			if (o1.getPriority() == null && o2.getPriority() == null)
				priorityComparison = 0;
			else if (o1.getPriority() == null && o2.getPriority() != null)
				priorityComparison = 1;
			else if (o1.getPriority() != null && o2.getPriority() == null)
				priorityComparison = -1;
			else {
				priorityComparison = o2.getPriority().compareTo(o1.getPriority());
			}
			if (priorityComparison == 0)
				return o1.getColumnConfig().getHeader().asString().compareToIgnoreCase(o2.getColumnConfig().getHeader().asString());
			return priorityComparison;
		};
		
		return comparator;
	}

}
