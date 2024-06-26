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
package tribefire.extension.antivirus.connector.api;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.resource.Resource;
import com.braintribe.utils.lcd.CommonTools;

import tribefire.extension.antivirus.model.service.result.AbstractAntivirusResult;

public abstract class AbstractAntivirusConnector<T extends AbstractAntivirusResult> implements AntivirusConnector<T> {

	private static final Logger logger = Logger.getLogger(AbstractAntivirusConnector.class);

	protected Resource resource;
	private String providerType;

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	@Override
	public T scan() {
		long start = System.currentTimeMillis();
		T result = scanViaExpert();
		long end = System.currentTimeMillis();

		long duration = (end - start);
		result.setDurationInMs(duration);

		String detailedInformation = resourceInformation(resource);

		logger.debug(() -> "Executing virus scan of: '" + detailedInformation + "' using: '" + providerType + "' in: '" + duration + "'ms");

		return result;
	}

	abstract protected T scanViaExpert();

	// Health check

	@Override
	public CheckResultEntry actualHealth() {
		long start = System.currentTimeMillis();
		CheckResultEntry result = healthCheck();
		long end = System.currentTimeMillis();

		long duration = (end - start);

		String detailedInformation = resourceInformation(resource);
		result.setDetails(detailedInformation);

		return result;
	}

	abstract protected CheckResultEntry healthCheck();

	// ------------------

	protected static String resourceName(Resource resource) {
		String name = resource.getName();
		if (CommonTools.isEmpty(name)) {
			name = "__notSet__";
		}
		return name;
	}

	public static String resourceInformation(Resource resource) {
		StringBuilder sb = new StringBuilder();
		Object id = resource.getId();
		if (id == null) {
			sb.append("ID: '__notSet__'");
		} else {
			sb.append("ID: '");
			sb.append(id.toString());
			sb.append("'");
		}
		sb.append(",");
		String fileName = resourceName(resource);
		sb.append(fileName);
		return sb.toString();
	}

	protected <R extends AbstractAntivirusResult> R createResult(EntityType<R> entityType, boolean infected, String message) {
		R result = entityType.create();
		String fileName = resourceName(resource);
		result.setResourceId(resource.getId());
		result.setResourceName(fileName);
		result.setInfected(infected);
		result.setMessage(message);

		return result;
	}

	public static <E extends AbstractAntivirusConnector<? extends AbstractAntivirusResult>> E createExpert(Supplier<E> factory,
			Consumer<E> configurer) {
		E expert = factory.get();
		configurer.accept(expert);
		return expert;
	}
}
