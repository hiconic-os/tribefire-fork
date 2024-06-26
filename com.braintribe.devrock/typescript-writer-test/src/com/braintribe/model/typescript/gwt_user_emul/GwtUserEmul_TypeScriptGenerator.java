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
package com.braintribe.model.typescript.gwt_user_emul;

import static com.braintribe.model.typescript.TypeScriptWriterHelper.createCustomGmTypeFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.braintribe.exception.Exceptions;
import com.braintribe.model.typescript.TypeScriptWriterForClasses;
import com.braintribe.utils.FileTools;
import com.braintribe.utils.ReflectionTools;
import com.braintribe.utils.StringTools;

/**
 * Start with {@link GwtUserEmul_CodeMover}.
 * 
 * Then run this and you have the index.d.ts for gwt-user-emul classes.
 * 
 * @author peter.gazdik
 */
public class GwtUserEmul_TypeScriptGenerator {

	public static void main(String[] args) {
		new GwtUserEmul_TypeScriptGenerator().run();
	}

	private static final String CLASS_FILE_SUFFIX = ".class";
	private static final int CLASS_FILE_SUFFIX_LENGTH = CLASS_FILE_SUFFIX.length();

	private final Predicate<Class<?>> customGmTypeFilter = createCustomGmTypeFilter(getClass().getClassLoader());

	private final File classesFolder = new File("classes");
	private final Path classesFolderPath = classesFolder.toPath();
	private final File fakeFolder = new File("classes/fake");

	private final File outputFile = new File("jsinterop-base.d.ts");

	private void run() {
		FileTools.write(outputFile).string(writeTypeScript());

		System.out.print("TS declarations for gwt-user-emul classes created in:    " + outputFile.getPath());
	}

	private String writeTypeScript() {
		List<Class<?>> classes = getClasses();

		StringBuilder sb = new StringBuilder();

		TypeScriptWriterForClasses.write(classes, customGmTypeFilter, sb);

		String result = sb.toString();

		result = result.replace("fake.com", "com");
		result =  result.replace("fake.java", "java");
		
		return result;
	}

	private List<Class<?>> getClasses() {
		try {
			return Files.walk(fakeFolder.toPath()) //
					.filter(this::isClassFile) //
					.map(classesFolderPath::relativize) //
					.map(this::toClassName) //
					.map(ReflectionTools::getExistingClass) //
					.collect(Collectors.toList());

		} catch (IOException e) {
			throw Exceptions.unchecked(e);
		}
	}

	private boolean isClassFile(Path path) {
		return Files.isRegularFile(path) && path.toString().endsWith(CLASS_FILE_SUFFIX);
	}

	private String toClassName(Path classFilePath) {
		String s = classFilePath.toString();
		s = StringTools.removeLastNCharacters(s, CLASS_FILE_SUFFIX_LENGTH);
		s = s.replace(File.separatorChar, '.');

		return s;
	}

}
