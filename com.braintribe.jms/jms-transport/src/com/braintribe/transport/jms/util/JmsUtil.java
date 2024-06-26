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
package com.braintribe.transport.jms.util;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.braintribe.logging.Logger;
import com.braintribe.logging.Logger.LogLevel;
import com.braintribe.transport.jms.message.IMessageContext;

public class JmsUtil {

	public static void logError(Logger logger, Throwable ext, String text) {
		log(logger, LogLevel.ERROR, ext, text);
	}
	public static void log(Logger logger, LogLevel logLevel, Throwable ext, String text) {
		logger.log(logLevel, text, ext);
		if ((ext != null) && (ext instanceof JMSException)) {
			logger.log(logLevel, "Linked Exception:", ((JMSException) ext).getLinkedException());
		}
	}

	public static String getMessageText(IMessageContext messageContext) throws Exception {
		String msgText = null;
		
		Message msg = messageContext.getMessage();
		if (msg instanceof MapMessage) {
			msgText = ((MapMessage) msg).getString("body");
		} else if (msg instanceof TextMessage) {
			msgText = ((TextMessage) msg).getText();
		} else if (msg instanceof BytesMessage) {
			msgText = ((BytesMessage) msg).readUTF();
		} else {
			throw new Exception("Unsupported JMS message type " + msg.getClass());
		}
		
		return msgText;
	}
	

	public static Object getMessageContent(IMessageContext messageContext) throws Exception {
		Message msg = messageContext.getMessage();
		if (msg instanceof MapMessage) {
			Map<String,Object> mapContent = new HashMap<String,Object>();
			MapMessage mm = (MapMessage) msg;
			for (Enumeration<?> e = mm.getMapNames(); e.hasMoreElements(); ) {
				String key = (String) e.nextElement();
				Object value = mm.getObject(key);
				mapContent.put(key, value);
			}
			return mapContent;
		} else if (msg instanceof TextMessage) {
			String msgText = ((TextMessage) msg).getText();
			return msgText;
		} else if (msg instanceof BytesMessage) {
			BytesMessage bm = (BytesMessage) msg;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = bm.readBytes(buffer);
			while (len != -1) {
				baos.write(buffer, 0, len);
				len = bm.readBytes(buffer);
			}
			byte[] msgBytes = baos.toByteArray();
			return msgBytes;
		} else if (msg instanceof ObjectMessage) {
			ObjectMessage om = (ObjectMessage) msg;
			Object content = om.getObject();
			return content;
		} else {
			throw new Exception("Unsupported JMS message type " + msg.getClass());
		}

	}
	
	
	public static String generateCorrelationID() {
		String correlationID = getRandomString() + getRandomString(10);
		return correlationID;
	}
	
	public static String getRandomString() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String id = sdf.format(d);

		Random rnd = new Random();
		long lng = rnd.nextLong();
		lng = (lng < 0) ? (-lng) : (lng);
		id += lng;

		return id;
	}
	
	public static String getRandomString(int length) {
		Random r = new Random();
		String result = "";
		while (result.length() < length) {
			long l = r.nextLong();
			if (l < 0) {
				l = -l;
			}
			result = result + l;
		}
		return result.substring(0, length);
	}
	public static String getBuildVersion() {
		return "$Build_Version$ $Id: JmsUtil.java 92352 2016-03-11 14:34:02Z roman.kurmanowytsch $";
	}
}
