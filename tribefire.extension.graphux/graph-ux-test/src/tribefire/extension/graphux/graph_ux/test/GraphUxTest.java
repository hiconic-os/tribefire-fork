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
package tribefire.extension.graphux.graph_ux.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.braintribe.gm.ux.decorator.StyleDecorator;
import com.braintribe.gm.ux.graph.Graph;
import com.braintribe.gm.ux.graph.Node;
import com.braintribe.logging.Logger;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.record.MapRecord;
import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;

import tribefire.extension.graphux.model.deployment.metadata.GraphUxNodeStyle;
import tribefire.extension.graphux.model.deployment.metadata.GraphUxNonRecursion;
import tribefire.extension.graphux.model.deployment.metadata.GraphUxPropertyAsNode;
import tribefire.extension.graphux.model.service.GetEntityAsGraph;

public class GraphUxTest extends GraphUxTestBase {
	private static Logger logger = Logger.getLogger(GraphUxTest.class);
	
	@Test
	public void testGraphUxProcessor() {
		GetEntityAsGraph request = GetEntityAsGraph.T.create();
		request.setDomainId("test.access");
		request.setEntityType("tribefire.extension.simple.model.data.Company");
		
		
		Graph result = (Graph) evaluator.eval(request).get();
		logger.info(result.toString());
		Assertions.assertThat(result.getNodes().size()).isEqualTo(7);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(12);
		
		request.setEntityType("tribefire.extension.simple.model.data.Department");
		
		result = (Graph) evaluator.eval(request).get();
		logger.info(result.toString());
		Assertions.assertThat(result.getNodes().size()).isEqualTo(7);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(12);
		
		request.setEntityType("tribefire.extension.simple.model.data.Person");
		
		result = (Graph) evaluator.eval(request).get();
		logger.info(result.toString());
		Assertions.assertThat(result.getNodes().size()).isEqualTo(4);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(1);
		
	}
	
	@Test
	public void testGraphUxProcessorWithId() {
		
		GetEntityAsGraph request = GetEntityAsGraph.T.create();
		request.setDomainId("test.access");
		request.setEntityType("tribefire.extension.simple.model.data.Department");
		request.setEntityId("1");
		
		Graph result = (Graph) evaluator.eval(request).get();
		Assertions.assertThat(result.getNodes().size()).isEqualTo(7);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(12);
		
		request.setEntityType("tribefire.extension.simple.model.data.Person");
		
		result = (Graph) evaluator.eval(request).get();
		Assertions.assertThat(result.getNodes().size()).isEqualTo(1);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(0);
		
	}
	
	@Test
	public void testGraphUxProcessorWidthMD() {
		
		HashMap<String, MetaData[]> mdData = getMdData(session);
		mdData.keySet().stream().forEach(key -> {
			Arrays.stream(mdData.get(key)).forEach(md ->{
				mdEditor.onEntityType(key).addMetaData(md);
			});
			
		});
		
		
		GetEntityAsGraph request = GetEntityAsGraph.T.create();
		request.setDomainId("test.access");
		request.setEntityType("tribefire.extension.simple.model.data.Company");
		
		
		Graph result = (Graph) evaluator.eval(request).get();
		Assertions.assertThat(result.getNodes().size()).isEqualTo(7);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(7);
		
		request.setEntityType("tribefire.extension.simple.model.data.Department");
		
		result = (Graph) evaluator.eval(request).get();
		Assertions.assertThat(result.getNodes().size()).isEqualTo(5);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(4);
		
		request.setEntityId("1");
		
		result = (Graph) evaluator.eval(request).get();
		Assertions.assertThat(result.getNodes().size()).isEqualTo(3);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(2);
		
		
		mdData.keySet().stream().forEach(key -> {
			Arrays.stream(mdData.get(key)).forEach(md ->{
				mdEditor.onEntityType(key).removeMetaData((m) -> m == md);
			});
			
		});
	}
	
