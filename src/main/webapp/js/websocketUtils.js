// JavaScript Document
var websocket={  
   //创建子命名空间,用的是yui的方法------  
   NS:function(ns) {  
       if (!ns || !ns.length) {  
           return null;  
       }  
       var levels = ns.split(".");  
       var nsobj = websocket;  
       for (var i=(levels[0] == "websocket") ? 1 : 0; i<levels.length; ++i) {  
         nsobj[levels[i]] = nsobj[levels[i]] || {};  
         nsobj = nsobj[levels[i]];  
       }  
       return nsobj;  
    }  
};
websocket.NS("util");
websocket.util = {
	getWs: function(url) {
		//var url = "ws://127.0.0.1:8080/${pageContext.request.contextPath}/demo";
		var ws;
        if ('WebSocket' in window) {  
            ws = new WebSocket(url);    
        } else if ('MozWebSocket' in window) {    
            ws = new MozWebSocket(url);    
        } else {    
            alert('浏览器不支持');    
            return;  
        }
        return ws;
	},
	//建立连接
	onopen: function(ws) {
		ws.onopen = function() {
			console.log("已经建立连接了......");
		}     
	},
	//发送消息
	sendMsg: function(ws,msg) {
		ws.send(JSON.stringify({
			'message' : msg
		}));
	},
	//断开连接
	onclose: function(ws) {
		ws.close();
		alert("断开连接成功");
	}
};
  