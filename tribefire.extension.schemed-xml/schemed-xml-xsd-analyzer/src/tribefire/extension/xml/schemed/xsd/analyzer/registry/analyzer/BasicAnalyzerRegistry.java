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
package tribefire.extension.xml.schemed.xsd.analyzer.registry.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.braintribe.logging.Logger;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.meta.GmTypeKind;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Qualification;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.MappingContext;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.MappingMode;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.QPath;
import tribefire.extension.xml.schemed.xsd.api.analyzer.AnalyzerRegistry;
import tribefire.extension.xml.schemed.xsd.api.analyzer.ConfigurableFromRequest;
import tribefire.extension.xml.schemed.xsd.api.analyzer.SchemaRegistry;
import tribefire.extension.xml.schemed.xsd.api.analyzer.naming.QPathGenerator;
import tribefire.extension.xml.schemed.xsd.mapper.BasicMapper;

public class BasicAnalyzerRegistry implements AnalyzerRegistry, ConfigurableFromRequest, QPathGenerator {
	private static Logger log = Logger.getLogger(BasicAnalyzerRegistry.class);
	private SchemaRegistry mainSchemaRegistry;
	private Set<String> usedXsdPrefixes;
	private BasicMapper mapper;
	private MappingContext context = new MappingContext();
	private boolean verbose;
	
	private boolean elementQualified;
	private boolean attributeQualified;
	
	private Map<Schema, SchemaRegistry> schemaToBackingRegistryMap = new HashMap<>();
	private Map<String, Namespace> namespaces;
	
	public BasicAnalyzerRegistry() {
		mapper = new BasicMapper();
	}
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
		
