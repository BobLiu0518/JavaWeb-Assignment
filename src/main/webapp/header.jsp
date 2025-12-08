<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<div class="header">
    <h1 onclick="location.href = '<c:url value="/goods/"/>'">二手交易平台</h1>
    <c:if test="${user != null}">
        <span>欢迎，<c:out value="${user.username}"/>！</span>
        <a href="<c:url value="/goods/?action=publish" />">发布二手商品</a>
    </c:if>
    <c:if test="${user == null}">
        <a href="<c:url value="/login.jsp" />">登录</a>
        <a href="<c:url value="/register.jsp" />">注册</a>
    </c:if>
</div>