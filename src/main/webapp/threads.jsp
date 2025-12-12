<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>在线问答留言交互平台</title>
    <link rel="stylesheet" href="<c:url value="/style.css"/>">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>在线问答留言交互平台</h1>
        <div class="nav">
            <c:if test="${user != null}">
                <span class="welcome">你好，${user.username}</span>
                <form method="post" action="<c:url value="/auth/logout"/>">
                    <button class="btn" type="submit">登出</button>
                </form>
            </c:if>
            <c:if test="${user == null}">
                <a class="btn" href="<c:url value="/login.jsp"/>">登录</a>
                <a class="btn btn-primary" href="<c:url value="/register.jsp"/>">注册</a>
            </c:if>
        </div>
    </div>

    <c:if test="${user != null}">
        <div class="card" id="new-thread">
            <h3 class="thread-title">发布新问题</h3>
            <form class="form" method="post" action="<c:url value="/threads/"/>">
                <div class="form-row">
                    <label class="label" for="title">标题</label>
                    <input class="input" id="title" name="title" type="text" required>
                </div>
                <div class="form-row">
                    <label class="label" for="content">内容</label>
                    <textarea class="textarea" id="content" name="content" required></textarea>
                </div>
                <div class="actions">
                    <button class="btn btn-primary" type="submit">发布</button>
                </div>
            </form>
        </div>
        <hr class="divider"/>
    </c:if>

    <c:forEach items="${threads.threads.descendingMap()}" var="thread">
        <div class="card">
            <h3 class="thread-title">
                <a href="<c:url value="/threads/${thread.value.threadId}"/>">${thread.value.title}</a>
                <span class="notice">#${thread.value.threadId}</span>
            </h3>
            <p class="post-content">${thread.value.posts[0].content}</p>
            <p class="thread-meta">${thread.value.sender.username} · ${thread.value.datetime}</p>
        </div>
    </c:forEach>

    <c:if test="${user == null}">
        <p class="notice">想要发布问题？请先<a class="footer-link" href="<c:url value="/login.jsp"/>">登录</a>或<a
                class="footer-link"
                href="<c:url value="/register.jsp"/>">注册</a>。
        </p>
    </c:if>
</div>
</body>
</html>
