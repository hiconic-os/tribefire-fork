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
package com.braintribe.utils;

import java.io.IOException;

/**
 * Simple Appender that appends Strings to an internal StringBuilder, but limits the maximum
 * size of the String stored. If the size of the contained String exceeds the provided maximum,
 * the corresponding number of characters are removed at the head of the StringBuilder.
 *
 */
public class LimitedStringBuilder implements Appendable {

	private StringBuilder delegate = new StringBuilder();
	private int maxSize;

	public LimitedStringBuilder(int maxSize) {
		if (maxSize <= 0) {
			throw new IllegalArgumentException("The size "+maxSize+" must be larger than 0.");
		}
		this.maxSize = maxSize;
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
	
	@Override
	public Appendable append(CharSequence csq) throws IOException {
		if (csq != null) {
			int appendLength = csq.length();
			int start = 0;
			if (appendLength > maxSize) {
				start = appendLength - maxSize;
				appendLength = maxSize;
			}
			int newLength = delegate.length() + appendLength;
			if (newLength > maxSize) {
				int remove = newLength - maxSize;
				delegate.delete(0, remove);
			}
			delegate.append(csq, start, start+appendLength);
		}
		return this;
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws IOException {
		if (csq != null && start >= 0 && end >= start) {
			int appendLength = (end - start);
			if (appendLength > maxSize) {
				start += maxSize - appendLength; 
				appendLength = maxSize;
			}
			int newLength = delegate.length() + appendLength;
			if (newLength > maxSize) {
				int remove = newLength - maxSize;
				delegate.delete(0, remove);
			}
			delegate.append(csq, start, end);
		}
		return this;
	}

	@Override
	public Appendable append(char c) throws IOException {
		int newLength = delegate.length() + 1;
		if (newLength > maxSize) {
			int remove = newLength - maxSize;
			delegate.delete(0, remove);
		}
		delegate.append(c);
		return this;
	}

}