	@Test
	public void testGraphUxProcessorWidthPropertyMD() {
		GraphUxPropertyAsNode mds = session.create(GraphUxPropertyAsNode.T);
		mdEditor.onEntityType("tribefire.extension.simple.model.data.Company").addPropertyMetaData("departments", mds);
		
		mdEditor.onEntityType("tribefire.extension.simple.model.data.Department").addPropertyMetaData("numberOfEmployees", mds);
		
		
		GetEntityAsGraph request = GetEntityAsGraph.T.create();
		request.setDomainId("test.access");
		request.setEntityType("tribefire.extension.simple.model.data.Company");
		
		
		Graph result = (Graph) evaluator.eval(request).get();
		Assertions.assertThat(result.getNodes().size()).isEqualTo(8);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(9);
		
		request.setEntityType("tribefire.extension.simple.model.data.Department");
		
		result = (Graph) evaluator.eval(request).get();
		logger.info(result.toString());
		Assertions.assertThat(result.getNodes().size()).isEqualTo(9);
		Assertions.assertThat(result.getEdges().size()).isEqualTo(12);
		
		mdEditor.onEntityType("tribefire.extension.simple.model.data.Company").removePropertyMetaData("departments", (md) -> md == mds);
		mdEditor.onEntityType("tribefire.extension.simple.model.data.Department").removePropertyMetaData("numberOfEmployees", (md) -> md == mds);
	
	}
	
	@Test
	public void testGraphUxProcessorWidthStyleMetadata() {
		GraphUxNodeStyle mds = session.create(GraphUxNodeStyle.T);
		MapRecord mapStyle = session.create(MapRecord.T);
		mapStyle.put("sharp", "circle");
		mapStyle.put("color", "red");
		mapStyle.put("size", "xl");
		mds.setStyleMap(mapStyle);
		mdEditor.onEntityType("tribefire.extension.simple.model.data.Company").addMetaData(mds);
		
		GetEntityAsGraph request = GetEntityAsGraph.T.create();
		request.setDomainId("test.access");
		request.setEntityType("tribefire.extension.simple.model.data.Company");
		
		
		Graph result = (Graph) evaluator.eval(request).get();
		List<Node> wirhDecorators = result.getNodes().stream().filter((n) -> n.getDecorators().size() > 0).collect(Collectors.toList());
		Assertions.assertThat(wirhDecorators.size()).isEqualTo(1);
		Assertions.assertThat(wirhDecorators.get(0).getName()).isEqualTo("Acme");
		Assertions.assertThat(wirhDecorators.get(0).getDecorators().size()).isEqualTo(1);
		StyleDecorator sd = (StyleDecorator) wirhDecorators.get(0).getDecorators().toArray()[0];
		Assertions.assertThat(sd.getName()).isEqualTo("style");
		Assertions.assertThat(sd.getStyleMap().get("sharp")).isEqualTo(mapStyle.get("sharp"));
		Assertions.assertThat(sd.getStyleMap().get("color")).isEqualTo(mapStyle.get("color"));
		Assertions.assertThat(sd.getStyleMap().get("size")).isEqualTo(mapStyle.get("size"));
		
		mdEditor.onEntityType("tribefire.extension.simple.model.data.Company").removeMetaData((md) -> md == mds);
	}
	
	
	private HashMap<String, MetaData[]> getMdData(PersistenceGmSession cortexSession) {
		HashMap<String, MetaData[]> mdData = new HashMap<String, MetaData[]>();
		
		GraphUxNonRecursion guRMreta = cortexSession.create(GraphUxNonRecursion.T);
		
		MetaData[] companyMd = {guRMreta};
		mdData.put("tribefire.extension.simple.model.data.Company", companyMd);
		
		MetaData[] departrmentMd = {guRMreta};
		mdData.put("tribefire.extension.simple.model.data.Department", departrmentMd);
		
		return mdData;
	}
	
	
}
