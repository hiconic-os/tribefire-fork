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
package edu.ycp.cs.dh.acegwt.client.ace;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Represents an Ace Range Object.
 */
public final class AceRange extends JavaScriptObject {
	
	protected AceRange() {
	}
	
	public static AceRange create(int startRow, int startColumn, int endRow, int endColumn) {
		return toJsObject(startRow, startColumn, endRow, endColumn).cast();
	}

	/**
	 * Detaches both, start and end from this {@link AceRange}.
	 */
	public void detach() {
		detachStart();
		detachEnd();
	}
	
	/**
	 * Detaches the start anchor from this {@link AceRange}.
	 */
	public native void detachStart() /*-{
		if (typeof this.start != 'undefined' && typeof this.start != 'object') {
			this.start.detach();
		}
	}-*/;

	/**
	 * Detaches the end achor from this {@link AceRange}.
	 */
	public native void detachEnd() /*-{
		if (typeof this.end != 'undefined' && typeof this.end != 'object') {
			this.end.detach();
		}
	}-*/;
	
	/**
	 * @return creates a new Range object.
	 */
	static native JavaScriptObject toJsObject(int startRow, int startColumn, int endRow, int endColumn) /*-{
		var Range = $wnd.require('ace/range').Range;
		var range = new Range(startRow, startColumn, endRow, endColumn);
		return range;
	}-*/;
}
