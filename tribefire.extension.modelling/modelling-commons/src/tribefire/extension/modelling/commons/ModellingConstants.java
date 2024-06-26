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
package tribefire.extension.modelling.commons;

public interface ModellingConstants {

	//
	// Accesses
	//
	
	String EXT_ID_ACCESS_MANAGEMENT = "access.modelling.management";
	String NAME_ACCESS_MANAGEMENT = "Modelling Management Access";
	
	String EXT_ID_ACCESS_MANAGEMENT_WB = "access.modelling.management.wb";
	String NAME_ACCESS_MANAGEMENT_WB = "Modelling Management Workbench Access";

	String EXT_ID_MODELLING_ACCESS_WB = "access.modelling.wb";
	String NAME_MODELLING_ACCESS_WB = "Modelling Workbench Access";
	
	String EXT_ID_ACCESS_REPOSITORY_CONFIGURATION = "access.repositoryConfiguration";
	
	String EXT_ID_ACCESS_CORTEX = "cortex";
	
	//
	// Models
	//
	
	String NAME_MANAGEMENT_WB_MODEL = "management-workbench-model";
	String NAME_MODELLING_WB_MODEL = "modelling-workbench-model";
	
	String NAME_MODELLING_MODEL = "modelling-model";
	
	String GLOBAL_ID_PROJECT_MODEL = "wire://ModellingCortexModule/ModellingCortexModelsSpace/projectModel";
	
	String GLOBAL_ID_MODELLING_API_MODEL = "model:tribefire.extension.modelling:modelling-api-model";
	
	String NAME_ROOT_MODEL = "com.braintribe.gm:root-model";
	String NAME_ROOT_MODEL_VERSIONED = NAME_ROOT_MODEL + "#1.0";
	
	//
	// Processors
	//
	
	String EXT_ID_MANAGEMENT_PROCESSOR = "processor.modelling.management";
	String NAME_MANAGEMENT_PROCESSOR = "Modelling Management Processor";
	
	String EXT_ID_MODELLING_PROCESSOR = "processor.modelling";
	String NAME_MODELLING_PROCESSOR = "Modelling Processor";
	
	//
	// MANAGEMENT PROCESSOR INTERNALS
	//
	
	String STAGE_NAME_MODELS = "resolved-models";

}
