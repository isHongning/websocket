<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
    String baseUrlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>主界面</title>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/sockjs.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/websocketUtils.js"></script>
    <!-- js code -->
    <script type="text/javascript">
        var path = '<%=basePath%>';
        var ws;
        $(function () {
            var url = "ws://" + path + "webSocketServer";
            ws = websocket.util.getWs(url);
            websocket.util.onopen(ws);

            ws.onmessage = function (event) {
                var receiveMsg = $("#receive").val();
                //$("#receive")[0].innerHTML(receiveMsg + event.data);
                $("#receive")[0].value = receiveMsg + event.data;
            }

            // 连接发生错误的回调方法
            ws.onerror = function (event) {
                console.log("WebSocket:发生错误 ", event);
            }
        });
        function send() {
            var value = $("#val").val();
            websocket.util.sendMsg(ws, value);
        }
        function closed() {
            console.log("close ws...");
            websocket.util.onclose(ws);
        }

        function test() {

            var a_cross_domain_url = "http://127.0.0.1:8080/${pageContext.request.contextPath }/index/test";
            alert(a_cross_domain_url);
            $.ajax({
                url: a_cross_domain_url,
                // 将XHR对象的withCredentials设为true
                xhrFields: {
                    withCredentials: true
                },
                success: function(data){
                    alert(data);
                    console.log(data);
                }
            });
        }

        function testAave() {
            var a_cross_domain_url = "${pageContext.request.contextPath }/index/saveSession";
            $.ajax({
                url: a_cross_domain_url,
                // 将XHR对象的withCredentials设为true
                xhrFields: {
                    withCredentials: true
                },
                success: function(data){
                    alert(data);
                    console.log(data);
                }
            });
        }
    </script>
</head>
<body>
欢迎:${sessionScope.loginUser['nickname']}

<textarea rows="5" cols="6" id="val">输入消息</textarea>

<input type="button" value="Send" onclick="send();"/>
<input type="button" value="Close" onclick="closed();"/><br/>

接受消息框
<textarea rows="10" cols="15" id="receive"></textarea>


<!-- 测试cession跨域问题 -->
<br /><br /><br />
<input type="button" onclick="test();" value="测试跨域问题" />

<br /><br /><br />
<input type="button" onclick="testAave();" value="存session" />
</body>
</html>