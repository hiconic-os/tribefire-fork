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
package tribefire.extension.azure.initializer.wire.contract;

import com.braintribe.model.accessdeployment.smood.SmoodAccess;
import com.braintribe.model.extensiondeployment.check.CheckBundle;
import com.braintribe.model.extensiondeployment.meta.BinaryProcessWith;
import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.azure.model.deployment.AzureBlobBinaryProcessor;
import tribefire.extension.azure.model.deployment.AzureServiceProcessor;

public interface AzureInitializerContract extends WireSpace {

	SmoodAccess defaultAccess();

	AzureBlobBinaryProcessor blobProcessor();

	BinaryProcessWith binaryProcessWith();

	boolean instantiateDefaultAccess();

	CheckBundle connectivityCheckBundle();

	AzureServiceProcessor serviceRequestProcessor();

	ProcessWith serviceProcessWith();

}
