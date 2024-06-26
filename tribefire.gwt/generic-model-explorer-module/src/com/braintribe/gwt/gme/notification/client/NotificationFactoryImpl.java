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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.model.command.Command;
import com.braintribe.model.generic.manipulation.DeleteManipulation;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.tracking.ManipulationListener;
import com.braintribe.model.notification.InternalCommand;
import com.braintribe.model.notification.Level;
import com.braintribe.model.notification.MessageNotification;
import com.braintribe.model.notification.MessageWithCommand;
import com.braintribe.model.notification.Notification;
import com.braintribe.model.notification.NotificationEventSource;
import com.braintribe.model.processing.notification.api.NotificationFactory;
import com.braintribe.model.processing.notification.api.NotificationListener;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.processing.session.api.notifying.GenericManipulationListenerRegistry;

public class NotificationFactoryImpl implements NotificationFactory {

	private ManagedGmSession session;
	private Supplier<? extends NotificationListener> listenerSupplier;
	private NotificationListener listener;

	private final Map<InternalCommand, Object> transientObjects = new HashMap<InternalCommand, Object>();

	private final ManipulationListener transientListener = new ManipulationListener() {
		@Override
		public void noticeManipulation(Manipulation manipulation) {
			if (manipulation instanceof DeleteManipulation)
				transientObjects.remove(((DeleteManipulation) manipulation).getEntity());
		}
	};

	public void setSession(ManagedGmSession session) {
		this.session = session;
	}

	public void setListener(Supplier<? extends NotificationListener> listenerSupplier) {
		this.listenerSupplier = listenerSupplier;
	}

	@Override
	public <NES extends NotificationEventSource> NES createEventSource(EntityType<NES> entityType) {
		NES nes = session.create(entityType);
		nes.setId("#" + nes.hashCode());
		return nes;
	}

	@Override
	public <C extends Command> C createCommand(EntityType<C> entityType) {
		C c = session.create(entityType);
		c.setId((long) c.hashCode());
		return c;
	}

	@Override
	public <N extends Notification> N createNotification(EntityType<N> entityType) {
		N n = session.create(entityType);
		n.setId((long) n.hashCode());
		return n;
	}

	@Override
	public <MN extends MessageNotification> MN createNotification(EntityType<MN> entityType, Level level, String message) {
		MN mn = createNotification(entityType);
		mn.setLevel(level);
		mn.setMessage(message);
		return mn;
	}

	@Override
	public <C extends Command> MessageWithCommand createNotification(EntityType<C> entityType, Level level, String message, String name) {
		C command = session.create(entityType);
		command.setId((long) command.hashCode());
		command.setName(name);
		MessageWithCommand mwc = createNotification(MessageWithCommand.T, level, message);
		mwc.setCommand(command);
		return mwc;
	}

	@Override
	public void broadcast(List<Notification> notifications, NotificationEventSource eventSource) {
		if (listener == null && listenerSupplier != null)
			listener = listenerSupplier.get();
		
		if (listener != null)
			listener.handleNotifications(notifications, eventSource);
	}

	@Override
	public GenericManipulationListenerRegistry listeners() {
		return session.listeners();
	}

	@Override
	public Object getTransientObject(InternalCommand command) {
		return transientObjects.get(command);
	}

	@Override
	public InternalCommand createTransientCommand(String name, Object object) {
		InternalCommand tc = createCommand(InternalCommand.T);
		tc.setName(name);
		if (transientObjects.put(tc, object) == null)
			listeners().entity(tc).add(transientListener);
		return tc;
	}

}
