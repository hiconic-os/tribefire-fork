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
package tribefire.extension.tutorial.processing;

import org.apache.velocity.VelocityContext;

import com.braintribe.cfg.InitializationAware;
import com.braintribe.web.servlet.BasicTemplateBasedServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LetterCaseTransformApp extends BasicTemplateBasedServlet implements InitializationAware {

	private static final long serialVersionUID = 5594198356463202820L;
	private static final String templateLocation = "tribefire/extension/tutorial/processing/template/transform.html.vm";
	
	@Override
	protected VelocityContext createContext(HttpServletRequest request, HttpServletResponse response) {
		// TODO implement
		return null;
	}

	@Override
	public void postConstruct() {		
		setTemplateLocation(templateLocation);
	}

}
