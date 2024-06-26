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
package com.braintribe.gwt.gme.workbench.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.braintribe.model.folder.Folder;
import com.sencha.gxt.data.shared.TreeStore;

public class BaseNode implements Serializable, TreeStore.TreeNode<BaseNode> {

	private static final long serialVersionUID = -3069831904013659374L;

	private Integer id;
	private String name;
	private Folder folder;
	private List<BaseNode> children = new ArrayList<>();

	protected BaseNode() {
	}

	public BaseNode(Integer id, String name, Folder folder) {
		this.id = id;
		this.name = name;
		this.folder = folder;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public BaseNode getData() {
		return this;
	}
	
	public Folder getFolder() {
		return folder;
	}
	
	@Override
	public List<BaseNode> getChildren() {
		return children;
	}

	public void setChildren(List<BaseNode> children) {
		this.children = children;
	}

	public void addChild(BaseNode child) {
		getChildren().add(child);
	}

	@Override
	public String toString() {
		return name != null ? name : super.toString();
	}

}
