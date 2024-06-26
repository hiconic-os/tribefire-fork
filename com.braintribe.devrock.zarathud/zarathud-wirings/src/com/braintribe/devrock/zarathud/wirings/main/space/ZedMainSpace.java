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
package com.braintribe.devrock.zarathud.wirings.main.space;

import com.braintribe.devrock.zarathud.wirings.console.contract.ZedConsoleOutContract;
import com.braintribe.devrock.zarathud.wirings.core.contract.ZedCoreContract;
import com.braintribe.devrock.zarathud.wirings.forensic.contract.ZedForensicsContract;
import com.braintribe.devrock.zarathud.wirings.main.contract.ZedMainContract;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
@Managed
public class ZedMainSpace implements ZedMainContract {
	@Import
	private ZedCoreContract coreContract;
	
	@Import
	private ZedForensicsContract forensicsContract;
	
	@Import
	private ZedConsoleOutContract consoleContract;

	@Override
	public ZedCoreContract coreContract() {
		return coreContract;
	}

	@Override
	public ZedForensicsContract forensicsContract() {
		return forensicsContract;
	}

	@Override
	public ZedConsoleOutContract consoleOutContract() {
		return consoleContract;
	}

	
}
