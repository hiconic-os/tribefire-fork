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
package com.braintribe.model.processing.email.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

import com.braintribe.model.email.deployment.connection.Pop3Connector;
import com.braintribe.model.email.deployment.connection.RetrieveConnector;

public class MailboxContext {

	protected Session session = null;
	protected Store store = null;
	protected Folder folder = null;

	protected Properties serverProperties = null;
	protected String protocol = null;
	protected String host = null;
	protected String port = null;
	protected String folderName = null;
	protected String username = null;
	protected String password = null;

	public MailboxContext(Properties serverProperties, String protocol, String host, String port, String folderName, String username,
			String password) {
		this.serverProperties = serverProperties;
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.folderName = folderName;
		this.username = username;
		this.password = password;
	}

	public MailboxContext(RetrieveConnector connection, String folder) {
		if (connection instanceof Pop3Connector) {
			this.protocol = "pop3";
		} else {
			this.protocol = "imap";
		}

		this.serverProperties = createServerProperties(connection, protocol, false);
		this.host = connection.getHost();
		this.port = Integer.toString(connection.getPort());
		this.folderName = folder;
		this.username = connection.getUser();
		this.password = connection.getPassword();
	}

	private Properties createServerProperties(RetrieveConnector connector, String proto, boolean isDebug) {
		String cHost = connector.getHost();
		String cPort = Integer.toString(connector.getPort());

		Properties properties = new Properties();
		// server settings
		properties.put(String.format("mail.%s.host", proto), cHost);
		properties.put(String.format("mail.%s.port", proto), cPort);
		properties.put("mail.store.protocol", proto);
		properties.put(String.format("mail.%s.fetchsize", proto), "4194304"); // 4 mbs
		// SSL settings
		properties.put(String.format("mail.%s.ssl.enable", proto), "true");
		properties.put(String.format("mail.%s.socketFactory.class", proto), "javax.net.ssl.SSLSocketFactory");
		properties.put(String.format("mail.%s.socketFactory.fallback", proto), "false");
		// misc
		properties.put("mail.debug", Boolean.toString(isDebug));
		return properties;
	}
	public void connect() throws MessagingException {
		connect(null);
	}

	public void connect(Integer folderType) throws MessagingException {
		session = Session.getInstance(serverProperties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		store = getSession().getStore(protocol);

		getStore().connect(username, password);
		folder = getStore().getFolder(folderName);
		if (folderType == null) {
			getFolder().open(Folder.READ_ONLY);
		} else {
			getFolder().open(folderType);
		}
	}

	public Message[] searchMessages() throws MessagingException {
		return searchMessages(null);
	}

	public Message[] searchMessages(SearchTerm searchTerm) throws MessagingException {
		Message[] messages = null;
		Folder searchFolder = getFolder();
		if (searchTerm == null) {
			messages = searchFolder.getMessages();
		} else {
			messages = searchFolder.search(searchTerm);
		}
		if (messages == null) {
			messages = new Message[0];
		}
		return messages;
	}

	public void close(boolean expunge) throws MessagingException {
		if (this.getFolder() != null) {
			this.getFolder().close(expunge);
			this.folder = null;
		}
		if (this.getStore() != null) {
			this.getStore().close();
			this.store = null;
		}
		this.session = null;
	}

	public Session getSession() {
		return session;
	}

	public Store getStore() {
		return store;
	}

	public Folder getFolder() {
		return folder;
	}
	public Folder getFolder(String name, boolean createIfNonExistent) throws MessagingException {
		Folder namedFolder = getStore().getFolder(name);
		if (!namedFolder.exists() && createIfNonExistent) {
			namedFolder.create(Folder.HOLDS_FOLDERS | Folder.HOLDS_MESSAGES);
		}
		return namedFolder;
	}

}
