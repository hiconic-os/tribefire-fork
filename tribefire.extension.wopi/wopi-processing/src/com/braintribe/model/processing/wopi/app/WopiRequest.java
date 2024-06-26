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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.braintribe.logging.Logger;
import com.braintribe.model.processing.wopi.misc.JsonUtils;
import com.braintribe.utils.lcd.CommonTools;
import com.fasterxml.jackson.core.JsonGenerator;

/**
 * 
 * Extended Request wrapper for WOPI requested by the Web App
 */
public class WopiRequest extends HttpServletRequestWrapper {

	private static final Logger logger = Logger.getLogger(WopiRequest.class);

	private final WopiEvent event;

	public WopiRequest(HttpServletRequest request) {
		super(request);
		String pathInfo = getPathInfo();
		if (CommonTools.isEmpty(pathInfo)) {
			pathInfo = "";
		}
		this.event = WopiEvent.eventOf(pathInfo);
	}

	@Override
	public String toString() {
		try (OutputStream out = new ByteArrayOutputStream(); JsonGenerator jg = JsonUtils.createGenerator(out)) {
			jg.writeRaw(getClass().getSimpleName());
			jg.writeStartObject();

			StringBuffer requestURL = this.getRequestURL();
			jg.writeStringField("requestURL", requestURL.toString());

			jg.writeObjectFieldStart("request-parameters");
			Map<String, String[]> parameterMap = this.getParameterMap();
			for (Map.Entry<String, String[]> parameter : parameterMap.entrySet()) {
				String parameterName = parameter.getKey();
				String[] parameterValues = parameter.getValue();
				String values = String.join(",", Arrays.stream(parameterValues).collect(Collectors.toList()));
				jg.writeStringField(parameterName, values);
			}

			jg.writeEndObject();

			if (event != null) {
				jg.writeObjectField("event", event);
				jg.writeStringField("fileId", getFileId());
			}
			for (Enumeration<String> en = getHeaderNames(); en.hasMoreElements();) {
				String key = en.nextElement();
				jg.writeObjectField(key, getHeader(key));
			}
			jg.writeObjectField("cookies", getCookies());
			jg.writeEndObject();
			jg.flush();
			return out.toString();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	public WopiEvent getEvent() {
		return event;
	}

	public String getFileId() {
		if (event != null) {
			String pathInfo = getPathInfo();
			Matcher matcher = event.pattern().matcher(pathInfo);
			if (matcher.find()) {
				int groupCount = matcher.groupCount();
				if (groupCount == 0) {
					return matcher.group(0);
				}
				String match = matcher.group(1);
				logger.debug(() -> "Based on event: '" + event + "' matched: '" + match + "' pathInfo: '" + pathInfo + "'");
				return match;
			}
		}
		return null;
	}

	public String getAccessToken() {
		Matcher matcher = Pattern.compile("Bearer (.*)").matcher(getHeader("Authorization"));
		String accessToken = matcher.find() ? matcher.group(1) : null;
		logger.debug(() -> "Got access token: '" + accessToken + "'");
		return accessToken;
	}

	public WopiOperation getWopiOverride() {
		return WopiOperation.valueOf(getHeader(WopiHeader.Override.key()));
	}

	public String getWopiProof() {
		return getHeader(WopiHeader.Proof.key());
	}

	public String getWopiLock() {
		return getHeader(WopiHeader.Lock.key());
	}

	public String getParameterName() {
		return getParameter("name");
	}

	public String getWopiOldLock() {
		return getHeader(WopiHeader.OldLock.key());
	}

	public String getWopiCorrelationId() {
		return getHeader(WopiHeader.CorrelationID.key());
	}

	public void log() {
		logger.debug(() -> "Received event: '" + this.getEvent() + "' wopiCorrelationId: '" + this.getWopiCorrelationId() + "' requestURL: '"
				+ this.getRequestURL() + "'");
		logger.debug(() -> "Received request: '" + this + "'");
	}

}
