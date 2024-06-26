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
package tribefire.extension.demo.model.data;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@SelectiveInformation("${date} - ${info}")
public interface AuditRecord extends GenericEntity {
	
	
	final EntityType<AuditRecord> T = EntityTypes.T(AuditRecord.class);
	
	// TODO: Dynamic initializer now() currently removed because fo causing an issue in the produced cortex manipulation stack. 
	// (issue after cartridge sync and restart)
	//@Initializer("now()")
	Date getDate();
	//@Initializer("now()")
	void setDate(Date date);
	
	@Mandatory
	String getInfo();
	void setInfo(String info);
}
