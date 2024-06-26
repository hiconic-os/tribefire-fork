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
package com.braintribe.devrock.mc.core.scratch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.KnownIssue;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.LoadingCache;
@Category(KnownIssue.class)
public class CaffeineExpireTest {
	
	private LoadingCache<String,String> cache = Caffeine.newBuilder().expireAfter( new Expiry<String, String>() {

		@Override
		public long expireAfterCreate(@NonNull String key, @NonNull String value, long currentTime) {
			return Long.MAX_VALUE;
		}

		@Override
		public long expireAfterUpdate(@NonNull String key, @NonNull String value, long currentTime, @NonNegative long currentDuration) {
			return currentDuration;
		}

		@Override
		public long expireAfterRead(@NonNull String key, @NonNull String value, long currentTime, @NonNegative long currentDuration) {
			if (numRead > maxRead) {
				numRead = 0;
				return 0;
			}
			return currentDuration;
		}
		
	}).build(this::load);
	
	private boolean abort;
	private int numRead = 0;
	private int maxRead = 10;
	private int numLoaded = 0;

	@Test
	public void expiryTest() {
		
		
		try {
			ExecutorService threadPool = Executors.newFixedThreadPool(4);
			
			Future<?> f1 = threadPool.submit( this::accessHash1);			
			
			Thread.sleep( 2000);
			abort = true;
			
			f1.get();

			threadPool.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public void accessHash1() {
		while (!abort) {
			try {
				System.out.println("num read : " + numRead++ + " gives " + cache.get( "foobar"));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	
	}
	
	public String load( String in) {
		System.out.println("loading : " + in);
		try {
	
			if (in.startsWith( "foobar"))
				Thread.sleep( 500);			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "->" + in + numLoaded++;
	}
	
	
}
