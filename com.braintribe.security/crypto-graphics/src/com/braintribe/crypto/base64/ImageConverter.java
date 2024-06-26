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
package com.braintribe.crypto.base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.braintribe.crypto.utils.TextUtils;

public class ImageConverter {
		
	
	public static void main( String [] args){
		File input = new File( args[0]);
		
		try {
			byte[] bytes = new byte[(int) input.length()];
			RandomAccessFile ref = new RandomAccessFile( input, "r");
			ref.readFully( bytes);
			ref.close();
			String base64 = Base64.encodeBytes(bytes);
			 
			TextUtils.writeContentsToFile( base64, new File( args[0] + ".b64"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
