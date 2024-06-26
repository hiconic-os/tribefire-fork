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
package com.braintribe.marshaller.impl.basic.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.Ignore;
import org.junit.Test;

import com.braintribe.codec.jseval.genericmodel.CondensedJavaScriptPrototypes;
import com.braintribe.codec.jseval.genericmodel.GenericModelJsEvalCodec;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.Marshaller;
import com.braintribe.codec.marshaller.api.OutputPrettiness;
import com.braintribe.codec.marshaller.bin.BinMarshaller;
import com.braintribe.codec.marshaller.bin.BinResearchMarshaller;
import com.braintribe.codec.marshaller.dom.DomMarshaller;
import com.braintribe.codec.marshaller.jse.JseMarshaller;
import com.braintribe.codec.marshaller.jseval.JsEvalMarshaller;
import com.braintribe.codec.marshaller.json.JsonStreamMarshaller;
import com.braintribe.codec.marshaller.sax.SaxMarshaller;
import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.codec.marshaller.stax.StaxResearchMarshaller;

public class MarshallOptionsTest {
	private SaxMarshaller<Object> saxMarshaller;
	private Marshaller xmlMarshaller;
	private StaxMarshaller staxMarshaller;
	private BinMarshaller binMarshaller;
	private BinResearchMarshaller binResearchMarshaller;
	private Object assembly;
	private JseMarshaller jseMarshaller;
	private JsEvalMarshaller jsEvalMarshaller;
	private DomMarshaller domMarshaller;
	private JsonStreamMarshaller jsonMarshaller;
	private StaxResearchMarshaller staxResearchMarshaller;

	@Test
	public void testCortexMarshal() throws Exception {

		File file = new File("current.xml");

		if (!file.exists()) {
			return;
		}

		int count = 20;

		assembly = getXmlMarshaller().unmarshall(new FileInputStream(file));

		Marshaller marshallers[] = new Marshaller[] {
				// getJsonResearchMarshaller()
				getStaxResearchMarshaller() };

		System.out.println("--- first run - Caching + 1. JIT pass");
		for (Marshaller marshaller : marshallers) {
			testMarshallerMarshall(marshaller, assembly, 1);
		}

		System.out.println("--- second run");
		for (Marshaller marshaller : marshallers) {
			testMarshallerMarshall(marshaller, assembly, 1);
		}

		System.out.println("--- 5 runs");
		for (Marshaller marshaller : marshallers) {
			testMarshallerMarshall(marshaller, assembly, 5);
		}

		System.out.println("--- " + count + " runs");
		for (Marshaller marshaller : marshallers) {
			testMarshallerMarshall(marshaller, assembly, count);
		}
	}

	@Ignore
	public void testMarshallerMarshall(Marshaller marshaller, Object assembly, int count) throws Exception {
		boolean propertyAccessModes[] = { true, false };

		for (boolean propertyAccessMode : propertyAccessModes) {
			for (OutputPrettiness outputPrettiness : OutputPrettiness.values()) {
				GmSerializationOptions options = GmSerializationOptions.deriveDefaults().outputPrettiness(outputPrettiness)
						.useDirectPropertyAccess(propertyAccessMode).build();
				String hint = "dpa=" + propertyAccessMode + ", prettiness=" + outputPrettiness;
				testMarshallerMarshall(options, hint, marshaller, assembly, count);

			}
		}
	}

	@Ignore
	public void testMarshallerMarshall(GmSerializationOptions options, String hint, Marshaller marshaller, Object assembly, int count)
			throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			marshaller.marshall(out, assembly, options);
			out.close();
		}
		long stop = System.currentTimeMillis();
		System.out.println("marshalling [" + hint + "] with " + marshaller.getClass().getSimpleName() + ": " + (stop - start) + " ms");
	}

	@Ignore
	private Marshaller getXmlMarshaller() {
		if (xmlMarshaller == null) {
			xmlMarshaller = StaxMarshaller.defaultInstance;
		}

		return xmlMarshaller;
	}

	@Ignore
	private Marshaller getSaxMarshaller() {
		if (saxMarshaller == null) {
			saxMarshaller = new SaxMarshaller<Object>();
			saxMarshaller.setWriteRequiredTypes(true);
			saxMarshaller.setCreateEnhancedEntities(true);
		}

		return saxMarshaller;
	}

	@Ignore
	private Marshaller getStaxMarshaller() {
		if (staxMarshaller == null) {
			staxMarshaller = new StaxMarshaller();
			return staxMarshaller;

		}

		return staxMarshaller;
	}

	@Ignore
	private Marshaller getStaxResearchMarshaller() {
		if (staxResearchMarshaller == null) {
			staxResearchMarshaller = new StaxResearchMarshaller();
			return staxResearchMarshaller;

		}

		return staxMarshaller;
	}

	@Ignore
	private Marshaller getBinMarshaller() {
		if (binMarshaller == null) {
			binMarshaller = new BinMarshaller();
			binMarshaller.setWriteRequiredTypes(true);
			return binMarshaller;
		}

		return binMarshaller;
	}

	@Ignore
	private Marshaller getDomMarshaller() {
		if (domMarshaller == null) {
			domMarshaller = new DomMarshaller();
			return domMarshaller;
		}

		return staxMarshaller;
	}

	@Ignore
	private JsEvalMarshaller getJsEvalMarshaller() {
		if (jsEvalMarshaller == null) {
			jsEvalMarshaller = new JsEvalMarshaller();
			GenericModelJsEvalCodec<Object> codec = new GenericModelJsEvalCodec<Object>();
			codec.setHostedMode(false);
			codec.setPrototypes(new CondensedJavaScriptPrototypes());
			jsEvalMarshaller.setCodec(codec);
		}

		return jsEvalMarshaller;
	}

	@Ignore
	private JseMarshaller getJseMarshaller() {
		if (jseMarshaller == null) {
			jseMarshaller = new JseMarshaller();
			jseMarshaller.setHostedMode(false);
		}

		return jseMarshaller;
	}

	@Ignore
	private BinResearchMarshaller getBinResearchMarshaller() {
		if (binResearchMarshaller == null) {
			binResearchMarshaller = new BinResearchMarshaller();
			binResearchMarshaller.setWriteRequiredTypes(true);
		}

		return binResearchMarshaller;
	}

	@Ignore
	private JsonStreamMarshaller getJsonMarshaller() {
		if (jsonMarshaller == null) {
			jsonMarshaller = new JsonStreamMarshaller();
		}

		return jsonMarshaller;
	}

}
