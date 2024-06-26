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
package com.braintribe.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyDirectoryRecursivelyAndKeepSymbolLinkTest {
	public static void main(String[] args) {
		// File source = new File("C:\\dev\\test");
		// File target = new File("C:\\test");
		// FileTools.copyRecursivelyAndKeepSymbolLinks(source, target);

		// try {
		// Path p1 = Paths.get("C:\\dev\\test");
		// Path p2 = Paths.get("..\\foo\\bar");
		// Path p3 = Paths.get("C:\\test2");
		//
		// System.out.println(p1.relativize(p3));
		//
		// Path resolve = p1.resolve(p2);
		// System.out.println(resolve);
		// System.out.println(resolve.normalize());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		try {
			Path p1 = Paths.get("C:\\copy-playground\\A");
			Path p2 = Paths.get("C:\\copy-playground\\Z\\z-alt");

			FileTools.copyRecursivelyAndKeepSymbolLinks(p1.toFile(), p2.toFile());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void prepareStructure(Path basePath) throws IOException {
		Path A = basePath.resolve("A");
		Path X = basePath.resolve("X");

		Path Y = X.resolve("Z");

		Path B = A.resolve("B");
		Path C = A.resolve("C");
		Path D = A.resolve("D");

		Path E = B.resolve("E");
		Path text1 = B.resolve("text1.txt");

		Path F = C.resolve("F");
		Path G = C.resolve("G");
		Path H = C.resolve("H");

		Path text2 = H.resolve("text2.txt");

		Path I = D.resolve("I");

		Files.createDirectory(A);

	}
}
