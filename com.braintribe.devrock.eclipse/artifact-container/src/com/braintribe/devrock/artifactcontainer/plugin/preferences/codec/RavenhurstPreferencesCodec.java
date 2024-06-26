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
package com.braintribe.devrock.artifactcontainer.plugin.preferences.codec;

import org.eclipse.jface.preference.IPreferenceStore;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.devrock.artifactcontainer.plugin.preferences.ArtifactContainerPreferenceConstants;
import com.braintribe.model.malaclypse.cfg.preferences.ac.ravenhurst.RavenhurstPreferences;

public class RavenhurstPreferencesCodec implements Codec<IPreferenceStore, RavenhurstPreferences> {
	
	private IPreferenceStore store;

	public RavenhurstPreferencesCodec(IPreferenceStore store) {
		this.store = store;
	}

	@Override
	public IPreferenceStore decode(RavenhurstPreferences ravenhurstPreferences) throws CodecException {
		int prunePeriod = ravenhurstPreferences.getPrunePeriod();
 		store.setValue( ArtifactContainerPreferenceConstants.PC_RAVENHURST_LOCAL_PRUNING, prunePeriod); 	
		return store;
	}

	@Override
	public RavenhurstPreferences encode(IPreferenceStore store) throws CodecException {
		RavenhurstPreferences ravenhurstPreferences = RavenhurstPreferences.T.create();
		ravenhurstPreferences.setPrunePeriod( store.getInt( ArtifactContainerPreferenceConstants.PC_RAVENHURST_LOCAL_PRUNING));
		return ravenhurstPreferences;
	}

	@Override
	public Class<IPreferenceStore> getValueClass() {
		return IPreferenceStore.class;
	}

}
