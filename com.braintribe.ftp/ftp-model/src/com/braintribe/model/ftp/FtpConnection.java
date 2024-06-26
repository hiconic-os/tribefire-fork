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
package com.braintribe.model.ftp;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.transport.ftp.enums.ClientMode;
import com.braintribe.transport.ftp.enums.FileType;


public interface FtpConnection extends Connection {

	final EntityType<FtpConnection> T = EntityTypes.T(FtpConnection.class);

	String getRemoteActiveHost();
	void setRemoteActiveHost(String remoteActiveHost);

	Integer getRemoteActivePort();
	void setRemoteActivePort(Integer remoteActivePort);

	String getAccount();
	void setAccount(String account);

	FileType getFileType();
	void setFileType(FileType arg);

	ClientMode getClientMode();
	void setClientMode(ClientMode clientMode);	
}
