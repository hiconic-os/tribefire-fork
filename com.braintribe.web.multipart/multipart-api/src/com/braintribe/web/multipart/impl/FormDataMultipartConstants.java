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
package com.braintribe.web.multipart.impl;

public interface FormDataMultipartConstants {
	public static final int BOUNDARY_TYPE_PART = -1;
	public static final int BOUNDARY_TYPE_TERMINAL = -2;

	public static final byte CR = 0x0D;
	public static final byte LF = 0x0A;
	public static final byte HYPHEN = 0x2D;
	public static final byte[] HTTP_LINEBREAK = new byte[] { CR, LF };
	public static final byte[] MULTIPART_HYPHENS = new byte[] { HYPHEN, HYPHEN };
//	public static final byte[] MULTIPART_OPENING = new byte[] { CR, LF, HYPHEN, HYPHEN };
}
