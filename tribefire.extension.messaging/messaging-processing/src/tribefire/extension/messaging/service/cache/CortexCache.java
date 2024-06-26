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
package tribefire.extension.messaging.service.cache;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.LifecycleAware;
import com.braintribe.cfg.Required;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.gm.model.reason.UnsatisfiedMaybeTunneling;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.Scheduler;

import tribefire.extension.messaging.connector.api.ConsumerMessagingConnector;
import tribefire.extension.messaging.connector.api.ProducerMessagingConnector;
import tribefire.extension.messaging.service.reason.validation.ArgumentNotSatisfied;

public class CortexCache<K, V> implements LifecycleAware {

	private Cache<K, V> cache;
	private Function<V, K> keyExtractionFunction;

	private Duration expirationDuration;
	private boolean expireAfterWrite;
	private RemovalListener<K, V> removalListener;

	@Override
	public void postConstruct() {
		expireAfterWrite = false;
	}

	@Override
	public void preDestroy() {
		if (cache != null) {
			invalidateCache();
		}
	}

	public void initialize() {
		//@formatter:off
        Caffeine<Object, Object> builder = Caffeine.newBuilder()
                .scheduler(Scheduler.systemScheduler());
        //@formatter:on

		if (expireAfterWrite) {
			builder.expireAfterWrite(expirationDuration);
		} else {
			builder.expireAfterAccess(expirationDuration);
		}
		if (removalListener != null) {
			builder.removalListener(removalListener);
		}

		this.cache = builder.build();
	}

	public V get(K key) {
		return this.cache.getIfPresent(key);
	}

	public void put(V value) {
		//@formatter:off
        Function<V, K> keyFunc = Optional.ofNullable(keyExtractionFunction)
                .orElseThrow(()-> new UnsatisfiedMaybeTunneling(Reasons.build(ArgumentNotSatisfied.T)
                                                                    .text("Key extraction function was not supplied to the CortexCache, so putting value without a key is not possible!")
                                                                    .toMaybe()));
        //@formatter:on
		this.cache.put(keyFunc.apply(value), value);
	}

	public void put(K key, V value) {
		this.cache.put(key, value);
	}

	public Collection<V> getAll() {
		return this.cache.asMap().values();
	}

	public <C extends Collection<V>> C getAll(Function<Collection<V>, C> conversionFunction) {
		return conversionFunction.apply(getAll());
	}

	public void invalidateCache() {
		if (this.cache.estimatedSize() > 0) {
			this.cache.asMap().values().forEach(c -> {
				if (c instanceof ProducerMessagingConnector connector) {
					connector.destroy();
				} else if (c instanceof ConsumerMessagingConnector connector) {
					connector.finalizeConsume();
				}
			});
		}
		this.cache.invalidateAll();
		this.cache.cleanUp();
	}

	@Configurable
	@Required
	public void setKeyExtractionFunction(Function<V, K> keyExtractionFunction) {
		this.keyExtractionFunction = keyExtractionFunction;
	}

	@Configurable
	public void setExpirationDuration(Duration expirationDuration) {
		this.expirationDuration = expirationDuration;
	}

	@Configurable
	public void setExpireAfterWrite(boolean expireAfterWrite) {
		this.expireAfterWrite = expireAfterWrite;
	}

	@Configurable
	public void setRemovalListener(RemovalListener<K, V> removalListener) {
		this.removalListener = removalListener;
	}

}
