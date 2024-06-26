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
package com.braintribe.common.db;

import com.braintribe.processing.test.db.derby.DerbyServerControl;

/**
 * @author peter.gazdik
 */
public class DbTestSupport {

	private static DerbyServerControl derbyServerControl;

	public static void startDerby() {
		if (derbyServerControl != null) {
			throw new IllegalStateException("Cannot start Derby as it is already running.");
		}

		derbyServerControl = new DerbyServerControl();
		try {
			derbyServerControl.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void shutdownDerby() {
		if (derbyServerControl != null) {
			try {
				derbyServerControl.stop();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			derbyServerControl = null;
		}
	}

}
