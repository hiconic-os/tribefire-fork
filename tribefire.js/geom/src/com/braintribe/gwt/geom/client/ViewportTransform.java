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
package com.braintribe.gwt.geom.client;

/**
 * This class can map points and rectangles from one coordinate space
 * to another. The two coordinate spaces are defined by to rectangles
 * named window (from) and viewport (to).
 * 
 * The resulting transformation can get retrieved via {@link #getAffineTransform()}
 * 
 * @author Dirk
 *
 */
public class ViewportTransform {
	private Rect viewport;
	private Rect window;
	private AffineTransform affineTransform;
	
	private ViewportTransform inverse;

	protected ViewportTransform(Rect window, Rect viewport, ViewportTransform inverse) {
		this(window, viewport);
		this.inverse = inverse;
	}
	
	public ViewportTransform(Rect window, Rect viewport) {
		super();
		this.viewport = viewport;
		this.window = window;


		double sx = viewport.getWidth() / window.getWidth();
		double sy = viewport.getHeight() / window.getHeight();
		
		double tx = viewport.getX() - window.getX() * sx;
		double ty = viewport.getY() - window.getY() * sy;
		
		affineTransform = AffineTransform.getTranslateInstance(tx, ty);
		affineTransform.scale(sx, sy);
	}

	public Point transform(Point p) {
		return affineTransform.transform(p, null);
	}
	
	public Rect transform(Rect r) {
		return new Rect(transform(r.getP1()), transform(r.getP2()));
	}
	
	public double transformDistance(double dinstanceToTransform){
		Rect distanceRect = new Rect(0, 0, dinstanceToTransform, 0);
		Rect transformedRect = transform(distanceRect);
		double width = transformedRect.getWidth();
		double height = transformedRect.getHeight();
		return Math.sqrt(width * width + height * height);
	}
	
	public AffineTransform getAffineTransform() {
		return affineTransform;
	}
	
	public ViewportTransform getInverse() {
		if (inverse == null) {
			inverse = new ViewportTransform(viewport, window, this);
		}
		return inverse;
	}
	
	public double getScaleX() {
		return affineTransform.getScaleX();
	}
	
	public double getScaleY() {
		return affineTransform.getScaleY();
	}
	
	public double getTranslationX() {
		return affineTransform.getTranslateX();
	}
	
	public double getTranslationY() {
		return affineTransform.getTranslateY();
	}

	public Rect getWindow() {
		return window;
	}

	public Rect getViewport() {
		return viewport;
	}
}
