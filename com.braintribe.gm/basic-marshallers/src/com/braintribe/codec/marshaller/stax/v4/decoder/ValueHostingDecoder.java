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
package com.braintribe.codec.marshaller.stax.v4.decoder;

import org.xml.sax.Attributes;

import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.stax.decoder.Decoder;
import com.braintribe.codec.marshaller.stax.factory.DecoderFactoryContext;
import com.braintribe.codec.marshaller.stax.v4.decoder.collection.ListDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.collection.MapDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.collection.SetDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.entity.AbsenceInformationDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.entity.EntityReferenceDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.BooleanDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.DateDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.DecimalDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.DoubleDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.EnumDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.FloatDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.IntegerDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.LongDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.NullDecoder;
import com.braintribe.codec.marshaller.stax.v4.decoder.scalar.StringDecoder;

public class ValueHostingDecoder extends Decoder {
	protected boolean propertyDecorated;
	private static abstract class DecoderFactory {
		public abstract Decoder create();
	}
	private static DecoderFactory[] decoders = new DecoderFactory[128];
	
	static {
		// special values
		decoders['a'] = new DecoderFactory() { @Override public Decoder create() { return new AbsenceInformationDecoder(); } };

		// simple values
		decoders['b'] = new DecoderFactory() { @Override public Decoder create() { return new BooleanDecoder(); } };
		decoders['s'] = new DecoderFactory() { @Override public Decoder create() { return new StringDecoder(); } };
		decoders['i'] = new DecoderFactory() { @Override public Decoder create() { return new IntegerDecoder(); } };
		decoders['l'] = new DecoderFactory() { @Override public Decoder create() { return new LongDecoder(); } };
		decoders['f'] = new DecoderFactory() { @Override public Decoder create() { return new FloatDecoder(); } };
		decoders['d'] = new DecoderFactory() { @Override public Decoder create() { return new DoubleDecoder(); } };
		decoders['D'] = new DecoderFactory() { @Override public Decoder create() { return new DecimalDecoder(); } };
		decoders['T'] = new DecoderFactory() { @Override public Decoder create() { return new DateDecoder(); } };
		
		// collections
		decoders['L'] = new DecoderFactory() { @Override public Decoder create() { return new ListDecoder(); } };
		decoders['S'] = new DecoderFactory() { @Override public Decoder create() { return new SetDecoder(); } };
		decoders['M'] = new DecoderFactory() { @Override public Decoder create() { return new MapDecoder(); } };
		
		// null
		decoders['n'] = new DecoderFactory() { @Override public Decoder create() { return new NullDecoder(); } };
		
		// custom types
		decoders['e'] = new DecoderFactory() { @Override public Decoder create() { return new EnumDecoder(); } };
		decoders['r'] = new DecoderFactory() { @Override public Decoder create() { return new EntityReferenceDecoder(); } };
		
	}
	
	/**
	 * @param context
	 */
	public Decoder newDecoderTable(DecoderFactoryContext context, String _elementName, Attributes attributes) throws MarshallException {
		if (_elementName.length() > 1)
			throw new MarshallException("Unsupported element type "+_elementName);
		
		Decoder decoder = null;
		char c = _elementName.charAt(0);
		if (c < 128) {
			DecoderFactory factory = decoders[c];
			if (factory != null)
				decoder = factory.create();
			else
				throw new MarshallException("Unsupported element type "+_elementName);
		}
		else {
			throw new MarshallException("Unsupported element type "+_elementName);
		}

		if (propertyDecorated)
			decoder.propertyName = attributes.getValue("p");
		
		return decoder;
	}
	
	@Override
	public Decoder newDecoder(DecoderFactoryContext context, String _elementName, Attributes attributes) throws MarshallException {
		if (_elementName.length() > 1)
			throw new MarshallException("Unsupported element type "+_elementName);
		
		Decoder decoder = null;
		switch (_elementName.charAt(0)) {
		// special values
		case 'a': decoder = new AbsenceInformationDecoder(); break;
		
		// simple values
		case 'b': decoder = new BooleanDecoder(); break;
		case 's': decoder = new StringDecoder(); break;
		case 'i': decoder = new IntegerDecoder(); break;
		case 'l': decoder = new LongDecoder(); break;
		case 'f': decoder = new FloatDecoder(); break;
		case 'd': decoder = new DoubleDecoder(); break;
		case 'D': decoder = new DecimalDecoder(); break;
		case 'T': decoder = new DateDecoder(); break;
		
		// collections
		case 'L': decoder = new ListDecoder(); break;
		case 'S': decoder = new SetDecoder(); break;
		case 'M': decoder = new MapDecoder(); break;
		
		// null
		case 'n': decoder = new NullDecoder(); break;
		
		// custom types
		case 'e': decoder = new EnumDecoder(); break;
		case 'r': decoder = new EntityReferenceDecoder(); break;
		
		default: 
			throw new MarshallException("Unsupported element type "+_elementName);
		}
		
		if (propertyDecorated)
			decoder.propertyName = attributes.getValue("p");
		
		return decoder;
	}
}
