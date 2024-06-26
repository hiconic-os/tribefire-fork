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
package com.braintribe.model.access.smart.test.manipulation;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessB.ItemB;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartItem;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.utils.junit.assertions.BtAssertions;

public class InverseKeyProperty_ManipulationsTests extends AbstractManipulationsTests {

	SmartPersonA p1, p2;
	SmartItem i1, i2;

	@Test
	public void changeValue_SingleSingle() throws Exception {
		p1 = newSmartPersonA();
		p1.setNickName("p1");

		SmartItem i1 = newSmartItem();
		i1.setNameB("i1");
		commit();

		p1.setInverseKeyItem(i1);
		commit();

		ItemB itemB = itemBByName("i1");
		BtAssertions.assertThat(itemB.getSingleOwnerName()).isEqualTo("p1");
	}

	@Test
	public void changeValue_SingleSingle_Nullify() throws Exception {
		changeValue_SingleSingle();

		p1.setInverseKeyItem(null);
		commit();

		ItemB itemB = itemBByName("i1");
		BtAssertions.assertThat(itemB.getSingleOwnerName()).isEqualTo(null);
	}

	@Test
	public void changeValue_SingleMulti() throws Exception {
		p1 = newSmartPersonA();
		p1.setNameA("p1");

		p2 = newSmartPersonA();
		p2.setNameA("p2");

		SmartItem i1 = newSmartItem();
		i1.setNameB("i1");
		commit();

		p1.setInverseKeySharedItem(i1);
		p2.setInverseKeySharedItem(i1);
		commit();

		ItemB itemB = itemBByName("i1");
		BtAssertions.assertThat(itemB.getSharedOwnerNames()).containsOnly("p1", "p2");
	}

	@Test
	public void changeValue_SingleMulti_Nullify() throws Exception {
		changeValue_SingleMulti();

		p2.setInverseKeySharedItem(null);
		commit();

		ItemB itemB = itemBByName("i1");
		BtAssertions.assertThat(itemB.getSharedOwnerNames()).containsOnly("p1");
	}

	@Test
	public void insert_MultiSingle() throws Exception {
		p1 = newSmartPersonA();
		p1.setNameA("p1");

		i1 = newSmartItem();
		i1.setNameB("i1");

		i2 = newSmartItem();
		i2.setNameB("i2");
		commit();

		p1.setInverseKeyItemSet(new HashSet<SmartItem>());
		p1.getInverseKeyItemSet().add(i1);
		p1.getInverseKeyItemSet().add(i2);
		commit();

		BtAssertions.assertThat(itemBByName("i1").getMultiOwnerName()).isEqualTo("p1");
		BtAssertions.assertThat(itemBByName("i2").getMultiOwnerName()).isEqualTo("p1");
	}

	@Test
	public void remove_MultiSingle() throws Exception {
		insert_MultiSingle();

		p1.getInverseKeyItemSet().remove(i2);
		commit();

		BtAssertions.assertThat(itemBByName("i1").getMultiOwnerName()).isEqualTo("p1");
		BtAssertions.assertThat(itemBByName("i2").getMultiOwnerName()).isNull();
	}

	@Test
	public void bulkInsert_MultiSingle() throws Exception {
		p1 = newSmartPersonA();
		p1.setNameA("p1");

		i1 = newSmartItem();
		i1.setNameB("i1");

		i2 = newSmartItem();
		i2.setNameB("i2");
		commit();

		p1.setInverseKeyItemSet(new HashSet<SmartItem>());
		p1.getInverseKeyItemSet().addAll(Arrays.asList(i1, i2));
		commit();

		BtAssertions.assertThat(itemBByName("i1").getMultiOwnerName()).isEqualTo("p1");
		BtAssertions.assertThat(itemBByName("i2").getMultiOwnerName()).isEqualTo("p1");
	}

	@Test
	public void bulkRemove_MultiSingle() throws Exception {
		bulkInsert_MultiSingle();

		p1.getInverseKeyItemSet().removeAll(Arrays.asList(i1, i2));
		commit();

		BtAssertions.assertThat(itemBByName("i1").getMultiOwnerName()).isNull();
		BtAssertions.assertThat(itemBByName("i2").getMultiOwnerName()).isNull();
	}

	@Test
	public void clear_MultiSingle() throws Exception {
		bulkInsert_MultiSingle();

		p1.getInverseKeyItemSet().clear();
		commit();

		BtAssertions.assertThat(itemBByName("i1").getMultiOwnerName()).isNull();
		BtAssertions.assertThat(itemBByName("i2").getMultiOwnerName()).isNull();
	}

	@Test
	public void insert_MultiMulti() throws Exception {
		p1 = newSmartPersonA();
		p1.setNameA("p1");

		i1 = newSmartItem();
		i1.setNameB("i1");
		commit();

		p1.setInverseKeyMultiSharedItemSet(new HashSet<SmartItem>());
		p1.getInverseKeyMultiSharedItemSet().add(i1);
		commit();

		ItemB itemB = itemBByName("i1");
		BtAssertions.assertThat(itemB.getMultiSharedOwnerNames()).containsOnly("p1");
	}

}
