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
package com.braintribe.model.processing.wopi.model;

import com.braintribe.model.processing.wopi.misc.HttpResponseJSON;

/*
	{
	"FolderName":{"type":"string","optional":false},
	"BreadcrumbBrandIconUrl":{"type":"string","default":"","optional":true},
	"BreadcrumbBrandName":{"type":"string","default":"","optional":true},
	"BreadcrumbBrandUrl":{"type":"string","default":"","optional":true},
	"BreadcrumbDocName":{"type":"string","default":"","optional":true},
	"BreadcrumbDocUrl":{"type":"string","default":"","optional":true},
	"BreadcrumbFolderName":{"type":"string","default":"","optional":true},
	"BreadcrumbFolderUrl":{"type":"string","default":"","optional":true},
	"ClientUrl":{"type":"string","default":"","optional":true},
	"CloseButtonClosesWindow":{"type":"bool","default":false,"optional":true},
	"CloseUrl":{"type":"string","default":"","optional":true},
	"HostAuthenticationId"{"type":"string","default":"","optional":true},
	"HostEditUrl":{"type":"string","default":"","optional":true},
	"HostEmbeddedEditUrl":{"type":"string","default":"","optional":true},
	"HostEmbeddedViewUrl":{"type":"string","default":"","optional":true},
	"HostName":{"type":"string","default":"","optional":true},
	"HostViewUrl":{"type":"string","default":"","optional":true},
	"OwnerId":{"type":"string","optional":false},
	"PresenceProvider"{"type":"string","default":"","optional":true},
	"PresenceUserId"{"type":"string","default":"","optional":true},
	"PrivacyUrl":{"type":"string","default":"","optional":true},
	"SignoutUrl":{"type":"string","default":"","optional":true},
	"SupportsSecureStore":{"type":"bool","default":false,"optional":true},
	"TenantId"{"type":"string","default":"","optional":true},
	"TermsOfUseUrl":{"type":"string","default":"","optional":true},
	"UserCanWrite":{"type":"bool","default":false,"optional":true},
	"UserFriendlyName":{"type":"string","default":"","optional":true},
	"UserId":{"type":"string","default":"","optional":true},
	"WebEditingDisabled":{"type":"bool","default":false,"optional":true},
	}
 */
public class CheckFolderInfo extends HttpResponseJSON {
	// empty by intention
}
