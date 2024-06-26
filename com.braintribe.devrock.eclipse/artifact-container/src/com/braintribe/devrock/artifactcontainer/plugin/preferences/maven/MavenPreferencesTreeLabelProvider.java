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
package com.braintribe.devrock.artifactcontainer.plugin.preferences.maven;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import com.braintribe.model.maven.settings.Mirror;
import com.braintribe.model.maven.settings.Profile;
import com.braintribe.model.maven.settings.Repository;
import com.braintribe.model.maven.settings.Server;

public class MavenPreferencesTreeLabelProvider extends LabelProvider implements IStyledLabelProvider, ToolTipProvider {
	
	private MavenPreferencesTreeRegistry registry;
	private String key;
		
	public MavenPreferencesTreeLabelProvider(MavenPreferencesTreeRegistry registry, String key) {
		this.key = key;
		this.registry = registry;
	}	

	@Override
	public StyledString getStyledText(Object suspect) {
		if (suspect instanceof Profile) {
			if (key.equals( "Profile")) {
				Profile profile = (Profile)suspect;
				StyledString styledString;
				if (registry.isProfileActive(profile)) {
					styledString = new StyledString( profile.getId());
				}
				else {
					styledString = new StyledString( profile.getId(), StyledString.QUALIFIER_STYLER);
				}
				String origin = registry.getOriginOfProfile(profile);
				if (origin != null) {
					styledString.append( " (" + origin + ")", StyledString.COUNTER_STYLER);
				}
				return styledString;
			}
		}
		else if (suspect instanceof Repository) {
			Repository repository = (Repository) suspect;
			if (key.equals( "Profile")) 
				return new StyledString();
			
			if (key.equalsIgnoreCase("Repository"))
				return new StyledString( repository.getId());
			
			if (key.equalsIgnoreCase("Url")) {
				
				Mirror mirror = registry.getMirror( repository);
				if (mirror == null) {
					return new StyledString( repository.getUrl());
				}
				else {
					return new StyledString( mirror.getUrl(), StyledString.COUNTER_STYLER);
				}
			}
			
			if (key.equalsIgnoreCase("Mirror")) {
				Mirror mirror = registry.getMirror( repository);
				if (mirror != null)
					return new StyledString( mirror.getId());
			}
			if (key.equalsIgnoreCase("Server")) {
				Server server = registry.getServer( repository);
				if (server != null) 
					return new StyledString( server.getId());
			}
			
		}
		return new StyledString();
	}

	@Override
	public String getToolTipText(Object element) {	
		if (key.equals( "Profile")) {
			Profile profile = (Profile) element;
			String origin = registry.getOriginOfProfile(profile);
			return profile.getId() + " from " + origin;						
		}
		if (element instanceof Repository) {
			Repository repository = (Repository) element;
			if (key.equals( "Profile")) 
				return null;
			
			if (key.equalsIgnoreCase("Repository")) {
				String name = repository.getName();
				String id = repository.getId();
				if (name != null)
					return name + "(id)";
				else
					return id;
			}
				
			if (key.equalsIgnoreCase("Url")) {
				Mirror mirror = registry.getMirror( repository);
				if (mirror == null) {
					return repository.getUrl();
				}
				else {
					return repository.getUrl() + " overriden by mirror " + mirror.getId() + "'s URL " + mirror.getUrl();
				}
			}
			
			if (key.equalsIgnoreCase("Mirror")) {
				Mirror mirror = registry.getMirror( repository);
				if (mirror != null) {
					return mirror.getId();
				}
			}
			if (key.equalsIgnoreCase("Server")) {
				Server server = registry.getServer( repository);
				if (server != null) 
					return server.getId();
			}
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof Profile) {
			
		}
		
		return super.getImage(element);
	}
	
	

}
