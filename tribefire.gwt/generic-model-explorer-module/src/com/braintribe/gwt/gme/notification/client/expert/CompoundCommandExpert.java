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
package com.braintribe.gwt.gme.notification.client.expert;

import java.util.function.Supplier;

import com.braintribe.cfg.Required;
import com.braintribe.gwt.logging.client.Logger;
import com.braintribe.model.command.Command;
import com.braintribe.model.command.CompoundCommand;
import com.braintribe.model.processing.core.expert.api.GmExpertRegistry;
import com.braintribe.model.processing.notification.api.CommandExpert;

public class CompoundCommandExpert implements CommandExpert<CompoundCommand> {
	private static final Logger logger = new Logger(CompoundCommandExpert.class);
	
	private Supplier<? extends GmExpertRegistry> gmCommandRegistrySupplier;
	private GmExpertRegistry gmCommandRegistry;
	
	@Required
	public void setCommandRegistry(Supplier<? extends GmExpertRegistry> gmCommandRegistrySupplier) {
		this.gmCommandRegistrySupplier = gmCommandRegistrySupplier;
	}
	
	@Override
	public void handleCommand(CompoundCommand compoundCommand) {
		if (gmCommandRegistry == null)
			gmCommandRegistry = gmCommandRegistrySupplier.get();
		
		for (Command command : compoundCommand.getCommands()) {
			CommandExpert<Command> ce = gmCommandRegistry.findExpert(CommandExpert.class).<CommandExpert<Command>>forInstance(command);
			if (ce != null)
				ce.handleCommand(command);
			else
				logger.info("No expert found for: " + command);
		}
	}

}