		mapper.setVerbose(this.verbose);
	}

	public void analyze(SchemaRegistry mainRegistry) {
		this.mainSchemaRegistry = mainRegistry;
			
		// get all schema prefixes from all registries
		usedXsdPrefixes = retrieveSchemaPrefix(mainSchemaRegistry);

		// get new mapper instance 
		mapper.setUsedXsdPrefixes(usedXsdPrefixes);
		
		mapper.initializeTypeSystem();
		
		context.typeMapper = mapper;
		context.nameMapper = mapper;
		context.metaDataMapper = mapper;

		// start the mapping 
		mainRegistry.map( context);
		
		if (mainRegistry.getSchema().getElementFormDefaultSpecified())  {
			Qualification qualification = mainRegistry.getSchema().getElementFormDefault();
			if (qualification == Qualification.qualified) {
				elementQualified = true;
			}
			else {
				elementQualified = false;
			}
		}
		if (mainRegistry.getSchema().getAttributeFormDefaultSpecified())  {
			Qualification qualification = mainRegistry.getSchema().getAttributeFormDefault();
			if (qualification == Qualification.qualified) {
				attributeQualified = true;
			}
			else {
				attributeQualified = false;
			}
		}
		
		// extract all target namespaces of the imported
									
	}


	private Set<String> retrieveSchemaPrefix( SchemaRegistry registry) {
		Set<String> prefixes = new HashSet<>();
		prefixes.add( registry.getSchema().getSchemaPrefix());
		for (SchemaRegistry importedRegistry : registry.getImportedSchemata().values()) {
			Set<String> retrieveSchemaPrefix = retrieveSchemaPrefix(importedRegistry);
			prefixes.addAll( retrieveSchemaPrefix);
		}		
		return prefixes;		
	}

	@Override
	public void parametrize(SchemedXmlXsdAnalyzerRequest request) {	
		mapper.parametrize(request);
		if (request.getExposeChoice()) {
			context.choiceMappingMode = MappingMode.structured;
		}
		else {
			context.choiceMappingMode = MappingMode.flat;
		}
		
		if (request.getExposeSequence()) {
			context.sequenceMappingMode = MappingMode.structured;
		}
		else {
			context.sequenceMappingMode = MappingMode.flat;
		}
	}


	@Override
	public SchemaRegistry getBackingRegistryForSchema(Schema schema) {
		return schemaToBackingRegistryMap.get(schema);
	}


	@Override
	public void setBackingRegistryForSchema(Schema schema, SchemaRegistry registry) {
		schemaToBackingRegistryMap.put(schema, registry);
	}
	
	@Override
	public Map<String, GmType> getExtractedTypes() {
		Map<String, GmType> result = new HashMap<>();
		for (GmType type : mapper.getExtractedTypes()) {
			if (
					type.typeKind() == GmTypeKind.ENTITY ||
					type.typeKind() == GmTypeKind.ENUM
			   ) {
				String signature = type.getTypeSignature();
				result.put(signature, type);
			}
		}
		// 
		for (GmType type : mapper.getActuallySubstitutedTypes()) {
			String signature = type.getTypeSignature();
			result.put(signature, type);
		}
		return result;
	}
	@Override
	public List<GmMetaModel> getActualSubstitutionModels() {
		List<GmMetaModel> result = new ArrayList<>();
		for (GmType type : mapper.getActuallySubstitutedTypes()) {
			result.add( type.getDeclaringModel());
		}
		return result;
	}

	
	@Override
	public Map<String, GmType> getExtractedTopLevelElements() {
		return mapper.getTopLevelElementToTypeAssociation();
	}


	@Override
	public QPath generateQPathForSchemaEntity(Schema sourceSchema) {
		QPath qpath = new QPath();			
		SchemaRegistry registryForSchema = getBackingRegistryForSchema(sourceSchema);
		if (registryForSchema == null) {
			//throw new IllegalStateException("no registry found for passed schema [" + sourceSchema + "]");
			qpath.setPath( "");
			return qpath;
		}
		SchemaRegistry parentRegistry = registryForSchema.getParentSchemaRegistry();
		
		if (parentRegistry == null) {
			// top level 
			qpath.setPath( "");
			return qpath;
		}
		List<String> namespaces = new ArrayList<>();
		do {
			String namespace = parentRegistry.getNamespaceForSchema( sourceSchema);
			if (namespace == null) {
				break;
			}
			namespaces.add(namespace);
			sourceSchema = parentRegistry.getSchema();
			parentRegistry = parentRegistry.getParentSchemaRegistry();
			
		} while (parentRegistry != null);
		
		StringBuilder builder = new StringBuilder();
		for (int i = namespaces.size()-1; i >= 0; i--) {
			if (builder.length() > 0) {
				builder.append( '.');
			}
			builder.append( namespaces.get(i));
		}
		qpath.setPath( builder.toString());
		
		// sanitize ..
		
		
		return qpath;
	}


	@Override
	public String getTargetNamespace() {
		Namespace targetNamespace = mainSchemaRegistry.getSchema().getTargetNamespace();
		if (targetNamespace != null)
			return targetNamespace.getUri();
		return null;
	}


	@Override
	public Map<String, String> getPrefixToNamespacesMap() {
		Map<String, String> result = new HashMap<>();
		namespaces = new HashMap<>();
		Map<String, SchemaRegistry> importedSchemata = mainSchemaRegistry.getImportedSchemata();
		for (Entry<String, SchemaRegistry> entry : importedSchemata.entrySet()) {			
			SchemaRegistry schemaRegistry = entry.getValue();
						
			Namespace targetNamespace = schemaRegistry.getTargetNamespace();
		
			String prefix = targetNamespace.getPrefix();
			if (prefix != null) {
				result.put(prefix, targetNamespace.getUri());
			}
			else {
				Schema schema = schemaRegistry.getSchema();
				if (schema.getElementFormDefault() == Qualification.qualified || schema.getAttributeFormDefault() == Qualification.qualified) {
					log.error( "qualification required for namespace [" + targetNamespace.getUri() + "], yet no prefix for the target namespace is declared");
				}
				else {
					log.warn( "[" + targetNamespace.getUri() +"] is a target namespace, yet has not prefix assigned");
				}
			}
			namespaces.put( targetNamespace.getUri(), targetNamespace);						
			
		}
		// add the current namespace
		Namespace targetNamespace = mainSchemaRegistry.getSchema().getTargetNamespace();
		if (targetNamespace != null) {
			result.put( targetNamespace.getPrefix(), targetNamespace.getUri());
			namespaces.put( targetNamespace.getUri(), targetNamespace);
		}
		return result;
	}
	
	@Override
	public Map<String, Namespace> getNamespaces() {
		if (namespaces == null) 
			getPrefixToNamespacesMap();
		return namespaces;
	}


	@Override
	public boolean getElementQualified() {	
		return elementQualified;
	}


	@Override
	public boolean getAttributeQualified() {
		return attributeQualified;
	}
	
	
	
	
	
	 
}
