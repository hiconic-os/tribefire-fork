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
package com.braintribe.gwt.processdesigner.client;

import com.braintribe.gwt.processdesigner.client.vector.Point;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ProcessDesignerConfiguration extends GenericEntity {

	final EntityType<ProcessDesignerConfiguration> T = EntityTypes.T(ProcessDesignerConfiguration.class);

	// @formatter:off
	@Initializer("12d")
	double getFontSize();
	void setFontSize(double fontSize);

	double getOutterMargin();
	void setOutterMargin(double outterMargin);

	@Initializer("15d")
	double getGridResolution();
	void setGridResolution(double gridResolution);

	@Initializer("35d")
	double getProcessNodeRadius();
	void setProcessNodeRadius(double processNodeRadius);

	@Initializer("3d")
	double getProcessNodeStrokeWidth();
	void setProcessNodeStrokeWidth(double processNodeStrokeWidth);

	@Initializer("'#e3e3e3'")
	String getProcessNodeColor();
	void setProcessNodeColor(String processNodeColor);	

	@Initializer("'silver'")
	String getProcessNodeStrokeColor();
	void setProcessNodeStrokeColor(String processNodeStrokeColor);	

	@Initializer("10d")
	double getDockingPointRadius();
	void setDockingPointRadius(double dockingPointRadius);

	@Initializer("3d")
	double getDockingPointStrokeWidth();
	void setDockingPointStrokeWidth(double dockingPointStrokeWidth);

	@Initializer("'#e3e3e3'")
	String getDockingPointColor();
	void setDockingPointColor(String dockingPointColor);

	@Initializer("'silver'")
	String getDockingPointStrokeColor();
	void setDockingPointStrokeColor(String dockingPointStrokeColor);

	@Initializer("2d")
	double getEdgeWidth();
	void setEdgeWidth(double edgeWidth);

	@Initializer("'#949494'")
	String getEdgeColor();
	void setEdgeColor(String edgeColor);
	
	@Initializer("25d")
	double getDefaultOffset();
	void setDefaultOffset(double defaultOffset);

	// TODO DV public Point defaultStartingPoint = new Point();
	Point getDefaultStartingPoint();
	void setDefaultStartingPoint(Point defaultStartingPoint);
	
	@Initializer("true")
	boolean getRenderNodes();
	void setRenderNodes(boolean renderNodes);

	@Initializer("true")
	boolean getRenderEdges();
	void setRenderEdges(boolean renderEdges);

	boolean getSnapToGrid();
	void setSnapToGrid(boolean snapToGrid);

	boolean getShowGridLines();
	void setShowGridLines(boolean showGridLines);

	@Initializer("10d")
	double getArrowHeight();
	void setArrowHeight(double arrowHeight);

	@Initializer("5d")
	double getArrowWidth();
	void setArrowWidth(double arrowWidth);

	@Initializer("enum(com.braintribe.gwt.processdesigner.client.ProcessDesignerMode,selecting)")
	ProcessDesignerMode getProcessDesignerMode();
	void setProcessDesignerMode(ProcessDesignerMode processDesignerMode);

	@Initializer("1d")
	double getScaleLevel();
	void setScaleLevel(double scaleLevel);

	@Initializer("0.25d")
	double getScaleChangeFactor();
	void setScaleChangeFactor(double scaleChangeFactor);
	// @formatter:on
}
