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
package com.braintribe.model.processing.lock.dmb.impl;

import java.io.File;
import java.util.concurrent.locks.Lock;

import com.braintribe.utils.FileTools;

public class SharedLockReader implements Runnable {

	private int readerId;
	private File file;
	private int iterations;
	private Lock lock;
	
	public SharedLockReader(int readerId, File file, int iterations, Lock lock) {
		this.readerId = readerId;
		this.file = file;
		this.iterations = iterations;
		this.lock = lock;
	}
	
	@Override
	public void run() {
		
		for (int i=0; i<iterations; ++i) {
		
			lock.lock();
			try {
				
				int currentNumber = getNumber(file);
				System.out.println("Reader "+readerId+" read in iteration "+i+": "+currentNumber);
				
			} finally {
				lock.unlock();
			}
			
			try {
				Thread.sleep(1L);
			} catch (InterruptedException e) {
				return;
			}
			
		}
		
	}

	protected static int getNumber(File file) {
		String firstLine = FileTools.readFirstLineFromFile(file);
		return Integer.parseInt(firstLine);
	}
}
