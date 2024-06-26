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
package com.braintribe.codec;

/**
 */
public class CodecOperationNotSupportedException extends CodecException {

  private static final long serialVersionUID = 1075616980837753029L;

  public CodecOperationNotSupportedException() {
  }

  public CodecOperationNotSupportedException(String message) {
    super(message);
  }

  public CodecOperationNotSupportedException(Throwable cause) {
    super(cause);
  }

  public CodecOperationNotSupportedException(String message, Throwable cause) {
    super(message, cause);
  }

}
