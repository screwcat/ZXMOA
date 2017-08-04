<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>保存结果</title>
<meta charset="UTF-8">
<meta name="viewport"content="width=device-width,initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="email=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<link rel="shortcut icon" href="/MOA/resources/images/logo_16.ico" type="image/x-icon" />
<script type="text/javascript" src="/MOA/resources/js/zx-all.js"></script>
</head>
<style>

</style>
<body onload="returnPrevPage()">
    <input type="hidden" id="message" value="${sessionScope.message}" />
<script type="text/javascript">
//返回到上一个页面
function returnPrevPage() {
	window.parent.goBack($('#message').val());
}
</script>
</body>
</html>
