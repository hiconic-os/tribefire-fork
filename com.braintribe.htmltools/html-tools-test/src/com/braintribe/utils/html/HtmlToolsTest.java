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
package com.braintribe.utils.html;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import com.braintribe.utils.xml.XmlTools;

public class HtmlToolsTest {


	@Test
	public void testGuessHtml() throws Exception {
		
		assertThat(HtmlTools.guessHTML("<html><body>Hello<br>world</body>")).isTrue();
		assertThat(HtmlTools.guessHTML("Hello, world")).isFalse();
		
	}
	
	@Test
	public void testNormalizeHtml() throws Exception {
		
		String html = "<html><body>Hello<br>world</body>";
		String xhtml = HtmlTools.normalizeHTML(html);
		
		try {
			XmlTools.parseXML(html);
			fail("Expected an exception here.");
		} catch(Exception e) {
			//expected
		}
		XmlTools.parseXML(xhtml);
		
	}
	
	@Test
	public void testNormalizeHtmlJTidy() throws Exception {
		
		String html = "<html><body>Hello<br>world</body>";
		String xhtml = HtmlTools.normalizeHTMLWithJTidy(html);
		
		try {
			XmlTools.parseXML(html);
			fail("Expected an exception here.");
		} catch(Exception e) {
			//expected
		}
		XmlTools.parseXML(xhtml);
		
	}
	
	@Test
	public void testNormalizeHtmlTagSoup() throws Exception {
		
		String html = "<html><body>Hello<br>world</body>";
		String xhtml = HtmlTools.normalizeHTMLWithTagSoup(html);
		
		try {
			XmlTools.parseXML(html);
			fail("Expected an exception here.");
		} catch(Exception e) {
			//expected
		}
		XmlTools.parseXML(xhtml);
		
	}
	
	@Test
	public void testEscapeHtml() throws Exception {
		
		assertThat(HtmlTools.escapeHtml("hello, world")).isEqualTo("hello, world");
		assertThat(HtmlTools.escapeHtml("hello\nworld")).isEqualTo("hello<br />world");
		assertThat(HtmlTools.escapeHtml("hello&world")).isEqualTo("hello&amp;world");
		assertThat(HtmlTools.escapeHtml("hello\"world")).isEqualTo("hello&quot;world");
	}

	@Test
	public void testEscapeXml() throws Exception {
		
		assertThat(HtmlTools.escapeXml("hello, world")).isEqualTo("hello, world");
		assertThat(HtmlTools.escapeXml("hello\nworld")).isEqualTo("hello\nworld");
		assertThat(HtmlTools.escapeXml("hello&world")).isEqualTo("hello&amp;world");
		assertThat(HtmlTools.escapeXml("hello\"world")).isEqualTo("hello&quot;world");
	}

	@Test
	public void testUnescapeHtml() throws Exception {
		
		assertThat(HtmlTools.unescapeHtml("hello, world")).isEqualTo("hello, world");
		assertThat(HtmlTools.unescapeHtml("hello&#x0020;world")).isEqualTo("hello world");
		assertThat(HtmlTools.unescapeHtml("hello&#0032;world")).isEqualTo("hello world");
		assertThat(HtmlTools.unescapeHtml("hello&amp;world")).isEqualTo("hello&world");
		assertThat(HtmlTools.unescapeHtml("hello&quot;world")).isEqualTo("hello\"world");
	}
}
