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
package com.braintribe.devrock.greyface.settings.codecs;

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.codec.json.genericmodel.GenericModelJsonStringCodec;
import com.braintribe.devrock.greyface.settings.preferences.GreyfacePreferenceConstants;
import com.braintribe.model.malaclypse.cfg.preferences.gf.GreyFacePreferences;
import com.braintribe.model.malaclypse.cfg.repository.RemoteRepository;

public class GreyfacePreferencesCodec implements Codec<IPreferenceStore, GreyFacePreferences> {
	
	private IPreferenceStore store;
	GenericModelJsonStringCodec<List<RemoteRepository>> codec = new GenericModelJsonStringCodec<List<RemoteRepository>>(); 

	public GreyfacePreferencesCodec(IPreferenceStore store) {
		this.store = store;	
	}

	@Override
	public IPreferenceStore decode(GreyFacePreferences preferences) throws CodecException {
		
	 store.setValue( GreyfacePreferenceConstants.PC_TEMPORARY_DIRECTORY, preferences.getTempDirectory());
	 
	 store.setValue( GreyfacePreferenceConstants.PC_FAKE_UPLOAD, preferences.getFakeUpload());
	 store.setValue( GreyfacePreferenceConstants.PC_FAKE_UPLOAD_TARGET, preferences.getFakeUploadTarget());
	 store.setValue( GreyfacePreferenceConstants.PC_ASYNC_SCAN_MODE, preferences.getAsyncScanMode());
	 store.setValue( GreyfacePreferenceConstants.PC_SIMULATE_ERRORS, preferences.getSimulateErrors());

	 store.setValue( GreyfacePreferenceConstants.PC_SETTING_EXCLUDE_OPTIONAL, preferences.getExcludeOptionals());
	 store.setValue( GreyfacePreferenceConstants.PC_SETTING_EXCLUDE_TEST, preferences.getExcludeTest());
	 store.setValue( GreyfacePreferenceConstants.PC_SETTING_EXCLUDE_EXISTING, preferences.getExcludeExisting());
	 store.setValue( GreyfacePreferenceConstants.PC_SETTING_OVERWRITE, preferences.getOverwrite());
	 store.setValue( GreyfacePreferenceConstants.PC_SETTING_REPAIR, preferences.getRepair());
	 store.setValue( GreyfacePreferenceConstants.PC_SETTING_PURGE_POMS, preferences.getPurgePoms());
	 store.setValue( GreyfacePreferenceConstants.PC_SETTING_ACCEPT_COMPILE, preferences.getApplyCompileScope());
	 store.setValue( GreyfacePreferenceConstants.PC_SETTING_ENFORCE_LICENSES, preferences.getEnforceLicenses());
	 store.setValue( GreyfacePreferenceConstants.PC_VALIDATE_POMS, preferences.getValidatePoms());
					
	 List<RemoteRepository> sourceRepositories = preferences.getSourceRepositories();
	 String encodeSourceRepositories = codec.encode(sourceRepositories);
	 store.setValue(GreyfacePreferenceConstants.PC_REPO_SETTINGS, encodeSourceRepositories);
	 
	 store.setValue(GreyfacePreferenceConstants.PC_LAST_SELECTED_TARGET_REPO, preferences.getLastSelectedTargetRepo());
	 return store;
	}

	@Override
	public GreyFacePreferences encode(IPreferenceStore store) throws CodecException {
		GreyFacePreferences gfPreferences = GreyFacePreferences.T.create();
		gfPreferences.setTempDirectory( store.getString( GreyfacePreferenceConstants.PC_TEMPORARY_DIRECTORY));
		
		gfPreferences.setFakeUpload( store.getBoolean( GreyfacePreferenceConstants.PC_FAKE_UPLOAD));
		gfPreferences.setFakeUploadTarget( store.getString( GreyfacePreferenceConstants.PC_FAKE_UPLOAD_TARGET));
		gfPreferences.setAsyncScanMode( store.getBoolean( GreyfacePreferenceConstants.PC_ASYNC_SCAN_MODE));
		gfPreferences.setSimulateErrors( store.getBoolean( GreyfacePreferenceConstants.PC_SIMULATE_ERRORS));
		
		gfPreferences.setExcludeOptionals( store.getBoolean( GreyfacePreferenceConstants.PC_SETTING_EXCLUDE_OPTIONAL));
		gfPreferences.setExcludeTest( store.getBoolean( GreyfacePreferenceConstants.PC_SETTING_EXCLUDE_TEST));
		gfPreferences.setExcludeExisting( store.getBoolean( GreyfacePreferenceConstants.PC_SETTING_EXCLUDE_EXISTING));
		gfPreferences.setOverwrite( store.getBoolean( GreyfacePreferenceConstants.PC_SETTING_OVERWRITE));
		gfPreferences.setPurgePoms( store.getBoolean( GreyfacePreferenceConstants.PC_SETTING_PURGE_POMS));
		gfPreferences.setApplyCompileScope( store.getBoolean( GreyfacePreferenceConstants.PC_SETTING_ACCEPT_COMPILE));
			
		String encodedValue = store.getString( GreyfacePreferenceConstants.PC_REPO_SETTINGS);
		if (encodedValue != null && encodedValue.length() != 0) {
			List<RemoteRepository> decodedSourceRepositories = codec.decode( encodedValue);
			gfPreferences.setSourceRepositories( decodedSourceRepositories);			
		}				
		gfPreferences.setEnforceLicenses( store.getBoolean( GreyfacePreferenceConstants.PC_SETTING_ENFORCE_LICENSES));
		gfPreferences.setValidatePoms( store.getBoolean( GreyfacePreferenceConstants.PC_VALIDATE_POMS));
		
		gfPreferences.setLastSelectedTargetRepo( store.getString( GreyfacePreferenceConstants.PC_LAST_SELECTED_TARGET_REPO));
						
		return gfPreferences;
	}

	@Override
	public Class<IPreferenceStore> getValueClass() {		
		return IPreferenceStore.class;
	}
	
	

}
