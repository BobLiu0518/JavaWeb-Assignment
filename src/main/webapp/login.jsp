<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <title>在线聊天室</title>
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
        form {
            display: flex;
            flex-direction: column;
            gap: 8px;
            text-align: center;
            padding-bottom: 120px;
        }
    </style>
</head>
<body>
<form action="./login" method="post">
    <h1>在线聊天室</h1>
    <div>
        <label for="username">用户名：</label>
        <input name="username" type="text" required />
    </div>
    <input type="submit" value="登录" />
</form>
</body>
</html>
