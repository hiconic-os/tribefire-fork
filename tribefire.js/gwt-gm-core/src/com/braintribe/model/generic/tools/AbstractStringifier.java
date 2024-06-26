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
package com.braintribe.model.generic.tools;

import com.braintribe.model.generic.GenericEntity;

/**
 * @author peter.gazdik
 */
public abstract class AbstractStringifier {

	protected StringBuilder builder;
	protected String prefix;
	protected String tab;

	protected boolean usedPrefix = false;

	protected AbstractStringifier() {
		this(new StringBuilder(), "", "\t");
	}

	public AbstractStringifier(AbstractStringifier parent) {
		this(parent.builder, parent.prefix, parent.tab);
	}

	public AbstractStringifier(StringBuilder builder, String prefix, String tab) {
		this.builder = builder;
		this.prefix = prefix;
		this.tab = tab;
	}

	protected void levelUp() {
		prefix += "\t";
	}

	protected void levelDown() {
		prefix = prefix.isEmpty() ? prefix : prefix.substring(tab.length());
	}

	protected String getFullClassName(Object object) {
		if (object instanceof GenericEntity)
			return ((GenericEntity) object).entityType().getTypeSignature();
		else
			return object.getClass().getName();
	}

	protected String getSimpleClassName(Object object) {
		if (object instanceof GenericEntity)
			return ((GenericEntity) object).entityType().getShortName();
		else
			return getSimpleName(object.getClass().getName());
	}

	/** Class.getSimpleName is not supported in GWT 2.4! */
	protected String getSimpleName(String name) {
		int index = name.lastIndexOf('.');
		return name.substring(index + 1);
	}

	protected void print(String s) {
		if (usedPrefix) {
			builder.append(s);
		} else {
			builder.append(prefix + s);
			usedPrefix = true;
		}
	}

	protected void println(String s) {
		if (usedPrefix) {
			builder.append(s + "\n");
			usedPrefix = false;
		} else {
			builder.append(prefix + s + "\n");
		}
	}
}
