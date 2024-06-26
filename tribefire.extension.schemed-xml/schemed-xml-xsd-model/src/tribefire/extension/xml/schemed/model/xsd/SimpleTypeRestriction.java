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
package tribefire.extension.xml.schemed.model.xsd;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.xml.schemed.model.xsd.restrictions.Enumeration;
import tribefire.extension.xml.schemed.model.xsd.restrictions.FractionDigits;
import tribefire.extension.xml.schemed.model.xsd.restrictions.Length;
import tribefire.extension.xml.schemed.model.xsd.restrictions.MaxExclusive;
import tribefire.extension.xml.schemed.model.xsd.restrictions.MaxInclusive;
import tribefire.extension.xml.schemed.model.xsd.restrictions.MaxLength;
import tribefire.extension.xml.schemed.model.xsd.restrictions.MinExclusive;
import tribefire.extension.xml.schemed.model.xsd.restrictions.MinInclusive;
import tribefire.extension.xml.schemed.model.xsd.restrictions.MinLength;
import tribefire.extension.xml.schemed.model.xsd.restrictions.Pattern;
import tribefire.extension.xml.schemed.model.xsd.restrictions.TotalDigits;
import tribefire.extension.xml.schemed.model.xsd.restrictions.Whitespace;

public interface SimpleTypeRestriction extends Restriction, SequenceAware {
	
	final EntityType<SimpleTypeRestriction> T = EntityTypes.T(SimpleTypeRestriction.class);
		
	SimpleType getSimpleType();
	void setSimpleType( SimpleType simpleType);
	
	java.util.List<Enumeration> getEnumerations();
	void setEnumerations(java.util.List<Enumeration> values);
	
	Whitespace getWhitespace();
	void setWhitespace( Whitespace value);
	
	Pattern getPattern();
	void setPattern( Pattern pattern);
	
	Length getLength();
	void setLength( Length value);
	
	MinLength getMinLength();
	void setMinLength( MinLength value);
	
	MaxLength getMaxLength();
	void setMaxLength( MaxLength value);
	
	MinInclusive getMinInclusive();
	void setMinInclusive( MinInclusive value);
	
	MinExclusive getMinExclusive();
	void setMinExclusive( MinExclusive value);
	
	MaxInclusive getMaxInclusive();
	void setMaxInclusive( MaxInclusive value);
	
	MaxExclusive getMaxExclusive();
	void setMaxExclusive( MaxExclusive value);
	
	TotalDigits getTotalDigits();
	void setTotalDigits( TotalDigits value);
	
	FractionDigits getFractionDigits();
	void setFractionDigits( FractionDigits value);
	
	
}
