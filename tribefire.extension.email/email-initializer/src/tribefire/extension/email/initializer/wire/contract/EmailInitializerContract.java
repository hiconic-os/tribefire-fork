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
package tribefire.extension.email.initializer.wire.contract;

import java.util.Set;

import com.braintribe.gm.model.reason.meta.HttpStatusCode;
import com.braintribe.gm.model.reason.meta.LogReason;
import com.braintribe.model.ddra.DdraMapping;
import com.braintribe.model.email.deployment.service.EmailServiceProcessor;
import com.braintribe.model.extensiondeployment.check.CheckBundle;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.service.domain.ServiceDomain;
import com.braintribe.wire.api.space.WireSpace;

public interface EmailInitializerContract extends WireSpace {

	EmailServiceProcessor emailServiceProcessor();

	MetaData processWithEmailServiceProcessor();

	CheckBundle connectivityCheckBundle();

	Set<DdraMapping> ddraMappings();

	ServiceDomain apiServiceDomain();

	LogReason logReasonTrace();
	HttpStatusCode httpStatus500Md();
	HttpStatusCode httpStatus404Md();
	HttpStatusCode httpStatus501Md();
	HttpStatusCode httpStatus502Md();

}
