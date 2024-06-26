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
package tribefire.extension.demo.model.data;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Abstract type that can be sub-classed by entities having comments assigned.
 * <br />
 * In real-life a Comment probably would be a complex entity with additional
 * information like Date, Author, ...<br />
 * To demonstrate a UseCase of a simple type (String) collection we reduced the
 * complexity.
 */
@Abstract
public interface HasComments extends GenericEntity {

	
	final EntityType<HasComments> T = EntityTypes.T(HasComments.class);
	
	/*
	 * Constants for each property name.
	 */
	public static final String comments = "comments";

	/**
	 * The natural ordered list of (simplified) comments.
	 */
	void setComments(List<String> comments);

	List<String> getComments();

}
