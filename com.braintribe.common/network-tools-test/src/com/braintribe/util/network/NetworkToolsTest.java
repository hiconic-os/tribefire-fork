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

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.time.Instant;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.braintribe.utils.StringTools;
import com.braintribe.utils.date.NanoClock;


public class NetworkToolsTest {

	@BeforeClass
	public static void beforeClass() throws Exception {
		Instant start = NanoClock.INSTANCE.instant();
		NetworkTools.getIPv4NetworkInterface();
		System.out.println("First run: "+StringTools.prettyPrintDuration(start, true, null));

		start = NanoClock.INSTANCE.instant();
		NetworkTools.getIPv4NetworkInterface();
		System.out.println("Second run: "+StringTools.prettyPrintDuration(start, true, null));

	}
	
	@Test
	public void testGetIPv4NetworkInterface() {
		InetAddress inetAddress = NetworkTools.getIPv4NetworkInterface();
		
		assertThat(inetAddress).isNotNull();
		assertThat(inetAddress).isInstanceOf(Inet4Address.class);
	}

	@Test
	public void testGetIPv6NetworkInterface() {
		InetAddress inetAddress = NetworkTools.getIPv6NetworkInterface();
		
		if (inetAddress != null) {
			assertThat(inetAddress).isInstanceOf(Inet6Address.class);
		}
	}
	
	@Test
	public void printNetworks() {
		List<NetworkDetectionContext> networkDetectionContexts = NetworkTools.getNetworkDetectionContexts();
		
		assertThat(networkDetectionContexts).isNotNull();
		assertThat(networkDetectionContexts).isNotEmpty();
		
		for (NetworkDetectionContext context : networkDetectionContexts) {
			System.out.println(context.toString());
		}
	}
}
