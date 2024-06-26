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
package com.braintribe.model.access.smood.distributed.test.concurrent.tester;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.braintribe.model.access.smood.distributed.test.concurrent.worker.WorkerExecutor;
import com.braintribe.model.access.smood.distributed.test.utils.InputStreamWriter;

public class MultiJvmSmoodDbAccessTest extends AbstractSmoodDbAccessTest {

	@Override
	protected void executeWorkers(int workerCountParam, int iterations) throws Exception {

		String separator = System.getProperty("file.separator");
		String classpath = System.getProperty("java.class.path");
		String path = System.getProperty("java.home") + separator + "bin" + separator + "java";
		
		List<Process> processes = new ArrayList<Process>();
		for (int i=0; i<workerCountParam; ++i) {
			ProcessBuilder processBuilder = new ProcessBuilder(path, "-cp", classpath, WorkerExecutor.class.getName(), ""+i);
			Process process = processBuilder.start();
			processes.add(process);
			
			InputStream inStream = process.getInputStream();
			InputStreamWriter ishStdout = new InputStreamWriter(inStream);
			ishStdout.start();
		}
		
		for (Process p : processes) {
			p.waitFor();
		}
		
	}

}
