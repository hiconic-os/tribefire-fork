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
package com.braintribe.utils.xml.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * xpath utilities with support for namespaces
 * 
 * @author Pit
 * 
 */
public class XPathUtils {

	private static XPathFactory xpathFactory = XPathFactory.newInstance();
	private static XPath xpath = xpathFactory.newXPath();

	/**
	 * evaluates an xpath and returns the resulting NodeList
	 * 
	 * @param parent
	 *            the parent element to scan from
	 * @param expression
	 *            the xpath expression
	 * @param namespaces
	 *            a Map<prefix,schemalocation> for the NamespaceContext
	 * @return the result as NodeList
	 * @throws DomUtilsException
	 */
	public static NodeList evaluateXPathExpression(final Element parent, final String expression,
			final Map<String, String> namespaces) throws DomUtilsException {
		final XPathNamespaceContextContainer namespaceContext = new XPathNamespaceContextContainer(namespaces);
		xpath.setNamespaceContext(namespaceContext);

		try {
			final XPathExpression xpathExpression = xpath.compile(expression);
			final NodeList nodes = (NodeList) xpathExpression.evaluate(parent, XPathConstants.NODESET);
			return nodes;
		} catch (final XPathExpressionException e) {
			throw new DomUtilsException("cannot evaluate XPath expression '" + expression + "'", e);
		}
	}

	/**
	 * same as above, but a list of w3c.dom.Element is returned
	 * 
	 * @param parent
	 * @param expression
	 * @param namespaces
	 * @return
	 * @throws DomUtilsException
	 */
	public static List<Element> evaluateXPathElementExpression(final Element parent, final String expression,
			final Map<String, String> namespaces) throws DomUtilsException {
		final NodeList nodes = evaluateXPathExpression(parent, expression, namespaces);
		final List<Element> elements = new ArrayList<Element>();
		if (nodes != null) {
			for (int i = 0; i < nodes.getLength(); i++) {
				elements.add((Element) nodes.item(i));
			}
		}
		return elements;
	}

	/**
	 * same as aboive, but only ONE w3c.dom.Element is returned
	 * 
	 * @param parent
	 * @param expression
	 * @param namespaces
	 * @return
	 * @throws DomUtilsException
	 */
	public static Element evaluateSingleXPathElementExpression(final Element parent, final String expression,
			final Map<String, String> namespaces) throws DomUtilsException {
		final NodeList nodes = evaluateXPathExpression(parent, expression, namespaces);
		if (nodes == null) {
			throw new DomUtilsException("xpath expression '" + expression + "' yields no results");
		}
		if (nodes.getLength() != 1) {
			throw new DomUtilsException("xpath expression '" + expression + "' doesn't result in one result");
		}
		final Node node = nodes.item(0);
		if (node.getNodeType() != Node.ELEMENT_NODE) {
			throw new DomUtilsException("xpatch expression '" + expression + "' doesn't yield an w3c.dom.Element");
		}
		return (Element) node;
	}

}
