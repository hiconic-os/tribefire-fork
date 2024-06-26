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
package com.braintribe.model.elasticsearchreflection.nodestats;

import java.util.Map;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface SegmentsStats extends StandardIdentifiable {

	final EntityType<SegmentsStats> T = EntityTypes.T(SegmentsStats.class);

	long getCount();
	void setCount(long count);

	long getMemoryInBytes();
	void setMemoryInBytes(long memoryInBytes);

	long getTermsMemoryInBytes();
	void setTermsMemoryInBytes(long termsMemoryInBytes);

	long getStoredFieldsMemoryInBytes();
	void setStoredFieldsMemoryInBytes(long storedFieldsMemoryInBytes);

	long getTermVectorsMemoryInBytes();
	void setTermVectorsMemoryInBytes(long termVectorsMemoryInBytes);

	long getNormsMemoryInBytes();
	void setNormsMemoryInBytes(long normsMemoryInBytes);

	long getPointsMemoryInBytes();
	void setPointsMemoryInBytes(long pointsMemoryInBytes);

	long getDocValuesMemoryInBytes();
	void setDocValuesMemoryInBytes(long docValuesMemoryInBytes);

	long getIndexWriterMemoryInBytes();
	void setIndexWriterMemoryInBytes(long indexWriterMemoryInBytes);

	long getVersionMapMemoryInBytes();
	void setVersionMapMemoryInBytes(long versionMapMemoryInBytes);

	long getMaxUnsafeAutoIdTimestamp();
	void setMaxUnsafeAutoIdTimestamp(long maxUnsafeAutoIdTimestamp);

	long getBitsetMemoryInBytes();
	void setBitsetMemoryInBytes(long bitsetMemoryInBytes);

	Map<String, Long> getFileSizes();
	void setFileSizes(Map<String, Long> fileSizes);

}
