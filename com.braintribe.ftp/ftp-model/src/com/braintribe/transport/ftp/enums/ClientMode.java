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
package com.braintribe.transport.ftp.enums;


/**
 *
 */
public enum ClientMode {
	/**
	 * A constant indicating the FTP session is expecting all transfers
	 * to occur between the client (local) and server and that the server
	 * should connect to the client's data port to initiate a data transfer.
	 * This is the default data connection mode when and FTPClient instance
	 * is created.
	 **/
	ACTIVE_LOCAL,
	
	/**
	 * A constant indicating the FTP session is expecting all transfers
	 * to occur between two remote servers and that the server
	 * the client is connected to should connect to the other server's
	 * data port to initiate a data transfer.
	 **/
	ACTIVE_REMOTE,
	
	/**
	 * A constant indicating the FTP session is expecting all transfers
	 * to occur between the client (local) and server and that the server
	 * is in passive mode, requiring the client to connect to the
	 * server's data port to initiate a transfer.
	 **/
	PASSIVE_LOCAL,

	/**
	 * A constant indicating the FTP session is expecting all transfers
	 * to occur between two remote servers and that the server
	 * the client is connected to is in passive mode, requiring the other
	 * server to connect to the first server's data port to initiate a data
	 * transfer.
	 **/
	PASSIVE_REMOTE,
}