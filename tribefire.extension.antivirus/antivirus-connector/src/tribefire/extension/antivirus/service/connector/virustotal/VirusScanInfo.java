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
package tribefire.extension.antivirus.service.connector.virustotal;

import com.google.gson.annotations.SerializedName;

public class VirusScanInfo {
	@SerializedName("detected")
	private boolean detected;
	@SerializedName("version")
	private String version;
	@SerializedName("result")
	private String result;
	@SerializedName("update")
	private String update;

	public VirusScanInfo() {
	}

	public boolean isDetected() {
		return detected;
	}

	public void setDetected(boolean detected) {
		this.detected = detected;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}
}
