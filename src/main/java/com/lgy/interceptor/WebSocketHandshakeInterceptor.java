package com.lgy.interceptor;

import java.util.Map;

import com.lgy.domain.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Repository
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		System.out.println("握手之前····");
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);

			//如果用户已经登录，允许聊天
			if(session !=null && session.getAttribute("loginUser")!=null){
				//获取登录的用户
				User loginUser=(User)session.getAttribute("loginUser") ;
				//将用户放入socket处理器的会话(WebSocketSession)中
				attributes.put("loginUser", loginUser);
				System.out.println("Websocket:用户[ID:" + (loginUser.getId() + ",Name:"+loginUser.getNickname()+"]要建立连接"));
			}else{
				//用户没有登录，拒绝聊天
				//握手失败！
				System.out.println("--------------握手已失败...");
				return false;
			}
		}
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		System.out.println("握手之后····");
		super.afterHandshake(request, response, wsHandler, exception);
	}
 
}