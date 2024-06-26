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
package tribefire.cortex.assets.darktheme_wb_initializer.wire.space;

import com.braintribe.model.style.Color;
import com.braintribe.model.style.Font;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.assets.darktheme_wb_initializer.wire.contract.DarkthemeWbUiThemeContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;

@Managed
public class DarkthemeWbUiThemeSpace extends AbstractInitializerSpace implements DarkthemeWbUiThemeContract {
	
	private static final String FONT_FAMILY_GOTHAM = "GothamBook2";
	private static final String FONT_FAMILY_OPENSANS = "Open Sans";

	@Managed
	@Override
	public Color blackColor() {
		return create(Color.T).color("#000000");
	}
	
	@Managed
	@Override
	public Color whiteColor() {
		return create(Color.T).color("#FFFFFF");
	}
	
	@Managed
	@Override
	public Color blueColor() {
		return create(Color.T).color("#0000FF");
	}
	
	@Managed
	@Override
	public Color redColor() {
		return create(Color.T).color("#FF0000");
	}
	
	@Managed
	@Override
	public Color greenColor() {
		return create(Color.T).color("#008000");
	}
	
	@Managed
	@Override
	public Color yellowColor() {
		return create(Color.T).color("#FFFF00");
	}
	
	@Managed
	@Override
	public Color grayColor() {
		return create(Color.T).color("#808080");
	}
	
	@Managed
	@Override
	public Color lightslateGrayColor() {
		return create(Color.T).color("#778899");
	}
	
	@Managed
	@Override
	public Color slateGrayColor() {
		return create(Color.T).color("#708090");
	}
	
	@Managed
	@Override
	public Color gainsboroColor() {
		return create(Color.T).color("#DCDCDC");
	}
	
	@Managed
	@Override
	public Font openSansBlackFont() {
		return Font.T.create(session(), f -> {
			f.setFamily(FONT_FAMILY_OPENSANS);
			f.setColor(blackColor());
		});
	}
	
	@Managed
	@Override
	public Font openSansWhiteFont() {
		return Font.T.create(session(), f -> {
			f.setFamily(FONT_FAMILY_OPENSANS);
			f.setColor(whiteColor());
		});
	}
	
	@Managed
	@Override
	public Font openSansGrayFont() {
		return Font.T.create(session(), f -> {
			f.setFamily(FONT_FAMILY_OPENSANS);
			f.setColor(grayColor());
		});
	}
	
	@Managed
	@Override
	public Font gothamBlackFont() {
		return Font.T.create(session(), f -> {
			f.setFamily(FONT_FAMILY_GOTHAM);
			f.setColor(blackColor());
		});
	}
	
	@Managed
	@Override
	public Font gothamWhiteFont() {
		return Font.T.create(session(), f -> {
			f.setFamily(FONT_FAMILY_GOTHAM);
			f.setColor(whiteColor());
		});
	}
	
	@Managed
	@Override
	public Font gothamGrayFont() {
		return Font.T.create(session(), f -> {
			f.setFamily(FONT_FAMILY_GOTHAM);
			f.setColor(grayColor());
		});
	}
}
