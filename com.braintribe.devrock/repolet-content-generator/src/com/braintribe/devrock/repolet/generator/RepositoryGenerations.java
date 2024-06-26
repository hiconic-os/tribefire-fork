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
package com.braintribe.devrock.repolet.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.api.ScalarEntityParsers;
import com.braintribe.codec.marshaller.common.ConfigurableScalarEntityParsers;
import com.braintribe.codec.marshaller.yaml.YamlMarshaller;
import com.braintribe.devrock.model.repolet.content.Dependency;
import com.braintribe.devrock.model.repolet.content.RepoletContent;
import com.braintribe.devrock.repolet.parser.RepoletContentParser;
import com.braintribe.devrock.repolet.parser.ResourceParsers;
import com.braintribe.exception.Exceptions;
import com.braintribe.model.processing.traverse.EntityCollector;
import com.braintribe.model.resource.FileResource;
import com.braintribe.model.resource.Resource;

/**
 * static helpers to create the contents for the repolet
 * 
 * @author pit / dirk
 *
 */
public interface RepositoryGenerations {
	
	/**
	 * generate content via a YAML file (full feature support)
	 * @param configurationFile - the YAML formatted file
	 * @param targetFolder - the directory to write the content to
	 */
	static void generateFromYaml(File configurationFile, File targetFolder) {
		try {
			final RepoletContent content = unmarshallConfigurationFile(configurationFile);
			
			
			
			RepoletContentGenerator.INSTANCE.generate(targetFolder, content);
			
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "Error while generating repository content");
		}
	}
	
	/**
	 * generate content via an 'expressive' file (currently reduced feature support)
	 * @param configurationFile - the txt file with the expressive description
	 * @param targetFolder - the directory to write the content to 
	 */
	static void generateFromExpressive(File configurationFile, File targetFolder) {
		try (InputStream in = new FileInputStream(configurationFile)) {
			RepoletContent content = RepoletContentParser.INSTANCE.parse(in);
			RepoletContentGenerator.INSTANCE.generate( targetFolder, content);
		}
		catch( Exception e) {
			throw Exceptions.unchecked(e, "Error while generating repository content");
		}
	}

	/**
	 * unmarshalls the YAML formatted configuration into a descriptive content 
	 * @param configurationFile - the YAML formatted configuration file 
	 * @return - the {@link RepoletContent} created
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static RepoletContent unmarshallConfigurationFile(File configurationFile) throws IOException, FileNotFoundException {
		ConfigurableScalarEntityParsers parsers = new ConfigurableScalarEntityParsers();
		parsers.addParser(Dependency.T, Dependency::parse);
		parsers.addParser(Resource.T, ResourceParsers::parse);
		parsers.addParser(FileResource.T, ResourceParsers::parseFileResource);
		
		GmDeserializationOptions options = GmDeserializationOptions.deriveDefaults() //
				.set(ScalarEntityParsers.class, parsers) // 
				.setInferredRootType(RepoletContent.T) //
				.build(); //
		
		YamlMarshaller marshaller = new YamlMarshaller();

		final RepoletContent content;
		
		try (InputStream in = new FileInputStream(configurationFile)) {
			content = (RepoletContent) marshaller.unmarshall(in, options);
		}
		
		File baseFolder = configurationFile.getParentFile();
		EntityCollector collector = new EntityCollector();
		collector.visit(RepoletContent.T, content);
		collector.getEntities().stream()
			.filter(e -> e instanceof FileResource)
			.map(e -> (FileResource)e)
			.forEach(r -> {
				File file = new File(r.getPath());
				if (file.isAbsolute())
					return;
				
				r.setPath(new File(baseFolder, r.getPath()).getPath());
			});
		return content;
	}
	
	/**
	 * parses an expressive text file into a descriptive content
	 * @param configurationFile - the expressivly formatted text file 
	 * @return - the {@link RepoletContent} created
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static RepoletContent parseConfigurationFile(File configurationFile) throws IOException, FileNotFoundException {
		try (InputStream in = new FileInputStream(configurationFile)) {
			RepoletContent content = RepoletContentParser.INSTANCE.parse(in);
			return content;
		}
		catch( Exception e) {
			throw Exceptions.unchecked(e, "Error while generating repository content " + e.getLocalizedMessage());
		}
	}
	
}
