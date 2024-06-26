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
package com.braintribe.utils.stream.tracking;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.braintribe.logging.Logger;
import com.braintribe.utils.RandomTools;
import com.braintribe.utils.stream.tracking.data.StreamInformation;
import com.braintribe.utils.stream.tracking.data.StreamStatistics;
import com.braintribe.utils.stream.tracking.data.StreamTrackingCollection;
import com.braintribe.utils.stream.tracking.stream.TrackedOutputStream;

/**
 * Utility class to keep track of OutputStreams and also to get statistics.
 */
public class OutputStreamTracker {

	private static final Logger logger = Logger.getLogger(OutputStreamTracker.class);

	private static String DEFAULT_TENANT = "default";

	private ConcurrentHashMap<String, StreamTrackingCollection> trackingDataMap = new ConcurrentHashMap<>();

	public OutputStream wrapOutputStream(OutputStream delegate, String tenant, String clientIdentifier, String context) {
		if (tenant == null) {
			tenant = DEFAULT_TENANT;
		}

		String streamId = RandomTools.newStandardUuid();

		if (logger.isDebugEnabled()) {
			logger.debug("Wrapping OutputStream " + streamId + " for tenant " + tenant + ", clientIdentifier: " + clientIdentifier + " and context "
					+ context);
		}

		StreamInformation info = new StreamInformation(tenant, streamId, clientIdentifier, context);
		TrackedOutputStream wrapper = new TrackedOutputStream(this, info, delegate);

		StreamTrackingCollection trackingData = trackingDataMap.computeIfAbsent(tenant, t -> new StreamTrackingCollection(t));
		trackingData.registerNewStream(streamId, info);

		return wrapper;
	}

	public void streamClosed(StreamInformation streamInfo) {
		StreamTrackingCollection trackingData = trackingDataMap.get(streamInfo.getTenant());
		trackingData.registerEofReached();
		trackingData.registerStreamClosed(streamInfo);
	}

	public TreeMap<String, StreamStatistics> getStatistics() {
		TreeMap<String, StreamStatistics> result = new TreeMap<>();
		List<StreamTrackingCollection> dataListClone = new ArrayList<>(trackingDataMap.values());
		for (StreamTrackingCollection d : dataListClone) {
			result.put(d.getTenant(), d.getStatistics());
		}
		return result;
	}

	public List<StreamTrackingCollection> getAllStreamTrackingCollections() {
		return new ArrayList<>(trackingDataMap.values());
	}

}
