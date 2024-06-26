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
package tribefire.extension.xml.schemed.requestbuilder.builder.impl;

import java.io.File;
import java.util.function.Supplier;

import com.braintribe.model.resource.Resource;

import tribefire.extension.xml.schemed.requestbuilder.resource.ResourceGenerator;
import tribefire.extension.xml.schemed.requestbuilder.resource.ResourceProvidingSession;

/**
 * a context to handle the input XSDs 
 * @author pit
 *
 * @param <T>
 */
public class XsdContext<T extends XsdResourceConsumer> {		
	private T consumer;
	private Supplier<ResourceProvidingSession> sessionSupplier;
	private Resource resource;
	private String terminalName;
	
	
	private ResourceProvidingSession getSession() {		
		return sessionSupplier.get();		
	}
	public XsdContext(T consumer, Supplier<ResourceProvidingSession> sessionSuplier) {
		this.consumer = consumer;
		this.sessionSupplier = sessionSuplier;
	}
	
	

	/**
	 * the simplest case : the single XSD (or the main XSD if references are used)
	 * @param file - the {@link File} that contains the XSD
	 * @return - this context
	 */
	public XsdContext<T> file(File file) {
		resource = ResourceGenerator.filesystemResourceFromFile( getSession(), file);
		return this;
	}
	



	/**
	 * if you have multiple XSDs in a zip file, use this: specifiy the zip-file and the name of
	 * the top XSD (just the last part of the ZipEntry's name
	 * @param file - the zipfile
	 * @param main - the name of top XSD file
	 * @return
	 */
	public XsdContext<T> archive( File file, String main) {
		resource = ResourceGenerator.filesystemResourceFromFile( getSession(), file);
		terminalName = main;
		return this;
	}
	


	/**
	 * finish and return to parent context
	 * @return - the parent context
	 */
	public T close() {
		if (terminalName == null) {
			consumer.accept(resource);
		}
		else {
			consumer.accept(resource, terminalName);
		}
		return consumer;
	}
}
