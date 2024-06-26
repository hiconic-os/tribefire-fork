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
package com.braintribe.gwt.genericmodelgxtsupport.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gxt.gxtresources.components.client.GmeToolTip;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.validation.Validation;
import com.braintribe.model.generic.validation.ValidatorResult;
import com.braintribe.model.generic.validation.log.ValidationLog;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.core.client.Style.Side;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;
import com.sencha.gxt.widget.core.client.form.ValueBaseField;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

public class GmMaxNumberValidator<N extends Number> extends MaxNumberValidator<N> {

	private PropertyFieldContext propertyFieldContext;
	private Validation validation;	
	private Field<?> field;
	private GmeToolTip tip;

	public GmMaxNumberValidator(N maxNumber) {
		super(maxNumber);
	}

	public PropertyFieldContext getPropertyFieldContext() {
		return propertyFieldContext;
	}

	public void setPropertyFieldContext(PropertyFieldContext propertyFieldContext) {
		this.propertyFieldContext = propertyFieldContext;
	}	
	
	@Override
	public List<EditorError> validate(Editor<N> field, N value) {
	    if (value == null)
	    	return null;

	    if (value.doubleValue() > maxNumber.doubleValue()) {
			if (validation != null) {
				Future<ValidationLog> valRes = validation.validatePropertyValue(propertyFieldContext.getParentEntity(), propertyFieldContext.getPropertyName(), value);
				valRes.andThen(result -> {
					String validationRes = 	doValidation(result);
					if (validationRes != null && !validationRes.isEmpty()) {
						tip = prepareToolTip(validationRes);
						tip.showAtHoverElement();
					} else {
						if (tip != null && tip.isVisible())
							tip.hide();
					}
				});	
				return null;
			}

		    String maxText;
		    if (field instanceof ValueBaseField) {
		        ValueBaseField<N> vbf = (ValueBaseField<N>) field;
		        PropertyEditor<N> pe = vbf.getPropertyEditor();
		        maxText = pe.render(maxNumber);
		    } else {
		        maxText = maxNumber.toString();
		    }
			
			tip = prepareToolTip(getMessages().numberMaxText(maxText));
			tip.showAtHoverElement();			
			return null;
	    } else {
			if (tip != null && tip.isVisible())
				tip.hide();	    	
	    }
	    
	    return null;
	}
	
	public Validation getValidation() {
		return validation;
	}

	public void setValidation(Validation validation) {
		this.validation = validation;
	}
	
	private String doValidation(ValidationLog validationLog) {
		if (propertyFieldContext.getPropertyName() == null)
			return null;
		
		for (Entry<GenericEntity, ArrayList<ValidatorResult>> validationEntry : validationLog.entrySet()) {
			GenericEntity parentEntity = validationEntry.getKey();
			if (!parentEntity.equals(propertyFieldContext.getParentEntity()))
				continue;
			
			for ( ValidatorResult validatorResult : validationEntry.getValue()) {
				String failPropertyName = validatorResult.getPropertyName();
				if (failPropertyName == null)
					continue;
				
				if (propertyFieldContext.getPropertyName().equals(failPropertyName)) {
					return validatorResult.getResultMessage();
				}
				
			}
		}		
		return null;
	}

	private GmeToolTip prepareToolTip(String toolTipText) {
		if (tip == null) {
			ToolTipConfig toolTipConfig = new ToolTipConfig(toolTipText);
			toolTipConfig.setAutoHide(false);						
			toolTipConfig.setAnchorArrow(true);
			toolTipConfig.setAnchor(Side.BOTTOM);
			toolTipConfig.setAnchorToTarget(true);
			
			tip = new GmeToolTip(field, field.getElement(), toolTipConfig);
			tip.setShowOnHover(false);
			tip.addStyleName("failValidationPropertyToolTip");			
		} else {
			tip.getToolTipConfig().setBody(toolTipText);
		}
		
		return tip;
	}
	
	public Field<?> getField() {
		return field;
	}

	public void setField(Field<?> field) {
		this.field = field;
	}
	
}
