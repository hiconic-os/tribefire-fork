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
package tribefire.extension.messaging.integration.test;

public interface StaticTestVariables {
	// String KAFKA_URL = "kafka-dev.kafka.svc.cluster.local:9092";// "localhost:29092";
	// String PULSAR_URL = "pulsar://pulsar-dev-proxy.pulsar.svc.cluster.local:6650";// "pulsar://localhost:6650";
	// String PULSAR_SERVICE_URL = "http://pulsar-dev-proxy.pulsar.svc.cluster.local:80";// "http://localhost:8081";
	String KAFKA_URL = "localhost:29092";
	String PULSAR_URL = "pulsar://localhost:6650";
	String PULSAR_SERVICE_URL = "http://localhost:8081";

	String TOPIC = "test";
}
