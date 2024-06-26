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
package tribefire.extension.tracing.model.deployment.service;

/**
 *
 */
public enum DefaultAttribute {

	// BEFORE execution
	ATTRIBUTE_TYPE("BEFORE"),
	ATTRIBUTE_ENTITY_TYPE("BEFORE"),
	ATTRIBUTE_REQUEST("BEFORE"),
	ATTRIBUTE_SESSION_ID("BEFORE"),
	ATTRIBUTE_DOMAIN_ID("BEFORE"),
	ATTRIBUTE_USER("BEFORE"),
	ATTRIBUTE_ROLES("BEFORE"),
	ATTRIBUTE_PARTITION("BEFORE"),
	ATTRIBUTE_INSTANCE_ID("BEFORE"),
	ATTRIBUTE_NODE_ID("BEFORE"),
	ATTRIBUTE_APPLICATION_ID("BEFORE"),
	ATTRIBUTE_HOST_ADDRESS_IPV4("BEFORE"),
	ATTRIBUTE_HOST_ADDRESS_IPV6("BEFORE"),
	ATTRIBUTE_TIMESTAMP("BEFORE"),
	ATTRIBUTE_TIMESTAMP_ISO8601("BEFORE"),

	// ERROR execution
	ATTRIBUTE_STACK("ERROR"),
	ATTRIBUTE_ERROR("ERROR", "error"),

	// AFTER execution
	ATTRIBUTE_RESULT("AFTER"),
	ATTRIBUTE_SERVICE_DURATION("AFTER"),
	ATTRIBUTE_TRACING_OVERHEAD("AFTER"),
	ATTRIBUTE_NOTIFICATION_MESSAGE("AFTER"),
	ATTRIBUTE_NOTIFICATION_DETAIL_MESSAGE("AFTER");

	// -----------------------------------------------------------------------

	private final String label;
	private final String value;

	private DefaultAttribute(String label) {
		this.label = label;
		this.value = null;
	}
	private DefaultAttribute(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public String label() {
		return label;
	}

	public String value() {
		if (value == null) {
			return this.toString();
		}
		return value;
	}

}
