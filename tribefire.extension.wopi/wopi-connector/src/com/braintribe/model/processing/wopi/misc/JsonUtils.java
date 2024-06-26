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
import java.io.OutputStream;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Helper class for JSON operations
 */
public class JsonUtils {

	private static final ObjectMapper JSON_MAPPER;
	private static final JsonFactory JSON_FACTORY;

	static {
		JSON_MAPPER = new ObjectMapper();
		JSON_MAPPER.setSerializationInclusion(Include.NON_NULL);
		JSON_MAPPER.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, true);
		JSON_FACTORY = new JsonFactory(JSON_MAPPER);
	}

	public static JsonGenerator createGenerator(OutputStream out) throws IOException {
		return JSON_FACTORY.createGenerator(out);
	}

	public static <T> T readValue(String content, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
		return JSON_MAPPER.readValue(content, valueType);
	}

	public static void writeValue(OutputStream out, Object value) throws JsonGenerationException, JsonMappingException, IOException {
		JSON_MAPPER.writeValue(out, value);
	}

	public static String writeValueAsString(Object value) throws JsonProcessingException {
		return JSON_MAPPER.writeValueAsString(value);
	}

}
