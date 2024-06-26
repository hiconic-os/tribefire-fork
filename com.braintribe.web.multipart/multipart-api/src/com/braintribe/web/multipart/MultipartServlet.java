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
package com.braintribe.web.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.braintribe.web.multipart.api.PartReader;
import com.braintribe.web.multipart.api.SequentialFormDataReader;
import com.braintribe.web.multipart.impl.ServletMultiparts;

public class MultipartServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 7830858376903147100L;

	public MultipartServlet() {
		// Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		SequentialFormDataReader multipartFormDataStreaming = ServletMultiparts.formDataReader(req).sequential();

		PartReader partStreaming = null;

		resp.setContentType("text/plain; charset=UTF-8");
		PrintWriter writer = resp.getWriter();

		while ((partStreaming = multipartFormDataStreaming.next()) != null) {
			writer.println("-----------------------------------");
			for (String headerName : partStreaming.getHeaderNames()) {
				String value = partStreaming.getHeader(headerName);
				writer.println(headerName + ": " + value);
			}

			writer.println();

			if (partStreaming.isFile()) {
				InputStream in = partStreaming.openStream();

				int res;
				while ((res = in.read()) != -1) {
					byte b = (byte) res;
					writer.write((char) b);
				}

				in.close();
			} else {
				writer.print(partStreaming.getContentAsString());
			}
			writer.println();
		}
	}

}
