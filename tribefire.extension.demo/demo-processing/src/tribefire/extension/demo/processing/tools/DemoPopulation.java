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
package tribefire.extension.demo.processing.tools;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.braintribe.model.generic.GenericEntity;

import tribefire.extension.demo.model.data.Address;
import tribefire.extension.demo.model.data.Company;
import tribefire.extension.demo.model.data.Department;
import tribefire.extension.demo.model.data.Gender;
import tribefire.extension.demo.model.data.Person;

/**
 * Static definition of raw data that is used by the {@link DemoPopulationBuilder} to build a population of the DemoModel. 
 */
public abstract class DemoPopulation {

	/*
	 * Definition property order of each type. 
	 */
	public static Map<Class<? extends GenericEntity>, String[]> demoDescriptions = new HashMap<Class<? extends GenericEntity>, String[]>();
	static {
		demoDescriptions.put(Person.class, new String[] {Person.ssn, Person.firstName, Person.lastName, Person.gender, Person.address, Person.mother, Person.father, Person.children, Person.picture, Person.anything});
		demoDescriptions.put(Company.class, new String[] {Company.name, Company.address, Company.ceo, Company.averageRevenue, Company.employees, Company.departments, Company.paperworkByCategory});
		demoDescriptions.put(Department.class, new String[] {Department.name, Department.manager, Department.numberOfEmployees, Department.company});
		demoDescriptions.put(Address.class, new String[] {Address.street, Address.streetNumber, Address.postalCode, Address.city, Address.country});
	}
	
	/*
	 * Raw data definition 
	 */
	public static Map<Class<? extends GenericEntity>, Object[][]> demoRawData = new HashMap<Class<? extends GenericEntity>, Object[][]>();
	static {
		
		demoRawData.put(Person.class, new Object[][] {
			{"111", "John", "Doe", Gender.male, new Object[] {"Kandlgasse", 3, "1070", "Vienna", "Austria"}, "ref:333", "ref:444", new Object[] { "ref:555", "ref:666" }, "/static/images/johndoe.gif", 1},
			{"222", "Jane", "Doe", Gender.female, new Object[] {"Barisanistrasse", 5, "5020", "Salzburg", "Austria"}, null, null, new Object[] { "ref:555", "ref:666" }, "/static/images/janedoe.jpg", "MyTest"},
			{"333", "Sue", "St. Doe", Gender.female, new Object[] {"Oak St", 100, "21520", "Accident, MD", "USA"}, null, null, null, "/static/images/suedoe.jpg"}, 
			{"444", "James", "Doeman", Gender.male, new Object[] {"Peterson Rd", 26450, "97009", "Boring, OR", "USA"}, null, null, null, "/static/images/jamesdoe.jpg"},
			{"555", "J.J.", "Doesen", Gender.male, new Object[] {"Merianvej", 7, "5500", "Middlefart", "Denmark"}, "ref:222", "ref:111", null, "/static/images/jjdoe.jpg"},
			{"666", "Mary", "Doebauer", Gender.female, new Object[] {"Museumstrasse", 10, "91555", "Feuchtwangen", "Germany"}, "ref:222", "ref:111", null, "/static/images/marydoe.gif"}});

		demoRawData.put(Company.class, new Object[][] {
			{"Braintribe IT-Technologies GmbH", new Object[] {"Kandlgasse", 21, "1070", "Vienna", "Austria"}, "ref:111", new BigDecimal("800000000"), new Object[] {"ref:555", "ref:666"}, 
				new Object[][] {
					{"Marketing", "ref:555", 10, "ref:Braintribe IT-Technologies GmbH"}, 
					{"R&D", "ref:666", 15, "ref:Braintribe IT-Technologies GmbH"}, 
					{"Professional Services", "ref:555", 10, "ref:Braintribe IT-Technologies GmbH"}, 
					{"Backoffice", "ref:666", 20, "ref:Braintribe IT-Technologies GmbH"}},
				new Object[][] {
					{"Mission Statement","/static/paperwork/Mission-Statement.pdf"}, 
					{"Tribefire Whitepaper","/static/paperwork/Tribefire-Overview.pdf"}}},
			{"Agile Documents GmbH", new Object[] {"Kandlgasse", 19, "1070", "Vienna", "Austria"}, "ref:222", new BigDecimal("40000000"), new Object[] {"ref:333", "ref:444"}, 
				new Object[][] {
					{"Marketing", "ref:333", 3, "ref:Agile Documents GmbH"}, 
					{"R&D", "ref:444", 5, "ref:Agile Documents GmbH"}, 
					{"Professional Services", "ref:333", 5, "ref:Agile Documents GmbH"}, 
					{"Backoffice", "ref:444", 10, "ref:Agile Documents GmbH"}},
				new Object[][] {
					{"Mission Statement","/static/paperwork/Mission-Statement.pdf"}}}});
	}


}
