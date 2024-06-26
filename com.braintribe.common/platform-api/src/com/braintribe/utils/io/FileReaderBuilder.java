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
package com.braintribe.utils.io;

import java.io.File;

/**
 * {@link ReaderBuilder} extension for reading a {@link File}.
 * <p>
 * For now this does not offer any extra functionality, but in the future maybe there will be some read options that only make sense when reading from
 * a file.
 *
 * @author peter.gazdik
 */
public interface FileReaderBuilder extends ReaderBuilder {
	// EMPTY FOR NOW
}
