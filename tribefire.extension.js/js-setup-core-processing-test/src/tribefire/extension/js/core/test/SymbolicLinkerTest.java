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
package tribefire.extension.js.core.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.build.artifact.name.NameParser;
import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.Solution;

import tribefire.extension.js.core.impl.SymbolicLinker;

/**
 * JUnit to test the link name generation
 * see https://docs.google.com/document/d/1I6BecP-MsQuD5LvftQECDbf6uWr8ykCp626X5TIsV0A/edit?skip_itp2_check=true#heading=h.varrcfvdds6p
 * @author pit
 *
 */
public class SymbolicLinkerTest {	
	
	Map<String, Map<String,String>> expectations = new HashMap<>();
	{
		Map<String,String> exp = new HashMap<>();
		exp.put( "a.b.c:x#[4.0,5.0)", "a.b.c.x-4~");
		exp.put( "a.b.c:x#4.1.5", "a.b.c.x-4.1.5");
		expectations.put( "a.b.c:x#4.1.5", exp);
		
		exp = new HashMap<>();
		exp.put( "a.b.c:x#[4.1,4.2)", "a.b.c.x-4.1~");
		exp.put( "a.b.c:x#[4.1,4.3)", "a.b.c.x-[4.1,4.3)");
		expectations.put( "a.b.c:x#4.1.4", exp);
		
		exp = new HashMap<>();
		exp.put( "a.b.c:x#1.0.5", "a.b.c.x-1.0.5");		
		expectations.put("a.b.c:x#1.0.5", exp);

		exp = new HashMap<>();
		exp.put( "a.b.c:x#[4.1,4.3)",  "a.b.c.x-[4.1,4.3)");		
		expectations.put("a.b.c:x#4.1.0", exp);
				
		
		exp = new HashMap<>();
		exp.put( "a.b.c:x#[1.0,1.1)",  "a.b.c.x-1.0~");
		exp.put( "a.b.c:x#[1.0,1.1]",  "a.b.c.x-[1.0,1.1]");
		exp.put( "a.b.c:x#(1.0,1.1]",  "a.b.c.x-(1.0,1.1]");
		exp.put( "a.b.c:x#(1.0,1.1)",  "a.b.c.x-(1.0,1.1)");
		expectations.put("a.b.c:x#1.1.1", exp);
		
		
	}
	private String collate(Collection<String> strs) {
		return strs.stream().collect(Collectors.joining(","));
	}
	
	
	private void test( Solution solution, Map<String,String> dependencyToResult) {
		
		Map<String,Dependency> nameToDependency = new HashMap<>();
		dependencyToResult.keySet().stream().forEach( n -> nameToDependency.put( n,  NameParser.parseCondensedDependencyName(n)));
		
		solution.getRequestors().addAll( nameToDependency.values());		
		
		List<String> linkNames = SymbolicLinker.determineLinkName(solution);
		Assert.assertTrue("[" + dependencyToResult.size() + "] names expected, [" + linkNames.size() + "] found", linkNames.size() == dependencyToResult.size());
		
		List<String> matching = new ArrayList<>();
		List<String> excess = new ArrayList<>();
		for (String linkname : linkNames) {
			if (dependencyToResult.values().contains(linkname)) {
				matching.add( linkname);
			}
			else {
				excess.add( linkname);
			}
		}
		List<String> missing = new ArrayList<>( dependencyToResult.values());
		missing.removeAll( matching);
		
		Assert.assertTrue("expected [" + collate( dependencyToResult.values()) + "], missing [" + collate( missing) + "]", missing.size() == 0);
		Assert.assertTrue("expected [" + collate( dependencyToResult.values()) + "], excess [" + collate( excess) + "]", excess.size() == 0);
		
	}
	
	@Test
	public void symboliclinkNameGenerationTest() {
		for (Map.Entry<String, Map<String,String>> entry : expectations.entrySet()) {								
			test( NameParser.parseCondensedSolutionName( entry.getKey()), entry.getValue());
		}
	}

}
