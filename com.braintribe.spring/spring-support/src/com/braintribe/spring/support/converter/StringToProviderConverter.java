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
package com.braintribe.spring.support.converter;

import java.util.function.Supplier;

import org.springframework.core.convert.converter.Converter;

import com.braintribe.provider.Holder;

/**
 * A Spring {@link Converter} that converts a String to a {@link Supplier} of the same String.
 * 
 * 
 */
public class StringToProviderConverter implements Converter<String, Supplier<String>> {

	@Override
	public Supplier<String> convert(final String source) {
		if (source == null) {
			return null;
		}

		final Holder<String> holder = new Holder<String>();
		holder.accept(source);
		return holder;
	}
}
