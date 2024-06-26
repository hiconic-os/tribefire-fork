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
package com.braintribe.devrock.greyface.process.notification;

import java.util.Set;

import com.braintribe.model.artifact.Part;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.malaclypse.cfg.preferences.gf.RepositorySetting;

/**
 * listener for upload process 
 * @author pit
 *
 */
public interface UploadProcessListener {
	/**
	 * upload begins 
	 * @param setting - the current target 
	 * @param count - the number of parts to be loaded (overall, all solutions combined)
	 */
	void acknowledgeUploadBegin( RepositorySetting setting, int count);
	
	/**
	 * upload ended	
	 */
	void acknowledgeUploadEnd( RepositorySetting setting);
	
	/**
	 * root solution 
	 * @param setting - target 
	 * @param solution - the solution that represents a root of a scan  
	 */
	void acknowledgeRootSolutions( RepositorySetting setting, Set<Solution> solution);
	
	/**
	 * solution's upload begins 
	 */
	void acknowledgeUploadSolutionBegin( RepositorySetting setting, Solution solution);
	
	/**
	 * solution's upload ends without any failures 
	 */
	void acknowledgeUploadSolutionEnd( RepositorySetting setting, Solution solution);
	
	/**
	 * solution's upload ends with some failures 	
	 */
	void acknowledgeUploadSolutionFail( RepositorySetting setting, Solution solution);
	
	/**
	 * successfully uploaded a part 
	 * @param setting - the target {@link RepositorySetting}
	 * @param solution - the owning {@link Solution}
	 * @param part - the {@link Part} uploaded 
	 * @param time - the time it took to upload (ms)
	 * @param index - the index of the part in the overall list of parts to upload 
	 */
	void acknowledgeUploadedPart( RepositorySetting setting, Solution solution, Part part, long time, int index);
	
	/**
	 * failed to upload a part 
	 * @param setting - the target {@link RepositorySetting}
	 * @param solution - the owning {@link Solution}
	 * @param part - the {@link Part} uploaded 
	 * @param reason - the reason why it failed 
	 * @param index - the index of the part in the overall list of parts to upload 
	 */
	void acknowledgeFailedPart( RepositorySetting setting, Solution solution, Part part, String reason, int index);
	
	
	/**
	 * failed to validate a part either via MD5 or SHA1
	 * @param setting - the target {@link RepositorySetting}
	 * @param solution - the owning {@link Solution}
	 * @param part - the {@link Part} uploaded 
	 * @param reason - the reason why it failed 
	 * @param index - the index of the part in the overall list of parts to upload
	 */
	void acknowledgeFailPartCRC( RepositorySetting setting, Solution solution, Part part, String reason, int index);
		
}
