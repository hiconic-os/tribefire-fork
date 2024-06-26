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
package com.braintribe.logging.handler.lumberjack.logpackage;

import java.util.ArrayList;
import java.util.List;

public class CombinedLogPackage {

	protected List<LogPackage> logPackages = new ArrayList<>(10);

	public CombinedLogPackage() {
	}

	public void addLogPackage(LogPackage logPackage) {
		if (logPackage != null) {
			this.logPackages.add(logPackage);
		}
	}

	public boolean isEmpty() {
		return this.logPackages.isEmpty();
	}
	public List<LogPackage> getLogPackages() {
		return this.logPackages;
	}
}
