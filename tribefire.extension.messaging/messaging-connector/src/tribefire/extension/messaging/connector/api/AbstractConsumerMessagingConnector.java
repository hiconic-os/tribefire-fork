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
package tribefire.extension.messaging.connector.api;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.api.Marshaller;
import com.braintribe.codec.marshaller.json.JsonStreamMarshaller;
import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;

import tribefire.extension.messaging.model.Message;

public abstract class AbstractConsumerMessagingConnector implements ConsumerMessagingConnector {
	private static final Logger logger = Logger.getLogger(AbstractConsumerMessagingConnector.class);
	private static final Marshaller marshaller = new JsonStreamMarshaller();

	private static final GmDeserializationOptions DESERIALIZATION_OPTIONS = GmDeserializationOptions.deriveDefaults()
			.absentifyMissingProperties(false).build();

	// -----------------------------------------------------------------------
	// CONSUME
	// -----------------------------------------------------------------------

	@Override
	public List<Message> consumeMessages() {
		List<byte[]> consumedBytesList = consumerConsume();
		//@formatter:off
		List<Message> messages = consumedBytesList.stream()
				.map(this::unmarshallMessage).toList();
		//@formatter:on

		if (consumedBytesList.size() != messages.size()) {
			logger.error(() -> "Was only possible to unmarshal '" + messages.size() + "' out of '" + consumedBytesList.size()
					+ "'. Ignore and continue...");
		}

		return messages;
	}

	protected abstract List<byte[]> consumerConsume();

	@Override
	public void initConsume(Set<String> topicsToListen) {
		// nothing so far
	}

	@Override
	public void finalizeConsume() {
		// nothing so far
	}

	// -----------------------------------------------------------------------
	// HEALTH
	// -----------------------------------------------------------------------

	@Override
	public CheckResultEntry health() {
		return actualHealth();
	}

	protected abstract CheckResultEntry actualHealth();

	// -----------------------------------------------------------------------
	// HELPERS - UNMARSHALLING
	// -----------------------------------------------------------------------

	protected Message unmarshallMessage(byte[] rawMessage) {
		try {
			return (Message) marshaller.unmarshall(new ByteArrayInputStream(rawMessage), DESERIALIZATION_OPTIONS);
		} catch (Exception e) {
			logger.error("Could not unmarshall message: '" + getMessageAsString(rawMessage) + "'");
			return null;
		}
	}

	private String getMessageAsString(byte[] rawMessage) {
		String messageAsString = "unknown";
		try {
			messageAsString = new String(rawMessage, StandardCharsets.UTF_8);
		} catch (Exception nothing) {
			// nothing
		}
		return messageAsString;
	}

	// -----------------------------------------------------------------------
	// HELPERS
	// -----------------------------------------------------------------------

}
