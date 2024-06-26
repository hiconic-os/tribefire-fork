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
package com.braintribe.model.processing.smart.query.planner.context;

import com.braintribe.model.processing.smart.query.planner.graph.SourceNodeGroup.SingleAccessGroup;
import com.braintribe.model.query.Ordering;
import com.braintribe.model.query.Paging;
import com.braintribe.model.query.Restriction;
import com.braintribe.model.query.SelectQuery;

/**
 * 
 * @author peter.gazdik
 */
public class OrderAndPagingManager {

	private final SelectQuery query;
	private final Paging pagination;

	private SingleAccessGroup orderedGroup;

	public OrderAndPagingManager(SelectQuery query) {
		this.query = query;
		this.pagination = extractPagination();
	}

	private Paging extractPagination() {
		Restriction r = query.getRestriction();
		return r != null ? r.getPaging() : null;
	}

	public Ordering getOrdering() {
		return query.getOrdering();
	}

	public Paging getQueryPagination() {
		return pagination;
	}

	public void removePaginationDelegation() {
		if (paginationDelegated())
			orderedGroup.orderAndPagination.limit = orderedGroup.orderAndPagination.offset = null;
	}

	public boolean paginationDelegated() {
		return orderedGroup != null && orderedGroup.orderAndPagination.limit != null;
	}

	/**
	 * If we have an order-by in our query, only a set of the most significant order-by operands can be delegated, those
	 * that all belong to the same {@link SingleAccessGroup}. That group is set here as the ordered-group candidate.
	 * This can, however, be later removed, if the planner decides that it will not start with this group but with some
	 * other group and this one will be DQJ-ed to it. In that case, of course, the smart-evaluator has to do the
	 * sorting. This removing is done using {@link #notifyJoinedGroup(SingleAccessGroup)} method.
	 */
	public void setOrderedGroupCandiate(SingleAccessGroup orderedGroup) {
		this.orderedGroup = orderedGroup;
	}

	/** @see #setOrderedGroupCandiate(SingleAccessGroup) */
	public void notifyJoinedGroup(SingleAccessGroup group) {
		group.disableBatching();

		if (orderedGroup == group) {
			orderedGroup.orderAndPagination = null;
			orderedGroup = null;
		}
	}

	public SingleAccessGroup getOrderedGroup() {
		return orderedGroup;
	}

}
