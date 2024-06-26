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
package com.braintribe.transport.ftp.enums;

/**
 *
 */
public enum FileType {
    /***
     * A constant used to indicate the file(s) being transfered should
     * be treated as ASCII.  This is the default file type.  All constants
     * ending in <code>FILE_TYPE</code> are used to indicate file types.
     ***/
    ASCII(0),

    /***
     * A constant used to indicate the file(s) being transfered should
     * be treated as EBCDIC.  Note however that there are several different
     * EBCDIC formats.  All constants ending in <code>FILE_TYPE</code>
     * are used to indicate file types.
     ***/
    EBCDIC(1),

   
    /***
     * A constant used to indicate the file(s) being transfered should
     * be treated as a binary image, i.e., no translations should be
     * performed.  All constants ending in <code>FILE_TYPE</code> are used to
     * indicate file types.
     ***/
    BINARY(2),

    /***
     * A constant used to indicate the file(s) being transfered should
     * be treated as a local type.  All constants ending in
     * <code>FILE_TYPE</code> are used to indicate file types.
     ***/
    LOCAL(3);

    private int arg;
	
	FileType(int arg) {
		this.arg = arg;
	}
	
	public int getFileType() {
		return this.arg;
	}
}