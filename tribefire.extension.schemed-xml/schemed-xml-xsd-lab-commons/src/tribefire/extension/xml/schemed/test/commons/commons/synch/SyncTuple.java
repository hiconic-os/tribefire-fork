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
package tribefire.extension.xml.schemed.test.commons.commons.synch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SyncTuple {	
	private String name;
	private String source;
	private String target;
	private List<String> namesToCopy = new ArrayList<String>();
	
	public SyncTuple(String name, String source, String target, String ... files) {
		this.name = name;
		this.source = source;
		this.target = target;
		if (files != null) {
			this.namesToCopy.addAll( Arrays.asList(files));
		}		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public List<String> getNamesToCopy() {
		return namesToCopy;
	}
	public void setNamesToCopy(List<String> namesToCopy) {
		this.namesToCopy = namesToCopy;
	}		
	
	public String toString() {
		return name + ":" + source + " -> " + target;
	}
	
	// format <name>,<source>,<target>,<file>[,<file>,..]
	public static SyncTuple fromString( String string) {
		String [] split = string.trim().split( ",");
		SyncTuple tuple = new SyncTuple(split[0], split[1], split[2]);
		if (split.length > 3) {
			for (int i = 3; i < split.length; i++) {
				tuple.getNamesToCopy().add( split[i]);
			}
		}
		return tuple;
	}
}
