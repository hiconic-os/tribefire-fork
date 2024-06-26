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
package com.braintribe.model.processing.dataio;

public interface SerializationCodes {
	static final byte CODE_NULL = 0;
	
	static final byte CODE_BOOLEAN = 1;
	static final byte CODE_INTEGER = 2;
	static final byte CODE_LONG = 3;
	static final byte CODE_FLOAT = 4;
	static final byte CODE_DOUBLE = 5;
	static final byte CODE_DECIMAL = 6;
	static final byte CODE_STRING = 7;
	static final byte CODE_DATE = 8;
	
	static final byte CODE_ENTITY = 9;
	static final byte CODE_ENUM = 10;
	
	static final byte CODE_LIST = 11;
	static final byte CODE_SET = 12;
	static final byte CODE_MAP = 13;
	
	static final byte CODE_CLOB = 14;
	
	static final byte CODE_REQUIRED_TYPES = 15;

	static final byte PROPERTY_TERMINATOR = 0; 
	static final byte PROPERTY_ABSENT = -1; 
	static final byte PROPERTY_DEFINED = 1;
	
	static final byte PROPERTY_NAME_PLAIN = 0;
	static final byte PROPERTY_NAME_DEF = 1; 
	static final byte PROPERTY_NAME_REF = 2;
	
	static final byte TYPE_SIG_PLAIN = 0;
	static final byte TYPE_SIG_DEF = 1; 
	static final byte TYPE_SIG_REF = 2;
	
	static final byte ENTITY_DEF = 0;
	static final byte ENTITY_REF = 1;
}
