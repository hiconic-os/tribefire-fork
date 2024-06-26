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
package tribefire.extension.js.model.asset.natures;

import com.braintribe.model.asset.PlatformAsset;
import com.braintribe.model.asset.natures.PlatformAssetNature;
import com.braintribe.model.asset.natures.SupportsNonAssetDeps;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * This is a little tricky - not every js library artifact needs to be an asset, so let's explain what it is and when it should be an asset.
 * <p>
 * Js library artifact is an artifact which contains a <code>js.zip</code> part.
 * <p>
 * From this perspective, we can package any 3rd party JavaScript library as our js library artifact, as well as every model is also such an artifact.
 * <p>
 * When doing a setup, for every js library artifact, the content of it's <code>zip</code> is extracted into a folder called
 * <code>${groupId}.${artifactId}-${version}~</code> inside a target folder (e.g. <code>tribefire-services/context/js-libraries</code>).
 * <p>
 * Now, what is a JsLibrary asset? Since the setup process is driven by {@link PlatformAsset} assets, i.e. it resolves all the assets starting from a
 * given terminal artifact, and then processes each asset according to it's {@link PlatformAssetNature nature}, we need an asset that marks a js
 * library artifact. Thus a setup process knows that artifact and all it's js dependencies need to have their <code>js.zip</code> parts processed.
 */
public interface JsLibrary extends PlatformAssetNature, SupportsNonAssetDeps {

	EntityType<JsLibrary> T = EntityTypes.T(JsLibrary.class);

}
