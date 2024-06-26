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

public class RemoteAddressInformation {

	protected String directClientAddress;
	protected List<String> xForwardedFor;
	protected List<Forwarded> forwarded;
	protected String xRealIp;
	protected List<String> customIpAddresses;
	

	public String getRemoteIp() {
		
		String candidate = this.directClientAddress;
		
		if (xRealIp != null && xRealIp.trim().length() > 0) {
			candidate = xRealIp;
		}
		
		if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
			candidate = xForwardedFor.get(0);
		}
		
		if (forwarded != null && !forwarded.isEmpty()) {
			Forwarded lastForwarded = forwarded.get(0);
			String forAddress = lastForwarded.getForAddress();
			if (forAddress != null) {
				candidate = forAddress;
			}
		}
		
		if (customIpAddresses != null && !customIpAddresses.isEmpty()) {
			candidate = customIpAddresses.get(0);
		}
		
		return candidate;
	}
	
	
	public String getDirectClientAddress() {
		return directClientAddress;
	}
	public void setDirectClientAddress(String directClientAddress) {
		this.directClientAddress = directClientAddress;
	}
	public List<String> getXForwardedFor() {
		return xForwardedFor;
	}
	public void setXForwardedFor(List<String> xForwardedFor) {
		this.xForwardedFor = xForwardedFor;
	}
	public List<Forwarded> getForwarded() {
		return forwarded;
	}
	public void setForwarded(List<Forwarded> forwarded) {
		this.forwarded = forwarded;
	}
	public String getxRealIp() {
		return xRealIp;
	}
	public void setxRealIp(String xRealIp) {
		this.xRealIp = xRealIp;
	}
	public List<String> getCustomIpAddresses() {
		return customIpAddresses;
	}
	public void setCustomIpAddresses(List<String> customIpAddresses) {
		this.customIpAddresses = customIpAddresses;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Direct address: ");
		sb.append(this.directClientAddress);
		if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
			sb.append(", X-Forwarded-For: ");
			sb.append(this.xForwardedFor);
		}
		if (forwarded != null && !forwarded.isEmpty()) {
			sb.append(", Forwarded: ");
			sb.append(this.forwarded);
		}
		if (xRealIp != null) {
			sb.append(", X-Real-IP: ");
			sb.append(this.xRealIp);
		}
		if (customIpAddresses != null && !customIpAddresses.isEmpty()) {
			sb.append(", Custom IPs: ");
			sb.append(this.customIpAddresses);			
		}
		return sb.toString();
	}
}
