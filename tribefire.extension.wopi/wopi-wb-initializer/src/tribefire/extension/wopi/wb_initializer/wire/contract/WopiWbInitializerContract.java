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

import java.util.List;

import com.braintribe.model.folder.Folder;
import com.braintribe.wire.api.space.WireSpace;

public interface WopiWbInitializerContract extends WireSpace {

	String WOPI_FOLDER = "WOPI";

	// WopiSession
	String WOPI_SESSIONS_FOLDER = "WOPI sessions";
	String OPEN_WOPI_SESSIONS = "Active";
	String EXPIRED_WOPI_SESSIONS = "Expired";
	String CLOSED_WOPI_SESSIONS = "Closed";
	String WOPI_SESSIONS_SEARCH = "Search";
	String WOPI_SESSION_STATISTICS = "Statistics";
	String WOPI_SESSION_OPEN = "Open";
	String WOPI_SESSION_CLOSE = "Close";
	String WOPI_SESSION_CLOSE_ALL = "Close All";
	String WOPI_SESSION_REMOVE = "Remove";
	String WOPI_SESSION_REMOVE_ALL = "Remove All";
	String WOPI_HEALTH_CHECK = "Health";

	// Demo
	String WOPI_DEMO_TESTING_FOLDER = "Demo/Testing Docs";
	String WOPI_DEMO_ADD = "Add";
	String WOPI_DEMO_REMOVE = "Remove";
	String WOPI_ENSURE_TES_DOC = "Ensure Test Doc";

	// Action
	String OPEN_WOPI_DOCUMENT = "Open WOPI document";
	String DOWNLOAD_CURRENT_RESOURCE = "Download Current Resource";
	String CLOSE_WOPI_SESSION = "Close WOPI Session";
	String REMOVE_WOPI_SESSION = "Remove WOPI Session";
	String EXPORT_WOPI_SESSION = "Export WOPI Session";

	Folder entryPointFolder();

	List<Folder> actionbarFolders();

	// -----------------------------------------------------------------------
	// ACTIONBAR
	// -----------------------------------------------------------------------

	Folder openWopiDocumentActionBar();
	Folder downloadCurrentResourceActionBar();
	Folder closeWopiSessionActionBar();
	Folder removeWopiSessionActionBar();
	Folder exportWopiSessionActionBar();

	// -----------------------------------------------------------------------
	// ADMINISTRATION
	// -----------------------------------------------------------------------

	Folder openWopiSessions();
	Folder expiredWopiSessions();
	Folder closedWopiSessions();
	Folder wopiSessionSearch();
	Folder wopiSessionStatistics();
	Folder openWopiSession();
	Folder closeAllWopiSessions();
	Folder removeAllWopiSessions();
	Folder wopiHealthCheck();

	// -----------------------------------------------------------------------
	// DEMO/TESTING
	// -----------------------------------------------------------------------

	Folder addDemoDocs();
	Folder removeDemoDocs();
	Folder ensureTestDoc();
}
