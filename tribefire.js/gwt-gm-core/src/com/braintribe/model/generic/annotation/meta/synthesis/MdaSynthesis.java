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
package com.braintribe.model.generic.annotation.meta.synthesis;

import static com.braintribe.utils.lcd.CollectionTools2.asList;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;
import static com.braintribe.utils.lcd.CollectionTools2.nullSafe;
import static java.util.Collections.emptySet;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

import com.braintribe.model.generic.annotation.meta.api.MdaHandler;
import com.braintribe.model.generic.annotation.meta.api.MetaDataAnnotations;
import com.braintribe.model.generic.annotation.meta.api.synthesis.AnnotationDescriptor;
import com.braintribe.model.generic.annotation.meta.api.synthesis.MdaSynthesisContext;
import com.braintribe.model.generic.annotation.meta.api.synthesis.RepeatedAnnotationDescriptor;
import com.braintribe.model.generic.annotation.meta.api.synthesis.SingleAnnotationDescriptor;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.data.MetaData;

/**
 * @author peter.gazdik
 */
public class MdaSynthesis {

	/**
	 * @return annotation descriptors for all metaData that are supported as annotations, or an empty collection.
	 */
	public static Collection<AnnotationDescriptor> synthesizeMetaDataAnnotations(Collection<? extends MetaData> metaData) {
		BasicMdAnnoSyncContext context = null;

		for (MetaData metaDatum : nullSafe(metaData)) {
			if (metaDatum.getSelector() != null)
				continue;

			MdaHandler<?, MetaData> synthesizer = (MdaHandler<?, MetaData>) MetaDataAnnotations.registry().mdTypeToHandler()
					.get(metaDatum.entityType());
			if (synthesizer == null)
				continue;

			if (context == null)
				context = new BasicMdAnnoSyncContext();

			context.currentMetaDatum = metaDatum;

			synthesizer.buildAnnotation(context, metaDatum);
		}

		return context == null ? emptySet() : context.annotations();
	}

	private static class BasicMdAnnoSyncContext implements MdaSynthesisContext {

		public MetaData currentMetaDatum;

		private final Map<EntityType<? extends MetaData>, AnnotationDescriptor> annoMap = newMap();

		@Override
		public SingleAnnotationDescriptor newDescriptor(Class<? extends Annotation> annotationClass) {
			return newDescriptor(annotationClass, true);
		}

		@Override
		public SingleAnnotationDescriptor newDescriptor(Class<? extends Annotation> annotationClass, boolean setAsCurrentDescriptor) {
			SingleAnnotationDescriptor result = new SingleAnnotationDescriptor(annotationClass);
			result.setGlobalId(currentMetaDatum.getGlobalId());

			if (setAsCurrentDescriptor)
				setCurrentDescriptor(result);

			return result;
		}

		@Override
		public AnnotationDescriptor getCurrentDescriptor() {
			return annoMap.get(currentMetaDatum.entityType());
		}

		@Override
		public void setCurrentDescriptor(AnnotationDescriptor descriptor) {
			annoMap.put(currentMetaDatum.entityType(), descriptor);
		}

		@Override
		public void setCurrentDescriptorMulti(SingleAnnotationDescriptor descriptor, Class<? extends Annotation> repeatabeAnnoClass) {
			AnnotationDescriptor currentDescriptor = getCurrentDescriptor();
			if (currentDescriptor == null) {
				setCurrentDescriptor(descriptor);

			} else if (currentDescriptor instanceof RepeatedAnnotationDescriptor) {
				RepeatedAnnotationDescriptor repeatableDescriptor = (RepeatedAnnotationDescriptor) currentDescriptor;
				repeatableDescriptor.getNestedAnnotations().add(descriptor);

			} else {
				SingleAnnotationDescriptor singleDescriptor = (SingleAnnotationDescriptor) currentDescriptor;
				setCurrentDescriptor(new RepeatedAnnotationDescriptor(repeatabeAnnoClass, asList(singleDescriptor, descriptor)));
			}
		}

		public Collection<AnnotationDescriptor> annotations() {
			return new TreeSet<>(annoMap.values());
		}

	}

}
