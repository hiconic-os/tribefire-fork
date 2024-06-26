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

import java.util.Date;

import com.braintribe.model.resource.Resource;
import com.braintribe.model.uitheme.UiTheme;
import com.braintribe.model.workbench.UiThemeTemplateSource;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.assets.darktheme_wb_initializer.wire.contract.DarkthemeWbIconContract;
import tribefire.cortex.assets.darktheme_wb_initializer.wire.contract.DarkthemeWbResourceContract;
import tribefire.cortex.assets.darktheme_wb_initializer.wire.contract.DarkthemeWbStyleContract;
import tribefire.cortex.assets.darktheme_wb_initializer.wire.contract.DarkthemeWbUiThemeContract;
import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;

@Managed
public class DarkthemeWbStyleSpace extends AbstractInitializerSpace implements DarkthemeWbStyleContract {

	@Import
	private DarkthemeWbUiThemeContract uiTheme;
	
	@Import
	private DarkthemeWbResourceContract resource;

	@Import
	private DarkthemeWbIconContract icon;
	
	@Import
	private DefaultWbContract workbench;
	
	@Override
	public DefaultWbContract workbenchContract() {
		return workbench;
	}
	
	@Override
	public DarkthemeWbIconContract iconContract() {
		return icon;
	}
	
	@Managed
	private UiTheme uiTheme() {
		UiTheme bean = create(UiTheme.T);
		
		bean.setSelectColor(uiTheme.lightslateGrayColor());
		bean.setSelectInactiveColor(uiTheme.gainsboroColor());
		bean.setHooverColor(uiTheme.slateGrayColor());
		bean.setBasicFont(uiTheme.gothamBlackFont());
		bean.setCaptionFont(uiTheme.gothamGrayFont());
		bean.setHeaderFont(uiTheme.gothamGrayFont());
		bean.setMenuFont(uiTheme.gothamBlackFont());
		bean.setTabFont(uiTheme.gothamWhiteFont());
		bean.setTetherFont(uiTheme.gothamBlackFont());
				
		return bean;
	}
	
	@Managed
	@Override
	public Resource styleSheet() {
		Resource bean = create(Resource.T);
		
		bean.setName("dynamic-stylesheet");
		bean.setMimeType("text/css");
		bean.setCreator("platform-setup-processing");
		bean.setCreated(new Date());
		
		explorerStyleTemplateSource().setUiTheme(uiTheme());
		
		bean.setResourceSource(explorerStyleTemplateSource());
		
		return bean;
	}
	
	@Managed
	public UiThemeTemplateSource explorerStyleTemplateSource() {
		UiThemeTemplateSource bean = create(UiThemeTemplateSource.T);
		
		bean.setTemplate(resource.explorerStyleTemplateCss());
		
		return bean;
	}
	
}
