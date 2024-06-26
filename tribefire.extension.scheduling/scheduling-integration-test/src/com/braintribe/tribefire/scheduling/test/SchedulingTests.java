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
package com.braintribe.tribefire.scheduling.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

import tribefire.extension.scheduling.model.Scheduled;
import tribefire.extension.scheduling.model.action.NopAction;
import tribefire.extension.scheduling.model.api.Cancel;
import tribefire.extension.scheduling.model.api.GetList;
import tribefire.extension.scheduling.model.api.ListResult;
import tribefire.extension.scheduling.model.api.PurgeRegistry;
import tribefire.extension.scheduling.model.api.RegistryPurged;
import tribefire.extension.scheduling.model.api.Schedule;
import tribefire.extension.scheduling.model.api.ScheduleResult;

public class SchedulingTests extends AbstractSchedulingTest {

	private boolean initialized = false;

	// -----------------------------------------------------------------------
	// SETUP & TEARDOWN
	// -----------------------------------------------------------------------

	@Override
	@Before
	public void before() throws Exception {
		super.before();
		if (!initialized) {
			initialized = true;
		}
	}

	// -----------------------------------------------------------------------
	// TESTS
	// -----------------------------------------------------------------------

	@Test
	public void testRegisterAndExecution() throws Exception {

		PersistenceGmSession session = getSession();

		PurgeRegistry purge = PurgeRegistry.T.create();
		RegistryPurged purged = purge.eval(session).get();
		System.out.println("Purged " + purged.getRemovedEntryCount() + " entries");

		GetList getList = GetList.T.create();
		ListResult listBefore = getList.eval(session).get();
		assertThat(listBefore.getList()).isEmpty();

		Schedule schedule = Schedule.T.create();
		schedule.setAction(NopAction.T.create());
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.SECOND, 10);
		schedule.setScheduledDate(cal.getTime());
		ScheduleResult scheduleResult = schedule.eval(session).get();
		assertThat(scheduleResult.getScheduledId()).isNotNull();

		ListResult listAfter = getList.eval(session).get();
		assertThat(listAfter.getList()).hasSize(1);
		assertThat((String) listAfter.getList().get(0).getId()).isEqualTo(scheduleResult.getScheduledId());

		Thread.sleep(15000L);

		PersistenceGmSession session2 = getSession();
		Scheduled checkScheduled = session2.query().entity(Scheduled.T, scheduleResult.getScheduledId()).require();
		assertThat(checkScheduled.getExecutionDate()).isNotNull();
		assertThat(checkScheduled.getExecutionSuccess()).isTrue();

		ListResult finalList = getList.eval(session).get();
		assertThat(finalList.getList()).isEmpty();

	}

	@Test
	public void testRegisterAndCancel() throws Exception {

		PersistenceGmSession session = getSession();

		PurgeRegistry purge = PurgeRegistry.T.create();
		RegistryPurged purged = purge.eval(session).get();
		System.out.println("Purged " + purged.getRemovedEntryCount() + " entries");

		GetList getList = GetList.T.create();
		ListResult listBefore = getList.eval(session).get();
		assertThat(listBefore.getList()).isEmpty();

		Schedule schedule = Schedule.T.create();
		schedule.setAction(NopAction.T.create());
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.SECOND, 10);
		schedule.setScheduledDate(cal.getTime());
		ScheduleResult scheduleResult = schedule.eval(session).get();
		assertThat(scheduleResult.getScheduledId()).isNotNull();

		ListResult listAfter = getList.eval(session).get();
		assertThat(listAfter.getList()).hasSize(1);
		assertThat((String) listAfter.getList().get(0).getId()).isEqualTo(scheduleResult.getScheduledId());

		Cancel cancel = Cancel.T.create();
		cancel.setScheduledId(scheduleResult.getScheduledId());
		cancel.eval(session).get();

		PersistenceGmSession session2 = getSession();
		Scheduled checkScheduled = session2.query().entity(Scheduled.T, scheduleResult.getScheduledId()).find();
		assertThat(checkScheduled).isNull();

	}
}
