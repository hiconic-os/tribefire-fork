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
package tribefire.extension.swagger.model_import.wire.space;

import com.braintribe.swagger.ConvertSwaggerModelProcessor;
import com.braintribe.swagger.ExportSwaggerModelProcessor;
import com.braintribe.swagger.ImportSwaggerModelProcessor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * The Deployables Space of Swagger Import Module.
 * 
 */
@Managed
public class SwaggerModelImportDeployablesSpace implements WireSpace {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	/**
	 * Import swagger model processor.
	 *
	 * @return the import swagger model processor
	 */
	@Managed
	public ImportSwaggerModelProcessor importSwaggerModelProcessor() {
		ImportSwaggerModelProcessor bean = new ImportSwaggerModelProcessor();
		return bean;
	}
	
	/**
	 * Export swagger model processor.
	 *
	 * @return the export swagger model processor
	 */
	@Managed
	public ExportSwaggerModelProcessor exportSwaggerModelProcessor() {
		ExportSwaggerModelProcessor bean = new ExportSwaggerModelProcessor();
		bean.setSessionFactory(tfPlatform.systemUserRelated().sessionFactory());
		bean.setEvaluator(tfPlatform.systemUserRelated().evaluator());
		return bean;
	}
	
	/**
	 * Convert swagger model processor.
	 *
	 * @return the convert swagger model processor
	 */
	@Managed
	public ConvertSwaggerModelProcessor convertSwaggerModelProcessor() {
		ConvertSwaggerModelProcessor bean = new ConvertSwaggerModelProcessor();
		return bean;
	}

}
