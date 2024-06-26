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
package com.braintribe.devrock.zed.scan.asm;

import java.util.List;

public class ClassData {

	private AccessModifier accessNature;
	private boolean staticNature;
	private boolean abstractNature;
	private boolean synchronizedNature;
	private boolean finalNature;
	private List<AnnotationTuple> annotationTuples;
	private List<FieldData> fieldData;
	private String signature;
	
	public AccessModifier getAccessNature() {
		return accessNature;
	}
	public void setAccessNature(AccessModifier accessNature) {
		this.accessNature = accessNature;
	}
	public boolean getIsStatic() {
		return staticNature;
	}
	public void setIsStatic(boolean staticNature) {
		this.staticNature = staticNature;
	}
	public boolean getIsAbstract() {
		return abstractNature;
	}
	public void setIsAbstract(boolean abstractNature) {
		this.abstractNature = abstractNature;
	}
	public boolean getIsSynchronized() {
		return synchronizedNature;
	}
	public void setIsSynchronized(boolean synchronizedNature) {
		this.synchronizedNature = synchronizedNature;
	}
	
	
	public List<FieldData> getFieldData() {
		return fieldData;
	}
	public void setFieldData(List<FieldData> fieldData) {
		this.fieldData = fieldData;
	}
	
	public List<AnnotationTuple> getAnnotationTuples() {
		return annotationTuples;
	}
	public void setAnnotationTuples(List<AnnotationTuple> annotationTuples) {
		this.annotationTuples = annotationTuples;
	}
	public boolean getIsFinal() {
		return finalNature;
	}
	public void setIsFinal(boolean finalNature) {
		this.finalNature = finalNature;
	}
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	
	
}
