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
package com.braintribe.devrock.repolet.launcher.builder.cfg;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * configuration entity for a repolet 
 * @author pit
 *
 */
public class RepoletCfg {
	private String name;
	private List<FilesystemCfg> filesystems = new ArrayList<>();
	private List<IndexedFilesystemCfg> indexedFilesystems = new ArrayList<>();
	private String changesUrl;
	private String restApiUrl;
	private String serverIdentification;
	private Map<Date, File> dateToContentFile = new HashMap<>();
	private FilesystemCfg uploadFilesystem;
	private List<DescriptiveContentCfg> descriptiveContentDescriptions = new ArrayList<>();
	private List<IndexedDescriptiveContentCfg> indexedDescriptiveContentDescriptions = new ArrayList<>();
	private Map<String,Map<String,String>> hashOverrides = new HashMap<>();
	private Map<String,Boolean> noHashesInHeader = new HashMap<>();	
	private Map<String, Integer> uploadReturnValuesOverride = new HashMap<>();
	private Integer overridingReponseCode;
	
	/**
	 * @return - name of the repolet
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	/**
	 * @return - the code to use to override any other reponse code (401,403 for instance)
	 */
	public Integer getOverridingReponseCode() {
		return overridingReponseCode;
	}
	public void setOverridingReponseCode(Integer overridingReponseCode) {
		this.overridingReponseCode = overridingReponseCode;
	}
	
	public FilesystemCfg getUploadFilesystem() {
		return uploadFilesystem;
	}
	public void setUploadFilesystem(FilesystemCfg uploadFilesystem) {
		this.uploadFilesystem = uploadFilesystem;
	}
	/**
	 * @return - filesystems 
	 */
	public List<FilesystemCfg> getFilesystems() {
		return filesystems;
	}
	public void setFilesystems(List<FilesystemCfg> filesystems) {
		this.filesystems = filesystems;
	}
		
	public List<DescriptiveContentCfg> getDescriptiveContentDescriptions() {
		return descriptiveContentDescriptions;
	}
	public void setDescriptiveContentDescriptions(List<DescriptiveContentCfg> descriptiveContentDescriptions) {
		this.descriptiveContentDescriptions = descriptiveContentDescriptions;
	}
	/**
	 * @return - index file systems 
	 */
	public List<IndexedFilesystemCfg> getIndexedFilesystems() {
		return indexedFilesystems;
	}
	public void setIndexedFilesystems(List<IndexedFilesystemCfg> indexedFilesystems) {
		this.indexedFilesystems = indexedFilesystems;
	}
	
	public List<IndexedDescriptiveContentCfg> getIndexedDescriptiveContentDescriptions() {
		return indexedDescriptiveContentDescriptions;
	}
	public void setIndexedDescriptiveContentDescriptions(
			List<IndexedDescriptiveContentCfg> indexedDescriptiveContentDescriptions) {
		this.indexedDescriptiveContentDescriptions = indexedDescriptiveContentDescriptions;
	}
	public String getChangesUrl() {
		return changesUrl;
	}
	public void setChangesUrl(String changesUrl) {
		this.changesUrl = changesUrl;
	}
	
	public String getRestApiUrl() {
		return restApiUrl;
	}
	public void setRestApiUrl(String restApiUrl) {
		this.restApiUrl = restApiUrl;
	}
	public String getServerIdentification() {
		return serverIdentification;
	}
	public void setServerIdentification(String serverIdentification) {
		this.serverIdentification = serverIdentification;
	}
	public Map<Date, File> getDateToContentFile() {
		return dateToContentFile;
	}
	public void setDateToContentFile(Map<Date, File> dateToContentFile) {
		this.dateToContentFile = dateToContentFile;
	}
	
	public Map<String, Map<String, String>> getHashOverrides() {
		return hashOverrides;
	}
	public void setHashOverrides(Map<String, Map<String, String>> hashOverrides) {
		this.hashOverrides = hashOverrides;
	}
	
	public Map<String, Boolean> getNoHashesInHeader() {
		return noHashesInHeader;
	}
	public void setNoHashesInHeader(Map<String, Boolean> noHashesInHeader) {
		this.noHashesInHeader = noHashesInHeader;
	}
	
	public Map<String, Integer> getUploadReturnValuesOverride() {
		return uploadReturnValuesOverride;
	}
	public void setUploadReturnValuesOverride(Map<String, Integer> uploadReturnValuesOverride) {
		this.uploadReturnValuesOverride = uploadReturnValuesOverride;
	}

	
	
	
	
	
	
	
	
}
