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
package com.braintribe.model.processing.modellergraph.common;

import java.util.ArrayList;
import java.util.List;


public abstract class ArrowTools {
	public static List<Complex> createArrowPath(Complex tip, Complex orientation, double width, double length) {
		List<Complex> points = new ArrayList<Complex>(3);
		
		points.add(tip);
		
		Complex normalizedOrientation = orientation.normalize();
		
		Complex scaledOrientation = normalizedOrientation.times(-length);
		Complex scaledPerpendicularOrientation = normalizedOrientation.perpendicular().times(width / 2);
		Complex bottom = tip.plus(scaledOrientation);
		Complex left = bottom.plus(scaledPerpendicularOrientation);
		Complex right = bottom.minus(scaledPerpendicularOrientation);
		
		points.add(left);
		points.add(right);
		
		return points; 
	}
}
