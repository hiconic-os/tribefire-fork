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
package com.braintribe.model.processing.resource.cache;

import java.io.InputStream;
import java.io.OutputStream;

import com.braintribe.model.processing.resource.streaming.ResourceStreamException;

/**
 * Interface of an representation cache entry. Provides streaming access to the cached content of a RepresentationSource
 * 
 * @author gunther.schenk
 *
 */
public interface ResourceCacheEntry {

	/**
	 * Opens an {@link InputStream} to the cached content.
	 */
	InputStream openCacheInputStream() throws ResourceStreamException;

	/**
	 * Opens an {@link OutputStream} to the cached content.
	 */
	OutputStream openCacheOutputStream() throws ResourceStreamException;

	/**
	 * Deletes all resources related to this cacheEntry.
	 */
	void delete() throws ResourceStreamException;

	/**
	 * Tells whether the representation is already cached.
	 */
	boolean isCached() throws ResourceStreamException;

	/**
	 * Tells the last modification time of this entry.
	 */
	long getLastModification() throws ResourceStreamException;

	/**
	 * @return the size of the cached entry in bytes.
	 */
	long getSize() throws ResourceStreamException;

	/**
	 * @return the md5 of the cached entry.
	 */
	String getMd5() throws ResourceStreamException;

	/**
	 * @return the cache domain.
	 */
	String getDomain();

}