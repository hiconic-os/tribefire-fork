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
package com.braintribe.tribefire.cartridge.library.load;

import java.io.File;

public class LicenseResource {
	public File originalFile;
	public File pdfFile;
	public String name;
	public String url;
	public String internalUrl;
	public boolean commercial;
	public String spdxId;
	public boolean isSpdxListed;

	public LicenseResource(File originalFile, File pdfFile, String name, String spdxId, boolean isSpdxListed, String url, String internalUrl,
			boolean commercial) {
		super();
		this.originalFile = originalFile;
		this.pdfFile = pdfFile;
		this.name = name;
		this.spdxId = spdxId;
		this.isSpdxListed = isSpdxListed;
		this.url = url;
		this.internalUrl = internalUrl;
		this.commercial = commercial;

		if (!originalFile.exists()) {
			throw new RuntimeException("Original: " + originalFile.getAbsolutePath() + " does not exist.");
		}
		if (!pdfFile.exists()) {
			throw new RuntimeException("PDF: " + pdfFile.getAbsolutePath() + " does not exist.");
		}
	}

}
