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
package com.braintribe.gwt.gme.websocket.client;

import java.util.Map;
import java.util.function.Function;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.browserfeatures.client.WebSocket;
import com.braintribe.gwt.genericmodel.client.codec.api.GmDecodingContext;
import com.braintribe.gwt.genericmodel.client.codec.jse.JseCodec;
import com.braintribe.gwt.gmrpc.api.client.itw.TypeEnsurer;
import com.braintribe.gwt.gmrpc.web.client.StandardDecodingContext;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.gwt.gmview.client.ModelEnvironmentSetListener;
import com.braintribe.gwt.gmview.client.WebSocketSupport;
import com.braintribe.gwt.ioc.client.DisposableBean;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.logging.client.Logger;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.service.api.ServiceRequest;
import com.google.gwt.user.client.Timer;
import com.sencha.gxt.core.shared.FastMap;

/**
 * Class responsible for receiving of messages via {@link WebSocket}, and handling it properly with the correct {@link WebSocketHandler}.
 * @author michel.docouto
 *
 */
public class WebSocketExpert implements DisposableBean, WebSocketSupport, ModelEnvironmentSetListener {
	
	private static final Logger logger = new Logger(WebSocketExpert.class);
	
	private WebSocket webSocket;
	private JseCodec jseCodec;
	private TypeEnsurer typeEnsurer;
	//private Supplier<WebSocketHandlerRegistry> webSocketHandlerRegistrySupplier;
	private Evaluator<ServiceRequest> evaluator;
	private Map<String, WebSocket> webSocketMap;
	private Function<String, String> webSocketUrlFunction;
	private final Timer connectionTimer = new Timer() {
		@Override
		public void run() {
			reconnectIfNecessary();
		}
	};

	private void reconnectIfNecessary() {
		if (webSocket == null || webSocket.readyState == WebSocket.CLOSED)
			openWebSocket();
	}

	/**
	 * Configures the ensurer used for the decoding process.
	 */
	@Required
	public void setTypeEnsurer(TypeEnsurer typeEnsurer) {
		this.typeEnsurer = typeEnsurer;
	}
	
	/*
	 * Configures the registry for handlers.
	 *
	@Required
	public void setWebSocketHandlerRegistry(Supplier<WebSocketHandlerRegistry> webSocketHandlerRegistrySupplier) {
		this.webSocketHandlerRegistrySupplier = webSocketHandlerRegistrySupplier;
	}*/
	
	/**
	 * Configures the required evaluator for the received {@link ServiceRequest}.
	 */
	@Required
	public void setEvaluator(Evaluator<ServiceRequest> evaluator) {
		this.evaluator = evaluator;
	}
	
	/**
	 * Configures the function for providing additional webSocket connections.
	 */
	@Required
	public void setWebSocketUrlFunction(Function<String, String> webSocketUrlFunction) {
		this.webSocketUrlFunction = webSocketUrlFunction;
	}
	
	@Override
	public void onModelEnvironmentSet() {
		disconnect();
		openWebSocket();
		connectionTimer.scheduleRepeating(5000);
	}
	
	@Override
	public void openNotificationChannel(String clientId) {
		if (webSocketMap == null)
			webSocketMap = new FastMap<>();
		
		if (webSocketMap.containsKey(clientId))
			return;
		
		String webSocketUrl = webSocketUrlFunction.apply(clientId);
		WebSocket webSocket = new WebSocket(webSocketUrl);
		prepareWebSocket(webSocket, webSocketUrl);
		webSocketMap.put(clientId, webSocket);
	}
	
	@Override
	public void closeNotificationChannel(String clientId) {
		if (webSocketMap == null)
			return;
		
		WebSocket webSocket = webSocketMap.remove(clientId);
		if (webSocket != null  && webSocket.readyState == WebSocket.OPEN)
			webSocket.close();
	}

	private void openWebSocket() {
		try {
			String webSocketUrl = webSocketUrlFunction.apply(null);
			webSocket = new WebSocket(webSocketUrl);
			prepareWebSocket(webSocket, webSocketUrl);
		} catch (Exception e) {
			logger.error("Error with WebSocket connection: " + e.getMessage());
		}						
	}
	
	private void prepareWebSocket(WebSocket webSocket, String webSocketUrl) {
		try {
			webSocket.onopen = event -> logger.info("WebSocket connection established to: " + webSocketUrl);
			
			webSocket.onmessage = messageEvent -> {
				String data = (String) messageEvent.data;
				GmDecodingContext context = new StandardDecodingContext(typeEnsurer);
				getJseCodec().<ServiceRequest> decodeAsync(data, context) //
						.andThen(result -> {
							if (result == null) {
								logger.warn("An empty ServiceRequest was received via WebSocket connection. Ignored.");
								return;
							}

							evaluator.eval(result).get(Future.async(WebSocketExpert.this::error, WebSocketExpert.this::onEvaluation));
							
							/*
							WebSocketHandler<ServiceRequest> webSocketHandler = (WebSocketHandler<ServiceRequest>) webSocketHandlerRegistrySupplier.get().apply(result.entityType());
							if (webSocketHandler == null)
								GlobalState.showWarning(LocalizedText.INSTANCE.noHandlerConfigured(result.entityType().getShortName()));
							else {
								try {
									webSocketHandler.handleServiceRequest(result);
								} catch (Exception e) {
									logger.error("Error while handling ServiceRequest received via WebSocket connection.", e);
								}
							}*/
						}).onError(t -> GlobalState.showError("Error while decoding received message from services.", t));
			};
			
			webSocket.onerror = error -> logger.error("Error with WebSocket connection to: " + webSocketUrl);
			webSocket.onclose = closeEvent -> logger.info("Lost WebSocket connection to: " + webSocketUrl);
		} catch (Exception e) {
			logger.error("Error with WebSocket connection: " + e.getMessage());
		}
	}
	
	private void error(Throwable t) {
		logger.error("Error while evaluating the received ServiceRequest via WebSocket.", t);
		t.printStackTrace();
	}
	
	/**
	 * @param evaluatedObject - Currently ignored. 
	 */
	private void onEvaluation(Object evaluatedObject) {
		//Nothing to do here with this result.
	}
	
	private JseCodec getJseCodec() {
		if (jseCodec != null)
			return jseCodec;
		
		jseCodec = new JseCodec();
		return jseCodec;
	}
	
	@Override
	public void disposeBean() throws Exception {
		disconnect();
	}
	
	private void disconnect() {
		if (webSocket == null)
			return;
		
		connectionTimer.cancel();
		
		if (webSocket.readyState == WebSocket.OPEN)
			webSocket.close();
		
		if (webSocketMap != null)
			webSocketMap.values().stream().filter(ws -> ws.readyState == WebSocket.OPEN).forEach(ws -> ws.close());
	}

}
