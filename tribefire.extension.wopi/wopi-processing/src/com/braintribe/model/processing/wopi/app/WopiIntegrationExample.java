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
package com.braintribe.model.processing.wopi.app;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Supplier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.wopi.WopiConnectorUtil;
import com.braintribe.model.processing.wopi.WopiQueryingUtil;
import com.braintribe.model.processing.wopi.service.AbstractWopiProcessing;
import com.braintribe.model.wopi.DocumentMode;
import com.braintribe.model.wopi.WopiSession;
import com.braintribe.utils.CommonTools;
import com.braintribe.utils.StringTools;
import com.braintribe.utils.html.HtmlTools;
import com.braintribe.web.servlet.BasicTemplateBasedServlet;

/**
 * Expert for WopiIntegrationExample
 * 
 * 
 * 
 * <br/>
 * <b>Important:</b> make sure to use the actual IP address and not 'localhost' for accessing the example
 * 
 * @see <a href= "https://wopi.readthedocs.io/en/latest/hostpage.html">Building a host page</a>
 * 
 *
 */
public class WopiIntegrationExample extends BasicTemplateBasedServlet implements AbstractWopiProcessing {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(WopiIntegrationExample.class);

	private static final String templateLocation = "com/braintribe/model/processing/wopi/app/template/wopiIntegrationExample.html.vm";

	private Supplier<PersistenceGmSession> sessionSupplier;

	private com.braintribe.model.wopi.service.WopiIntegrationExample deployable;

	// -----------------------------------------------------------------------
	// INITIALIZATION
	// -----------------------------------------------------------------------

	@Override
	public void init() throws ServletException {
		super.init();
		setTemplateLocation(templateLocation);
	}

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	@Override
	protected VelocityContext createContext(HttpServletRequest request, HttpServletResponse response) {

		String correlationId = request.getParameter("correlationId");
		String title = request.getParameter("title");
		if (CommonTools.isNull(title)) {
			title = "";
		}

		PersistenceGmSession session = sessionSupplier.get();

		String wopiUrl;
		WopiSession wopiSession;

		String demoCorrelationId = resolveDefaultContentCorrelationId(AbstractWopiProcessing.DEMO_CORRELATION_ID_PREFIX,
				AbstractWopiProcessing.DEMO_CORRELATION_ID_POSTFIX, "demo.docx", DocumentMode.edit);
		if (StringTools.isBlank(correlationId)) {
			wopiSession = WopiQueryingUtil.queryWopiSession(session, demoCorrelationId);
		} else {
			wopiSession = WopiQueryingUtil.queryWopiSession(session, correlationId);
		}
		if (wopiSession == null) {
			throw new IllegalStateException("Neither a correlationId was provided nor a docx demo edit WopiSession (with correlationId: '"
					+ demoCorrelationId
					+ "') exists for demonstration. Either provide a correlationId as URL parameter or initialized the default demo WOPI sessions");
		}

		wopiUrl = wopiSession.getWopiUrl();

		// just filling here with dummy values
		String accessToken = "myAccessToken";
		String accessTokenTtl = Long.toString(deployable.getWopiApp().getAccessTokenTtlInSec());

		String servicesUrl = WopiConnectorUtil.getPublicServicesUrl(deployable.getWopiApp().getWopiWacConnector().getCustomPublicServicesUrl());
		String status;
		try {
			URL url = new URL(servicesUrl + "/healthz");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(deployable.getHealthConnectTimeoutInMs());
			con.setReadTimeout(deployable.getHealthReadTimeoutInMs());
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode == 200) {
				status = "OKAY - " + responseCode;
			} else {
				status = "NOT OKAY - " + responseCode;
			}
		} catch (Exception e) {
			status = "ERROR - " + e.getMessage();
		}

		// invalid accessToken
		// sessionId
		// TODO: the session probably should not be added here
		wopiUrl = wopiUrl + "&sessionId=" + session.getSessionAuthorization().getSessionId();

		wopiUrl = servicesUrl + wopiUrl;

		VelocityContext context = new VelocityContext();
		context.put("wopiUrl", wopiUrl);
		context.put("accessToken", accessToken);
		context.put("accessTokenTtl", accessTokenTtl);
		context.put("title", title);
		context.put("status", status);
		context.put("HtmlTools", HtmlTools.class);
		context.put("StringTools", StringTools.class);
		return context;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	@Required
	public void setSessionSupplier(Supplier<PersistenceGmSession> sessionSupplier) {
		this.sessionSupplier = sessionSupplier;
	}

	@Configurable
	@Required
	public void setDeployable(com.braintribe.model.wopi.service.WopiIntegrationExample deployable) {
		this.deployable = deployable;
	}

}
