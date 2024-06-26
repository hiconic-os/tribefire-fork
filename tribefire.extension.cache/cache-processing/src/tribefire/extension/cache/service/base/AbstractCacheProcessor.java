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
package tribefire.extension.cache.service.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.notification.HasNotifications;
import com.braintribe.model.notification.Level;
import com.braintribe.model.notification.Notification;
import com.braintribe.model.processing.notification.api.builder.Notifications;
import com.braintribe.model.processing.notification.api.builder.NotificationsBuilder;

import tribefire.extension.cache.model.service.demo.CacheDemo;

/**
 * Base for all CACHE service processors
 * 
 *
 */
// TODO: should be the base class
public abstract class AbstractCacheProcessor {

	protected <T extends HasNotifications> ResponseBuilder<T> responseBuilder(EntityType<T> responseType, CacheDemo request) {

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

	protected <T extends HasNotifications> T prepareSimpleNotification(EntityType<T> responseEntityType, CacheDemo request, Level level,
			String msg) {

		//@formatter:off
		T result = responseBuilder(responseEntityType, request)
				.notifications(builder -> 
					builder	
					.add()
						.message()
							.level(level)
							.message(msg)
						.close()
					.close()
				).build();
		//@formatter:on
		return result;
	}

}
