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

import static com.braintribe.model.processing.cmd.test.provider.SpecialSelectorMdProvider.JUST_UC;
import static com.braintribe.model.processing.cmd.test.provider.SpecialSelectorMdProvider.NOT_UC;
import static com.braintribe.model.processing.cmd.test.provider.SpecialSelectorMdProvider.NO_UC;
import static com.braintribe.model.processing.cmd.test.provider.SpecialSelectorMdProvider.X_AND_UC;
import static com.braintribe.model.processing.cmd.test.provider.SpecialSelectorMdProvider.X_OR_UC;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Supplier;

import org.junit.Test;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.data.PropertyMetaData;
import com.braintribe.model.meta.selector.UseCaseSelector;
import com.braintribe.model.processing.cmd.test.meta.property.SimplePropertyMetaData;
import com.braintribe.model.processing.cmd.test.meta.selector.SimpleSelector;
import com.braintribe.model.processing.cmd.test.meta.selector.SimpleSelectorExpert;
import com.braintribe.model.processing.cmd.test.meta.selector.StaticContextSelector;
import com.braintribe.model.processing.cmd.test.meta.selector.StaticContextSelectorExpert;
import com.braintribe.model.processing.cmd.test.model.HardwiredAccess;
import com.braintribe.model.processing.cmd.test.model.Person;
import com.braintribe.model.processing.cmd.test.provider.SpecialSelectorMdProvider;
import com.braintribe.model.processing.meta.cmd.CmdResolverBuilder;
import com.braintribe.model.processing.meta.cmd.builders.PropertyMdResolver;
import com.braintribe.model.processing.meta.cmd.context.aspects.AccessTypeAspect;

public class SpecialSelectorTests extends MetaDataResolvingTestBase {

	/** @see SpecialSelectorMdProvider#addSimplePropertyMdWithAccessSelector() */
	@Test
	public void accessSelector() {
		PropertyMetaData md;

		md = pmd("name").meta(SimplePropertyMetaData.T).exclusive();
		assertNoMd(md);

		md = pmd("name").access(SpecialSelectorMdProvider.accessSelector).meta(SimplePropertyMetaData.T).exclusive();
		assertOneMetaData(SimplePropertyMetaData.T, md);
	}

	/** @see SpecialSelectorMdProvider#addSimplePropertyMdWithAccessTypeSelector() */
	@Test
	public void accessTypeSelector() {
		PropertyMetaData md;

		md = pmd("age").meta(SimplePropertyMetaData.T).exclusive();
		assertNoMd(md);

		md = pmd("age").with(AccessTypeAspect.class, HardwiredAccess.T.getTypeSignature()).meta(SimplePropertyMetaData.T).exclusive();
		assertOneMetaData(SimplePropertyMetaData.T, md);
	}

	/** @see SpecialSelectorMdProvider#addSimplePropertyMdWithAccessTypeSignatureSelector() */
	@Test
	public void accessTypeSignatureSelector() {
		PropertyMetaData md;

		md = pmd("friend").meta(SimplePropertyMetaData.T).exclusive();
		assertNoMd(md);

		md = pmd("friend").with(AccessTypeAspect.class, HardwiredAccess.T.getTypeSignature()).meta(SimplePropertyMetaData.T).exclusive();
		assertOneMetaData(SimplePropertyMetaData.T, md);
	}

	/** @see SpecialSelectorMdProvider#addSimplePropertyMdForIgnoreSelectors() */
	@Test
	public void ignoresSelectors() {
		SimplePropertyMetaData md;

		md = pmd("friends").meta(SimplePropertyMetaData.T).exclusive();
		assertNoMd(md);

		md = pmd("friends").ignoreSelectors().meta(SimplePropertyMetaData.T).exclusive();
		assertOneMetaData(SimplePropertyMetaData.T, md, JUST_UC); // highest priority selector

		List<SimplePropertyMetaData> mds;
		mds = pmd("friends").meta(SimplePropertyMetaData.T).list();
		assertEmptyMd(mds);

		mds = pmd("friends").ignoreSelectors().meta(SimplePropertyMetaData.T).list();
		assertThat(mds).hasSize(4);
	}

	/** @see SpecialSelectorMdProvider#addSimplePropertyMdForIgnoreSelectors() */
	@Test
	public void ignoresSelectors_withFork() {
		SimplePropertyMetaData md;

		PropertyMdResolver pmdResolver = pmd("friends");

		md = pmdResolver.meta(SimplePropertyMetaData.T).exclusive();
		assertNoMd(md);

		md = pmdResolver.fork().ignoreSelectors().meta(SimplePropertyMetaData.T).exclusive();
		assertOneMetaData(SimplePropertyMetaData.T, md, JUST_UC); // highest priority selector

		md = pmdResolver.meta(SimplePropertyMetaData.T).exclusive();
		assertNoMd(md);
	}

	/** @see SpecialSelectorMdProvider#addSimplePropertyMdForIgnoreSelectors() */
	@Test
	public void ignoresSelectors_ExceptUseCase() {
		List<SimplePropertyMetaData> mds;

		mds = pmd("friends").ignoreSelectorsExcept(UseCaseSelector.T).meta(SimplePropertyMetaData.T).list();
		assertMultipleMetaData(SimplePropertyMetaData.T, mds, X_OR_UC, NO_UC);

		mds = pmd("friends").ignoreSelectorsExcept(UseCaseSelector.T).useCase(JUST_UC).meta(SimplePropertyMetaData.T).list();
		assertMultipleMetaData(SimplePropertyMetaData.T, mds, JUST_UC, X_OR_UC, NO_UC);

		mds = pmd("friends").ignoreSelectorsExcept(UseCaseSelector.T).useCase(X_AND_UC).meta(SimplePropertyMetaData.T).list();
		assertMultipleMetaData(SimplePropertyMetaData.T, mds, X_AND_UC, X_OR_UC, NO_UC);

		mds = pmd("otherFriends").ignoreSelectorsExcept(UseCaseSelector.T).meta(SimplePropertyMetaData.T).list();
		assertMultipleMetaData(SimplePropertyMetaData.T, mds, NOT_UC);

		mds = pmd("otherFriends").ignoreSelectorsExcept(UseCaseSelector.T).useCase(NOT_UC).meta(SimplePropertyMetaData.T).list();
		assertEmptyMd(mds);
	}

	// Person's property MD
	private PropertyMdResolver pmd(String propertyName) {
		return getMetaData().entityType(Person.T).property(propertyName);
	}

	@Override
	protected Supplier<GmMetaModel> getModelProvider() {
		return new SpecialSelectorMdProvider();
	}

	@Override
	protected void setupCmdResolver(CmdResolverBuilder crb) {
		crb.setSessionProvider(Thread::currentThread);
		crb.addExpert(SimpleSelector.T, new SimpleSelectorExpert());
		crb.addExpert(StaticContextSelector.T, new StaticContextSelectorExpert());
	}

}
