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
package com.braintribe.gwt.gme.constellation.client.action;

public class ShowPackagingInfoAsTextAction /*extends Action*/ {
	/*
	private Packaging packaging;
	private Window packagingWindow;
	private TextArea packagingInfo;
	private String packagingInfoString;
	private String versionString; 
	
	public ShowPackagingInfoAsTextAction() {
		setHidden(true);
		setHoverIcon(ConstellationResources.INSTANCE.infoBig());
		setTooltip(LocalizedText.INSTANCE.packagingInfoDescription());
		setName(LocalizedText.INSTANCE.showAsText());
	}
	
	public void configurePackaging(Packaging packaging) {
		this.packaging = packaging;
		setHidden(packaging == null);
	}
	
	public void configureVersionString(String versionString) {
		this.versionString = versionString;
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		getPackagingWindow().show();
	}
	
	private Window getPackagingWindow() {
		if (packagingWindow == null) {
			packagingWindow = new Window();
			packagingWindow.setBorders(false);
			packagingWindow.setBodyBorder(false);
			packagingWindow.setHeading(LocalizedText.INSTANCE.packagingInfo(versionString));
			packagingWindow.setSize("640px", "480px");
			packagingWindow.setModal(true);
			
			packagingInfo = new TextArea();
			packagingInfo.setReadOnly(true);
			
			FormPanel formPanel = new FormPanel();
			formPanel.setBorders(false);
			formPanel.add(packagingInfo);
			packagingWindow.add(formPanel);
		}
		
		packagingInfo.setValue(getPackagingInfoString());
		return packagingWindow;
	}
	
	private String getPackagingInfoString() {
		if (packagingInfoString != null)
			return packagingInfoString;
		
		StringBuilder builder = new StringBuilder();
		
		if (!versionString.isEmpty())
			builder.append(versionString).append("\n\n");
		
		Artifact terminalArtifact = packaging.getTerminalArtifact();
		
		builder.append(LocalizedText.INSTANCE.packagingInfoFor(terminalArtifact.getGroupId() + ":" + terminalArtifact.getArtifactId() + "-" + terminalArtifact.getVersion()));
		
		if (packaging.getDependencies() != null) {
			builder.append("\n\n").append(LocalizedText.INSTANCE.dependencies()).append("\n");
			for (Artifact artifact : packaging.getDependencies()) {
				builder.append("\n");
				builder.append(artifact.getGroupId() + ":" + artifact.getArtifactId() + "-" + artifact.getVersion());
			}
		}
		
		packagingInfoString = builder.toString();
		return packagingInfoString;
	}*/

}
