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
package tribefire.extension.xml.schemed.test.marshaller.xsd;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.DifferenceEvaluator;

public class XsdComparator {
	private DifferenceEvaluator differenceEvaluator;
	
	
	

	public XsdComparator() { 
		differenceEvaluator = new IgnoreAttributeDifferenceEvaluator( getOptionalAttributeMap());
	}
	
	private Map<String, String> getOptionalAttributeMap() {
		Map<String, String> map = new HashMap<>();
		map.put( "attributeFormDefault", "unqualified");
		map.put( "elementFormDefault", "unqualified");
		map.put("minOccurs", "1");
		map.put("maxOccurs", "1");
		
		return map;
	}

	private class IgnoreAttributeDifferenceEvaluator implements DifferenceEvaluator {
	    private Map<String, String> attrToValueMap;;
	    
	    public IgnoreAttributeDifferenceEvaluator(Map<String, String> map) {
	        this.attrToValueMap = map;
	    }
	     
	    @Override
	    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
	        if (outcome == ComparisonResult.EQUAL)
	            return outcome;
	        
	        final Node controlNode = comparison.getControlDetails().getTarget();
	        final Node testNode = comparison.getTestDetails().getTarget();
	        
	        if (testNode instanceof Attr) {
	            Attr attr = (Attr) testNode;
	            String name = attr.getName();
				String controlValue = attrToValueMap.get( name);				
				System.out.println("[" + name + "]:[" + controlValue + "] -> [" + attr.getValue() + "]");	            
	        }
	        return outcome;
	    }
	}
	
	
	public Diff compare( File in, File out) {
	     
        Diff myDiff = DiffBuilder.compare( in).withDifferenceEvaluator( differenceEvaluator ).withTest( out).checkForSimilar().build();
        return myDiff;
             	       
}
}
