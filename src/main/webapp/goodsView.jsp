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
    <h1>商品详情</h1>
    <div style="margin-bottom: 1rem;">
        <c:if test="${user != null && user.id == goods.publisherId}">
            <c:if test="${!goods.sold}">
                <a href="<c:url value="/goods/${goods.id}?action=sold"/>">卖掉了</a>
                <a href="<c:url value="/goods/${goods.id}?action=edit"/>">编辑</a>
            </c:if>
            <a href="<c:url value="/goods/${goods.id}?action=delete"/>" style="color: #e74c3c;">删除</a>
        </c:if>
    </div>

    <img class="detail-image" src="<c:url value="/image/${goods.imageHash}" />"/>
    <h2><c:out value="${goods.name}"/></h2>
    <p class="goods-price">￥<c:out value="${goods.price}"/></p>
    <p style="line-height: 1.6; color: #555;"><c:out value="${goods.description}"/></p>
</div>
</body>
</html>
