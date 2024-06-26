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
package com.braintribe.processing.test.web.undertow;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UndertowServerTest {

	public static void main(String[] args) throws Exception {

		// @formatter:off
		UndertowServer undertowServer =
				UndertowServer.create("/ping-app")
						.addServlet("ping-servlet", new PingServlet(), "/ping")
						.start();
		// @formatter:on

		Thread.sleep(10000);

		undertowServer.stop();

		Thread.sleep(3000);

	}

	private static class PingServlet extends HttpServlet {

		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			resp.getWriter().println(this + " is up and accepting calls from " + req.getRequestURL().toString());
		}
	}

}
