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
package com.braintribe.gwt.gmrpc.api.client.transport;

/**
 * Must be kept in sync with com.braintribe.model.processing.webrpc.api.RpcHeaders
 */
public enum RpcHeaders {
	
	/*
	 * indicates whether the request or response body contains an unmarshallable RPC body.
	 */
	rpcBody("gm-rpc-body"),
	
	/*
	 * a flag that controls if the call occurs reasoned or not (true|false)
	 */
	rpcReasoning("gm-rpc-reasoning"),

	/*
	 * indicates the type of the marshalled generic model entity 
	 * contained in the body of a RPC request or response.
	 */
	rpcBodyType("gm-rpc-body-type");

	
	private String headerName;
	
	private RpcHeaders(String headerName) {
		this.headerName = headerName;
	}
	
	public String getHeaderName() {
		return headerName;
	}

}
