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

import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.devrock.artifactcontainer.plugin.preferences.ArtifactContainerPreferenceConstants;
import com.braintribe.model.malaclypse.cfg.AntTarget;
import com.braintribe.model.malaclypse.cfg.preferences.ac.AntRunnerPreferences;

/**
 * a little codec to translate between GE preferences and {@link IPreferenceStore} values
 * 
 * @author pit
 *
 */
public class AntPreferencesCodec implements Codec<IPreferenceStore, AntRunnerPreferences> {
	
	private IPreferenceStore store;

	public AntPreferencesCodec(IPreferenceStore store) {
		this.store = store;
	}

	@Override
	public IPreferenceStore decode(AntRunnerPreferences antPreferences) throws CodecException {
		store.setValue( ArtifactContainerPreferenceConstants.PC_ANT_HOME, antPreferences.getAntHome());		
		store.setValue( ArtifactContainerPreferenceConstants.PC_ANT_RUNNER, antPreferences.getAntRunner());
		List<AntTarget> antTargets = antPreferences.getTargets();
		if (antTargets != null && antTargets.size()> 0) {
			StringBuilder builder = new StringBuilder();
			for (AntTarget antTarget : antTargets) {
				if (builder.length() > 0) {
					builder.append(";");
				}
				String name = antTarget.getName();
				String target = antTarget.getTarget();
				boolean transitive = antTarget.getTransitiveNature();
				builder.append( name + "," + target + "," + transitive);				
			}
			store.setValue( ArtifactContainerPreferenceConstants.PC_ANT_TARGETS, builder.toString());
		}
		return store;
	}

	@Override
	public AntRunnerPreferences encode(IPreferenceStore store) throws CodecException {
		// ant
		AntRunnerPreferences antPreferences = AntRunnerPreferences.T.create();
		antPreferences.setAntHome( store.getString( ArtifactContainerPreferenceConstants.PC_ANT_HOME));
		antPreferences.setAntRunner( store.getString( ArtifactContainerPreferenceConstants.PC_ANT_RUNNER));
		String targetLine = store.getString( ArtifactContainerPreferenceConstants.PC_ANT_TARGETS);
		String [] targets = targetLine.split(";");		
		if (targets.length > 0) {
			List<AntTarget> antTargets = antPreferences.getTargets();
			for (String target : targets) {
				String [] singleTargetValues = target.split(",");
				// must have 3 values in there, otherwise it's broken
				if (singleTargetValues.length < 3)
					continue;
				String targetName = singleTargetValues[0];
				String targetTask = singleTargetValues[1];
				String targetTransitiveNature= singleTargetValues[2];
				
				AntTarget antTarget = AntTarget.T.create();
				antTarget.setName(targetName);
				antTarget.setTarget(targetTask);
				antTarget.setTransitiveNature( Boolean.parseBoolean(targetTransitiveNature));				
				antTargets.add(antTarget);
			}			
		}		
		return antPreferences;
	}

	@Override
	public Class<IPreferenceStore> getValueClass() {	
		return IPreferenceStore.class;
	}

	
}
