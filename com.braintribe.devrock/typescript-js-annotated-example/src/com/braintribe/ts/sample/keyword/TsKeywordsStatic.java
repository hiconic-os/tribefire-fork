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
package com.braintribe.ts.sample.keyword;

import com.braintribe.ts.sample.keyword.with.TsKeywordPackageEntity_NotInModel;
import com.braintribe.ts.sample.keyword.with.TsKeywordPackageInterface;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;

/**
 * @author peter.gazdik
 */
@SuppressWarnings("unused")
public interface TsKeywordsStatic {

	// @formatter:off
	// Integers method are omitted
	@JsMethod static Integer arguments() {return null;}
	@JsMethod static Integer await() {return null;}
	@JsMethod static Integer debugger() {return null;}
	@JsMethod static Integer delete() {return null;}
	@JsMethod static Integer eval() {return null;}
	@JsMethod static Integer export() {return null;}
	@JsMethod static Integer function() {return null;}
	@JsMethod static Integer in() {return null;}
	@JsMethod static Integer let() {return null;}
	@JsMethod static Integer prototype() {return null;}
	@JsMethod static Integer typeof() {return null;}
	@JsMethod static Integer var() {return null;}
	@JsMethod static Integer with() {return null;}
	@JsMethod static Integer yield() {return null;}

	
	@JsMethod static void argumentsMethod(String arguments) {/*NOOP*/}
	@JsMethod static void awaitMethod(String await) {/*NOOP*/}
	@JsMethod static void debuggerMethod(String debugger) {/*NOOP*/}
	@JsMethod static void deleteMethod(String delete) {/*NOOP*/}
	@JsMethod static void evalMethod(String eval) {/*NOOP*/}
	@JsMethod static void exportMethod(String export) {/*NOOP*/}
	@JsMethod static void functionMethod(String function) {/*NOOP*/}
	@JsMethod static void inMethod(String in) {/*NOOP*/}
	@JsMethod static void letMethod(String let) {/*NOOP*/}
	@JsMethod static void prototypeMethod(String prototype) {/*NOOP*/}
	@JsMethod static void typeofMethod(String typeof) {/*NOOP*/}
	@JsMethod static void varMethod(String var) {/*NOOP*/}
	@JsMethod static void withMethod(String with) {/*NOOP*/}
	@JsMethod static void yieldMethod(String yield) {/*NOOP*/}
	
	@JsMethod static void arguments_Method(String arguments_) {/*NOOP*/}

	@JsProperty static TsKeywordPackageInterface STATIC_KEYWORD_PACKAGE = null;
	@JsProperty static TsKeywordPackageEntity_NotInModel STATIC_KEYWORD_PACKAGE_ENTITY = null;

	@JsMethod static void pckgKeywordFun(TsKeywordPackageInterface arg) {/*NOOP*/}
	@JsMethod static void pckgKeywordEntityFun(TsKeywordPackageEntity_NotInModel arg) {/*NOOP*/}
	// @formatter:on

}
