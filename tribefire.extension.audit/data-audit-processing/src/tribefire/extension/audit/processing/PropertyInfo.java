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
package tribefire.extension.audit.processing;

import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.TypeCode;

public class PropertyInfo {
	private TrackMode trackMode;
	private Property property;
	private boolean changeValueOccured = false;
	private boolean incrementalValueChangeOccured = false;
	
	public PropertyInfo(TrackMode trackMode, Property property) {
		super();
		this.trackMode = trackMode;
		this.property = property;
	}

	public TrackMode getTrackMode() {
		return trackMode;
	}
	
	public Property getProperty() {
		return property;
	}
	
	public boolean isSetTypeProperty() {
		return property.getType().getTypeCode() == TypeCode.setType;
	}

	public boolean isListTypeProperty() {
		return property.getType().getTypeCode() == TypeCode.listType;
	}

	public boolean getIncrementalValueChangeOccured() {
		return incrementalValueChangeOccured;
	}

	public void incrementalValueChangeOccured() {
		this.incrementalValueChangeOccured = true;
	}

	public boolean getChangeValueOccured() {
		return changeValueOccured;
	}

	public void changeValueOccured() {
		this.changeValueOccured = true;
	}

	@Override
	public String toString() {
		return "PropertyInfo[name=" + property.getName() + ", trackMode=" + trackMode + ", changeValueOccured: "+changeValueOccured+", incrementalValueChangeOccured: "+incrementalValueChangeOccured+"]"; 
	}
}
