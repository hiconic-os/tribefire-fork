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
package com.braintribe.console;

import java.io.IOException;
import java.io.UncheckedIOException;

import com.braintribe.console.output.ConsoleOutput;
import com.braintribe.console.output.ConsoleOutputContainer;
import com.braintribe.console.output.ConsoleText;

public abstract class AbstractAnsiConsole implements Console, ConsoleOutputs {
	private boolean ansiConsole;
	private boolean stylesPrepared = false;
	private Object outputMonitor = new Object();
	private boolean resetStyles;

	public AbstractAnsiConsole(boolean ansiConsole, boolean resetStyles) {
		this.ansiConsole = ansiConsole;
		this.resetStyles = resetStyles;
	}

	@Override
	public Console print(ConsoleOutput output) {
		return out(output, false);
	}

	@Override
	public Console print(String text) {
		return out(text, false);
	}

	@Override
	public Console println(ConsoleOutput output) {
		return out(output, true);
	}

	@Override
	public Console println(String text) {
		return out(text, true);
	}

	private Console out(ConsoleOutput output, boolean linebreak) {
		ensureStylesPrepared();

		StringBuilder builder = new StringBuilder();

		try {
			out(builder, output, false, false, ConsoleStyles.FG_DEFAULT, ConsoleStyles.BG_DEFAULT, null);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		if (linebreak)
			builder.append('\n');

		synchronized (outputMonitor) {
			out(builder, false);
			return this;
		}
	}

	private void ensureStylesPrepared() {
		if (!resetStyles)
			return;

		if (!stylesPrepared) {
			synchronized (outputMonitor) {
				printStyle(ConsoleStyles.FG_DEFAULT);
				printStyle(ConsoleStyles.BG_DEFAULT);
				printStyle(22);
			}
			stylesPrepared = true;
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					printStyle(0);
				}
			});
		}
	}

	private void printStyle(int style) {
		if (ansiConsole) {
			out("\033[" + Integer.toString(style) + "m", false);
		}
	}

	private void printStyle(StringBuilder builder, int style) {
		if (ansiConsole) {
			builder.append("\033[");
			builder.append(Integer.toString(style));
			builder.append('m');
		}
	}

	private static class Replacing {
		int lines;
	}

	private void out(StringBuilder builder, ConsoleOutput output, boolean underline, boolean bright, int color, int bgColor, Replacing replacing)
			throws IOException {
		switch (output.kind()) {
			case container:
				ConsoleOutputContainer container = (ConsoleOutputContainer) output;

				int size = container.size();

				int resetStyle = 0;

				int style = container.getStyle();

				if (style != 0) {
					switch (style) {
						case ST_BRIGHT:
							if (!bright) {
								bright = true;
								resetStyle = 22;
							}
							break;
						case ST_UNDERLINE:
							if (!underline) {
								underline = true;
								resetStyle = 24;
							}
							break;
						case FG_BLACK:
						case FG_RED:
						case FG_GREEN:
						case FG_YELLOW:
						case FG_BLUE:
						case FG_MAGENTA:
						case FG_CYAN:
						case FG_WHITE | FG_DEFAULT:
						case FG_BRIGHT_BLACK:
						case FG_BRIGHT_RED:
						case FG_BRIGHT_GREEN:
						case FG_BRIGHT_YELLOW:
						case FG_BRIGHT_BLUE:
						case FG_BRIGHT_MAGENTA:
						case FG_BRIGHT_CYAN:
						case FG_BRIGHT_WHITE:
							if (style != color) {
								resetStyle = color;
								color = style;
							}
							break;
						case BG_BLACK:
						case BG_RED:
						case BG_GREEN:
						case BG_YELLOW:
						case BG_BLUE:
						case BG_MAGENTA:
						case BG_CYAN:
						case BG_WHITE:
						case BG_DEFAULT:
						case BG_BRIGHT_BLACK:
						case BG_BRIGHT_RED:
						case BG_BRIGHT_GREEN:
						case BG_BRIGHT_YELLOW:
						case BG_BRIGHT_BLUE:
						case BG_BRIGHT_MAGENTA:
						case BG_BRIGHT_CYAN:
						case BG_BRIGHT_WHITE:
							if (style != bgColor) {
								resetStyle = bgColor;
								bgColor = style;
							}
							break;

					}

					printStyle(builder, style);
				}

				boolean startReplacing = container.resetPosition() && replacing == null;

				if (startReplacing) {
					replacing = new Replacing();
					storePosition(builder);
				}

				try {
					for (int i = 0; i < size; i++) {
						out(builder, container.get(i), underline, bright, color, bgColor, replacing);
					}
				} finally {
					if (resetStyle != 0)
						printStyle(builder, resetStyle);

					if (startReplacing)
						resetPosition(builder, replacing);
				}

				break;
			case text:
				ConsoleText text = (ConsoleText) output;
				if (replacing != null) {
					replacing.lines += countChar(text.getText().toString(), '\n');
				}
				text.append(builder);
				break;
			default:
				break;
		}
	}

	private void storePosition(StringBuilder builder) {
		if (ansiConsole) {
			// builder.append("\033[s");
		}
	}

	private void resetPosition(StringBuilder builder, Replacing replacing) {
		if (ansiConsole) {
			int lines = replacing.lines;

			if (lines > 0) {
				builder.append("\033[");
				builder.append(replacing.lines);
				builder.append("A");
			}
			// builder.append("\033[u");
		}
	}

	private static int countChar(String s, char c) {
		int index = 0;
		int count = 0;

		while ((index = s.indexOf(c, index)) != -1) {
			count++;
			index++;
		}

		return count;
	}

	private Console out(CharSequence text, boolean linebreak) {
		synchronized (outputMonitor) {
			_out(text, linebreak);
			return this;
		}
	}

	protected abstract void _out(CharSequence text, boolean linebreak);
}
