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
package com.braintribe.transport.messaging.mq.test;

public class Main {

	public static void main(String[] args) throws Exception {
	
		BasicJmsTests.initTests();
		BasicJmsTests tests = new BasicJmsTests();
		
//		System.out.println("connectTest");
//		tests.initialize();
//		tests.connectTest();
//		tests.destroy();

		System.out.println("sendSingleMessageTest");
		tests.initialize();
		tests.sendSingleMessageTest();
		tests.destroy();

		System.out.println("sendMultipleMessagesTest");
		tests.initialize();
		tests.sendMultipleMessagesTest();
		tests.destroy();

		System.out.println("sendMultipleMessagesTestMultiSessions");
		tests.initialize();
		tests.sendMultipleMessagesTestMultiSessions();
		tests.destroy();

		System.out.println("sendMultipleMessagesTestMultiConnections");
		tests.initialize();
		tests.sendMultipleMessagesTestMultiConnections();
		tests.destroy();

		System.out.println("sendMessagesToTopicTest");
		tests.initialize();
		tests.sendMessagesToTopicTest();
		tests.destroy();

		System.out.println("testTopicMultithreadedMassive");
		tests.initialize();
		tests.testTopicMultithreadedMassive();
		tests.destroy();


		BasicJmsTests.shutdown();
	}
	
}
