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
package com.braintribe.model.smoodstorage;

import java.util.Set;

import com.braintribe.model.generic.StandardStringIdentifiable;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;


public interface SmoodStorage extends StandardStringIdentifiable {

	EntityType<SmoodStorage> T = EntityTypes.T(SmoodStorage.class);
	
	String accessId = "accessId";
	String sequenceNumber = "sequenceNumber";
	String encodedData = "encodedData";
	String bufferedManipulations = "bufferedManipulations";
	String classDependenciesSequenceNumber = "classDependenciesSequenceNumber";
	String classDependencies = "classDependencies";
	
	String getAccessId();
	void setAccessId(String accessId);
	
	int getSequenceNumber();
	void setSequenceNumber(int sequenceNumber);
	
	String getEncodedData();
	void setEncodedData(String encodedData);
	
	Set<BufferedManipulation> getBufferedManipulations();
	void setBufferedManipulations(Set<BufferedManipulation> manipulations);

	int getSize();
	void setSize(int size);
	
	int getBufferedManipulationsSize();
	void setBufferedManipulationsSize(int size);
	
	int getClassDependenciesSequenceNumber();
	void setClassDependenciesSequenceNumber(int classDependenciesSequenceNumber);
	
	Set<JavaClass> getClassDependencies();
	void setClassDependencies(Set<JavaClass> classDependencies);
}
