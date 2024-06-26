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
package com.braintribe.gwt.genericmodelgxtsupport.client.field.font;

import java.util.ArrayList;
import java.util.List;

public class FontData {

	public static List<FontSelection> getFontFamily() {
	    List<FontSelection> fontFamily = new ArrayList<FontSelection>();
		fontFamily.add(new FontSelection("10", "Arial"));
		fontFamily.add(new FontSelection("20", "Arial Black"));
		fontFamily.add(new FontSelection("30", "Comic Sans MS"));
		fontFamily.add(new FontSelection("40", "Courier New"));
		fontFamily.add(new FontSelection("50", "Georgia"));
		fontFamily.add(new FontSelection("60", "Helvetica"));
		fontFamily.add(new FontSelection("70", "Impact"));
		fontFamily.add(new FontSelection("80", "Lucida Sans Unicode"));
		fontFamily.add(new FontSelection("90", "Lucida Console"));		
		fontFamily.add(new FontSelection("100", "Palatino Linotype"));
		fontFamily.add(new FontSelection("110", "Tahoma"));
		fontFamily.add(new FontSelection("120", "Times New Roman"));
		fontFamily.add(new FontSelection("130", "Trebuchet MS"));
		fontFamily.add(new FontSelection("140", "Verdana"));				
		return fontFamily;
	}
	
	public static List<FontSelection> getFontSize() {
	    List<FontSelection> fontSize = new ArrayList<FontSelection>();
		fontSize.add(new FontSelection("10", "10%"));
		fontSize.add(new FontSelection("20", "20%"));
		fontSize.add(new FontSelection("30", "30%"));				
		fontSize.add(new FontSelection("40", "40%"));				
		fontSize.add(new FontSelection("50", "50%"));				
		fontSize.add(new FontSelection("60", "60%"));				
		fontSize.add(new FontSelection("70", "70%"));				
		fontSize.add(new FontSelection("80", "80%"));				
		fontSize.add(new FontSelection("90", "90%"));				
		fontSize.add(new FontSelection("100", "100%"));				
		fontSize.add(new FontSelection("200", "200%"));				
		fontSize.add(new FontSelection("400", "400%"));				
		//fontSize.add(new FontSelection("800", "800%"));				
		fontSize.add(new FontSelection("1000", "xx-small"));
		fontSize.add(new FontSelection("1010", "x-small"));
		fontSize.add(new FontSelection("1020", "small"));
		fontSize.add(new FontSelection("1030", "smaller"));
		fontSize.add(new FontSelection("1040", "medium"));
		fontSize.add(new FontSelection("1050", "larger"));		
		fontSize.add(new FontSelection("1060", "large"));
		fontSize.add(new FontSelection("1070", "x-large"));
		fontSize.add(new FontSelection("1080", "xx-large"));
		//fontSize.add(new FontSelection("1090", "initial"));
		//fontSize.add(new FontSelection("1100", "inherit"));
		return fontSize;
	}	
	
	public static List<FontSelection> getFontStyle() {
	    List<FontSelection> fontStyle = new ArrayList<FontSelection>();
		fontStyle.add(new FontSelection("10", "normal"));
		fontStyle.add(new FontSelection("20", "italic"));
		fontStyle.add(new FontSelection("30", "oblique"));
		return fontStyle;
	}
	
	public static List<FontSelection> getFontWeight() {
	    List<FontSelection> fontWeight = new ArrayList<FontSelection>();
		fontWeight.add(new FontSelection("10", "normal"));
		fontWeight.add(new FontSelection("20", "bold"));
		//fontWeight.add(new FontSelection("100", "100"));
		//fontWeight.add(new FontSelection("200", "200"));
		//fontWeight.add(new FontSelection("300", "300"));
		//fontWeight.add(new FontSelection("400", "400"));
		//fontWeight.add(new FontSelection("500", "500"));
		//fontWeight.add(new FontSelection("600", "600"));
		//fontWeight.add(new FontSelection("700", "700"));
		//fontWeight.add(new FontSelection("800", "800"));
		//fontWeight.add(new FontSelection("900", "900"));		
		//fontWeight.add(new FontSelection("120", "bolder"));
		//fontWeight.add(new FontSelection("130", "lighter"));
		//fontWeight.add(new FontSelection("140", "initial"));				
		//fontWeight.add(new FontSelection("150", "inherit"));				
		return fontWeight;
	}
}
