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

/**
 * Interface exposing command line functions used by editor. This way of
 * command line abstraction allows to have different implementations of
 * command line component. It could be GWT TextBox or one-line instance 
 * of ace editor.
 */
public interface AceCommandLine {
	
	/**
	 * Set listener getting callback from command line component. 
	 * Typically editor registers listener itself.
	 * @param listener listener for command entering event
	 */
	public void setCommandLineListener(AceCommandLineListener listener);

	/**
	 * Give current text which command line contains.
	 * @return command stored in command line
	 */
	public String getValue();
	
	/**
	 * Set text into command line. It could be for instance a result of 
	 * command execution.
	 * @param value text to be placed into command line
	 */
	public void setValue(String value);
}
