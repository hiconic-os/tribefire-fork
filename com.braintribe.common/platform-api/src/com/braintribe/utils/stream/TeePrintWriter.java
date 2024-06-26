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
package com.braintribe.utils.stream;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Locale;

/**
 * This class is a wrapper around a provided PrintWriter. All actions are invoked with the delegate PrintWriter. The functionality of this wrapper is
 * that it additionally writes the content to a secondary ("tee") writer. Output to the secondary Writer can be stopped irrevocably by invocing
 * {@link #stopTee()}. This implementation will first try to write the content to the tee Writer and then to the main delegate PrintWriter. If the tee
 * Writer throws an exception, the data will not be written to the delegate PrintWriter. If the delegate PrintWriter throws an Exception, the tee
 * Writer has already gotten the information.
 */
public class TeePrintWriter extends PrintWriter {

	protected PrintWriter delegate;
	protected Writer writer;

	public TeePrintWriter(PrintWriter delegatePrintWriter, Writer writer) {
		// This writer will not functionality of the super-class anyway. We just need it to mimik a PrintWriter
		super(new NullWriter());

		this.delegate = delegatePrintWriter;
		this.writer = writer;
	}

	public void stopTee() {
		if (writer != null) {
			try {
				writer.flush();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			} finally {
				writer = null;
			}
		}
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	@Override
	public void flush() {
		if (writer != null) {
			try {
				writer.flush();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.flush();
	}

	@Override
	public void close() {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.close();
	}

	@Override
	public boolean checkError() {
		return delegate.checkError();
	}

	@Override
	public void write(int c) {
		if (writer != null) {
			try {
				writer.write(Integer.toString(c));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.write(c);
	}

	@Override
	public void write(char[] buf, int off, int len) {
		if (writer != null) {
			try {
				writer.write(buf, off, len);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.write(buf, off, len);
	}

	@Override
	public void write(char[] buf) {
		if (writer != null) {
			try {
				writer.write(buf);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.write(buf);
	}

	@Override
	public void write(String s, int off, int len) {
		if (writer != null) {
			try {
				writer.write(s, off, len);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.write(s, off, len);
	}

	@Override
	public void write(String s) {
		if (writer != null) {
			try {
				writer.write(s);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.write(s);
	}

	@Override
	public void print(boolean b) {
		if (writer != null) {
			try {
				writer.write(Boolean.toString(b));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.print(b);
	}

	@Override
	public void print(char c) {
		if (writer != null) {
			try {
				writer.write(c);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.print(c);
	}

	@Override
	public void print(int i) {
		if (writer != null) {
			try {
				writer.write(Integer.toString(i));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.print(i);
	}

	@Override
	public void print(long l) {
		if (writer != null) {
			try {
				writer.write(Long.toString(l));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.print(l);
	}

	@Override
	public void print(float f) {
		if (writer != null) {
			try {
				writer.write(Float.toString(f));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.print(f);
	}

	@Override
	public void print(double d) {
		if (writer != null) {
			try {
				writer.write(Double.toString(d));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.print(d);
	}

	@Override
	public void print(char[] s) {
		if (writer != null) {
			try {
				writer.write(s);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.print(s);
	}

	@Override
	public void print(String s) {
		if (writer != null) {
			try {
				writer.write(s);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.print(s);
	}

	@Override
	public void print(Object obj) {
		if (writer != null) {
			try {
				if (obj != null) {
					writer.write(obj.toString());
				} else {
					writer.write("null");
				}
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.print(obj);
	}

	@Override
	public void println() {
		if (writer != null) {
			try {
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println();
	}

	@Override
	public void println(boolean x) {
		if (writer != null) {
			try {
				writer.write(Boolean.toString(x));
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println(x);
	}

	@Override
	public void println(char x) {
		if (writer != null) {
			try {
				writer.write(x);
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println(x);
	}

	@Override
	public void println(int x) {
		if (writer != null) {
			try {
				writer.write(Integer.toString(x));
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println(x);
	}

	@Override
	public void println(long x) {
		if (writer != null) {
			try {
				writer.write(Long.toString(x));
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println(x);
	}

	@Override
	public void println(float x) {
		if (writer != null) {
			try {
				writer.write(Float.toString(x));
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println(x);
	}

	@Override
	public void println(double x) {
		if (writer != null) {
			try {
				writer.write(Double.toString(x));
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println(x);
	}

	@Override
	public void println(char[] x) {
		if (writer != null) {
			try {
				writer.write(x);
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println(x);
	}

	@Override
	public void println(String x) {
		if (writer != null) {
			try {
				writer.write(x);
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println(x);
	}

	@Override
	public void println(Object x) {
		if (writer != null) {
			try {
				if (x != null) {
					writer.write(x.toString());
				} else {
					writer.write("null");
				}
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.println(x);
	}

	@Override
	public PrintWriter printf(String format, Object... args) {
		if (writer != null) {
			try {
				writer.write(String.format(format, args));
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.printf(format, args);
		return this;
	}

	@Override
	public PrintWriter printf(Locale l, String format, Object... args) {
		if (writer != null) {
			try {
				writer.write(String.format(l, format, args));
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.printf(l, format, args);
		return this;
	}

	@Override
	public PrintWriter format(String format, Object... args) {
		if (writer != null) {
			try {
				writer.write(String.format(format, args));
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.format(format, args);
		return this;
	}

	@Override
	public PrintWriter format(Locale l, String format, Object... args) {
		if (writer != null) {
			try {
				writer.write(String.format(l, format, args));
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.format(l, format, args);
		return this;
	}

	@Override
	public PrintWriter append(CharSequence csq) {
		if (writer != null) {
			try {
				if (csq != null) {
					writer.write(csq.toString());
				} else {
					writer.write("null");
				}
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.append(csq);
		return this;
	}

	@Override
	public PrintWriter append(CharSequence csq, int start, int end) {
		if (writer != null) {
			try {
				if (csq != null) {
					writer.write(csq.subSequence(start, end).toString());
				} else {
					writer.write("null");
				}
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.append(csq, start, end);
		return this;
	}

	@Override
	public PrintWriter append(char c) {
		if (writer != null) {
			try {
				writer.write(c);
				writer.write("\n");
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		delegate.append(c);
		return this;
	}

}
