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
package com.braintribe.devrock.zed.api.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.braintribe.devrock.zed.api.context.ZedAnalyzerContext;
import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.zarathud.model.data.ZedEntity;

/**
 * @author pit
 *
 */
public interface CachingZedRegistry {
	/**
	 * @param <T> - whatever derivation of a {@link ZedEntity}
	 * @param context -  the {@link ZedAnalyzerContext}
	 * @param desc - the type desc as found in the signatures
	 * @param entityType - the expected {@link EntityType} of the {@link ZedEntity}
	 * @return - the real {@link ZedEntity}
	 */
	<T extends ZedEntity> T acquire(ZedAnalyzerContext context, String desc, EntityType<T> entityType);
	/**
	 * @return - a {@link Collection} of all analyzed {@link ZedEntity}
	 */
	Collection<ZedEntity> population();
	
	/**
	 * @return - the {@link Map} of short names and their full names of simple types
	 */
	Map<String,String> simpleTypeDescs ();
	/**
	 * @param value - the expression to test
	 * @return - true if it's parameter code (generics), false if not
	 */
	boolean isParameterCode( String value);
	
	/**
	 * @return - a {@link List} of all {@link Reason} collected during the analysis
	 */
	List<Reason> collectedAsmAnalysisErrorReasons();
	
	@SuppressWarnings("serial")
	Map<String,String> simpleTypeDescs = new HashMap<String,String>() {{ 
		put( "I", "int");		
		put( "J", "long");				
		put( "S", "short");	
		
		put( "F", "float");
		put( "D", "double");
				
		put( "B", "byte");

		put( "Z", "boolean");
				
		put( "C", "char");
		
		put( "V", "void");
	}};
	
	@SuppressWarnings("serial")
	List<String> simpleTypes = new ArrayList<String>() {{
		add("java.lang.String");
		add("java.lang.Integer");
		add("java.lang.Long");
		add("java.lang.Short");
		add("java.lang.float");
		add("java.lang.Double");
		add("java.lang.Byte");
		add("java.lang.Boolean");		
	}};
	
	
	@SuppressWarnings("serial")
	List<String> linearCollectionTypes = new ArrayList<String>() {{
		add("java.util.List");
		add("java.util.Collection");
		add("java.util.Set");		
	}};
	
	@SuppressWarnings("serial")
	List<String> nonlinearCollectionTypes = new ArrayList<String>() {{
		add("java.util.Map");
	}};
}
