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
package com.braintribe.model.processing.activemq.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.DestinationStatistics;
import org.apache.activemq.command.ActiveMQDestination;

import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckRequest;
import com.braintribe.model.check.service.CheckResult;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.check.service.CheckStatus;
import com.braintribe.model.processing.check.api.CheckProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.utils.StringTools;

public class HealthCheckProcessor implements CheckProcessor {

	private static final Logger logger = Logger.getLogger(HealthCheckProcessor.class);

	private Supplier<BrokerService> brokerServiceSupplier;

	@Override
	public CheckResult check(ServiceRequestContext requestContext, CheckRequest request) {

		CheckResult response  = CheckResult.T.create();
		List<CheckResultEntry> entries = response.getEntries();

		BrokerService service = brokerServiceSupplier.get();
		if (service == null) {
			service = ActiveMqWorkerImpl.staticBrokerService();
		}

		CheckResultEntry workerReady = CheckResultEntry.T.create();
		entries.add(workerReady);
		workerReady.setName("ActiveMq Broker Service");
		if (service != null) {
			workerReady.setCheckStatus(CheckStatus.ok);
			workerReady.setDetails("ActiveMq Broker Service has been started");

			collectDestinationStatus(service, entries);
		} else {
			workerReady.setCheckStatus(CheckStatus.fail);
			workerReady.setDetails("ActiveMq Broker Service has not been started.");			
		}



		return response;
	}

	private void collectDestinationStatus(BrokerService service, List<CheckResultEntry> entries) {

		try {
			Map<ActiveMQDestination, Destination> destinationMap = service.getBroker().getDestinationMap();
			if (destinationMap == null || destinationMap.isEmpty()) {
				return;
			}
			
			StringBuilder sb = new StringBuilder();

			DecimalFormat df = new DecimalFormat("#.00"); 

			for (Map.Entry<ActiveMQDestination, Destination> entry : destinationMap.entrySet()) {
				ActiveMQDestination mqDest = entry.getKey();
				
				String destinationTypeAsString = mqDest.getDestinationTypeAsString();
				String physicalName = mqDest.getPhysicalName();
				String qualifiedName = mqDest.getQualifiedName();
				
				if (sb.length() > 0) {
					sb.append("\n");
				}
				sb.append(destinationTypeAsString);
				sb.append(" ");
				sb.append(physicalName);
				sb.append(" (");
				sb.append(qualifiedName);
				sb.append(")\n");
				
				Destination destination = entry.getValue();
				
				DestinationStatistics statistics = destination.getDestinationStatistics();
				if (statistics != null) {
					sb.append("Enqueued: "+statistics.getEnqueues().getCount()+"\n");
					sb.append("Dequeued: "+statistics.getDequeues().getCount()+"\n");
					sb.append("Dispatched: "+statistics.getDispatched().getCount()+"\n");
					sb.append("Expired: "+statistics.getExpired().getCount()+"\n");
					sb.append("Inflight: "+statistics.getInflight().getCount()+"\n");
					double avgSize = statistics.getMessageSize().getAverageSize();
					sb.append("Avg message size: "+StringTools.prettyPrintBytesBinary((long) avgSize)+" ("+df.format(avgSize)+")\n");
					sb.append("Avg messages/s: "+df.format(statistics.getMessageSize().getAveragePerSecond())+" / s\n");
					sb.append("Producers: "+statistics.getProducers().getCount()+"\n");
					sb.append("Consumers: "+statistics.getConsumers().getCount()+"\n");
				}
			}
			
			CheckResultEntry destinationEntry = CheckResultEntry.T.create();
			entries.add(destinationEntry);
			destinationEntry.setCheckStatus(CheckStatus.ok);
			destinationEntry.setName("Destinations");
			destinationEntry.setDetails(sb.toString());
		} catch(Exception e) {
			logger.error("Error while trying to collect queue destination information.", e);
		}
	}

	@Required
	public void setBrokerServiceSupplier(Supplier<BrokerService> brokerServiceSupplier) {
		this.brokerServiceSupplier = brokerServiceSupplier;
	}

}
