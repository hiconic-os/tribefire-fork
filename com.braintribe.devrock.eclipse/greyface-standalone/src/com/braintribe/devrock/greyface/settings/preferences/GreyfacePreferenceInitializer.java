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
package com.braintribe.devrock.greyface.settings.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import com.braintribe.codec.CodecException;
import com.braintribe.devrock.greyface.settings.codecs.GreyfacePreferencesCodec;
import com.braintribe.logging.Logger;
import com.braintribe.model.malaclypse.cfg.preferences.gf.GreyFacePreferences;
import com.braintribe.model.malaclypse.cfg.repository.RemoteRepository;

public class GreyfacePreferenceInitializer {
	
	private static Logger log = Logger.getLogger(GreyfacePreferenceInitializer.class);


	public static void initializeDefaultPreferences( IPreferenceStore store) {
	 	
			
		GreyFacePreferences gfPreferences = GreyFacePreferences.T.create();
		gfPreferences.setTempDirectory("${java.io.tmpdir}");
		
		gfPreferences.setExcludeExisting( true);
		gfPreferences.setExcludeOptionals(true);
		gfPreferences.setExcludeTest(true);
		gfPreferences.setOverwrite(false);
		
		gfPreferences.setRepair(false);		
		gfPreferences.setPurgePoms(true);
		gfPreferences.setApplyCompileScope(false);
		
		gfPreferences.setAsyncScanMode(true);		
		gfPreferences.setEnforceLicenses(true);
		
		gfPreferences.setFakeUpload(false);
		gfPreferences.setFakeUploadTarget("${GF_TARGET}");
		gfPreferences.setSimulateErrors(false);
		
		gfPreferences.setValidatePoms( false);
		
		
		gfPreferences.setLastSelectedTargetRepo( "third-party");
					
		List<RemoteRepository> settings = new ArrayList<RemoteRepository>(3);
					
		RemoteRepository mavenCentral = RemoteRepository.T.create();
		mavenCentral.setName( "Maven central");
		mavenCentral.setUrl( "https://repo1.maven.org/maven2");
		settings.add( mavenCentral);
		
		RemoteRepository mvnRepository = RemoteRepository.T.create();
		mvnRepository.setName( "Mvn repository");
		mvnRepository.setUrl( "http://central.maven.org/maven2");
		settings.add( mvnRepository);
				
		RemoteRepository sonaTypeGoogle = RemoteRepository.T.create();
		sonaTypeGoogle.setName( "Sonatype google");
		sonaTypeGoogle.setUrl( "https://oss.sonatype.org/content/groups/google");
		settings.add( sonaTypeGoogle);
		
		RemoteRepository sonaTypeGoogleCode = RemoteRepository.T.create();
		sonaTypeGoogleCode.setName( "Sonatype google code");
		sonaTypeGoogleCode.setUrl( "https://oss.sonatype.org/content/groups/googlecode");
		settings.add(sonaTypeGoogleCode);
		
		RemoteRepository jbossRepository = RemoteRepository.T.create();
		jbossRepository.setName( "JBoss repository");
		jbossRepository.setUrl( "https://repository.jboss.org/nexus/content/repositories/releases");
		settings.add( jbossRepository);
		
		
		RemoteRepository jcenterRepository = RemoteRepository.T.create();
		jcenterRepository.setName( "JCenter repository");
		jcenterRepository.setUrl( "https://jcenter.bintray.com");
		settings.add( jcenterRepository);
		
			
		gfPreferences.setSourceRepositories(settings);
			
		try {
			GreyfacePreferencesCodec codec = new GreyfacePreferencesCodec( store);
			codec.decode(gfPreferences);
		} catch (CodecException e) {
			log.error("cannt prime preferences", e);
		}
			

		
	}

}
