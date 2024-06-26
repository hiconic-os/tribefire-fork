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
package tribefire.extension.process.processing.condition;

import java.util.HashMap;
import java.util.Map;

import com.braintribe.logging.Logger;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

import tribefire.extension.process.api.ConditionProcessorContext;
import tribefire.extension.process.model.data.Process;

public class BasicConditionProcessorContext<S extends Process> implements ConditionProcessorContext<S> {
	private static final Logger logger = Logger.getLogger(BasicConditionProcessorContext.class);
	private enum Axis { request, system }
	private S process;
	private PersistenceGmSession session;
	private Map<Axis, BasicConditionProcessorContext<S>> axises; 
	
	public BasicConditionProcessorContext(PersistenceGmSession session, PersistenceGmSession systemSession, S subject, S systemSubject) {
		this(session, subject);
		BasicConditionProcessorContext<S> systemContext = new BasicConditionProcessorContext<S>(systemSession, systemSubject);

		axises = new HashMap<Axis, BasicConditionProcessorContext<S>>();
		axises.put(Axis.request, this);
		axises.put(Axis.system, systemContext);

		systemContext.axises = axises;
	}
	
	private BasicConditionProcessorContext(PersistenceGmSession session, S subject) {
		this.session = session;
		this.process = subject;
	}

	@Override
	public S getProcess() {
		return process;
	}

	@Override
	public PersistenceGmSession getSession() {
		return session;
	}

	@Override
	public ConditionProcessorContext<S> system() {
		return axises.get(Axis.system);
	}

	@Override
	public ConditionProcessorContext<S> request() {
		return axises.get(Axis.request);
	}

	protected void commitIfNecessary( PersistenceGmSession session) throws GmSessionException {
		if (session.getTransaction().hasManipulations()) {
			session.commit();
		}
	}

	public void commitIfNecessary() throws GmSessionException{
		for (Axis axis: new Axis[]{Axis.system, Axis.request}) {
			BasicConditionProcessorContext<S> context = axises.get(axis);
			try {
				commitIfNecessary(context.session);
			} catch (GmSessionException e) {
				String msg = "cannot commit " + axis + " session for subject " + process;
				logger.error( msg, e);
				throw e;
			}
		}
	}

}
