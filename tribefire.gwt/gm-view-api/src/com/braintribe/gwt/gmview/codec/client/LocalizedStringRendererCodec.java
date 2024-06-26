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

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.utils.i18n.I18nTools;

/**
 * This codec is responsible for transforming a LocalizedString into String,
 * for visualization only purpose.
 * @author michel.docouto
 * 
 */
public class LocalizedStringRendererCodec implements Codec<LocalizedString, String> {

	@Override
	public LocalizedString decode(String encodedValue) throws CodecException {
		throw new CodecException("Decode is not supported");
	}

	@Override
	public String encode(LocalizedString localizedString) throws CodecException {
		return I18nTools.getDefault(localizedString, "");
	}

	@Override
	public Class<LocalizedString> getValueClass() {
		return LocalizedString.class;
	}

}
