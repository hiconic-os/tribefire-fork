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
package tribefire.extension.elastic.elasticsearch;

import java.util.Collection;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.Node;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.PluginsService;

import com.braintribe.logging.Logger;

public class PluginEnabledNode extends Node {

	private static final Logger logger = Logger.getLogger(PluginEnabledNode.class);

	public PluginEnabledNode(Settings settings, Collection<Class<? extends Plugin>> plugins) {
		// TODO: check migration from 2.2.1
		// super(InternalSettingsPreparer.prepareEnvironment(settings, null), Version.CURRENT, plugins);
		super(InternalSettingsPreparer.prepareEnvironment(settings, null), plugins);
	}

	// TODO: check migration from 2.2.1
	@Override
	public PluginsService getPluginsService() {
		try {
			PluginsService services = super.getPluginsService();
			return services;
		} catch (Exception e) {
			logger.debug("Could not access the plugins service.", e);
		}
		return null;
	}
}
