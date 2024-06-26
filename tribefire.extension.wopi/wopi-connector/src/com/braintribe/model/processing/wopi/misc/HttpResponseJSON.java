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
package com.braintribe.model.processing.wopi.misc;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * Base class of JSON response conversion (bean -> JSON)
 */
@JsonInclude(Include.NON_NULL)
public abstract class HttpResponseJSON implements HttpResponse {

	@Override
	public final void close() throws Exception {
		// NOP
	}

	@Override
	public final void write(HttpServletResponse response) throws IOException {
		response.setContentType(ContentType.APPLICATION_JSON.toString());
		JsonUtils.writeValue(response.getOutputStream(), this);
	}

	@Override
	public final String toString() {
		try {
			return JsonUtils.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

}
