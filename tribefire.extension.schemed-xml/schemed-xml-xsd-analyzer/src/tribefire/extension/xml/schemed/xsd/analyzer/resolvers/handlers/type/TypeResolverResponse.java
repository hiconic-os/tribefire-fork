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
package tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type;

import com.braintribe.model.meta.GmType;

import tribefire.extension.xml.schemed.marshaller.commons.QNameExpert;
import tribefire.extension.xml.schemed.model.xsd.QName;

public class TypeResolverResponse {
	private GmType gmType;
	private QName apparentTypeName;
	private QName actualTypeName;
	private boolean alreadyAcquired = false;
	//private Collection<MetaData> metadata = new ArrayList<>();
	
	public GmType getGmType() {
		return gmType;
	}
	public void setGmType(GmType gmType) {
		this.gmType = gmType;
	}
	public QName getApparentTypeName() {
		return apparentTypeName;
	}
	public void setApparentTypeName(QName apparentTypeName) {
		this.apparentTypeName = apparentTypeName;
	}
	public void setApparentTypeName(String apparentTypeName) {
		this.apparentTypeName = QNameExpert.parse(apparentTypeName);
	}
	public QName getActualTypeName() {
		return actualTypeName;
	}
	public void setActualTypeName(QName actualTypeName) {
		this.actualTypeName = actualTypeName;
	}
	public void setActualTypeName(String actualTypeName) {
		this.actualTypeName = QNameExpert.parse(actualTypeName);
	}
	public boolean isAlreadyAcquired() {
		return alreadyAcquired;
	}
	public void setAlreadyAcquired(boolean alreadyAcquired) {
		this.alreadyAcquired = alreadyAcquired;
	}
	/*
	public Collection<MetaData> getMetadata() {
		return metadata;
	}
	public void setMetadata(Collection<MetaData> metadata) {
		this.metadata = metadata;
	}
	*/
	
}
