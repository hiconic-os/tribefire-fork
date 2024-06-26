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

public class MethodData {

	private String methodName;
	
	private String signature;
	private String desc;
	private String returnType;
	private List<String> argumentTypes;
	private List<String> exceptions;
	private List<AnnotationTuple> annotationTuples;
	
	private boolean staticNature;
	private boolean synchronizedNature;
	private boolean abstractNature;
	private AccessModifier accessNature;
	private boolean nativeNature;
	private boolean finalNature;
	private boolean strictFpNature;
	private boolean containsBody;
	private List<MethodBodyTypeReference> bodyTypes;
	
	private EntityTypeInitializerData entityTypeInitializerData;
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
		
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public List<String> getArgumentTypes() {
		return argumentTypes;
	}
	public void setArgumentTypes(List<String> argumentTypes) {
		this.argumentTypes = argumentTypes;
	}
	public List<String> getExceptions() {
		return exceptions;
	}
	public void setExceptions(List<String> exceptions) {
		this.exceptions = exceptions;
	}
	public boolean getIsStatic() {
		return staticNature;
	}
	public void setIsStatic(boolean staticNature) {
		this.staticNature = staticNature;
	}
	public boolean getIsSynchronized() {
		return synchronizedNature;
	}
	public void setIsSynchronized(boolean synchronizedNature) {
		this.synchronizedNature = synchronizedNature;
	}
	public boolean getIsAbstract() {
		return abstractNature;
	}
	public void setIsAbstract(boolean abstractNature) {
		this.abstractNature = abstractNature;
	}
	public AccessModifier getAccessModifier() {
		return accessNature;
	}
	public void setAccessModifier(AccessModifier accessNature) {
		this.accessNature = accessNature;
	}
	public List<AnnotationTuple> getAnnotationTuples() {
		return annotationTuples;
	}
	public void setAnnotationTuples(List<AnnotationTuple> annotationTuples) {
		this.annotationTuples = annotationTuples;
	}
	public boolean getIsNative() {
		return nativeNature;
	}
	public void setIsNative(boolean nativeNature) {
		this.nativeNature = nativeNature;
	}
	public boolean getIsFinal() {
		return finalNature;
	}
	public void setIsFinal(boolean finalNature) {
		this.finalNature = finalNature;
	}
	public boolean getIsStrictFp() {
		return strictFpNature;
	}
	public void setIsStrictFp(boolean strictFpNature) {
		this.strictFpNature = strictFpNature;
	}
	
	public List<MethodBodyTypeReference> getBodyTypes() {
		return bodyTypes;
	}

	public void setBodyTypes(List<MethodBodyTypeReference> bodyTypes) {
		this.bodyTypes = bodyTypes;
	}
	public EntityTypeInitializerData getEntityTypeInitializerData() {
		return entityTypeInitializerData;
	}
	public void setEntityTypeInitializerData(EntityTypeInitializerData entityTypeInitializerData) {
		this.entityTypeInitializerData = entityTypeInitializerData;
	}
	public void setContainsBody(boolean b) {
		this.containsBody = b;		
	}
	
	public boolean getContainsBody() {
		return this.containsBody;
	}

	
	

	
		
}
