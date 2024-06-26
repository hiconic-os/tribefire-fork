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
package com.braintribe.devrock.artifactcontainer.ui.intelligence.manual;

import java.util.List;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.braintribe.build.artifact.retrieval.multi.repository.reflection.impl.bias.ArtifactBias;
import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;

public class SolutionBiasColumnLabelProvider extends AbstractSolutionBiasLabelProvider {
	
	private Font font;
	private Image image;
	
	
	@Configurable @Required
	public void setFont(Font font) {
		this.font = font;
	}
	
	@Configurable @Required
	public void setImage(Image image) {
		this.image = image;
	}
	
	
	
	
	@Override
	public Font getFont(Object element) {
		if (getBias( element) != null)
			return font;
		return super.getFont(element);
	}



	@Override
	public Image getImage(Object element) {
		if (getBias( element) != null) {
			return image;
		}
		return super.getImage(element);
	}



	@Override
	public String getText(Object element) {
		return null;
		/*
		ArtifactBias artifactBias = getBias(element);
		if (artifactBias != null) {
			return "*";
		}
		return "";
		*/
	}

	@Override
	public String getToolTipText(Object element) {	
		ArtifactBias artifactBias = getBias(element);
		if (artifactBias == null) {
			return "no bias";
		}
		StringBuffer buffer = new StringBuffer();
		
		if (artifactBias.hasLocalBias()) {
			buffer.append( "local");
		}
		List<String> activeRepositories = artifactBias.getActiveRepositories();
		if (activeRepositories != null && activeRepositories.size() > 0) {
			if (buffer.length() > 0) 
				buffer.append(';');
			buffer.append("positive:");
			boolean first = true;
			for (String activeRepository : activeRepositories) {
				if (!first) {
					buffer.append( ",");
					first = false;
				}
				buffer.append( activeRepository);
			}
			
		}
		List<String> inactiveRepositories = artifactBias.getInactiveRepositories();
		if (inactiveRepositories != null && inactiveRepositories.size() > 0) {
			if (buffer.length() > 0) 
				buffer.append(';');
			buffer.append("negative:");
			boolean first = true;
			for (String inactiveRepository : inactiveRepositories) {
				if (!first) {
					buffer.append( ",");
					first = false;
				}
				buffer.append( inactiveRepository);
			}
		}
		String tooltip = buffer.toString();
		return tooltip;
	}
}
