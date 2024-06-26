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
package com.braintribe.gm.config.yaml.api;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Function;

import com.braintribe.codec.marshaller.api.options.GmDeserializationContextBuilder;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.essential.NotFound;
import com.braintribe.model.generic.session.InputStreamProvider;
import com.braintribe.model.generic.value.Variable;

/**
 * Prepares a yaml configuration read with fluent methods. After the preparation is done one of the from methods can be called to trigger the actual reading and potential post processing.
 * @author Dirk Scheffler
 *
 * @param <T> The expected root type of the assembly return from any of the from methods.
 */
public interface ConfigurationReadBuilder<T> {

	/**
	 * Reads the configuration from the given file. If the file is not present the {@link Maybe} will be unsatisfied with a {@link NotFound} reason.
	 */
	Maybe<T> from(File file);

	/**
	 * Reads the configuration from the given url file.
	 */
	Maybe<T> from(URL file);

	/**
	 * Reads the configuration from the input stream that is taken from the given provider. The {@link InputStream} will be closed after reading.
	 */
	Maybe<T> from(InputStreamProvider streamProvider);

	/**
	 * Reads the configuration from the given input stream. The {@link InputStream} <b>won't</b> be closed after reading.
	 */
	Maybe<T> from(InputStream in);
	
	/**
	 * Reads the configuration from the given reader. The {@link Reader} <b>won't</b> be closed after reading.
	 */
	Maybe<T> from(Reader reader);

	/**
	 * Activates the placeholder parsing in the yaml unmarshalling and further more the ValueDescriptor resolving using the given resolver.
	 */
	ConfigurationReadBuilder<T> placeholders(Function<Variable, Object> resolver);

	/**
	 * Activates the placeholder parsing in the yaml unmarshalling. ValueDescriptors are not being resolved but returned within the final assembly.
	 */
	ConfigurationReadBuilder<T> placeholders();

	/**
	 * Deactivates the entity default initialization.
	 */
	ConfigurationReadBuilder<T> noDefaulting();

	/**
	 * Allows to further control the yaml marshaller's working with a configurer that can apply options on a {@link GmDeserializationContextBuilder}.
	 */
	ConfigurationReadBuilder<T> options(Consumer<GmDeserializationContextBuilder> configurer);

	ConfigurationReadBuilder<T> absentifyMissingProperties();

}
