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
package tribefire.extension.xml.schemed.marshaller.xml.processor.commons;

import javax.xml.namespace.QName;

import com.braintribe.cc.lcd.HashSupportWrapperCodec;
import com.braintribe.common.lcd.equalscheck.IgnoreCaseEqualsCheck;


public class QNameWrapperCodec extends HashSupportWrapperCodec<QName> {
	private static final IgnoreCaseEqualsCheck check = new IgnoreCaseEqualsCheck();
	
	
	public QNameWrapperCodec() {
		super(true);
	}

	@Override
	protected int entityHashCode(QName e) {
		int result = 23;
		
		result = 31 * result + e.getLocalPart().hashCode();
		if (e.getPrefix() != null) {
			result = 31 * result + e.getPrefix().hashCode();
		}
		if (e.getNamespaceURI() != null) {
			result = 31 * result + e.getNamespaceURI().hashCode();
		}
		
		return result;
	}

	@Override
	protected boolean entityEquals(QName e1, QName e2) {
		if (!check.equals( e1.getLocalPart(), e2.getLocalPart()))
			return false;
		if (!check.equals( e1.getPrefix(), e2.getPrefix()))
			return false;
		if (!check.equals( e1.getNamespaceURI(), e2.getNamespaceURI()))
			return false;			
		return true;
	}

}
