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
package tribefire.extension.messaging.test.comparison;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import tribefire.extension.messaging.service.utils.PropertyVisitor;
import tribefire.extension.messaging.test.comparison.model.Complex;
import tribefire.extension.messaging.test.comparison.model.ComplexWithCollectionOfSimple;
import tribefire.extension.messaging.test.comparison.model.Simple;

public class PropertyVisitorTest {
	private static final String SUCCESS = "success";

	@Test // Simple object property (first tier embedment)
	public void visitPrimitiveFirst() {
		String propertyPathToVisit = "partition";
		Simple obj = getSimple();

		Object result = new PropertyVisitor().visit(obj, propertyPathToVisit);
		assertEquals(SUCCESS, result);
	}

	@Test // Complex object -> simple object -> property (second tier embedment)
	public void visitComplexSecond() {
		String propertyPathToVisit = "simple.partition";
		Complex obj = Complex.T.create();
		obj.setSimple(getSimple());

		Object result = new PropertyVisitor().visit(obj, propertyPathToVisit);
		assertEquals(SUCCESS, result);

	}

	@Test // Complex object -> simple object -> property (third tier embedment)
	public void visitComplexListThird() {
		String propertyPathToVisit = "listSimple.partition";
		ComplexWithCollectionOfSimple obj = getComplex("simpleList");

		Object result = new PropertyVisitor().visit(obj, propertyPathToVisit);
		assertEquals(List.of(SUCCESS, SUCCESS), result);
	}

	@Test // Complex object -> simple object -> property (third tier embedment)
	public void visitComplexMapThird() {
		String propertyPathToVisit = "mapSimple.partition";
		ComplexWithCollectionOfSimple obj = getComplex("simpleMap");

		Object result = new PropertyVisitor().visit(obj, propertyPathToVisit);
		assertEquals(List.of(SUCCESS, SUCCESS), result);
	}

	@Test // Complex object -> simple object -> property (third tier embedment, only element indexed 1 should be
			// visited)
	public void visitIndexedObjectsProperty() {
		String propertyPathToVisit = "listSimple[1].partition";
		ComplexWithCollectionOfSimple obj = getComplex("simpleList");

		Object result = new PropertyVisitor().visit(obj, propertyPathToVisit);
		assertEquals(SUCCESS, result);
	}

	@Test // Complex object -> simple object -> property (third tier embedment, only element with key: 2 should be
			// visited)
	public void visitIndexedObjectsPropertyMap() {
		String propertyPathToVisit = "mapSimple(2).partition";
		ComplexWithCollectionOfSimple obj = getComplex("simpleMap");

		Object result = new PropertyVisitor().visit(obj, propertyPathToVisit);
		assertEquals(SUCCESS, result);
	}

	private ComplexWithCollectionOfSimple getComplex(String objToInsert) {
		ComplexWithCollectionOfSimple obj = ComplexWithCollectionOfSimple.T.create();
		switch (objToInsert) {
			case "simpleList" -> obj.setListSimple(List.of(getSimple(), getSimple()));
			case "simpleSet" -> obj.setSetSimple(Set.of(getSimple(), getSimple()));
			case "simpleMap" -> obj.setMapSimple(Map.of("1", getSimple(), "2", getSimple()));
			default -> throw new IllegalStateException("Unexpected value: " + objToInsert);
		}
		return obj;
	}

	private Simple getSimple() {
		Simple obj = Simple.T.create();
		obj.setPartition(SUCCESS);
		return obj;
	}
}
