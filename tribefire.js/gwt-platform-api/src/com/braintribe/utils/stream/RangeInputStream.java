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
package com.braintribe.utils.stream;

import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream wrapper that limits the read input to the specified range.
 * 
 * @author Roman Kurmanowytsch
 */
public class RangeInputStream extends InputStream {
	
    private InputStream parent;
    

	private long remaining;

    /**
     * Created the InputStream wrapper, starting the input at <code>start</code> (0-based) and 
     * returns the content up until <code>end</code> is reached.
     * 
     * @param parent The source InputStream.
     * @param start The starting point.
     * @param end The end point. If less then 0, {@link Long#MAX_VALUE} will be used.
     * @throws IOException Thrown if the underlying InputStream throws an exception. 
     * 
     */
    public RangeInputStream(InputStream parent, long start, long end) throws IOException {
    	if (end < 0) {
    		end = Long.MAX_VALUE;
    	}
        if (end < start) {
            throw new IllegalArgumentException("end < start");
        }

        if (parent.skip(start) < start) {
            throw new IOException("Unable to skip leading bytes");
        }

        this.parent=parent;
        remaining = end - start;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
    	
    	if (remaining <= 0) {
    		return -1;
    	}
    	int available = (len > remaining) ? (int) remaining : len;
    	int actualRead = parent.read(b, off, available);
    	if (actualRead >= 0) {
    		remaining -= actualRead;
    	}
    	return actualRead;
    }
    
    @Override
    public int read() throws IOException {
        return --remaining >= 0 ? parent.read() : -1;
    }
    
    @Override
	public int available() throws IOException {
		int available = parent.available();
		if (available > remaining) {
			return (int) remaining;
		} else {
			return available;
		}
	}

	@Override
	public void close() throws IOException {
		super.close();
		parent.close();
	}

	@Override
	public boolean markSupported() {
		return false;
	}
}