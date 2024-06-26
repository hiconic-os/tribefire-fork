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
package tribefire.extension.gcp.initializer.wire.contract;

import com.braintribe.model.extensiondeployment.check.CheckBundle;
import com.braintribe.model.extensiondeployment.meta.BinaryProcessWith;
import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.model.gcp.deployment.GcpConnector;
import com.braintribe.model.gcp.deployment.GcpServiceProcessor;
import com.braintribe.model.gcp.deployment.GcpStorageBinaryProcessor;
import com.braintribe.wire.api.space.WireSpace;

/**
 * <p>
 * This {@link WireSpace Wire contract} exposes our custom instances (e.g. instances of our deployables).
 * </p>
 */
public interface GcpInitializerModuleContract extends WireSpace {

	GcpStorageBinaryProcessor gcpDefaultStorageBinaryProcessor();

	GcpServiceProcessor serviceRequestProcessor();

	GcpConnector gcpDefaultConnector();

	CheckBundle functionalCheckBundle();

	BinaryProcessWith binaryProcessWith();

	ProcessWith serviceProcessWith();
}
