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
package com.braintribe.model.processing.cmd;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.junit.Test;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.data.ModelMetaData;
import com.braintribe.model.processing.cmd.test.meta.aspects.StaticAspect;
import com.braintribe.model.processing.cmd.test.meta.model.SessionScopedMetaData;
import com.braintribe.model.processing.cmd.test.meta.selector.SimpleSelector;
import com.braintribe.model.processing.cmd.test.meta.selector.SimpleSelectorExpert;
import com.braintribe.model.processing.cmd.test.meta.selector.StaticContextSelector;
import com.braintribe.model.processing.cmd.test.meta.selector.StaticContextSelectorExpert;
import com.braintribe.model.processing.cmd.test.provider.ModelMdProvider;
import com.braintribe.model.processing.meta.cmd.CmdResolverBuilder;
import com.braintribe.model.processing.meta.cmd.context.SelectorContextAspect;
import com.braintribe.model.processing.meta.cmd.context.aspects.RoleAspect;
import com.braintribe.utils.lcd.CollectionTools2;

/**
 * 
 */
public class SessionProviderMisconfiguredTests extends MetaDataResolvingTestBase {

	/**
	 * This is a copy of {@link ModelMetaDataResolvingTests#model_SessionScope()}, but we do not configure any sessionProvider. It still works the
	 * same, but a warning is logged.
	 */
	@Test
	public void test_Model_SessionScope() {
		List<? extends ModelMetaData> mmds = getMetaData().meta(SessionScopedMetaData.T).list();
		assertOneMetaData(SessionScopedMetaData.T, mmds);
	}

	@Override
	protected Supplier<GmMetaModel> getModelProvider() {
		return new ModelMdProvider();
	}

	@Override
	protected void setupCmdResolver(CmdResolverBuilder crb) {
		crb.addExpert(SimpleSelector.T, new SimpleSelectorExpert());
		crb.addExpert(StaticContextSelector.T, new StaticContextSelectorExpert());

		crb.addStaticAspect(StaticAspect.class, "YES");

		crb.addDynamicAspectProviders(dynamicAspectProviders());
	}

	private Map<Class<? extends SelectorContextAspect<?>>, Supplier<?>> dynamicAspectProviders() {
		return CollectionTools2.asMap(RoleAspect.class, new RoleAspectProvider());
	}

	private static class RoleAspectProvider implements Supplier<Set<String>> {
		@Override
		public Set<String> get() throws RuntimeException {
			return new HashSet<String>(Arrays.asList("admin"));
		}
	}

}
