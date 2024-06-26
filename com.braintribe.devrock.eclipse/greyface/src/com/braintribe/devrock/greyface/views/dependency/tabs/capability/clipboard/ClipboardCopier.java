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
package com.braintribe.devrock.greyface.views.dependency.tabs.capability.clipboard;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.braintribe.devrock.greyface.view.tab.HasTreeTokens;
import com.braintribe.model.artifact.Part;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.artifact.processing.version.VersionProcessor;

public class ClipboardCopier implements HasTreeTokens {
	private final static int buflen = 40;
	
	public static String copyToClipboard( Tree tree) {
		TreeItem [] items = tree.getItems();
		StringBuilder builder = new StringBuilder();
		int depth = 0;
		for (TreeItem item : items) {
			builder.append( process( item, depth));
		}
		return builder.toString();
	}

	public static String processArtifact( Solution solution, int depth) {
		Set<String> processedLocations = new HashSet<String>();
		StringBuilder builder = new StringBuilder();
		for (Part part : solution.getParts()) {
			String location = part.getLocation();			
			if (processedLocations.contains( location))
				continue;
				processedLocations.add(location);
			if (builder.length() > 0) {
				builder.append( "\n");
			}
			for (int i = 0; i < depth; i++) {
				builder.append("\t");
			}					
			String name = new File( location).getName();
			if (name.contains("pom.gf")) {
				name = part.getArtifactId() + "-" + VersionProcessor.toString( part.getVersion()) + ".pom";
			}
			int nameLen = name.length();
			for (int i = nameLen; i < buflen; i++) {
				name += " ";
			}
			builder.append( name);
			builder.append("\t");
			builder.append( location);
		}
		return builder.toString();
	}
	
	private static Object process(TreeItem item, int depth) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			builder.append("\t");
		}
		builder.append( item.getText());
		String ttip = (String) item.getData(KEY_TOOLTIP);
		if (ttip != null) { 
			builder.append("\t" + ttip);
		}
		
		builder.append( "\n");
		TreeItem[] items = item.getItems();
		if (items != null) {
			for (TreeItem child : items) {
				
				builder.append( process( child, depth+1));
			}
		}
		return builder.toString();
	}
}
