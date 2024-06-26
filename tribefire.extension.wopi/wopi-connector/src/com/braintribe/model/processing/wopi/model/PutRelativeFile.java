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
	"Name":{"type":"string","optional":false},
	"Url":{"type":"string","default":"","optional":false},
	"HostViewUrl":{"type":"string","default":"","optional":true},
	"HostEditUrl":{"type":"string","default":"","optional":true},
	}
 */
public class PutRelativeFile extends HttpResponseJSON {

	private String name;
	private String url;
	private String hostViewUrl;
	private String hostEditUrl;

	public PutRelativeFile(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHostViewUrl() {
		return hostViewUrl;
	}

	public void setHostViewUrl(String hostViewUrl) {
		this.hostViewUrl = hostViewUrl;
	}

	public String getHostEditUrl() {
		return hostEditUrl;
	}

	public void setHostEditUrl(String hostEditUrl) {
		this.hostEditUrl = hostEditUrl;
	}

}
