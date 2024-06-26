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
package tribefire.extension.xml.schemed.xsd.mapper;

import java.util.Collection;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.processing.itw.analysis.JavaTypeAnalysis;

import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.QPath;
import tribefire.extension.xml.schemed.xsd.api.mapper.type.TypeMapper;

/**
 * a generator that creates {@link GmType} for some special XSD types, such as NMTOKEN,TOKEN,NcName etc
 * @author pit
 *
 */
public interface SpecialConstraintsTypeResolver {
	static String token_Pattern = "^[^\\s][^\\n\\t]*[^\\s]$";
	static final String nmToken_Pattern = "\\c+";
	static final String language_Pattern = "[a-zA-Z]{1,8}(-[a-zA-Z0-9]{1,8})*";
	static final String ncName_Pattern = "[\\i-[:]][\\c-[:]]*";
	static final String normalizedString_Pattern = "[^\\n\\t]*";
	
	

	default GmType createSpecializedType( TypeMapper typeMapper, String xsdName, GmType baseType, Collection<MetaData> metadata) {
		QPath qpath = new QPath();
		GmEntityType specializedType = typeMapper.generateGmEntityTypeForSimpleType(qpath, null, xsdName, baseType);
		specializedType.setGlobalId( JavaTypeAnalysis.typeGlobalId( specializedType.getTypeSignature()));
		specializedType.getMetaData().addAll(metadata);		
		return specializedType;
	}

	GmType createNmToken();
	GmType createLanguage();
	GmType createToken();
	GmType createNormalizedString();
	GmType createNcName();
	GmType createId();
	GmType createIdRef();
	
	GmType createAnyType(String packageName);
}
