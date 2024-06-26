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
package com.braintribe.model.processing.xmi.converter.coding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.logging.Logger;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmType;

/**
 * helper class for models and their dependencies
 * @author pit
 *
 */
public class MetaModelDependencyHandler {
	private static Logger log = Logger.getLogger(MetaModelDependencyHandler.class);
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	private static Map<String,String> extractAndEncodeModelDependencies( GmMetaModel model) {
		StringBuilder sb = new StringBuilder();
		Map<String,String> result = new HashMap<>();
		for (GmMetaModel dependency : model.getDependencies()) {
			if (sb.length() != 0) {
				sb.append( ",");
			}
			sb.append( dependency.getName());		
			result.putAll( extractAndEncodeModelDependencies(dependency));
		}
		result.put( model.getName()+ "(" + model.getGlobalId() + ")", sb.toString());
		return result;		
	}
	
	/**
	 * encodes the model structure to a single string for a tag reference
	 * i.e. dependencies and global ids
	 * @param model - the {@link GmMetaModel} to extract
	 * @return - a collated String
	 */
	public static String collectEncodedModelDependencies(GmMetaModel model) {
		Map<String,String> extractedModelDependencies = extractAndEncodeModelDependencies(model);
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : extractedModelDependencies.entrySet()) {
			if (sb.length() > 0) {
				sb.append(";");
			}
			if (entry.getValue() != null && entry.getValue().length() > 0) {
				sb.append( entry.getKey() + "=" + entry.getValue());
			}
			else {
				sb.append( entry.getKey());
			}
		}
		String extracted = sb.toString();
		return extracted;
	}
	
	/**
	 * decodes the model structure from a single string read from a tag reference
	 * @param expression - the {@link String} to parse 
	 * @return - a {@link Map} of model-name to {@link GmMetaModel}
	 */
	public static Map<String,GmMetaModel> decodeModelAndDependencies( String expression) {
		Map<String, GmMetaModel> models = new HashMap<>();
		String [] modelExpressions = expression.split( ";");
		for (String modelExpression : modelExpressions) {
			int p = modelExpression.indexOf( "=");
			if (p > 0) {
				String nameAndGlobal = modelExpression.substring( 0, p);
				String dependencies = modelExpression.substring( p+1);
				GmMetaModel model = instrumentModel(models, nameAndGlobal);				
				
				String [] deps = dependencies.split( ",");
				for (String dependency : deps) {
					GmMetaModel dModel = models.computeIfAbsent( dependency, m -> GmMetaModel.T.create());
					model.getDependencies().add( dModel);
				}			
			}
			else {
															
				instrumentModel(models, modelExpression);			
			}
		}
		return models;
	}

	/**
	 * instruments a model, i.e. creates it with correct name and global id
	 * @param models - a {@link Map} of model-name to {@link GmMetaModel}
	 * @param nameAndGlobal - a string formatted {@code '<model-name>(<globalId>)'}
	 * @return - the newly created {@link GmMetaModel}
	 */
	private static GmMetaModel instrumentModel(Map<String, GmMetaModel> models, String nameAndGlobal) {
		int b = nameAndGlobal.indexOf( '(');
		String modelName = nameAndGlobal.substring(0, b);
		String globalId = nameAndGlobal.substring(b, nameAndGlobal.length()-1);
		
		GmMetaModel model = models.computeIfAbsent( modelName, m -> GmMetaModel.T.create());
		// may be eagerly acquired, so we need to set the stuff even if we didn't create the model
		model.setGlobalId( globalId);
		model.setName( modelName);
		return model;
	}
	
	private static void recursivelyLogTypes( GmMetaModel model, Set<String> processed) {
		if (processed.add( model.getName())) {
			for (GmMetaModel dependency : model.getDependencies()) {
				recursivelyLogTypes( dependency, processed);
			}
			String msg = model.getName() + ": [" + model.entityTypes().count() + "] entity types and [" + model.enumTypes().count() + "] enumtypes]";
			log.info(msg);
		}		
	}
	
	public static void logTypes( GmMetaModel model) {
		Set<String> processed = new HashSet<>();	
		recursivelyLogTypes( model, processed);		
	}
	
	 
	
	public static List<GmType> getChangedTypes( GmMetaModel decodedModel, GmMetaModel encodedModel) {
		List<GmType> changed = new ArrayList<>();
		
		
		
		return changed;
	}
}
