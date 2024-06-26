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
package com.braintribe.codec.dom.genericmodel;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.utils.FileTools;

/**
 * Provides tests for {@link GmXmlResourceProviderTest}.
 * 
 * @author michael.lafite
 */
public class GmXmlResourceProviderTest {

	
	@Test @Category(KnownIssue.class)
	public void testReadMetaModel() throws RuntimeException {

		File metamodelFile = new File("res/metamodel.xml");
		URL metamodelURL = FileTools.toURL(metamodelFile);

		GmXmlResourceProvider<GmMetaModel> provider = new GmXmlResourceProvider<GmMetaModel>();
		provider.setResource(metamodelURL);
		GmMetaModel metamodel = provider.get();

		assertThat(metamodel).isNotNull();
	}

}
