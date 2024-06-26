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
package com.braintribe.utils.stream;

import com.braintribe.utils.StringTools;
import com.braintribe.utils.stream.RepeatableInputStreamTest.TestInputStream;

/**
 * @author peter.gazdik
 */
public class RepeatableInputStreamThrouputTest_Main {

	public static void main(String[] args) throws Exception {

		int runs = 2;
		long t = 0;
		long readData = 0;
		int maxSourceThroughput = 100; // Mbit per second

		byte[] originalData = RepeatableInputStreamTest.randomData(1024 * 1024 * 20);

		for (int i = runs; i > 0; i--) {

			TestInputStream originalInput = new TestInputStream(originalData, maxSourceThroughput);

			RepeatableInputStream repeatableInput = new RepeatableInputStream(originalInput);

			try {
				long c = System.currentTimeMillis();
				readData = RepeatableInputStreamTest.readBuffered(repeatableInput);
				repeatableInput.close();
				t += System.currentTimeMillis() - c;
			} finally {
				repeatableInput.destroy();
			}

		}

		System.out.println("Consumption of " + StringTools.prettyPrintBytesDecimal(readData) + " bytes limited to " + maxSourceThroughput
				+ " Mbps took in average " + t / runs + "ms");

	}
}
