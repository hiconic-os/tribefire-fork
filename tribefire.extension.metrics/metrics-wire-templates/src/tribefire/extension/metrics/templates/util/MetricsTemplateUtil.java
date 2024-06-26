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
package tribefire.extension.metrics.templates.util;

import com.braintribe.logging.Logger;
import com.braintribe.utils.StringTools;
import com.braintribe.utils.lcd.CommonTools;
import com.braintribe.wire.api.scope.InstanceConfiguration;
import com.braintribe.wire.api.scope.InstanceQualification;

import tribefire.extension.metrics.MetricsConstants;
import tribefire.extension.metrics.templates.api.MetricsTemplateContext;

/**
 * Utility class related to handling of {@link MetricsTemplateContext}
 * 
 *
 */
public class MetricsTemplateUtil {

	private static final Logger logger = Logger.getLogger(MetricsTemplateUtil.class);

	/**
	 * Create a globalId based on the {@link MetricsTemplateContext}
	 * 
	 * @param context
	 *            {@link MetricsTemplateContext}
	 * @param instanceConfiguration
	 *            {@link InstanceConfiguration}
	 * @return context based globalId
	 */
	public static String resolveContextBasedGlobalId(MetricsTemplateContext context, InstanceConfiguration instanceConfiguration) {
		String _context = resolveContext(context);

		String globalIdPrefix = context.getGlobalIdPrefix();
		String _globalIdPrefix = "";
		if (!CommonTools.isEmpty(globalIdPrefix)) {
			_globalIdPrefix = globalIdPrefix + "/";
		}

		InstanceQualification qualification = instanceConfiguration.qualification();

		String globalId = "wire://" + _globalIdPrefix + qualification.space().getClass().getSimpleName() + "/" + qualification.name() + "/"
				+ _context;

		return globalId;
	}

	/**
	 * Create a human readable Deployable name based on the {@link MetricsTemplateContext}
	 * 
	 * @param deployableName
	 *            Deployable name without context information
	 * @param context
	 *            {@link MetricsTemplateContext}
	 * @return context based Deployable name
	 */
	public static String resolveContextBasedDeployableName(String deployableName, MetricsTemplateContext context) {
		String contextBasedDeployableName = deployableName + " - " + resolvePrettyName(context);
		logger.debug(() -> "Resolve context based deployable name: '" + contextBasedDeployableName + "' for context: '" + context + "'");
		return contextBasedDeployableName;
	}

	public static String resolveContext(MetricsTemplateContext context) {
		String _context = context.getContext();

		if (CommonTools.isEmpty(_context)) {
			throw new IllegalStateException("'context' must be specified!");
		}

		String idPrefix = StringTools.camelCaseToDashSeparated(_context);

		logger.debug(() -> "Resolve idPrefix: '" + idPrefix + "' for context: '" + context + "'");

		return idPrefix;
	}

	public static String resolveServiceModelName(MetricsTemplateContext context) {
		return MetricsConstants.SERVICE_MODEL_QUALIFIEDNAME + "-" + MetricsTemplateUtil.resolveContext(context);
	}

	// -----------------------------------------------------------------------
	// HELPER METHODS
	// -----------------------------------------------------------------------

	private static String resolvePrettyName(MetricsTemplateContext context) {
		String _context = resolveContext(context);
		String prettyName = StringTools.prettifyCamelCase(_context);

		logger.debug(() -> "Resolve prettyName: '" + prettyName + "' for context: '" + context + "'");
		return prettyName;
	}

}
