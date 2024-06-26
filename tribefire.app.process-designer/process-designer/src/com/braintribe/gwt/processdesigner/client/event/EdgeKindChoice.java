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
package com.braintribe.gwt.processdesigner.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.braintribe.gwt.processdesigner.client.ProcessDesignerConfiguration;
import com.braintribe.gwt.processdesigner.client.ProcessDesignerRenderer;
import com.braintribe.gwt.processdesigner.client.element.AbstractProcessSvgElement;
import com.braintribe.gwt.processdesigner.client.element.EdgeSvgElement;
import com.braintribe.gwt.processdesigner.client.element.NodeDivElement;
import com.braintribe.gwt.processdesigner.client.resources.ProcessDesignerResources;
import com.braintribe.model.generic.GenericEntity;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.widget.core.client.tips.ToolTip;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

public class EdgeKindChoice extends FlowPanel{
	
	public enum ElementKind {standardNode, restartNode, edge, processDef}
	public enum EdgeKind {normal, conditional, restart, overdue, error}
	
	//private Map<EdgeKind, OMSVGPathElement> edgeKinds;
	private Map<EdgeKind, String> edgeKindDescription;
	private Map<EdgeKind, ImageResource> edgeKindIcon;
	
	//private ProcessDesignerConfiguration pdc;
	private ProcessDesignerRenderer renderer;
	//private FlowPanel wrapper;
	
	private List<HandlerRegistration> handlerRegistrations = new ArrayList<>();
	
	public EdgeKindChoice() {		
		//edgeKinds = new HashMap<>();	
		edgeKindDescription = new HashMap<>();
		edgeKindIcon = new HashMap<>();
		
		edgeKindDescription.put(EdgeKind.conditional, "Add conditional edge");
		edgeKindDescription.put(EdgeKind.error, "Set error node");
		edgeKindDescription.put(EdgeKind.normal, "Add edge");
		edgeKindDescription.put(EdgeKind.overdue, "Set overdue node");
		edgeKindDescription.put(EdgeKind.restart, "Set restart edge");
		
		edgeKindIcon.put(EdgeKind.conditional, ProcessDesignerResources.INSTANCE.conditionalEdge());
		edgeKindIcon.put(EdgeKind.error, ProcessDesignerResources.INSTANCE.errorNode());
		edgeKindIcon.put(EdgeKind.normal, ProcessDesignerResources.INSTANCE.edge());
		edgeKindIcon.put(EdgeKind.overdue, ProcessDesignerResources.INSTANCE.overdueNode());
		edgeKindIcon.put(EdgeKind.restart, ProcessDesignerResources.INSTANCE.edge());
		
		addStyleName("edgeKindChoice");
	}
	
	@SuppressWarnings("unused")
	public void setPdc(ProcessDesignerConfiguration pdc) {
		//this.pdc = pdc;
	}
	
	public void setRenderer(ProcessDesignerRenderer renderer) {
		this.renderer = renderer;
	}
	
	@SuppressWarnings("unused")
	public void show(AbstractProcessSvgElement<?> element, GenericEntity from, GenericEntity to, ElementKind fromKind, ElementKind toKind){
		List<EdgeKind> validKinds = getValidKinds(fromKind, toKind);
		clear();
		
		Map<Integer, FlowPanel> wrappers = new HashMap<Integer, FlowPanel>();
		
		for(int i = 0; i<validKinds.size();i++) {
			
			FlowPanel fp = wrappers.get(i / 2);
			
			if(fp == null) {
				fp = new FlowPanel();
				wrappers.put(i / 2, fp);
				fp.addStyleName("edgeKindChoiceWrapper");
				add(fp);
			}
			
			EdgeKind edgeKind = validKinds.get(i);
			FlowPanel edgeKindPanel = new FlowPanel();
			edgeKindPanel.addStyleName("edgeKindChoiceElement");
			handlerRegistrations.add(edgeKindPanel.addDomHandler(event -> {
				renderer.addEdge(from, to, edgeKind);
				renderer.getProcessDefintionElementEventHandler().onMouseUp(event);
				hide();
			}, MouseUpEvent.getType()));
			
			Image icon = new Image(edgeKindIcon.get(edgeKind));
			//edgeKindPanel.getElement().setInnerText(edgeKindDescription.get(edgeKind));
//			edgeKindPanel.getElement().setAttribute("title", edgeKindDescription.get(edgeKind));
			
			new ToolTip(edgeKindPanel, new ToolTipConfig(edgeKindDescription.get(edgeKind)));
			
			edgeKindPanel.add(icon);
			fp.add(edgeKindPanel);
		}
		
		double x = 0,y = 0, height = 0, width = 0;
		if(element instanceof NodeDivElement) {
			NodeDivElement nodeDivElement = (NodeDivElement) element;
			x = nodeDivElement.getX();
			y = nodeDivElement.getY();
			height = nodeDivElement.getHeight();
			width = nodeDivElement.getWidth();
		}
		else if(element instanceof EdgeSvgElement){
			EdgeSvgElement edgeSvgElement = (EdgeSvgElement)element;
			x = edgeSvgElement.getCenterX() - 50;
			y = edgeSvgElement.getCenterY() - 50;
			height = 100;
			width = 100;			
		}
		
		Style style = getElement().getStyle();
		
		style.setLeft(x, Unit.PX);
		style.setTop(y, Unit.PX);
		style.setWidth(width, Unit.PX);
		style.setHeight(height, Unit.PX);
	}
	
	public void hide() {
		removeFromParent();
	}
		
	public void dispose(){
		for(HandlerRegistration handlerRegistration : handlerRegistrations){
			handlerRegistration.removeHandler();
		}
		handlerRegistrations.clear();
	}
	
	private List<EdgeKind> getValidKinds(ElementKind fromKind, ElementKind toKind){
		List<EdgeKind> edgeKinds = new ArrayList<EdgeKindChoice.EdgeKind>();
		
		if(fromKind == ElementKind.standardNode && (toKind == ElementKind.standardNode || toKind == ElementKind.restartNode)){
			edgeKinds.add(EdgeKind.normal);
			edgeKinds.add(EdgeKind.conditional);
			edgeKinds.add(EdgeKind.error);
			edgeKinds.add(EdgeKind.overdue);			
		}else if((fromKind == ElementKind.restartNode && (toKind == ElementKind.standardNode || toKind == ElementKind.restartNode))){
			edgeKinds.add(EdgeKind.error);
			edgeKinds.add(EdgeKind.overdue);
		}else if(fromKind == ElementKind.restartNode && toKind == ElementKind.edge){
			edgeKinds.add(EdgeKind.restart);
		}else if(fromKind == ElementKind.processDef && (toKind == ElementKind.restartNode || toKind == ElementKind.standardNode)){
			edgeKinds.add(EdgeKind.error);
			edgeKinds.add(EdgeKind.overdue);
		}
		
		return edgeKinds;
	}
	
}
