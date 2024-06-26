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
package com.braintribe.model.processing.library.service.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.braintribe.common.lcd.Numbers;
import com.braintribe.logging.Logger;
import com.braintribe.model.library.deployment.service.WkHtmlToPdf;
import com.braintribe.utils.IOTools;
import com.braintribe.utils.StringTools;
import com.braintribe.utils.html.HtmlTools;
import com.braintribe.utils.system.exec.CommandExecution;
import com.braintribe.utils.system.exec.RunCommandContext;
import com.braintribe.utils.system.exec.RunCommandRequest;

public class PdfTools {

	private final static Logger logger = Logger.getLogger(PdfTools.class);

	private CommandExecution commandExecution;
	private WkHtmlToPdf wkhtmltopdf;

	public byte[] createPdfFromHtml(String html, boolean cleanHtml) throws Exception {
		if (this.commandExecution != null && this.wkhtmltopdf != null && !StringTools.isEmpty(wkhtmltopdf.getPath())) {
			return createPdfFromHtmlWk(html);
		} else {
			return createPdfFromHtmlFs(html, cleanHtml);
		}
	}

	private static byte[] createPdfFromHtmlFs(String html, boolean cleanHtml) throws Exception {

		if (cleanHtml) {
			String normalizedHtml = HtmlTools.normalizeHTML(html);
			final String originalHtml = html;
			logger.trace(() -> "Changed original HTML \n========\n" + originalHtml + "\n========\nto\n========\n" + normalizedHtml + "\n========\n");
			html = normalizedHtml;
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(html);
			renderer.layout();
			renderer.createPDF(os);
			renderer.finishPDF();

		} catch (Exception e) {
			throw new Exception("Could not convert the HTML file to PDF.", e);
		}

		return os.toByteArray();
	}

	private byte[] createPdfFromHtmlWk(String html) throws Exception {

		File tempFile = File.createTempFile("convert", ".html");
		File pdfFile = File.createTempFile("convert", ".pdf");
		pdfFile.delete();
		try {
			IOTools.spit(tempFile, html, "UTF-8", false);

			File wkHtmlToPdfFile = new File(wkhtmltopdf.getPath());
			List<String> commandList = new ArrayList<>();
			commandList.add(wkHtmlToPdfFile.getAbsolutePath());
			commandList.add("-q");
			Integer dpi = wkhtmltopdf.getDpi();
			if (dpi != null && dpi.intValue() > 0) {
				commandList.add("-d");
				commandList.add("" + dpi);
			}
			Integer zoom = wkhtmltopdf.getZoom();
			if (zoom != null) {
				commandList.add("--zoom");
				commandList.add("" + zoom);
			}
			commandList.add("--enable-local-file-access");
			commandList.add(tempFile.getAbsolutePath());
			commandList.add(pdfFile.getAbsolutePath());
			String[] commandParts = commandList.toArray(new String[commandList.size()]);
			logger.debug(() -> "WkHtmlToPdf command: " + commandList);

			RunCommandRequest req = new RunCommandRequest(commandParts, Numbers.MILLISECONDS_PER_MINUTE * 10);
			RunCommandContext ctx = this.commandExecution.runCommand(req);
			if (ctx.getErrorCode() != 0) {
				throw new Exception("Got error code " + ctx.getErrorCode() + " when converting an HTML page to PDF: " + ctx.toString());
			}
			if (!pdfFile.exists()) {
				throw new Exception("The target PDF file does not exist.");
			}
			try (InputStream in = new BufferedInputStream(new FileInputStream(pdfFile))) {
				byte[] pdf = IOTools.slurpBytes(in, false);
				return pdf;
			}

		} catch (Exception e) {
			throw new Exception("Could not convert the HTML file to PDF.", e);
		} finally {
			if (tempFile != null) {
				try {
					tempFile.delete();
				} catch (Exception e) {
					logger.error("Could not delete temporary file: " + tempFile.getAbsolutePath(), e);
				}
			}
			try {
				pdfFile.delete();
			} catch (Exception e) {
				logger.error("Could not delete temporary file: " + pdfFile.getAbsolutePath(), e);
			}
		}
	}

	public void setCommandExecution(CommandExecution commandExecution) {
		this.commandExecution = commandExecution;
	}
	public void setWkhtmltopdf(WkHtmlToPdf wkhtmltopdf) {
		this.wkhtmltopdf = wkhtmltopdf;
	}
}
