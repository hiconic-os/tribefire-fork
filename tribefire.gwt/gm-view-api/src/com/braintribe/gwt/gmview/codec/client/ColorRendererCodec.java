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
import com.braintribe.model.style.Color;

/**
 * This codec is responsible for transforming a {@link Color} into String,
 * for visualization only purpose.
 * @author michel.docouto
 * 
 */
public class ColorRendererCodec implements Codec<Color, String> {
	private static final String EMPTY_STRING = "";

	@Override
	public Color decode(String encodedValue) throws CodecException {
		throw new CodecException("Decode is not supported");
	}

	@Override
	public String encode(Color color) throws CodecException {
		if (color == null)
			return EMPTY_STRING;
		
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(color.getRed()).append(",").append(color.getGreen()).append(",").append(color.getBlue()).append(")");
		return builder.toString();
		/*String hRed = "00";
		String hGreen = "00";
		String hBlue = "00";
		if (color.getRed() != null)
			hRed = Integer.toHexString(color.getRed());
        if (color.getGreen() != null)
        	hGreen = Integer.toHexString(color.getGreen());
        if (color.getBlue() != null)
        	hBlue = Integer.toHexString(color.getBlue());

        if (hRed.length() == 0)
        	hRed = "00";
        else if (hRed.length() == 1)
        	hRed = "0" + hRed;
        
        if (hGreen.length() == 0) 
        	hGreen = "00";
        else if (hGreen.length() == 1) 
        	hGreen = "0" + hGreen;
        
        if (hBlue.length() == 0) 
        	hBlue = "00";
        else if (hBlue.length() == 1) 
        	hBlue = "0" + hBlue;

        return hRed + hGreen + hBlue;*/
	}

	@Override
	public Class<Color> getValueClass() {
		return Color.class;
	}

}
