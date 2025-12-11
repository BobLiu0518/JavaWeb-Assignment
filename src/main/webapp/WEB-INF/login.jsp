<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>在线聊天室 Pro Plus Max Ultra</title>
    <style>
        * {
            margin: 0;
            box-sizing: border-box;
        }

        body {
            width: 100vw;
            height: 100vh;
            padding: 24px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .tag {
            position: absolute;
            font-size: 8px;
            color: #888;
            background-color: white;
            border: 1px solid #888;
            border-radius: 4px;
            padding: 2px 6px;
            margin: 4px 0 0 4px;
        }

        #main {
            display: flex;
            flex-direction: column;
            gap: 8px;
            text-align: center;
            padding-bottom: 120px;
        }
    </style>
</head>
<body>
<div id="main">
    <h1>在线聊天室 <span class="tag">Pro Plus Max Ultra</span></h1>
    <div>
        <label for="username">用户名</label>
        <input id="username" name="username" type="text" placeholder="请输入用户名" autocomplete="off" required/>
    </div>
    <button id="login">登录</button>
</div>
<script>
    const loginBtn = document.getElementById('login')
    const usernameInput = document.getElementById('username')

    const login = async () => {
        const username = usernameInput.value.trim()
        const response = await fetch('<c:url value="/auth/login" />', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username }),
        })

        if (response.ok) {
            window.location.href = '<c:url value="/chat" />'
        } else {
            const result = await response.json()
            alert('登录失败：' + result.message)
        }
    }
    loginBtn.addEventListener('click', login)
</script>
</body>
</html>