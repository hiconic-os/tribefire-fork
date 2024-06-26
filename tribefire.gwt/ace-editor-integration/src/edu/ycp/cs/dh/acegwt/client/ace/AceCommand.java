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
 * Enumeration for ACE command types.
 */
public enum AceCommand {
	FIND("find"),
	FIND_NEXT("findnext"),
	FIND_PREVIOUS("findprevious"),
	GOTO_LINE("gotoline"),
	REPLACE("replace"),
	REPLACE_ALL("replaceall"),
	SHOW_SETTINGS_MENU("showSettingsMenu"),
	GO_TO_NEXT_ERROR("goToNextError"),
	GO_TO_PREVIOUS_ERROR("goToPreviousError"),
	SELECT_ALL("selectall"),
	CENTER_SELECTION("centerselection"),
	FOLD("fold"),
	UNFOLD("unfold"),
	FOLD_ALL("foldall"),
	UNFOLD_ALL("unfoldall"),
	OVERWRITE("overwrite"),
	GOTO_WORD_LEFT("gotowordleft"),
	GOTO_WORD_RIGHT("gotowordright"),
	TOGGLE_RECORDING("togglerecording"),
	REPLAY_MACRO("replaymacro"),
	REMOVE_LINE("removeline"),
	TOGGLE_COMMENT("togglecomment"),
	TOGGLE_BLOCK_COMMENT("toggleBlockComment"),
	OUTDENT("outdent"),
	INDENT("indent"),
	BLOCK_OUTDENT("blockoutdent"),
	BLOCK_INDENT("blockindent"),
	TO_UPPER_CASE("touppercase"),
	TO_LOWER_CASE("tolowercase"),
	JOIN_LINES("joinlines");

	private final String name;

	private AceCommand(final String name) {
		this.name = name;
	}

	/**
	 * @return the theme name (e.g., "error")
	 */
	public String getName() {
		return name;
	}
}
