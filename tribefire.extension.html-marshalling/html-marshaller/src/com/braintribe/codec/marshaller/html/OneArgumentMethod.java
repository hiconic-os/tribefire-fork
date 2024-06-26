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
package com.braintribe.codec.marshaller.html;

import java.util.List;
import java.util.function.Function;

import freemarker.ext.util.WrapperTemplateModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public interface OneArgumentMethod<T> extends TemplateMethodModelEx {
	@Override
	default Object exec(List arguments) throws TemplateModelException {
		Object firstArgument = unwrap(arguments.get(0));
		
		return execute((T) firstArgument);
	}
	
	Object execute(T argument);
	
	static Object unwrap(Object freemarkerArgument) {
		Object originalObject;
		if (freemarkerArgument instanceof WrapperTemplateModel) {
			WrapperTemplateModel argument = (WrapperTemplateModel) freemarkerArgument;
			originalObject = argument.getWrappedObject();
		} else {
			originalObject = freemarkerArgument;
		}
		return originalObject;
	}
	
	static <T> OneArgumentMethod<T> of(Function<T, Object> impl){
		return impl::apply;
	}
}
