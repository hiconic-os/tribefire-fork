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
package com.braintribe.model.processing.modellergraph.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmCustomType;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.GmType;

public class GmModellerFilterMetaModelAnalyzer {

	private GmMetaModel gmMetaModel;
	//private Map<String, Set<GenericEntity>> cache = new HashMap<>();
//	private final Map<String, BindingArtifact> bindingArtifacts = new HashMap<>();
	private final List<GmMetaModel> dependencies = new ArrayList<>();
	private final Map<String, Set<GmProperty>> gmProperties = new HashMap<>();
	private final Set<GmType> types = new HashSet<>();
	
	public GmModellerFilterMetaModelAnalyzer(GmMetaModel gmMetaModel) {
		setGmMetaModel(gmMetaModel);
		analyzeMetaModel(gmMetaModel);
	}
	
	private void analyzeMetaModel(GmMetaModel gmMetaModel){
		/*
		EntityVisitor entityVisitor = new EntityVisitor() {
			protected void visitEntity(GenericEntity entity, EntityCriterion criterion, TraversingContext traversingContext) {
				if(entity instanceof BindingArtifact){
					addBinding((BindingArtifact) entity);
				}else if(entity instanceof GmProperty){
					addProperty((GmProperty) entity);
				}
			}
		};
		EntityType<GmMetaModel> metaModelType = GmMetaModel.T;
		metaModelType.traverse(gmMetaModel, null , entityVisitor);
		*/
//		if(gmMetaModel.getArtifactBinding() != null)
//			addBinding(gmMetaModel.getArtifactBinding());
		
		dependencies.addAll(getDeps(gmMetaModel));
		
		
		for (GmType gmType : getTypes(gmMetaModel)) {
			if (!gmType.isGmCustom()) {
				continue;
			}
			
			GmCustomType gmCustomType = (GmCustomType) gmType;
			
			if (!gmCustomType.isGmEntity()) {
				continue;
			}
			
			if(((GmEntityType)gmCustomType).getProperties() != null) {
				for(GmProperty gmProperty : ((GmEntityType)gmCustomType).getProperties()){
					addProperty(gmProperty);
				}
			}
			types.add(gmType);
		}
	}
	
	private List<GmMetaModel> getDeps(GmMetaModel model){
		List<GmMetaModel> deps = new ArrayList<GmMetaModel>();
		deps.add(model);
		if(model.getDependencies() != null){
			deps.addAll(model.getDependencies());
			for(GmMetaModel dep : model.getDependencies()){
				deps.addAll(getDeps(dep));
			}
		}
		
		return deps;
	}
	
	private Set<GmType> getTypes(GmMetaModel model){
		Set<GmType> types = new HashSet<GmType>();
		
		types.addAll(model.getTypes());
		if(model.getDependencies() != null){
			for(GmMetaModel dep : model.getDependencies()){
				types.addAll(getTypes(dep));
			}
		}	
		
		return types;
	}
	
//	private void addBinding(BindingArtifact bindingArtifact){
//		String artifactName = bindingArtifact.getGroupId() + ":" + bindingArtifact.getArtifactId() + "#" + bindingArtifact.getVersion();
//		if(!bindingArtifacts.containsKey(artifactName))
//			bindingArtifacts.put(artifactName, bindingArtifact);
//	}
	
	private void addProperty(GmProperty gmProperty){
		if(!gmProperties.containsKey(gmProperty.getName())){
			Set<GmProperty> newGmProperties = new HashSet<>();
			gmProperties.put(gmProperty.getName(), newGmProperties);
		}
		gmProperties.get(gmProperty.getName()).add(gmProperty);
	}
	
	public List<GenericEntity> getMatchingCandidates(String compareTerm){
		List<GenericEntity> candidates = new ArrayList<>();

		candidates.addAll(dependencies.stream().filter(dep -> {
			return dep.getName().toLowerCase().contains(compareTerm.toLowerCase());
		}).collect(Collectors.toSet()));
		
		candidates.addAll(types.stream().filter(type -> {
			return type.getTypeSignature().toLowerCase().contains(compareTerm.toLowerCase());
		}).collect(Collectors.toSet()));

		/*
		gmProperties.forEach((name, properties) -> {
			if(name.toLowerCase().contains(compareTerm.toLowerCase()))
				candidates.addAll(properties);
		});
		*/
		
		return candidates;
	}
	
	public void setGmMetaModel(GmMetaModel gmMetaModel) {
		this.gmMetaModel = gmMetaModel;
	}

	public GmMetaModel getGmMetaModel() {
		return gmMetaModel;
	}
	
}
