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
package com.braintribe.servlet;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;

public class PublicResourcesServletTest {
	ResourceServlet servlet;
	Path rootPath = Paths.get("a","b","c");
	
	@Before
	public void init() {
		servlet  = new ResourceServlet();
		servlet.setPublicResourcesDirectory(rootPath);
	}

	@Test
	public void testResourcePathWithSlashes() {
		testResourcePath("/d", "a/b/c/d");
		testResourcePath("d", "a/b/c/d");
		testResourcePath("d/", "a/b/c/d/");
		testResourcePath("d//", "a/b/c/d/");
		
		testResourcePath("//d", "a/b/c/d");
		testResourcePath("///d", "a/b/c/d");
		testResourcePath("/d/", "a/b/c/d/");
		testResourcePath("//d//", "a/b/c/d/");
	}
	
	@Test
	public void testComplexPath() {
		testResourcePath("d/../e", "a/b/c/e");
		testResourcePath("d/../e/f", "a/b/c/e/f");
		testResourcePath("d/./e/f", "a/b/c/d/e/f");
		testResourcePath(".", "a/b/c");
		testResourcePath("a/..", "a/b/c");
	}
	
	@Test
	public void testNoPath() {
		assertExceptionThrownFor("");
		assertExceptionThrownFor("/");
		assertExceptionThrownFor("//");
		assertExceptionThrownFor("///////////////////");
	}
	
	@Test
	public void testSecurePath() {
		assertExceptionThrownForVariantsOf("..");
		assertExceptionThrownForVariantsOf("../hack");
		assertExceptionThrownForVariantsOf("../a/../");
		assertExceptionThrownForVariantsOf("../a/../");
		assertExceptionThrownForVariantsOf("../../../../../../../../../../../../../../../../../../../../../../../../../../../../../../../../../");
		
		assertExceptionThrownForVariantsOf("a/../a/../a/../../hack/");
		assertExceptionThrownForVariantsOf("d/e/f/../../../../../hack");
		
		// The following could be legal because even if the path leaves the public resource root folder it returns back to it
		// However it makes no sense to support this because that folder name is neither known by the typical user nor necessarily fixed 
		assertExceptionThrownForVariantsOf("../" + rootPath.getFileName());
		assertExceptionThrownForVariantsOf("../" + rootPath.getFileName() + "/d");
	}
	
	private void assertExceptionThrownForVariantsOf(String subPath) {
		assertExceptionThrownFor(subPath);
		
		assertExceptionThrownFor("./" + subPath);
		assertExceptionThrownFor("/" + subPath);
		assertExceptionThrownFor("//" + subPath);
		assertExceptionThrownFor(subPath + "/");
		assertExceptionThrownFor(subPath + "//");
		assertExceptionThrownFor("/" + subPath + "/");
	}
	private void assertExceptionThrownFor(String subPath) {
		Assertions.assertThatThrownBy(() -> testResourcePath(subPath, null)).isExactlyInstanceOf(IllegalArgumentException.class);
	}
	
	private void testResourcePath(String subPath, String expectedResult) {
		File resourceFile = servlet.getResourceFile(subPath);
		assertThat(resourceFile).hasSamePathAs(Paths.get(expectedResult).toFile());
	}
}
