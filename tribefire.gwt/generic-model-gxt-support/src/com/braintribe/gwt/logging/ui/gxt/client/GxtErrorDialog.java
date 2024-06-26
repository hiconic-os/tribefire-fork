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
package com.braintribe.gwt.logging.ui.gxt.client;

import java.util.function.Supplier;

import com.braintribe.gwt.async.client.AsyncCallbacks;
import com.braintribe.gwt.gmview.action.client.IgnoreKeyConfigurationDialog;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.gwt.logging.client.ExceptionUtil;
import com.braintribe.gwt.logging.client.Formatter;
import com.braintribe.gwt.logging.client.LogEvent;
import com.braintribe.gwt.logging.client.LogEventBuffer;
import com.braintribe.gwt.logging.client.StackTraceRenderer;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Label;

/**
 * This class can be used to display error messages. It will mask the screen (modal) and display Exception chains and
 * log entries if available.
 * 
 * @author Dirk
 *
 */
public class GxtErrorDialog extends TabbedDialog implements IgnoreKeyConfigurationDialog {

	private final Formatter<String> formatter = ErrorDialog.getFormatter();
	private static boolean showDetailTab = true;
	private static boolean showPackagingInfoTab = true;
	private static Supplier<String> packagingInfoProvider;
	private ExtendedTextArea packagingInfoTextArea;
	private ExtendedTextArea errorInfoTextArea;
	
	protected GxtErrorDialog(String message, String details) {
		this(message, details, null);
	}
	
	protected GxtErrorDialog(final String message, final Throwable throwable) {
		this(message, null, throwable);
	}

	protected GxtErrorDialog(final String message, String details, final Throwable throwable) {
		setSize("400px", "300px");
		
		Label messagePanel = new Label(message);
		messagePanel.getElement().getStyle().setDisplay(Display.TABLE_CELL);
		messagePanel.addStyleName("errorDialogErrorMessageStyle");
		
		tabPanel.add(messagePanel, LocalizedText.INSTANCE.message());
		
		if (showDetailTab) {
			StringBuffer builder = new StringBuffer();
			builder.append(message);
			if (throwable != null) {
				builder.append("\n\n");
				builder.append(ExceptionUtil.format(throwable));
			} else if (details != null) {
				builder.append("\n\n");
				builder.append(details);
			}
			
			LogEventBuffer logEventBuffer = ErrorDialog.getLogEventBuffer();
			
			if (logEventBuffer != null) {
				builder.append("\n\nLog:\n");
				for (LogEvent event: logEventBuffer.getEvents()) {
					builder.append('\n');
					builder.append(formatter.format(event));
				}
			}
			
			errorInfoTextArea = new ExtendedTextArea();
			errorInfoTextArea.setValue(builder.toString());
			errorInfoTextArea.setReadOnly(true);
			errorInfoTextArea.setBorders(false);
			errorInfoTextArea.getInputEl().getStyle().setBorderStyle(BorderStyle.NONE);
			errorInfoTextArea.getInputEl().setAttribute("wrap", "off");
			//errorInfoTextArea.getInputEl().setElementAttribute("wrap", "off");
			tabPanel.add(errorInfoTextArea, LocalizedText.INSTANCE.details());
			
			if (throwable != null) {
				StackTraceRenderer.renderExceptionTranslated(throwable, AsyncCallbacks.of(result -> {
					StringBuffer buffer = new StringBuffer();
					buffer.append(message);

					buffer.append("\n\n");
					buffer.append(result);

					LogEventBuffer eventBuffer = ErrorDialog.getLogEventBuffer();

					if (eventBuffer != null) {
						buffer.append("\n\nLog:\n");
						for (LogEvent event : eventBuffer.getEvents()) {
							buffer.append('\n');
							buffer.append(formatter.format(event));
						}
					}

					errorInfoTextArea.setValue(buffer.toString());
					errorInfoTextArea.getInputEl().getStyle().setBorderStyle(BorderStyle.NONE);
				}, e -> {
					StringBuffer buffer = new StringBuffer();
					buffer.append(message);

					buffer.append("\n\n");
					buffer.append(ExceptionUtil.format(throwable));

					LogEventBuffer eventBuffer = ErrorDialog.getLogEventBuffer();

					if (eventBuffer != null) {
						buffer.append("\n\nLog:\n");
						for (LogEvent event : eventBuffer.getEvents()) {
							buffer.append('\n');
							buffer.append(formatter.format(event));
						}
					}

					errorInfoTextArea.setValue(buffer.toString());
				}));
			}
		}
		
		if (showPackagingInfoTab) {
			packagingInfoTextArea = new ExtendedTextArea();
			packagingInfoTextArea.setReadOnly(true);
			packagingInfoTextArea.setBorders(false);
			packagingInfoTextArea.getInputEl().getStyle().setBorderStyle(BorderStyle.NONE);
			tabPanel.add(packagingInfoTextArea, LocalizedText.INSTANCE.packagingInfo());
		}
	}
	
	/**
	 * This will start a pseudo modal dialog that show the error
	 * information
	 */
	public static void showMessage(String message) {
		show(message, (Throwable) null);
	}

	/**
	 * This will start a pseudo modal dialog that show the error
	 * information
	 */
	public static void show(String message, Throwable t, boolean details) {
		GxtErrorDialog dialog = new GxtErrorDialog(message, t);
		handleDetailsPackageInfoAndShowDialog(details, dialog);
	}

	private static void handleDetailsPackageInfoAndShowDialog(boolean details, final GxtErrorDialog dialog) {
		if (showPackagingInfoTab) {
			String packagingInfo = "";
			if (packagingInfoProvider != null) {
				try {
					packagingInfo = packagingInfoProvider.get();
				} catch (RuntimeException ex) {
					//NOP
				}
			}
			if (packagingInfo != null && !packagingInfo.isEmpty()) {
				dialog.packagingInfoTextArea.setValue(packagingInfo);
				dialog.packagingInfoTextArea.getInputEl().getStyle().setBorderStyle(BorderStyle.NONE);
			} else {
				dialog.tabPanel.remove(dialog.packagingInfoTextArea);
				showPackagingInfoTab = false;
			}
		}
		
		if (details)
			dialog.tabPanel.setActiveWidget(dialog.errorInfoTextArea);
		dialog.show();
	}
	
	public static void show(String message, String details) {
		GxtErrorDialog dialog = new GxtErrorDialog(message, details);
		handleDetailsPackageInfoAndShowDialog(true, dialog);
	}
	
	public static void show(String message, Throwable t) {
		show(message, t, false);
	}
	
	/**
	 * Configures whether to show the detail tab.
	 * Defaults to true.
	 */
	@Configurable
	public static void setShowDetailTab(boolean showDetail) {
		showDetailTab = showDetail;
	}
	
	/**
	 * Configures the {@link Supplier} which providers Packaging info.
	 * If this is set and has actual data, then the packaging info tab will be displayed.
	 */
	@Configurable
	public static void setPackagingInfoProvider(Supplier<String> provider) {
		packagingInfoProvider = provider;
	}
}
