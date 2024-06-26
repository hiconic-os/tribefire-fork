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
package com.braintribe.test.framework.resultscanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.braintribe.build.artifact.name.NameParser;
import com.braintribe.build.artifact.name.NameParserException;
import com.braintribe.model.artifact.Artifact;
import com.braintribe.model.artifact.processing.version.VersionProcessor;
import com.braintribe.utils.xml.dom.DomUtils;
import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

public class Scanner {

	public Scanner() {
		
	}

	List<File> parseForPoms( File directory) {
		List<File> result = new ArrayList<File>();
		File [] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				result.addAll( parseForPoms(file));
			}
			else {
				if (file.getName().endsWith( ".pom")) {
					result.add( file);
				}
			}			
		}
		return result;
	}
	
	List<File> parseForDependency( List<File> poms, Artifact artifact) {
		List<File> result = new ArrayList<File>();
		String groupId = artifact.getGroupId();
		String artifactId = artifact.getArtifactId();
		String version = VersionProcessor.toString( artifact.getVersion());
		
		for (File pom : poms) {
			Document doc;
			try {
				doc = DomParser.load().from(pom);
			} catch (DomParserException e) {
				System.err.println();
				continue;
			}
			NodeList nodes = doc.getDocumentElement().getElementsByTagName("dependency");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (
						groupId.equalsIgnoreCase( DomUtils.getElementValueByPath(element, "groupId", false)) &&
						artifactId.equalsIgnoreCase( DomUtils.getElementValueByPath(element, "artifactId", false)) &&
						version.equalsIgnoreCase( DomUtils.getElementValueByPath(element, "version", false))
					) {
					result.add(pom);
				}
			}
		}
		return result;		
	}
	
	List<File> parseForParent( List<File> poms, Artifact artifact) {
		List<File> result = new ArrayList<File>();
		String groupId = artifact.getGroupId();
		String artifactId = artifact.getArtifactId();
		String version = VersionProcessor.toString( artifact.getVersion());
		
		for (File pom : poms) {
			Document doc;
			try {
				doc = DomParser.load().from(pom);
			} catch (DomParserException e) {
				System.err.println();
				continue;
			}
			NodeList nodes = doc.getDocumentElement().getElementsByTagName("parent");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (
						groupId.equalsIgnoreCase( DomUtils.getElementValueByPath(element, "groupId", false)) &&
						artifactId.equalsIgnoreCase( DomUtils.getElementValueByPath(element, "artifactId", false)) &&
						version.equalsIgnoreCase( DomUtils.getElementValueByPath(element, "version", false))
					) {
					result.add(pom);
				}
			}
		}
		return result;		
	}
	
	public static void main( String [] args) {		
		File root = new File( System.getenv( "M2_REPO"));
		Scanner scanner = new Scanner();
		List<File> poms = scanner.parseForPoms(root);
		System.out.println("\nFound [" + poms.size() + "] poms");
		
		for (String arg : args) {
			if (arg.equalsIgnoreCase( "-"))
				break;
			if (arg.equalsIgnoreCase(";")) {
				continue;
			}
			System.out.println(arg);
			Artifact artifact;
			try {
				artifact = NameParser.parseCondensedArtifact(arg);				
			} catch (NameParserException e) {
				throw new IllegalArgumentException("cannot parse [" + arg + "]");
			}
			
			List<File> result = scanner.parseForDependency(poms, artifact);
			System.out.println("Found [" + result.size() + "] matches on dependency");
			for (File file : result) {
				System.out.println( file.getAbsolutePath());
			}			
			result = scanner.parseForParent(poms, artifact);
			System.out.println("Found [" + result.size() + "] matches on parent");
			for (File file : result) {
				System.out.println( file.getAbsolutePath());
			}			
			
		}
	}
}
