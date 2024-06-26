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
package com.braintribe.model.processing.zargo.wire.space;


import java.util.function.Supplier;

import com.braintribe.model.processing.xmi.converter.experts.ClasspathResourceStringProvider;
import com.braintribe.model.processing.xmi.converter.wire.contract.XmiConverterContract;
import com.braintribe.model.processing.zargo.MetaModelToZargoConverterWorker;
import com.braintribe.model.processing.zargo.ZargoToMetaModelConverterWorker;
import com.braintribe.model.processing.zargo.wire.contract.WorkerContract;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

/**
 * @author pit
 *
 */
@Managed
public class WorkerSpace implements WorkerContract {
	
	@Import
	XmiConverterContract xmiConverter;
	
	
	@Managed(com.braintribe.wire.api.annotation.Scope.prototype)
	private Supplier<String> templateSupplier(String key) {
		ClasspathResourceStringProvider bean = new ClasspathResourceStringProvider(key);
		return bean;
	}

	@Override
	@Managed
	public MetaModelToZargoConverterWorker metaModelToZargoWorker() {
		MetaModelToZargoConverterWorker bean = new MetaModelToZargoConverterWorker();
		bean.setXmiToMetaModelCodec( xmiConverter.xmiConverter());
		
		bean.setArgoProvider( templateSupplier("templates/argo.argo"));
		bean.setProfileProvider( templateSupplier("templates/argo.profile"));
		bean.setToDoProvider( templateSupplier("templates/argo.todo"));
		bean.setDiagrammProvider( templateSupplier( "templates/argo.pgml"));
		
		return bean;
	}

	@Override
	@Managed
	public ZargoToMetaModelConverterWorker zargoToMetaModelWorker() {
		ZargoToMetaModelConverterWorker bean = new ZargoToMetaModelConverterWorker();
		bean.setXmiToMetaModelCodec( xmiConverter.xmiConverter());			
		return bean;
	}

}
