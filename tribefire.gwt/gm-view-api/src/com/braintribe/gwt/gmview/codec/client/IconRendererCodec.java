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
package com.braintribe.gwt.gmview.codec.client;

import java.util.Set;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.gmresourceapi.client.GmImageResource;
import com.braintribe.gwt.gmview.util.client.GMEIconUtil;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.resource.AdaptiveIcon;
import com.braintribe.model.resource.Icon;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.SimpleIcon;
import com.braintribe.model.resource.specification.PixelDimensionSpecification;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class IconRendererCodec implements Codec<Icon, String>, HtmlRenderer {
	
	private int maxWidth = 40;
	private int maxHeight = 40;
	private String accessId;
	
	/**
	 * Configures the max height to be used by the icon. Defaults to 40.
	 */
	@Configurable
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}
	
	/**
	 * Configures the max width to be used by the icon. Defaults to 40.
	 */
	@Configurable
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	
	/**
	 * Configures the optional access where the streaming will be performed.
	 * If no acessId is provided, then the original access within the session will be used.
	 */
	@Configurable
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	
	@Override
	public String encode(Icon icon) throws CodecException {
		if (icon == null)
			return null;
		
		Resource resource = null;
		if (icon instanceof SimpleIcon)
			resource = ((SimpleIcon) icon).getImage();
		else if (icon instanceof AdaptiveIcon) {
			resource = GMEIconUtil.getImageFromIcon(icon, 1, 40);
			if (resource == null) {
				Set<Resource> representations = ((AdaptiveIcon) icon).getRepresentations();
				if (representations != null) {
					for (Resource representation : representations) {
						if (resource == null)
							resource = representation;
						else {
							if (representation.getSpecification() instanceof PixelDimensionSpecification && resource.getSpecification() instanceof PixelDimensionSpecification) {
								if (((PixelDimensionSpecification) representation.getSpecification()).getPixelHeight() < ((PixelDimensionSpecification) resource.getSpecification()).getPixelHeight())
									resource = representation;
							}
						}
					}
				}
			}
		}
		
		if (resource == null || !(resource.session() instanceof ManagedGmSession))
			return null;
		
		ManagedGmSession session = (ManagedGmSession) resource.session();
		String url = accessId == null ? session.resources().url(resource).asString() : session.resources().url(resource).accessId(accessId).asString();
		GmImageResource imageResource = new GmImageResource(resource, url);
		if (imageResource.getHeight() > maxHeight || imageResource.getWidth() > maxWidth || imageResource.getWidth() == 0 || imageResource.getHeight() == 0) {
			StringBuilder imageHtml = new StringBuilder();
			imageHtml.append("<img src='").append(url).append("' width='32px' height='32px'/>");
			return imageHtml.toString();
		} else
			return AbstractImagePrototype.create(imageResource).getHTML();
	}

	@Override
	public Icon decode(String encodedValue) throws CodecException {
		throw new UnsupportedOperationException("This codec should be used as an encoder (renderer) only.");
	}

	@Override
	public Class<Icon> getValueClass() {
		return Icon.class;
	}

}
