<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>二手交易平台</title>
</head>
<body>
<c:import url="/header.jsp"/>
<form action="<c:url value="/goods/" />" method="GET">
    <input name="keyword" value="<c:out value="${keyword}" />" placeholder="搜索二手商品"/>
    <input type="submit"/>
</form>
<p>共找到 ${goodsCount} 个结果</p>
<c:forEach items="${goodsList}" var="goods">
    <div>
        <img src="<c:url value="/image?hash=${goods.imageHash}&mime=${goods.imageMime}" />"/>
        <h2><a href="<c:url value="/goods/${goods.id}"/>"><c:out value="${goods.name}"/></a></h2>
        <p>￥<c:out value="${goods.price}"/></p>
        <p><c:out value="${goods.description}"/></p>
    </div>
</c:forEach>
<div>
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
</body>
</html>