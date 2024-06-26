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
package com.braintribe.gwt.quickaccess.continuation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class QuickAccessPrefilterPopulation<T> implements Iterable<T> {
	private Iterator<T> populationIt;
	private List<T> prefiltered = new ArrayList<>();
	private Predicate<T> filter;
	
	public QuickAccessPrefilterPopulation(Iterator<T> populationIt, Predicate<T> filter) {
		super();
		this.populationIt = populationIt;
		this.filter = filter;
	}

	@Override
	public Iterator<T> iterator() {
		return new IteratorImpl();
	}
	
	private class IteratorImpl implements Iterator<T> {
		private Iterator<T> it = prefiltered.iterator();
		
		@Override
		public boolean hasNext() {
			if (it != null) {
				boolean hasNext = it.hasNext();
				
				if (hasNext)
					return true;
				else {
					it = null;
					return hasNext();
				}
			}
			else {
				return populationIt.hasNext();
			}
		}

		@Override
		public T next() {
			if (it != null) {
				T element = it.next();
				if (!it.hasNext())
					it = null;
				
				return element;
			}
			else {
				T element = populationIt.next();
				
				if (filter.test(element)) {
					prefiltered.add(element);
					return element;
				}
				else
					return null;
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("not implementated as it makes no sense for this iterator");
		}
	}
	
	public static void main(String[] args) {
		List<String> population = Arrays.asList("Hallo", "Ballo", "Schnallo", "Wurst", "Rallo", "Hub");
		
		Predicate<String> prefilter = new Predicate<String>() {
			@Override
			public boolean test(String obj) {
				return obj.contains("allo");
			}
		};
		QuickAccessPrefilterPopulation<String> prefilteredPopulation = new QuickAccessPrefilterPopulation<>(
				population.iterator(), prefilter);

		for (int i = 0; i < 3; i++) {
			int count = 0;
			System.out.println("---- Run #" + i);
			for (String value: prefilteredPopulation) {
				System.out.println(value);
				if (count > 2 && i == 0)
					break;
				count++;
			}
		}
	}

}
