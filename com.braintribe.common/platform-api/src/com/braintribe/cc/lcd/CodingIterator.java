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
package com.braintribe.cc.lcd;

import java.util.Iterator;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

/**
 * an iterator to work with the coding set<br/>
 * <br/>
 * if you need any concrete examples, have a look that the test cases in com.braintribe:PlatformApiTest#1.0, package com.braintribe.coding<br/>
 * <br/>
 *
 * @param <WE>
 *            - wrapper element
 * @param <E>
 *            - element
 *
 * @author pit
 */
public class CodingIterator<WE, E> implements Iterator<E> {

	private final Codec<E, WE> codec;
	private final Iterator<WE> delegate;

	public CodingIterator(final Iterator<WE> delegate, final Codec<E, WE> codec) {
		this.delegate = delegate;
		this.codec = codec;
	}

	@Override
	public boolean hasNext() {
		return this.delegate.hasNext();
	}

	@Override
	public E next() {
		try {
			return this.codec.decode(this.delegate.next());
		} catch (final CodecException e) {
			final String msg = "cannot retrieve next object as " + e;
			throw new IllegalStateException(msg, e);
		}
	}

	@Override
	public void remove() {
		this.delegate.remove();

	}

}
