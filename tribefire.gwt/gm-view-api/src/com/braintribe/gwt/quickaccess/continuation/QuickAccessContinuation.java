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
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.braintribe.filter.pattern.PatternMatcher;
import com.braintribe.filter.pattern.Range;
import com.braintribe.gwt.async.client.CanceledException;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.quickaccess.api.QuickAccessContinuationListener;
import com.braintribe.gwt.quickaccess.api.QuickAccessHit;
import com.braintribe.gwt.quickaccess.api.QuickAccessHitReason;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class QuickAccessContinuation<T> implements RepeatingCommand {
	private Iterator<T> it;
	private Predicate<T> prefilter;
	private QuickAccessContinuationListener<T> listener;
	private Iterable<? extends PatternMatcher> patternMatchers;
	private Iterable<Function<T, String>> stringRepresentationProviders;
	private int maxProcessTimeInMs = 20;
	private String userInput;
	private boolean cancel;
	private Future<Void> future;
	private Iterator<QuickAccessHit<T>> notifications;
	private QuickAccessPrefilterPopulation<T> prefilteredPopulation;
	
	public QuickAccessContinuation(Iterator<T> it, Predicate<T> prefilter, Iterable<Function<T, String>> stringRepresentationProviders,
			QuickAccessContinuationListener<T> listener, Iterable<? extends PatternMatcher> patternMatchers) {
		this.prefilter = prefilter;
		this.stringRepresentationProviders = stringRepresentationProviders;
		this.listener = listener;
		this.patternMatchers = patternMatchers;
		this.prefilteredPopulation = new QuickAccessPrefilterPopulation<T>(it, prefilter);
	}
	
	public void setIterator(Iterator<T> it) {
		this.it = it;
	}
	
	public void resetPreFilter(Iterator<T> it) {
		this.prefilteredPopulation = new QuickAccessPrefilterPopulation<T>(it, prefilter);
	}
	
	@Override
	public boolean execute() {
		if (cancel) {
			future.onFailure(new CanceledException());
			return false;
		}

		long s = System.currentTimeMillis();
		while (true) {
			if (doStep()) {
				long d = System.currentTimeMillis() - s;
				
				if (d > maxProcessTimeInMs) {
					return true;
				}
			}
			else {
				future.onSuccess(null);
				return false;
			}
		}
	}
	
	private boolean doStep() {
		return notifications != null?
				notifyHit(): search();
	}
	
	private boolean notifyHit() {
		if (!notifications.hasNext()) {
			notifications = null;
			return true;
		}
		
		QuickAccessHit<T> hit = notifications.next();
		listener.onHit(hit);
		return true;
	}
	
	private boolean search() {
		if (!it.hasNext())
			return false;
		
		T element = it.next();
		
		if (element == null)
			return true;
		
		List<QuickAccessHit<T>> hits = null;
		List<QuickAccessHitReason<T>> reasons = null;
		for (PatternMatcher matcher: patternMatchers) {
			for (Function<T, String> stringRepresentationProvider: stringRepresentationProviders) {
				String stringRepresentation = stringRepresentationProvider.apply(element);
				List<Range> ranges = matcher.matches(userInput, stringRepresentation);
				if (ranges != null && !ranges.isEmpty()) {
					if (reasons == null)
						reasons = new ArrayList<>();
						
					reasons.add(new QuickAccessHitReasonImpl<>(stringRepresentationProvider, ranges));
				}
			}
		}
		if (reasons != null && !reasons.isEmpty()) {
			QuickAccessHitImpl<T> hit = new QuickAccessHitImpl<>(reasons, element);
			hits = new ArrayList<>();
			hits.add(hit);
		}
		
		if (hits != null)
			notifications = hits.iterator();
			
		return true;
	}
	
	public Future<Void> start(String input) {
		this.cancel = false;
		this.userInput = input;
		this.it = this.prefilteredPopulation.iterator();
		this.future = new Future<>();
		Scheduler.get().scheduleIncremental(this);
		return this.future;
	}
	
	public void cancel() {
		this.cancel = true;
	}
	
	public String getUserInput() {
		return userInput;
	}
}
