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
package tribefire.extension.antivirus.initializer.wire.contract;

import com.braintribe.wire.api.annotation.Default;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;

public interface RuntimePropertiesContract extends PropertyLookupContract {

	DefaultAntivirusProvider ANTIVIRUS_DEFAULT_PROVIDER();

	//
	// ClamAV
	//
	@Default("clamav.clamav")
	String ANTIVIRUS_CLAMAV_URL();
	@Default("3310")
	int ANTIVIRUS_CLAMAV_PORT();
	@Default("20000")
	int ANTIVIRUS_CLAMAV_TIMEOUT();

	//
	// Cloudmersive
	//
	String ANTIVIRUS_CLOUD_MERSIVE_API_KEY();

	//
	// VirusTotal
	//
	String ANTIVIRUS_VIRUSTOTAL_API_KEY();
	@Default("2")
	int ANTIVIRUS_VIRUSTOTAL_POLL_INTERVAL();
	@Default("60")
	int ANTIVIRUS_VIRUSTOTAL_POLL_DURATION();

}
