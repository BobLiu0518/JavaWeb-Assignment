<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>二手交易平台</title>
        <link rel="stylesheet" href="<c:url value='/css/style.css'/>" />
    </head>
    <body>
        <c:import url="/header.jsp" />
        <div class="container">
            <h1 style="text-align: center; margin-bottom: 1rem">${goods != null ? "编辑商品" : "发布商品"}</h1>
            <form action="#" method="POST" enctype="multipart/form-data">
                <label>
                    <span>标题：</span>
                    <input name="name" value="<c:out value="${goods.name}" />"/>
                </label>
                <label>
                    <span>描述：</span>
                    <textarea name="description" rows="5"><c:out value="${goods.description}"/></textarea>
                </label>
                <label>
                    <span>价格：</span>
                    <input name="price" value="<c:out value="${goods.price}" />"/>
                </label>
                <label>
                    <span>图片：</span>
                    <input name="image" type="file" accept="image/*" />
                </label>
                <input name="id" value="${goods != null ? goods.id : 0}" type="hidden" />
                <button type="submit">保存</button>
            </form>
        </div>
    </body>
</html>
