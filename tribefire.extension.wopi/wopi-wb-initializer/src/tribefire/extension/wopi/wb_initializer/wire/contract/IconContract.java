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
package tribefire.extension.wopi.wb_initializer.wire.contract;

import com.braintribe.model.resource.Icon;
import com.braintribe.wire.api.space.WireSpace;

/**
 * <p>
 * For structural reasons icons get their own contract.
 * 
 */
public interface IconContract extends WireSpace {

	Icon logoIcon();

	Icon magnifierIcon();

	Icon tribefireIcon();

	Icon officeIcon();

	Icon adxIcon();

	Icon sessionExpiredIcon();

	Icon addDemoAndTestingDocsIcon();

	Icon wordIcon();
	Icon excelIcon();
	Icon powerpointIcon();

	// -----------------------------------------------------------------------
	// STANDARD ACTION ICONS
	// -----------------------------------------------------------------------

	Icon viewIcon();
	Icon openIcon();
	Icon editIcon();
	Icon deleteIcon();
	Icon assignIcon();
	Icon addIcon();
	Icon removeIcon();
	Icon refreshIcon();
	Icon downloadIcon();
	Icon runIcon();
	Icon homeIcon();
	Icon mailIcon();
	Icon copyIcon();
	Icon changesIcon();
	Icon uploadIcon();
	Icon backIcon();
	Icon nextIcon();
	Icon commitIcon();
	Icon expandIcon();
	Icon fileIcon();
	Icon eventIcon();
	Icon settingsIcon();
	Icon infoIcon();
	Icon pictureIcon();
	Icon codeIcon();
	Icon healthIcon();
	Icon activeIcon();
	Icon inactiveIcon();
	Icon statisticsIcon();
	Icon sessionOpenIcon();
	Icon sessionClosedIcon();
	Icon repositoryTemplateIcon();
	Icon documentationIcon();
	Icon academyIcon();
	Icon publicKeyIcon();
	Icon migrationToolIcon();

	Icon quickAccessIcon();

}
