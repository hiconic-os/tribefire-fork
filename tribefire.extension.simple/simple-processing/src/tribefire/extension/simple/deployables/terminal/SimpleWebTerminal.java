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
package tribefire.extension.simple.deployables.terminal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.braintribe.cfg.Configurable;

/**
 * A simple {@link HttpServlet} which processes {@code GET} requests. It generates a basic HTML page with information about the incoming request. The
 * content of the response page to some extent depends on the terminal configuration, see {@link #setPrintRequestHeaders(boolean)} and
 * {@link #setPrintRequestParameters(boolean)}..
 *
 * @author michael.lafite
 */
public class SimpleWebTerminal extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** See {@link #setPrintRequestHeaders(boolean)}. */
	private boolean printRequestHeaders = false;
	/** See {@link #setPrintRequestParameters(boolean)}. */
	private boolean printRequestParameters = false;

	/** Specifies whether or not to print out the request headers. */
	public void setPrintRequestHeaders(boolean printRequestHeaders) {
		this.printRequestHeaders = printRequestHeaders;
	}

	/** Specifies whether or not to print out the request parameters. */
	@Configurable
	public void setPrintRequestParameters(boolean printRequestParameters) {
		this.printRequestParameters = printRequestParameters;
	}

	/**
	 * Processes the <code>request</code> adding a simple web page to the <code>response</code>.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter writer = response.getWriter();

		writer.print("<h1>Simple Web Terminal!</h1>");
		writer.print("<p>");
		writer.print("<i>This is just a simple web terminal demo. Below you find information about the received HTTP request.</i>");
		writer.print("<h2>Request method: " + request.getMethod() + "</h2>");

		if (printRequestHeaders) {
			printHeaders(request, writer);
		}

		if (printRequestParameters) {
			printParams(request, writer);
		}
	}

	/**
	 * Prints information about the <code>request</code> parameters to the passed <code>writer</code>.
	 */
	private void printParams(HttpServletRequest request, PrintWriter writer) {
		writer.println("<h3>Parameters</h3>");
		Enumeration<String> paramNames = request.getParameterNames();
		writer.println("<table>");
		writer.println("<tr><th>Name</th><th>Value</th></tr>");
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			writer.print("<tr><td>");
			writer.print(paramName);
			writer.print("</td><td>");
			writer.print(request.getParameter(paramName));
			writer.println("</td></tr>");

		}
		writer.println("</table>");
	}

	/**
	 * Prints information about the <code>request</code> headers to the passed <code>writer</code>.
	 */
	private void printHeaders(HttpServletRequest request, PrintWriter writer) {
		writer.println("<h3>Headers</h3>");
		Enumeration<String> headerNames = request.getHeaderNames();
		writer.println("<table>");
		writer.println("<tr><th>Name</th><th>Value</th></tr>");
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			writer.print("<tr><td>");
			writer.print(headerName);
			writer.print("</td><td>");
			writer.print(request.getHeader(headerName));
			writer.println("</td></tr>");

		}
		writer.println("</table>");
	}
}
