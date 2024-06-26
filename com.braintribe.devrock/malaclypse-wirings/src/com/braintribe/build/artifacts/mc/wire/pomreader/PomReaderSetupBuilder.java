// ============================================================================
// BRAINTRIBE TECHNOLOGY GMBH - www.braintribe.com
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2018 - All Rights Reserved
// It is strictly forbidden to copy, modify, distribute or use this code without written permission
// To this file the Braintribe License Agreement applies.
// ============================================================================

package com.braintribe.build.artifacts.mc.wire.pomreader;

import java.util.function.Consumer;

import com.braintribe.build.artifacts.mc.wire.pomreader.contract.PomReaderContract;
import com.braintribe.wire.api.context.WireContextBuilder;

public interface PomReaderSetupBuilder {
	PomReaderSetupBuilder configureWireContext(Consumer<WireContextBuilder<PomReaderContract>> contextConfigurer);
	PomReaderSetup done();
}
