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

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.transport.ftp.enums.SourcePostProcessing;
import com.braintribe.transport.ftp.enums.TargetConflictHandling;

public interface BatchFtpJob extends StandardIdentifiable {
	
	final EntityType<BatchFtpJob> T = EntityTypes.T(BatchFtpJob.class);
	
	 String getName();
	 void setName(String name);
	
	 FtpConnection getConnection();
	 void setConnection(FtpConnection connection);

	 String getRemotePath();
	 void setRemotePath(String arg);

	 String getLocalPath();
	 void setLocalPath(String arg);

	 SourcePostProcessing getSourcePostProcessing();
	 void setSourcePostProcessing(SourcePostProcessing arg);

	 TargetConflictHandling getTargetConflictHandling();
	 void setTargetConflictHandling(TargetConflictHandling arg);
	
	 String getSourceArchivePath();
	 void setSourceArchivePath(String arg);
	
	 boolean getContinueOnError();
	 void setContinueOnError(boolean arg);

	 void setFilenameInclusionFilter(String arg);
	 String getFilenameInclusionFilter();
}
