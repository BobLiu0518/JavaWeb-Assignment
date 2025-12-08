<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>二手交易平台</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<c:import url="/header.jsp"/>
<div class="container detail-container">
    <h2 style="margin: 0 0 1rem;">商品详情</h2>
    <c:if test="${user != null && user.id == goods.publisherId}">
        <div style="margin-bottom: 1rem;">
            <span>操作</span>
            <c:if test="${!goods.sold}">
                <a href="<c:url value="/goods/${goods.id}?action=sold"/>">卖掉了</a>
                <a href="<c:url value="/goods/${goods.id}?action=edit"/>">编辑</a>
            </c:if>
            <a href="<c:url value="/goods/${goods.id}?action=delete"/>" style="color: #e74c3c;">删除</a>
        </div>
    </c:if>

    <img class="detail-image" src="<c:url value="/image/${goods.imageHash}" />"/>
    <h2><c:out value="${goods.name}"/></h2>
    <p class="goods-price<c:if test="${goods.sold}"> is-sold</c:if>">￥<c:out
            value="${goods.price >= 0 ? goods.price : \"∞\"}"/></p>
    <p>发布者：<c:out value="${publisher.username}"/></p>
    <p class="goods-view-desc"><c:out value="${goods.description}"/></p>
</div>
</body>
</html>
