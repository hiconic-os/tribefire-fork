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
package com.braintribe.gwt.codec.string.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.ioc.client.Configurable;

import com.google.gwt.i18n.client.NumberFormat;

/**
 * A number string codec using derivatives of {@link Number} as value class and an instance of {@link NumberFormat}
 * to parse and format {@link Number} values; 
 * @author Dirk
 *
 */
public class GwtNumberCodec<T extends Number> implements Codec<T, String> {
	private static Map<Class<? extends Number>, Function<Double, ? extends Number>> numberFactories;
	
	static {
		numberFactories = new HashMap<Class<? extends Number>, Function<Double,? extends Number>>();
		
		numberFactories.put(Byte.class, new Function<Double, Byte>() {
			@Override
			public Byte apply(Double index) throws RuntimeException {
				return index.byteValue();
			}
		});
		numberFactories.put(Short.class, new Function<Double, Short>() {
			@Override
			public Short apply(Double index) throws RuntimeException {
				return index.shortValue();
			}
		});
		numberFactories.put(Integer.class, new Function<Double, Integer>() {
			@Override
			public Integer apply(Double index) throws RuntimeException {
				return index.intValue();
			}
		});
		numberFactories.put(Long.class, new Function<Double, Long>() {
			@Override
			public Long apply(Double index) throws RuntimeException {
				return index.longValue();
			}
		});
		numberFactories.put(Float.class, new Function<Double, Float>() {
			@Override
			public Float apply(Double index) throws RuntimeException {
				return index.floatValue();
			}
		});
		numberFactories.put(Double.class, new Function<Double, Double>() {
			@Override
			public Double apply(Double index) throws RuntimeException {
				return index.doubleValue();
			}
		});
	}
	
	private Class<T> numberClass;
	private NumberFormat format = NumberFormat.getFormat("0");
	private Function<Double, ? extends Number> numberFactory; 
	
	@SuppressWarnings("rawtypes")
	public GwtNumberCodec() {
		this((Class) Integer.class);
	}
	
	public GwtNumberCodec(Class<T> numberClass) {
		this.numberClass = numberClass;
		numberFactory = numberFactories.get(numberClass);
	}
	
	/**
	 * Configures a {@link NumberFormat} used for decoding the String to Number and
	 * vice-versa.
	 */
	@Configurable
	public void setFormat(NumberFormat format) {
		this.format = format;
	}
	
	/**
	 * Configures a pattern used for getting a {@link NumberFormat} used for decoding the String
	 * to Number and vice-versa.
	 */
	@Configurable
	public void setFormatByString(String format) {
		setFormat(NumberFormat.getFormat(format));
	}
	
	@Override
	public T decode(String encodedValue) throws CodecException {
		if (encodedValue == null || encodedValue.trim().length() == 0)
			return null;
		else {
			try {
				Double value = format.parse(encodedValue);
				return (T)numberFactory.apply(value);
			} catch (Exception e) {
				throw new CodecException("error while decoding value", e);
			}
		}
	}
	
	@Override
	public String encode(T value) throws CodecException {
		if (value == null) return "";
		else return format.format(value.doubleValue());
	}
	
	@Override
	public Class<T> getValueClass() {
		return numberClass;
	}
}
