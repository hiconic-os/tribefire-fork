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
package com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client;

import java.util.Date;
import java.util.List;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;
import com.sencha.gxt.widget.core.client.form.validator.AbstractValidator;

/**
 * This Validator is used for validating Date Fields by using a given Codec.
 * @author michel.docouto
 *
 */
public class DateCodecValidator extends AbstractValidator<String> {
	
	private Codec<Date, String> codec;
	private String patternForErrorMessage;
	
	/**
	 * Configures the required codec use for validation.
	 */
	public void setCodec(Codec<Date, String> codec) {
		this.codec = codec;
	}
	
	/**
	 * Configures the required pattern for displaying in the error message, in case a given string
	 * is not a valid date.
	 */
	public void setPatternForErrorMessage(String patternForErrorMessage) {
		this.patternForErrorMessage = patternForErrorMessage;
	}
	
	@Override
	public List<EditorError> validate(Editor<String> editor, String value) {
		try {
			codec.decode(value);
		} catch (CodecException e) {
			return createError(new DefaultEditorError(editor, LocalizedText.INSTANCE.invalidDate(patternForErrorMessage), value));
		}
		return null;
	}

}
