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
package com.braintribe.gwt.gme.templateevaluation.client;

public class TemplateEvaluationDialog extends TemplateGIMADialog /*ClosableWindow implements InitializableBean, QueryProviderView<Template>*/ {
	
	@Override
	public void show() {
		applyButton.setText(LocalizedText.INSTANCE.execute());
		super.show();
	}
	
	/*private TemplateEvaluationPanel templateEvaluationPanel;
	private BorderLayoutContainer borderLayoutContainer;
	
	public TemplateEvaluationDialog() {
		setHeaderVisible(false);
		setClosable(false);
		setModal(true);
		setSize("800px", "600px");
		setOnEsc(true);
		setMaximizable(true);
		setMinWidth(560);
		setMinHeight(200);
		setBodyBorder(false);
		setBorders(false);
		
		borderLayoutContainer = new BorderLayoutContainer();
		setWidget(borderLayoutContainer);
	}
	
	@Override
	public void setOtherModeQueryProviderView(QueryProviderView<Template> otherModelQueryProviderView) {
		//Nothing to do
	}
	
	@Override
	public void modeQueryProviderViewChanged() {
		//Nothing to do
	}

	public void setTemplateEvaluationContext(TemplateEvaluationContext templateEvaluationContext) {
		this.templateEvaluationPanel.setTemplateEvaluationContext(templateEvaluationContext);
	}

	@Required
	public void setTemplateEvaluationPanel(TemplateEvaluationPanel templateEvaluationPanel) {
		this.templateEvaluationPanel = templateEvaluationPanel;
	}
	
	@Override
	public void show() {
		super.show();
		
		int currentHeight = getOffsetHeight();
		int computedHeight = Math.min(Document.get().getClientHeight(), currentHeight);
		if (computedHeight != currentHeight)
			setHeight(computedHeight);
	}

	@Override
	public void configureGmSession(PersistenceGmSession gmSession) {
		//NOP
	}
	
	@Override
	public void intializeBean() throws Exception {
		borderLayoutContainer.setCenterWidget(templateEvaluationPanel);
		forceLayout();
	}
	
	public Future<Object> getEvaluatedPrototype(){
		Future<Object> result = templateEvaluationPanel.getEvaluatedPrototype();
		result.get(new AsyncCallback<Object>() {
			@Override
			public void onFailure(Throwable caught) {
				hide();
			}
			
			@Override
			public void onSuccess(Object result) {
				hide();
			}
		});
		return result;
	}

	@Override
	public Widget getWidget() {
		return this;
	}

	@Override
	public QueryProviderContext getQueryProviderContext() {
		return templateEvaluationPanel.getQueryProviderContext();
	}

	@Override
	public void notifyQueryPerformed(QueryResult queryResult, QueryProviderContext queryProviderContext) {
		templateEvaluationPanel.notifyQueryPerformed(queryResult, queryProviderContext)	;
	}

	@Override
	public void setEntityContent(Template entityContent) {
		templateEvaluationPanel.setEntityContent(entityContent);
	}

	@Override
	public void addQueryProviderViewListener(QueryProviderViewListener listener) {
		templateEvaluationPanel.addQueryProviderViewListener(listener);	
	}

	@Override
	public void removeQueryProviderViewListener(QueryProviderViewListener listener) {
		templateEvaluationPanel.removeQueryProviderViewListener(listener);
	}
	
	@Override
	public void focusEditor() {
		//NOP
	}*/

}
