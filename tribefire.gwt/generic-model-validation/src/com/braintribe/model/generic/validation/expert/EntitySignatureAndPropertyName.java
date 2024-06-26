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
package com.braintribe.model.generic.validation.expert;

public class EntitySignatureAndPropertyName {
	
	private String entityTypeSignature;
	private String propertyName;
	
	public EntitySignatureAndPropertyName(String entityTypeSignature, String propertyName) {
		super();
		this.entityTypeSignature = entityTypeSignature;
		this.propertyName = propertyName;
	}
	
	public void setEntityTypeSignature(String entityTypeSignature) {
		this.entityTypeSignature = entityTypeSignature;
	}
	
	public String getEntityTypeSignature() {
		return entityTypeSignature;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entityTypeSignature == null) ? 0 : entityTypeSignature.hashCode());
		result = prime * result
				+ ((propertyName == null) ? 0 : propertyName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntitySignatureAndPropertyName other = (EntitySignatureAndPropertyName) obj;
		if (entityTypeSignature == null) {
			if (other.entityTypeSignature != null)
				return false;
		} else if (!entityTypeSignature.equals(other.entityTypeSignature))
			return false;
		if (propertyName == null) {
			if (other.propertyName != null)
				return false;
		} else if (!propertyName.equals(other.propertyName))
			return false;
		return true;
	}

}
