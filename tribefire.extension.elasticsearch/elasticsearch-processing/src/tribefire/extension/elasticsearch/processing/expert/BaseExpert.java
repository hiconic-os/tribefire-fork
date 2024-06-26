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
package tribefire.extension.elasticsearch.processing.expert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.notification.HasNotifications;
import com.braintribe.model.notification.Notification;
import com.braintribe.model.processing.notification.api.builder.Notifications;
import com.braintribe.model.processing.notification.api.builder.NotificationsBuilder;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import tribefire.extension.elasticsearch.model.api.ElasticsearchRequest;
import tribefire.extension.elasticsearch.model.api.ElasticsearchResponse;
import tribefire.extension.elasticsearch.processing.expert.base.ResponseBuilder;

public abstract class BaseExpert<S extends ElasticsearchRequest, T extends ElasticsearchResponse> implements Expert<T> {

	protected S request;

	protected ElasticsearchClient client;

	protected String indexName;

	// ***************************************************************************************************
	// Configuration
	// ***************************************************************************************************

	public void setRequest(S request) {
		this.request = request;
	}

	public void setClient(ElasticsearchClient client) {
		this.client = client;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	// ***************************************************************************************************
	// Generic factory method to create and configure concrete instances
	// ***************************************************************************************************

	protected static <E extends BaseExpert<? extends ElasticsearchRequest, ? extends ElasticsearchResponse>> E createExpert(Supplier<E> factory,
			Consumer<E> configurer) {
		E expert = factory.get();
		configurer.accept(expert);
		return expert;
	}

	// -----------------------------------------------------------------------
	// FOR NOTIFICATIONS
	// -----------------------------------------------------------------------

	protected <T extends HasNotifications> ResponseBuilder<T> responseBuilder(EntityType<T> responseType, ElasticsearchRequest request) {

		return new ResponseBuilder<T>() {
			private List<Notification> localNotifications = new ArrayList<>();
			private boolean ignoreCollectedNotifications = false;
			private Consumer<T> enricher;
			private NotificationsBuilder notificationsBuilder = null;
			private List<Notification> notifications = new ArrayList<>();

			@Override
			public ResponseBuilder<T> notifications(Supplier<List<Notification>> notificationsSupplier) {
				notifications = notificationsSupplier.get();
				return this;
			}
			@Override
			public ResponseBuilder<T> notifications(Consumer<NotificationsBuilder> consumer) {
				this.notificationsBuilder = Notifications.build();
				consumer.accept(notificationsBuilder);
				return this;
			}

			@Override
			public ResponseBuilder<T> ignoreCollectedNotifications() {
				this.ignoreCollectedNotifications = true;
				return this;
			}

			@Override
			public ResponseBuilder<T> responseEnricher(Consumer<T> enricher) {
				this.enricher = enricher;
				return this;
			}

			@Override
			public T build() {

				T response = responseType.create();
				if (enricher != null) {
					this.enricher.accept(response);
				}
				if (request.getSendNotifications()) {
					response.setNotifications(localNotifications);
					if (!ignoreCollectedNotifications) {

						if (notificationsBuilder != null) {
							notifications.addAll(notificationsBuilder.list());
						}

						Collections.reverse(notifications);
						response.getNotifications().addAll(notifications);
					}
				}
				return response;
			}
		};

	}

}
