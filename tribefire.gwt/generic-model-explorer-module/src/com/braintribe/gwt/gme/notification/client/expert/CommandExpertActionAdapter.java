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
import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.model.command.Command;
import com.braintribe.model.processing.notification.api.CommandExpert;
import com.google.gwt.core.client.Scheduler;

public class CommandExpertActionAdapter<T extends Command> implements CommandExpert<T> {

	public static final String COMMAND_PROPERTY = "command";

	private Supplier<? extends Action> actionProvider;

	@Required
	public void setActionProvider(Supplier<? extends Action> actionProvider) {
		this.actionProvider = actionProvider;
	}

	@Override
	public synchronized void handleCommand(final T command) {
		TriggerInfo triggerInfo = new TriggerInfo();
		triggerInfo.put(COMMAND_PROPERTY, command);
		Scheduler.ScheduledCommand cmd = new ScheduledActionCommand(actionProvider.get(), triggerInfo);
		Scheduler.get().scheduleDeferred(cmd);
	}

	private class ScheduledActionCommand implements Scheduler.ScheduledCommand {

		private final Action action;
		private final TriggerInfo triggerInfo;

		public ScheduledActionCommand(Action action, TriggerInfo triggerInfo) {
			this.action = action;
			this.triggerInfo = triggerInfo;
		}

		@Override
		public void execute() {
			action.perform(triggerInfo);
		}

	}

}
