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
package com.braintribe.util.network;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;

public class NetworkDetectionContext {

	protected NetworkInterface networkInterface;
	protected Inet4Address inet4Address;
	protected Inet6Address inet6Address;
	protected String macAddress;

	public NetworkDetectionContext() {
	}

	public boolean isLoopback() {
		if (this.inet4Address != null) {
			return this.inet4Address.isLoopbackAddress();
		}
		if (this.inet6Address != null) {
			return this.inet6Address.isLoopbackAddress();
		}
		return true;
	}
	public boolean isLinkLocal4() {
		if (this.inet4Address != null) {
			return this.inet4Address.isLinkLocalAddress();
		}
		return false;
	}
	public boolean isLinkLocal6() {
		if (this.inet6Address != null) {
			return this.inet6Address.isLinkLocalAddress();
		}
		return false;
	}

	public boolean isVmNet() {
		if (this.networkInterface == null) {
			return false;
		}

		String name = this.networkInterface.getName();
		if (name == null) {
			return false;
		}
		if (name.toLowerCase().indexOf("vmnet") != -1) {
			return true;
		}
		return false;
	}
	public boolean isOfType(Class<? extends InetAddress> type) {
		if (type == null) {
			return this.inet4Address != null || this.inet6Address != null;
		}
		if (type.isAssignableFrom(Inet4Address.class)) {
			return this.inet4Address != null;
		}
		if (type.isAssignableFrom(Inet6Address.class)) {
			return this.inet6Address != null;
		}
		throw new IllegalArgumentException("Unsupported InetAddress type " + type);
	}

	public NetworkInterface getNetworkInterface() {
		return networkInterface;
	}

	public void setNetworkInterface(NetworkInterface networkInterface) {
		this.networkInterface = networkInterface;
	}

	public Inet4Address getInet4Address() {
		return inet4Address;
	}

	public void setInet4Address(Inet4Address inet4Address) {
		this.inet4Address = inet4Address;
	}

	public Inet6Address getInet6Address() {
		return inet6Address;
	}

	public void setInet6Address(Inet6Address inet6Address) {
		this.inet6Address = inet6Address;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.networkInterface != null) {
			sb.append(this.networkInterface.getName());
		} else {
			sb.append("No network interface.");
		}
		sb.append(";");
		if (this.inet4Address != null) {
			sb.append("IP4:");
			sb.append(this.inet4Address.getHostAddress());
			sb.append(";");
		}
		if (this.inet6Address != null) {
			sb.append("IP6:");
			sb.append(this.inet6Address.getHostAddress());
			sb.append(";");
		}
		if (this.macAddress != null) {
			sb.append("mac:");
			sb.append(this.macAddress);
			sb.append(";");
		}
		sb.append("Loopback:");
		sb.append(this.isLoopback());
		sb.append(";");
		sb.append("Link-local(IPv4):");
		sb.append(this.isLinkLocal4());
		sb.append(";");
		sb.append("Link-local(IPv6):");
		sb.append(this.isLinkLocal6());
		return sb.toString();
	}

}
