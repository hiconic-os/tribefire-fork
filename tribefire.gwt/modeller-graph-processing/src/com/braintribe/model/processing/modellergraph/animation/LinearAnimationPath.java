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
package com.braintribe.model.processing.modellergraph.animation;

public class LinearAnimationPath {
	
	private ModelGraphStateAnimationVector start;
	//private ModelGraphStateAnimationVector end;
	private ModelGraphStateAnimationVector direction;
	
	public LinearAnimationPath(ModelGraphStateAnimationVector start, ModelGraphStateAnimationVector end) {
		this.start = start;
		//this.end = end;
		this.direction = end.minus(start);
	}
	
	public ModelGraphStateAnimationVector getPointAt(double normalizedTime){
		return start.plus(direction.times(normalizedTime));	
	}

}
