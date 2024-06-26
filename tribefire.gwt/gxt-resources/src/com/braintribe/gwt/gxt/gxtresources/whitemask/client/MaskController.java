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
package com.braintribe.gwt.gxt.gxtresources.whitemask.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;

/**
 * Controller for marking whether a mask should be done with the entire screen opaque.
 */
public class MaskController {
	
	public static boolean maskScreenOpaque = false;
	private static String progressBarImage;
	private static String progressBarTitle = "";
	public static Integer progressBarInitialValue;
	public static Integer progressBarMaxValue;
	
	public static void setProgressMask(boolean opaque, Integer initialValue, Integer maxValue) {
		maskScreenOpaque = opaque;
		progressBarInitialValue = initialValue;
		progressBarMaxValue = maxValue;
	}
	
	public static void setProgressBarTitle(String title) {
		progressBarTitle = title;
		
		Element titleElement = Document.get().getElementById("progressBarTitle");
		if (titleElement == null)
			return;
		
		titleElement.setInnerText(title);
	}
	
	public static void setProgressBarImage(String image) {
		progressBarImage = image;
		
		Element element = Document.get().getElementById("progressBarImage");
		if (element == null)
			return;
		
		ImageElement imageElement = element.cast();
		imageElement.setSrc(image);
	}
	
	public static String getProgressBarTitle() {
		return progressBarTitle;
	}
	
	public static String getProgressBarImage() {
		return progressBarImage;
	}

}
