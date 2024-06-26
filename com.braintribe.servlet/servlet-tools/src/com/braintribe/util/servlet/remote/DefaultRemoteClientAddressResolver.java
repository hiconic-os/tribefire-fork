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
package com.braintribe.util.servlet.remote;

import java.util.List;

public class DefaultRemoteClientAddressResolver {

	protected static StandardRemoteClientAddressResolver resolver = new StandardRemoteClientAddressResolver(); 
	
	public static StandardRemoteClientAddressResolver getDefaultResolver() {
		return resolver;
	}
	
	public void setIncludeXForwardedFor(boolean includeXForwardedFor) {
		resolver.includeXForwardedFor = includeXForwardedFor;
	}
	public void setIncludeForwarded(boolean includeForwarded) {
		resolver.includeForwarded = includeForwarded;
	}
	public void setIncludeXRealIp(boolean includeXRealIp) {
		resolver.includeXRealIp = includeXRealIp;
	}
	public void setSourceWhiteList(List<String> sourceWhiteList) {
		resolver.sourceWhiteList = sourceWhiteList;
	}
	public void setLenientParsing(boolean lenientParsing) {
		resolver.lenientParsing = lenientParsing;
	}
}
