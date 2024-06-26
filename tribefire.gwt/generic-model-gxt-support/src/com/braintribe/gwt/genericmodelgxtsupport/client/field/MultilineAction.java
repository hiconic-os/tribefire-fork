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
package com.braintribe.gwt.genericmodelgxtsupport.client.field;

import java.util.Arrays;
import java.util.List;

import com.braintribe.codec.Codec;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.codec.registry.client.CodecRegistry;
import com.braintribe.gwt.genericmodelgxtsupport.client.LocalizedText;
import com.braintribe.gwt.gmview.action.client.resources.GmViewActionResources;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;
import com.braintribe.gwt.gmview.client.PropertyBean;
import com.braintribe.gwt.gmview.codec.client.PropertyRelatedCodec;
import com.braintribe.gwt.gmview.metadata.client.SelectiveInformationResolver;
import com.braintribe.gwt.gmview.util.client.GMEMetadataUtil;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.ModelPathElement;
import com.braintribe.model.generic.path.PropertyRelatedModelPathElement;
import com.braintribe.model.generic.path.RootPathElement;
import com.braintribe.model.generic.reflection.CollectionType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.meta.data.display.formatting.CodeFormatting;
import com.braintribe.model.meta.data.prompt.Name;
import com.braintribe.model.meta.data.prompt.VirtualEnum;
import com.braintribe.model.meta.data.prompt.VirtualEnumConstant;
import com.braintribe.model.processing.meta.cmd.builders.EntityMdResolver;
import com.braintribe.model.processing.meta.cmd.builders.PropertyMdResolver;
import com.braintribe.model.processing.session.api.common.GmSessions;
import com.braintribe.utils.i18n.I18nTools;

/**
 * Action used for showing text at Multiline Dialog
 *
 */
public class MultilineAction extends ModelAction {

	private ModelPath currentModelPath;
	private CodecRegistry<String> codecRegistry;
	private ExtendedStringFieldAceEditorDialog extendedStringFieldDialog;
	private Boolean readOnly = false;
	
