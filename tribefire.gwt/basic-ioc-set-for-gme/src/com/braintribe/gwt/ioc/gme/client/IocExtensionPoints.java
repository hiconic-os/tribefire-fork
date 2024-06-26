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
package com.braintribe.gwt.ioc.gme.client;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import com.braintribe.model.workbench.HyperlinkAction;
import com.braintribe.provider.Holder;


public class IocExtensionPoints {
	
	
	public static IocExtensionPoints INSTANCE = new IocExtensionPoints();
	
	public Supplier<List<HyperlinkAction>> topBannerLinks(){
		List<HyperlinkAction> emptyList = Collections.emptyList();
		return wrapWithHolder(emptyList);
	}
	
	protected  <T> Supplier<T> wrapWithHolder(T value){
		return new Holder<T>(value);
	}
	
}
