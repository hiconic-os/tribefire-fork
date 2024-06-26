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
package com.braintribe.utils.system.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Base64;

import com.braintribe.logging.Logger;

public class SystemInformationLogWriter extends AbstractSystemInformationLog {

	private static Logger logger = Logger.getLogger(SystemInformationLogWriter.class);

	@Override
	protected void logInformation(String info) {
		try {
			byte[] infoBytes = info.getBytes("UTF-8");
			for (int i=0; i<infoBytes.length; ++i) {
				infoBytes[i] = infoBytes[i] ^= 0x42; 
			}
			String output = Base64.getEncoder().encodeToString(infoBytes);
			if (output != null) {
				logger.log(this.logLevel, "=======START");
				while (output.length() > 80) {
					logger.log(this.logLevel, output.substring(0, 80));
					output = output.substring(80);
				}
				if (output.length() > 0) {
					logger.log(this.logLevel, output);
				}
				logger.log(this.logLevel, "=======END");
			}
		} catch(Exception e) {
			logger.info("Could not compile system information", e);
		}
	}

	public static void main(String[] args) throws Exception {
		if ((args == null) || (args.length == 0)) {
			System.out.println("Please provide the file path.");
			System.exit(1);
		}
		File inputFile = new File(args[0]);
		System.out.println("Reading from file "+inputFile.getAbsolutePath());
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputFile));

			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() > 0) {
					sb.append(line);
				}
			}
			String input = sb.toString();
			byte[] infoBytes = Base64.getDecoder().decode(input);
			for (int i=0; i<infoBytes.length; ++i) {
				infoBytes[i] = infoBytes[i] ^= 0x42; 
			}
			String info = new String(infoBytes, "UTF-8");
			System.out.println(info);

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch(Exception e) {
					//ignore
				}
			}
		}
	}

}
