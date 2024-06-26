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
package tribefire.extension.process.model.data.tracing;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;


public interface ExceptionTrace extends GenericEntity {

	EntityType<ExceptionTrace> T = EntityTypes.T(ExceptionTrace.class);

	String exception = "exception";
	String message = "message";
	String stackTrace = "stackTrace";
	
	String getException();
	void setException(String exception);

	String getMessage();
	void setMessage(String message);

	String getStackTrace();
	void setStackTrace(String stackTrace);

}
