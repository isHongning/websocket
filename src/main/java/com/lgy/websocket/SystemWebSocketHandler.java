package com.lgy.websocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SystemWebSocketHandler extends TextWebSocketHandler {
	private static final Logger logger;
	private static final Set<WebSocketSession> connections;

	static {
		logger = Logger.getLogger(SystemWebSocketHandler.class.getName());
		connections = new CopyOnWriteArraySet<WebSocketSession>();
	}

	/**
	 * 接受消息
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage");
		session.sendMessage(message);
	}

	/**
	 * 连接成功时候，会触发UI上onopen方法
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("connect to the websocket success......");
		connections.add(session);
		// 这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
		TextMessage returnMessage = new TextMessage("你大爷");
		SystemWebSocketHandler.sendMessageToUsers(returnMessage);
	}
	
	/**
	 * 用户关闭连接时候
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.info("websocket connection closed......");
		connections.remove(session);
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 */
	public static void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : connections) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
