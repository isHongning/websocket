package com.lgy.controller;

import com.lgy.domain.User;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/index")
public class IndexController {
	@RequestMapping("")
	public String index(HttpServletRequest httpRequest, String nickname) {
		HttpSession httpSession = httpRequest.getSession();
		System.out.println("###跳转进入到了首页###");
		User user = new User();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(new Date());

		user.setId(dateString + "-" + nickname);
		user.setNickname(nickname);

		// 登录操作
		// 判断是否是一个已经登录的用户，没有则登录
		if (null != httpSession.getAttribute("loginUser")) {
			// 清除旧的用户
			httpSession.removeAttribute("loginUser");
		}
		// 将用户放入session
		httpSession.setAttribute("loginUser", user);
		return "redirect:/index/mainpage";
	}

	// 跳转到聊天室页面
	@RequestMapping(value = "mainpage", method = RequestMethod.GET)
	public String mainpage(HttpServletRequest request) {
		//判断，如果没有session，则跳到登录页面
		HttpSession session = request.getSession();
		if(null==session.getAttribute("loginUser")){
			return "main";
		}else{
			return "index";
		}
	}

	@RequestMapping(value = "test", method = RequestMethod.GET)
	@ResponseBody
	public String test(HttpServletRequest request, HttpServletResponse httpResponse) {
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8080");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

		//判断，如果没有session，则跳到登录页面
		HttpSession session = request.getSession();
		if(null != session.getAttribute("loginUser")){
			User user = (User) session.getAttribute("loginUser");
			System.out.println("session 不为空");
			return "session 不为空 nicknage :" + user.getNickname();
		}else{
			System.out.println("session ---------------------------- null");
			return "session ---------------------------- null";
		}
	}


	@RequestMapping("saveSession")
	@ResponseBody
	public User saveSession(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		httpResponse.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域

		HttpSession httpSession = httpRequest.getSession();
		System.out.println("###跳转进入到了首页###");
		User user = new User();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(new Date());
		String nickname = "test";

		user.setId(dateString + "-" + nickname);
		user.setNickname(nickname);

		// 登录操作
		// 判断是否是一个已经登录的用户，没有则登录
		User u = (User) httpSession.getAttribute("loginUser");

		System.out.println(u);

		if (null != httpSession.getAttribute("loginUser")) {
			// 清除旧的用户
			httpSession.removeAttribute("loginUser");
			System.out.println("session 存在");
		}
		// 将用户放入session
		httpSession.setAttribute("loginUser", user);
		return user;
	}
}
