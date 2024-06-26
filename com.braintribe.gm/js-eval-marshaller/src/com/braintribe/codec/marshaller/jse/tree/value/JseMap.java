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
package com.braintribe.codec.marshaller.jse.tree.value;

import java.io.IOException;

import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.jse.CountingWriter;
import com.braintribe.codec.marshaller.jse.tree.JseNode;

public class JseMap extends JseNode {
	private static final char[] opening = "$.M([".toCharArray();
	private static final char[] closing = "])".toCharArray();
	private JseNode elements[];

	public JseMap(JseNode elements[]) {
		super();
		this.elements = elements;
	}
	
	@Override
	public void write(CountingWriter writer) throws MarshallException, IOException {
		writer.write(opening);
		
		int i = 0;
		for (JseNode element: elements) {
			if (i++ > 0) writer.write(',');
			element.write(writer);
		}
		
		writer.write(closing);
	}
}
