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
package com.braintribe.utils.lcd;

/**
 * This class provides utility methods related to reflection.<br>
 * WARNING: please be careful when adding methods to this class. Most reflection related features/methods are not supported in GWT!
 *
 * @author michael.lafite
 */
public class ReflectionTools {

	protected ReflectionTools() {
		// nothing to do
	}

	/**
	 * Gets the fully qualified name of the passed class and removes the package name. Note that this is not exactly the same way to retrieve the
	 * simple name as method {@link Class#getSimpleName()} uses (no support for inner classes, arrays, etc.), but it is GWT compatible.
	 */
	public static String getSimpleName(final Class<?> clazz) {
		return getSimpleName(clazz.getName());
	}

	public static String getSimpleName(String className) {
		return CommonTools.getClassNameFromFullyQualifiedClassName(className);
	}

	public static String ensureStableBeanPropertyName(final String name) {
		int i = 0;
		for (; i < name.length(); i++) {
			if (i >= 1 && Character.isLowerCase(name.charAt(i))) {
				break;
			}
		}

		return name.substring(0, i).toLowerCase() + name.substring(i);
	}

	public static String ensureValidJavaBeansName(String propertyName) {
		if (propertyName.length() > 1 && Character.isUpperCase(propertyName.charAt(1))) {
			return propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		}

		return propertyName;
	}

	/* static final class X { private String AA; private String aA; private String toDate;
	 *
	 * public String getAA() { return AA; } public void setAA(String aA) { AA = aA; } public String getaA() { return aA;} public void setaA(String aA)
	 * { this.aA = aA; } public String getToDate() { return toDate;} public void setToDate(String toDate) { this.toDate = toDate; }
	 *
	 * } */
	// MLA: if needed, please add to PlatformApiTest
	// public static void main(final String[] args) {
	//
	// for (final String arg : args) {
	// System.out.println(arg + " -> " + ensureStableBeanPropertyName(arg));
	// }
	// }

}
