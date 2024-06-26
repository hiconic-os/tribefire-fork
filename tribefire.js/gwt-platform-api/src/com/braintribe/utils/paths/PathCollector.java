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
package com.braintribe.utils.paths;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class PathCollector implements Collector<String, StringBuilder, String> {
	
	private char pathDelimiter;
	
	public PathCollector(char pathDelimiter) {
		this.pathDelimiter = pathDelimiter;
	}
	
	@Override
	public Set<Characteristics> characteristics() {
		return Collections.emptySet();
	}
	
	@Override
	public BiConsumer<StringBuilder, String> accumulator() {
		return this::combine;
	}
	
	@Override
	public Supplier<StringBuilder> supplier() {
		return StringBuilder::new;
	}
	
	@Override
	public BinaryOperator<StringBuilder> combiner() {
		return this::combine;
	}
	
	private StringBuilder combine(StringBuilder o1, CharSequence o2) {
		if (o1.length() == 0) {
			o1.append(o2);
		}
		else {
			int appendIndex = getAppendIndex(o1);
			int prependIndex = getPrependIndex(o2);
			
			o1.setLength(appendIndex);
			o1.append(pathDelimiter);
			o1.append(o2, prependIndex, o2.length());
		}

		return o1;
	}
	
	private int getAppendIndex(CharSequence s) {
		int length = s.length();
		
		for (int i = length - 1; i >= 0; i--) {
			if (s.charAt(i) != pathDelimiter)
				return i+1;
		}
		
		return length;
	}
	
	private int getPrependIndex(CharSequence s) {
		int length = s.length();
		
		for (int i = 0; i < length; i++) {
			if (s.charAt(i) != pathDelimiter)
				return i;
		}
		
		return length;
	}
	
	@Override
	public Function<StringBuilder, String> finisher() {
		return StringBuilder::toString;
	}

	public String join(String... tokens) {
		return Stream.of(tokens).collect(this);
	}
}

