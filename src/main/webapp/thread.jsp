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
                <a class="btn" href="/login.jsp">登录</a>
                <a class="btn btn-primary" href="<c:url value="/register.jsp"/>">注册</a>
            </c:if>
        </div>
    </div>

    <div><a class="footer-link" href="<c:url value="/threads"/>">&lt; 返回主页</a></div>

    <div class="card">
        <h3 class="thread-title">${thread.title}<span class="notice">#${thread.threadId}</span></h3>
        <div class="divider"></div>
        <c:forEach items="${thread.posts}" var="post" varStatus="status">
            <div class="post">
                <div class="post-header">
                    <span>${post.sender.username}</span>
                    <span>#${status.index}</span>
                    <span>· ${post.datetime}</span>
                </div>
                <c:if test="${post.replyTo != -1}">
                    <div class="post-quote">回复 #${post.replyTo}<br>&gt; ${thread.posts[post.replyTo].content}</div>
                </c:if>
                <p class="post-content">${post.content}</p>
                <div class="actions">
                    <button class="btn btn-reply" type="button" data-reply-index="${status.index}">回复</button>
                </div>
            </div>
            <hr class="divider"/>
        </c:forEach>
    </div>

    <c:if test="${user != null}">
        <div class="card" id="answer-box">
            <div id="replyBanner" class="reply-banner">
                <span>回复 <span id="replyTarget" class="reply-target">#</span></span>
                <button id="clearReply" type="button" class="btn btn-link" aria-label="清除回复">✕</button>
            </div>
            <form class="form" id="answerForm" method="post" action="<c:url value="/threads/${thread.threadId}"/>">
                <input type="hidden" id="replyTo" name="replyTo" value="-1">
                <div class="form-row">
                    <label class="label" for="content">你的回答</label>
                    <textarea class="textarea" id="content" name="content" required></textarea>
                </div>
                <div class="actions">
                    <button class="btn btn-primary" type="submit">发布回答</button>
                </div>
            </form>
        </div>
    </c:if>

    <c:if test="${user == null}">
        <p class="notice">想要参与讨论？请先<a class="footer-link" href="<c:url value="/login.jsp"/>">登录</a>或<a
                class="footer-link"
                href="<c:url value="/register.jsp"/>">注册</a>。
        </p>
    </c:if>
</div>

<script type="module">
    const replyButtons = document.querySelectorAll('[data-reply-index]');
    const replyInput = document.getElementById('replyTo');
    const content = document.getElementById('content');
    const banner = document.getElementById('replyBanner');
    const target = document.getElementById('replyTarget');
    const clearBtn = document.getElementById('clearReply');

    const setReply = (index) => {
        if (!replyInput) return;
        replyInput.value = index;
        target.textContent = '#' + index;
        banner.classList.add('show');
        content.focus();
    }

    const clearReply = () => {
        if (!replyInput) return;
        replyInput.value = -1;
        banner.classList.remove('show');
    }

    replyButtons.forEach((btn) => {
        btn.addEventListener('click', () => {
            setReply(btn.getAttribute('data-reply-index'));
        });
    });

    clearBtn.addEventListener('click', clearReply);
</script>
</body>
</html>
