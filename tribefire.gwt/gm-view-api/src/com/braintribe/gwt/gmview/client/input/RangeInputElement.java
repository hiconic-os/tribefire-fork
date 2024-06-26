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
package com.braintribe.gwt.gmview.client.input;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasValue;

public abstract class RangeInputElement<T> extends FlowPanel implements HasValue<T>, HasName {
	
	List<ValueChangeHandler<T>> handlers = new ArrayList<>();
//	private boolean suppressFire = false;

	public RangeInputElement() {
		super("input");
		getElement().setAttribute("type", "range");
		
		addInputHandler(value -> fireValueChange());
		
		setMin(0);
		setStep(1);
	}
	
	public void setEnabled(boolean enabled) {
	    getElement().setPropertyBoolean("disabled", !enabled);
	}

	public void setMin(double min) {
		getElement().setAttribute("min", min + "");
	}
	
	public void setMax(double max) {
		getElement().setAttribute("max", max + "");
	}
	
	public void setStep(double step) {
		getElement().setAttribute("step", step + "");
	}
	
	@Override
	public void setName(String name) {
		getElement().setAttribute("name", name);
	}
	
	@Override
	public String getName() {
		return getElement().getAttribute("name");
	}
	
	public void addInputHandler(InputEventHandler<T> handler){
		_addInputHandler(getElement(), handler);
	}
	
	public native void _addInputHandler(Element el, InputEventHandler<T> handler) /*-{
		el.addEventListener("input", handler);
	}-*/;
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		handlers.add(handler);
		return () -> handlers.remove(handler);		
	}

	@Override
	public T getValue() {
		return parse(_getValue(getElement()));
	}
	
	public abstract T parse(String v);
	
	private final native String _getValue(Element el) /*-{
		return el.value;
	}-*/;	

	@Override
	public void setValue(T value) {
		setValue(value, true);
	}	

	@Override
	public void setValue(T value, boolean fireEvents) {
//		this.suppressFire = !fireEvents;
		_setValue(getElement(), value);
	}
	
	private final native String _setValue(Element el, T v) /*-{
		el.value = v;				
	}-*/;
	
	protected void fireValueChange() {
		handlers.forEach(h -> {
			h.onValueChange(new RangeInputChangeEvent(getValue()));
		});	
	}
	
	class RangeInputChangeEvent extends ValueChangeEvent<T>{
		public RangeInputChangeEvent(T value) {
			super(value);
		}
	}	

}
