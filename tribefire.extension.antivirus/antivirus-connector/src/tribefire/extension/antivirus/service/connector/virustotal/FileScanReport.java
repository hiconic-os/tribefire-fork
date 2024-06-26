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

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class FileScanReport {

    @SerializedName("scans")
    private Map<String, VirusScanInfo> scans;
    @SerializedName("scan_id")
    private String scanId;
    @SerializedName("sha1")
    private String sha1;
    @SerializedName("resource")
    private String resource;
    @SerializedName("response_code")
    private Integer responseCode;
    @SerializedName("scan_date")
    private String scanDate;
    @SerializedName("permalink")
    private String permalink;
    @SerializedName("verbose_msg")
    private String verboseMessage;
    @SerializedName("total")
    private Integer total;
    @SerializedName("positives")
    private Integer positives;
    @SerializedName("sha256")
    private String sha256;
    @SerializedName("md5")
    private String md5;

    public FileScanReport() {
    }

	public Map<String, VirusScanInfo> getScans() {
		return scans;
	}

	public void setScans(Map<String, VirusScanInfo> scans) {
		this.scans = scans;
	}

	public String getScanId() {
		return scanId;
	}

	public void setScanId(String scanId) {
		this.scanId = scanId;
	}

	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getVerboseMessage() {
		return verboseMessage;
	}

	public void setVerboseMessage(String verboseMessage) {
		this.verboseMessage = verboseMessage;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPositives() {
		return positives;
	}

	public void setPositives(Integer positives) {
		this.positives = positives;
	}

	public String getSha256() {
		return sha256;
	}

	public void setSha256(String sha256) {
		this.sha256 = sha256;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

}
