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

/**
 * See https://tools.ietf.org/html/rfc7239
 * 
 * @author roman.kurmanowytsch
 */
public class Forwarded {

	protected String forAddress;
	protected String proto;
	protected String byAddress;
	protected String host;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getForAddress() {
		return forAddress;
	}
	public void setForAddress(String forAddress) {
		this.forAddress = forAddress;
	}
	public String getProto() {
		return proto;
	}
	public void setProto(String proto) {
		this.proto = proto;
	}
	public String getByAddress() {
		return byAddress;
	}
	public void setByAddress(String byAddress) {
		this.byAddress = byAddress;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (this.forAddress != null) {
			sb.append("For: ");
			sb.append(this.forAddress);
		}
		if (this.byAddress != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}
			sb.append(", By: ");
			sb.append(this.byAddress);
		}
		if (this.proto != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}
			sb.append("Proto: ");
			sb.append(this.proto);
		}
		if (this.host != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}
			sb.append("Host: ");
			sb.append(this.host);
		}
		
		sb.append("}");
		return sb.toString();
	}
}
