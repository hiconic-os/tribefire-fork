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
package com.braintribe.gwt.gme.notification.client;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.gwt.gme.constellation.client.LocalizedText;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.model.generic.reflection.ScalarType;
import com.braintribe.model.notification.CommandNotification;
import com.braintribe.model.notification.Level;
import com.braintribe.model.notification.MessageNotification;
import com.braintribe.model.notification.Notification;

public class MessageModel {
	
	private String message;
	private Level level;
	private String id;
	private CommandModel command;
	private List<String> context = new ArrayList<>();
	private Notification notification;
	private boolean ignore;
	
	public MessageModel(Notification notification) {
		this.notification = notification;
		boolean messageSet = false;
		if (notification instanceof MessageNotification) {
			messageSet = true;
			message = ((MessageNotification) notification).getMessage();
			level = ((MessageNotification) notification).getLevel();
						
			//RVE test
			/*
			Set<String> setContext;
			if (notification.getContext() != null) {
				setContext = notification.getContext();
				if (Math.random() > 0.5)
					setContext.add("RVE");
				else
					setContext.add("MessageNotification");
			}
			*/
			
			if (notification.getContext() != null || !notification.getContext().isEmpty()) 
				context.addAll(notification.getContext());
		}
		
		if (notification instanceof CommandNotification) {
			command = new CommandModel(((CommandNotification) notification).getCommand());
			if (notification.getContext() != null || !notification.getContext().isEmpty()) 
				context.addAll(notification.getContext());
			if (!messageSet) {
				message = LocalizedText.INSTANCE.executedUiCommand(command.getName());
				level = Level.INFO;
				ignore = true;
			}
		}
		
		Object idObject = notification.getId();
		if (idObject == null) {
			idObject = (long) notification.hashCode();
			notification.setId(idObject);
		}
		ScalarType type = (ScalarType) notification.entityType().getIdProperty().getType().getActualType(idObject);
		id = type.instanceToString(idObject);
		
		if (notification instanceof MessageNotification && GlobalState.MASK_DETAILS.equals(((MessageNotification) notification).getDetails()))
			ignore = true;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean isIgnore() {
		return ignore;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public CommandModel getCommand() {
		return command;
	}
	
	public void setCommand(CommandModel command) {
		this.command = command;
	}

	public List<String> getContext() {
		return context;
	}

	public Notification getNotification() {
		return notification;
	}

}
