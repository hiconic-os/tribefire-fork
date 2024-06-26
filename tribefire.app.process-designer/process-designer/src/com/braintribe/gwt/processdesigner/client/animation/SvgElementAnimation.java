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
package com.braintribe.gwt.processdesigner.client.animation;

import java.util.HashMap;
import java.util.Map;

import com.braintribe.processing.async.api.AsyncCallback;
import com.google.gwt.user.client.Timer;

public class SvgElementAnimation {
	
	private static final SvgElementAnimation instance = new SvgElementAnimation();
	
	public static SvgElementAnimation getInstance() {
		return instance;
	}
	
	private Map<Object, SvgElementAnimationContext> animators = new HashMap<>();
	
	private SvgElementAnimation(){
		
	}
	
	public SvgElementAnimationContext startAnimation(final Object elementToAnimate, String attribute, double start, double end, long duration, long frequency){
		return startAnimation(elementToAnimate, attribute, start, end, duration, frequency, null);
	}
	
	public SvgElementAnimationContext startAnimation(final Object elementToAnimate, String attribute, double start, double end, long duration, long frequency,final AsyncCallback<Void> callback){
		SvgElementAnimationContext context = animators.get(elementToAnimate);
		if(context == null){		
			context = new SvgElementAnimationContext();
			final SvgElementAnimationContext innerContext = context;
			
			context.timer = new Timer() {
				@Override
				public void run() {
					try{
						double normalizedTime = SvgElementAnimation.getNormalizedTime(innerContext);
						if(normalizedTime >= 1){
							normalizedTime = 1;
							cancel();
							if(callback != null)
								callback.onSuccess(null);
						}
						
						double normalizedTimeSquared = normalizedTime * normalizedTime;
						double normalizedTimeCubed = normalizedTimeSquared * normalizedTime;
						
						normalizedTime = (-2*normalizedTimeCubed + 3*normalizedTimeSquared);
						
						innerContext.adapt(normalizedTime);
					}catch(Exception ex){
						if(callback != null)
							callback.onFailure(null);
					}
					
				}

			};
			
			context.element = elementToAnimate;
			context.attribute = attribute;
			context.startValue = start;
			context.currentValue = start;
			
			
			animators.put(elementToAnimate, context);
		
		}else{
			context.startValue = context.currentValue;
			context.timer.cancel();
		}
		
		context.endValue = end;
		context.startTime = System.currentTimeMillis();
		context.freqency = frequency;
		context.durationTime = duration;
		context.timer.scheduleRepeating((int)(((double)1/frequency) * 1000));
		return context;
	}
	
	public static double getNormalizedTime(SvgElementAnimationContext context) {
		long currentTime = System.currentTimeMillis();
		long delta = currentTime - context.startTime;
		double normalizedTime = delta / (double)context.durationTime;
//		System.err.println(normalizedTime);
		return normalizedTime;
	}
	

}
