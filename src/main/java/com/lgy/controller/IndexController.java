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
		return "";
	}

}
