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
package com.braintribe.gwt.processdesigner.client.element;

import java.util.Set;

import com.braintribe.gwt.geom.client.Rect;
import com.braintribe.gwt.processdesigner.client.event.AbstractEventHandler;
import com.braintribe.gwt.processdesigner.client.event.EdgeKindChoice.ElementKind;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.tracking.ManipulationListener;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public interface ProcessSvgElement<G extends GenericEntity> extends ManipulationListener, MouseDownHandler, MouseUpHandler, MouseMoveHandler, MouseOverHandler, MouseOutHandler, ClickHandler{
	
	public void setEntity(G genericEntity);
	public GenericEntity getEntity();
	public void setSession(PersistenceGmSession session);
	public PersistenceGmSession getSession();
	public Object getRepresentation();
	public Set<HandlerRegistration> registerHandlers(AbstractEventHandler... handlers);
	public Set<HandlerRegistration> getHandlers();
	
	public void setX(double x);
	public double getX();
	public void setY(double y);	
	public double getY();
	
	public void setX2(double x2);
	public double getX2();
	public void setY2(double y2);	
	public double getY2();
	
	public void setCenterX(double centerX);
	public double getCenterX();
	public void setCenterY(double centerY);
	public double getCenterY();
	
	public void setWidth(double width);
	public double getWidth();
	public void setHeight(double height);
	public double getHeight();
	
	public void handleSelectionChange();
	public void handleHoveringChange();
	public void handleActiveChange();
	
	public void initialize();
	public void dispose();
	
	public boolean doesIntersect(Rect mouseRect);
	
	public void commit();
	
	public String getDescription();
	public ElementKind getElementKind();
	
	public boolean canBeConnected();
}
