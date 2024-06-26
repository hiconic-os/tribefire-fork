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
package com.braintribe.codec.marshaller.jse;




public class PropertyPoolAddressSequence extends PoolAddressSequence  {
	//private static final char[] firstDigitChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final char[] firstDigitChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$".toCharArray();
	private static final char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$123456789".toCharArray();
	
	private int[] sequence = new int[6];
	private int digits = 1;
	public int count = 0;
	private char poolName;
	
	public PropertyPoolAddressSequence(char poolName) {
		this.poolName = poolName;
	}
	
	public char[] next() {
		count++;
		char extract[] = new char[digits+2];
		extract[0] = poolName;
		extract[1] = '.';
		char chars[] = firstDigitChars;
		int base = chars.length;
		boolean carry = true;
		for (int i = 0; i < digits; i++) {
			int d = sequence[i];
			
			extract[i + 2] = chars[d];
			
			if (carry) {
				d++;
				
				if (d == base) {
					sequence[i] = 0;
				}
				else {
					sequence[i] = d;
					carry = false;
				}
			}
			
			if (i == 0) {
				chars = PropertyPoolAddressSequence.chars;
				base = chars.length;
			}
		}
		
		if (carry) {
			digits++;
		}
		
		return extract;
	}
	
	public int getCount() {
		return count;
	}
	
	public static void main(String[] args) {
		PropertyPoolAddressSequence sequence = new PropertyPoolAddressSequence('E');
		for (int i = 0; i < 100000; i++) {
			System.out.println(new String(sequence.next()));
		}
	}
}