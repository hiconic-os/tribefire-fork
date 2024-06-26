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
package com.braintribe.gwt.modeller.client.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.modellergraph.animation.ModelGraphStateAnimationListener;
import com.braintribe.model.processing.modellergraph.animation.ModelGraphStateTransition;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.Timer;
import com.sencha.gxt.core.shared.FastMap;

public class StandardModelGraphStateAnimator{
	
	private Map<String,ModelGraphStateTransition<GenericEntity>> transitions = new FastMap<>();
	private long durationInMillies;
	
	private long startTime;
	private double intendedAnimationRate;
	private List<ModelGraphStateAnimationListener> modelGraphStateAnimationListeners = new ArrayList<>();
	private Timer timer;
	boolean useTimer = false;
	boolean paused = false;
	
	public StandardModelGraphStateAnimator() {
	
	}
	
	public boolean isAnimating() {
		return false;
	}
		
	public void setDurationInMillies(long durationInMillies) {
		this.durationInMillies = durationInMillies;
	}
	
	public void setIntendedAnimationRate(double intendedAnimationRate) {
		this.intendedAnimationRate = intendedAnimationRate;
	}
	
	public void pauseOrResume() {
		this.paused = !this.paused;
		if(!this.paused)
			animate(null);
	}
		
	public void addListener(ModelGraphStateAnimationListener listener) {
		modelGraphStateAnimationListeners.add(listener);
	}
	
	public void animate(Map<String,ModelGraphStateTransition<GenericEntity>> transitions) {
		if(transitions != null)
			this.transitions.putAll(transitions);
		int time = (int)(1/intendedAnimationRate * 1000);
		startTime = System.currentTimeMillis();
		
		if(useTimer) {		
			if(timer == null){
				timer = new Timer() {				
					@Override
					public void run() {
						handleAnimation(timer);
					}					
				};				
			}
			timer.scheduleRepeating(time);
		}else {
			Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {			
				@Override
				public boolean execute() {
					return handleAnimation(null);
				}
			}, time);
		}
	}
		
	private boolean handleAnimation(Timer timer) {
		if(!paused) {
			double normalizedTime = getNormalizedTime();
//			System.err.println(normalizedTime);
			if(normalizedTime >= 1){
				normalizedTime = 1;			
			}else {
				double normalizedTimeSquared = normalizedTime * normalizedTime;
				double normalizedTimeCubed = normalizedTimeSquared * normalizedTime;			
				normalizedTime = (-2*normalizedTimeCubed + 3*normalizedTimeSquared);
			}					
			
			List<GenericEntity> entitiesToRender = new ArrayList<GenericEntity>();
			for(ModelGraphStateTransition<GenericEntity> transition : transitions.values()){
				entitiesToRender.add(transition.getStateAt(normalizedTime));
			}
			try {
				fireRender(entitiesToRender);
			} catch (Exception e) {
				e.printStackTrace();
				return cancel(timer);
			}
			if(normalizedTime == 1){
				return cancel(timer);
			}else
				return true;		
		}else
			return false;
	}
	
	protected boolean cancel(Timer timer) {
		if(timer != null) timer.cancel();
		fireOnAnimationFinished();
		transitions.clear();
		return false;	
	}
	
	protected double getNormalizedTime() {
		return (double)(System.currentTimeMillis() - startTime) / (double)durationInMillies;
	}
	
	public void dispose(){
		transitions.clear();
		timer = null;
	}
	
	private void fireOnAnimationFinished(){
		modelGraphStateAnimationListeners.forEach(listener -> {
			listener.onAnimationFinished();
		});
	}
	
	private void fireRender(List<GenericEntity> entitiesToRender) {
		modelGraphStateAnimationListeners.forEach(listener -> {
			listener.render(entitiesToRender);
		});
	}
}