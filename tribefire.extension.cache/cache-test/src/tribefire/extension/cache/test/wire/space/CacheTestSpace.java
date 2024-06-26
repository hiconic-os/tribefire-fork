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
package tribefire.extension.cache.test.wire.space;

import com.braintribe.codec.marshaller.json.JsonMarshaller;
import com.braintribe.model.processing.service.common.ConfigurableDispatchingServiceProcessor;
import com.braintribe.model.service.api.InstanceId;
import com.braintribe.model.time.TimeSpan;
import com.braintribe.model.time.TimeUnit;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.cache.model.deployment.service.cache2k.Cache2kCacheAspectConfiguration;
import tribefire.extension.cache.model.deployment.service.cache2k.SimpleConstantExpiration;
import tribefire.extension.cache.model.service.demo.CacheDemo;
import tribefire.extension.cache.service.CacheAspect;
import tribefire.extension.cache.service.CacheAspectAdminServiceProcessor;
import tribefire.extension.cache.service.CacheAspectInterface;
import tribefire.extension.cache.service.CacheDemoProcessor;
import tribefire.extension.cache.service.cache2k.Cache2kCacheAspect;
import tribefire.extension.cache.test.wire.contract.CacheTestContract;

@Managed
public class CacheTestSpace extends AbstractCacheTestSpace implements CacheTestContract {

	@Override
	protected void configureServices(ConfigurableDispatchingServiceProcessor bean) {
		bean.register(CacheDemo.T, cacheDemoProcessor());
		bean.registerInterceptor("test-aspect-interceptor").registerForType(CacheDemo.T, cacheAspect());

	}

	// -----------------------------------------------------------------------
	// PROCESSOR
	// -----------------------------------------------------------------------

	@Managed
	public CacheDemoProcessor cacheDemoProcessor() {
		CacheDemoProcessor bean = new CacheDemoProcessor();
		return bean;
	}

	@Managed
	public CacheAspect cacheAspect() {

		CacheAspectInterface expert = cache2kCacheAspect();

		tribefire.extension.cache.model.deployment.service.CacheAspect deployable = tribefire.extension.cache.model.deployment.service.CacheAspect.T
				.create();

		CacheAspect bean = new CacheAspect();
		bean.setDeployable(deployable);
		bean.setExpert(expert);
		bean.setMarshaller(new JsonMarshaller());

		return bean;
	}

	@Managed
	private Cache2kCacheAspect cache2kCacheAspect() {

		Cache2kCacheAspect bean = new Cache2kCacheAspect();
		bean.setCacheNameSupplier(() -> "test");

		SimpleConstantExpiration simpleConstantExpiration = SimpleConstantExpiration.T.create();

		Cache2kCacheAspectConfiguration configuration = Cache2kCacheAspectConfiguration.T.create();
		configuration.setExpiration(simpleConstantExpiration);
		bean.setConfiguration(configuration);

		return bean;
	}

	@Managed
	public CacheAspectAdminServiceProcessor cacheAspectLocalStatus() {

		TimeSpan multicastTimeout = TimeSpan.T.create();
		multicastTimeout.setUnit(TimeUnit.second);
		multicastTimeout.setValue(10);

		InstanceId multicastInstanceId = InstanceId.T.create();
		multicastInstanceId.setNodeId("testNodeId");
		multicastInstanceId.setApplicationId("testApplicationId");

		tribefire.extension.cache.model.deployment.service.CacheAspectAdminServiceProcessor deployable = tribefire.extension.cache.model.deployment.service.CacheAspectAdminServiceProcessor.T
				.create();

		CacheAspectAdminServiceProcessor bean = new CacheAspectAdminServiceProcessor<>();
		bean.setDeployable(deployable);
		// bean.setCortexSessionProvider(cortexSessionSupplier());
		// bean.setDeployRegistry(tfPlatform.deployment().deployRegistry());
		bean.setMulticastInstanceId(multicastInstanceId);
		bean.setMulticastTimeout(multicastTimeout);

		return bean;
	}

}