	public MultilineAction() {
		setHidden(true);
		setIcon(GmViewActionResources.INSTANCE.multiLine());
		setName(LocalizedText.INSTANCE.multiline());
		
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar, ModelActionPosition.ContextMenu));
	}
	
	/**
	 * Configures the {@link CodecRegistry} used as renderers.
	 */
	@Required
	public void setCodecRegistry(CodecRegistry<String> codecRegistry) {
		this.codecRegistry = codecRegistry;
	}

	@Override
	protected void updateVisibility() {
		currentModelPath = null;
		if (modelPaths == null || modelPaths.size() != 1) {
			setHidden(true, true);
			return;
		}
		
		List<ModelPath> selection = modelPaths.get(0);
		for (ModelPath modelPath : selection) {
			ModelPathElement lastElement = modelPath.last();
			GenericModelType elementType = lastElement.getType();
			GenericModelType modelType = elementType.isCollection() ? ((CollectionType) elementType).getCollectionElementType() : elementType;
									
			if ((!modelType.isSimple()) && (!modelType.isEnum()) && !(modelType.getJavaType().equals(VirtualEnumConstant.class)))
				continue;
			
			if (modelType.getJavaType() == Boolean.class)
				continue;
			
			if (lastElement instanceof PropertyRelatedModelPathElement) {
				PropertyRelatedModelPathElement propertyElement = (PropertyRelatedModelPathElement) lastElement;
				GenericEntity parentEntity = propertyElement.getEntity();
				if (!(parentEntity instanceof VirtualEnum) && GMEMetadataUtil.isPropertyPassword(GmSessions.getMetaData(parentEntity)
						.entity(parentEntity).property(propertyElement.getProperty()).useCase(gmContentView.getUseCase())))
					continue;
			}
			
			currentModelPath = modelPath;
			setHidden(false, true);
			return;
		}
		
		setHidden(true, true);
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		if (currentModelPath == null)
			return;
		
		GenericModelType valueType = currentModelPath.last().getType();
		Object value = currentModelPath.last().getValue();
		String text = (value == null) ? null : value.toString();
		String headertext = null;
		String codeFormat = null;
		
		//get Dialog headerText - $property of $instance
		GenericEntity parentEntity = null; 
		Property property = null;
		for (ModelPathElement modelPathElement : currentModelPath) {
			if (modelPathElement instanceof RootPathElement && modelPathElement.getValue() instanceof GenericEntity)
				parentEntity = modelPathElement.getValue();
			if (modelPathElement instanceof PropertyRelatedModelPathElement) {
				property = ((PropertyRelatedModelPathElement) modelPathElement).getProperty();
				parentEntity = ((PropertyRelatedModelPathElement) modelPathElement).getEntity();
			}
		}
		
		if (property != null && parentEntity != null) {
			String propertyName = property.getName();
			String parentEntityName = parentEntity.entityType().getShortName();

			if (!(parentEntity instanceof VirtualEnum)) {
				EntityMdResolver entityMdResolver = GmSessions.getMetaData(parentEntity).entity(parentEntity).lenient(true);
				PropertyMdResolver propertyMdResolver = entityMdResolver != null ? entityMdResolver.property(property).useCase(gmContentView.getUseCase()) : null;			
				Name name = propertyMdResolver == null ? null : propertyMdResolver.meta(Name.T).exclusive();
				if (name != null && name.getName() != null) 
					propertyName = I18nTools.getLocalized(name.getName());
				String selectiveInformation = SelectiveInformationResolver.resolve(parentEntity.entityType(), parentEntity,
						GmSessions.getMetaData(parentEntity), gmContentView.getUseCase());		
				if (selectiveInformation != null)
					parentEntityName = selectiveInformation;
				
				CodeFormatting codeFormatting = propertyMdResolver == null ? null : propertyMdResolver.meta(CodeFormatting.T).exclusive();
				if (codeFormatting != null)
					codeFormat = codeFormatting.getCodeFormat().toString();
			}
			
			headertext = propertyName + " " + LocalizedText.INSTANCE.of() + " " + parentEntityName;
		}
		
		//get text
		Codec<Object, String> renderer = codecRegistry.getCodec(valueType.getJavaType());
		if (renderer != null) {
			if (renderer instanceof PropertyRelatedCodec && currentModelPath.last() instanceof PropertyRelatedModelPathElement) {
				PropertyRelatedModelPathElement propertyElement = (PropertyRelatedModelPathElement) currentModelPath.last();
				PropertyRelatedCodec propertyRelatedCodec = (PropertyRelatedCodec) renderer;
				propertyRelatedCodec.configureModelMdResolver(gmContentView
						.getGmSession()
						.getModelAccessory()
						.getMetaData());
				propertyRelatedCodec.configurePropertyBean(new PropertyBean(propertyElement.getProperty().getName(),
						propertyElement.getEntity(), null));
				propertyRelatedCodec.configureUseCase(gmContentView.getUseCase());
			}
			
			text = renderer.encode(value);
		} 
		
		showMultilineDialog(text, headertext, codeFormat);
	}

	private void showMultilineDialog(String text, String headerText, String codeFormat) {
		ExtendedStringFieldAceEditorDialog extendedStringFieldDialog = getExtendedStringFieldDialog();
		extendedStringFieldDialog.setString(text);
		extendedStringFieldDialog.setReadOnly(readOnly);
		extendedStringFieldDialog.setCodeFormat(codeFormat);
		if (headerText != null)
			extendedStringFieldDialog.setHeading(headerText);
		extendedStringFieldDialog.show();		
	}
	
	private ExtendedStringFieldAceEditorDialog getExtendedStringFieldDialog() {
		if (extendedStringFieldDialog != null)
			return extendedStringFieldDialog;
		
		extendedStringFieldDialog = new ExtendedStringFieldAceEditorDialog();
		return extendedStringFieldDialog;
	}
	
	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
	
}
