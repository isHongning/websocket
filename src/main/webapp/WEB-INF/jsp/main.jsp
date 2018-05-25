<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.1.js"></script>

    <script type="application/javascript">
        function toIndex() {
            var nickname = $("#nickName").val();
            // <%--${pageContext.request.contextPath}/index--%>
            window.location.href="${pageContext.request.contextPath}/index?nickname="+nickname;
        }
    </script>
</head>
<body>
    昵称:<input id="nickName" type="text" name="nickname" />
    <a href="javascript:void(0)" onclick="toIndex();">点击跳入到主界面</a>
</body>
</html>