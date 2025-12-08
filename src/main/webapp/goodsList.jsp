<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>二手交易平台</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<c:import url="/header.jsp"/>
<div class="container">
    <form action="<c:url value="/goods/" />" method="GET" class="search-form">
        <input name="keyword" value="<c:out value="${keyword}" />" placeholder="搜索二手商品"/>
        <input type="submit" value="搜索"/>
    </form>
    <p>共找到 ${goodsCount} 个结果<c:if test="${keyword != null && !keyword.isEmpty()}"> <a
            href="<c:url value="/goods/"/>">清空搜索</a></c:if></p>
    <div class="goods-grid">
        <c:forEach items="${goodsList}" var="goods">
            <div class="goods-card" onclick="location.href='<c:url value="/goods/${goods.id}"/>'">
                <div class="goods-image-container">
                    <img class="goods-image" src="<c:url value="/image/${goods.imageHash}" />"/>
                    <c:if test="${goods.sold}">
                        <div class="sold-overlay">
                            <div class="sold-text">卖掉了~</div>
                        </div>
                    </c:if>
                </div>
                <div class="goods-info">
                    <h2 class="goods-title"><c:out
                            value="${goods.name}"/></h2>
                    <p class="goods-price<c:if test="${goods.sold}"> is-sold</c:if>">￥<c:out
                            value="${goods.price}"/></p>
                    <p class="goods-desc"><c:out value="${goods.description}"/></p>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="pagination">
        <c:if test="${page > 1}">
            <a href="<c:url value="/goods/">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="page" value="${page - 1}"/>
            </c:url>">&lt; 上一页</a>
        </c:if>
        <c:forEach begin="1" end="${pageCount}" var="i">
            <a href="<c:url value="/goods/">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="page" value="${i}"/>
            </c:url>"
               <c:if test="${i == page}">class="is-active"</c:if>
            >${i}</a>
        </c:forEach>
        <c:if test="${page < pageCount}">
            <a href="<c:url value="/goods/">
                <c:param name="keyword" value="${keyword}"/>
                <c:param name="page" value="${page + 1}"/>
            </c:url>">下一页 &gt;</a>
        </c:if>
    </div>
</div>
</body>
</html>