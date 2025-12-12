<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>在线问答留言交互平台</title>
    <link rel="stylesheet" href="<c:url value="/style.css"/>">
</head>
<body>
<div class="container">
    <div class="header topbar">
        <h1>在线问答留言交互平台</h1>
        <div class="nav">
            <a class="footer-link" href="<c:url value="/threads"/>">返回主页</a>
        </div>
    </div>

    <div class="card auth">
        <h2>登录</h2>
        <form class="form" method="post" action="<c:url value="/auth/login"/>">
            <div class="form-row">
                <label class="label" for="username">用户名</label>
                <input class="input" id="username" name="username" type="text" required placeholder="请填写用户名">
            </div>
            <div class="form-row">
                <label class="label" for="password">密码</label>
                <input class="input" id="password" name="password" type="password" required placeholder="请填写密码">
            </div>
            <div class="form-row">
                <label class="label" for="captcha">验证码</label>
                <div class="captcha-input">
                    <input class="input" id="captcha" name="captcha" type="text" required
                           placeholder="请填写右侧算式的结果">
                    <img class="captchaImg" id="captchaImg" src="<c:url value="/captcha"/>" height="36px"/>
                </div>
            </div>
            <div class="actions">
                <button class="btn btn-primary" type="submit">登录</button>
                <a class="footer-link" href="<c:url value="/register.jsp"/>">去注册</a>
            </div>
        </form>
    </div>
</div>
<script type="module">
    const captchaImg = document.getElementById("captchaImg");
    captchaImg.addEventListener("click", () => {
        captchaImg.src = `<c:url value="/captcha"/>?t=\${Date.now()}`;
    })
</script>
</body>
</html>

