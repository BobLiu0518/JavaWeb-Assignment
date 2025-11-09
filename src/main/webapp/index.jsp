<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
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
            flex-direction: column;
            gap: 8px;
        }
        #content {
            flex: 1;
            background-color: #eee;
            white-space: pre;
        }
        #action {
            display: flex;
            gap: 8px;
        }
        #message {
            flex: 1;
        }
    </style>
</head>
<body>
<h1>在线聊天室</h1>
<p id="content">聊天室加载中…</p>
<form id="action">
    <input type="text" id="message" />
    <input type="submit" id="send" value="发送" />
</form>
<script type="module">
    const form = document.getElementById('action');
    const content = document.getElementById('content');
    const msgInput = document.getElementById('message');
    const sendBtn = document.getElementById('send');

    const fetchWithCheck = async (...args) => {
        const response = await fetch(...args);
        if (response.status === 401) {
            location.href = './login.jsp';
        }
        return response;
    };

    const send = async (e) => {
        e.preventDefault();

        const msg = msgInput.value;
        if (!msg) {
            alert('未输入内容');
            return;
        }

        msgInput.disabled = true;
        sendBtn.disabled = true;

        try {
            await fetchWithCheck('./message', { method: 'POST', body: msg });
            await refresh();
            msgInput.value = '';
        } catch (e) {
            alert(`发送消息失败：${e.message}`);
        }

        msgInput.disabled = false;
        sendBtn.disabled = false;
    };

    const refresh = async () => {
        try {
            const response = await fetchWithCheck('./message', { method: 'GET' });
            const result = await response.text();
            content.textContent = result;
        } catch (e) {
            alert(`收取消息失败：${e.message}`);
        }
    };

    await refresh();
    setInterval(refresh, 1000);
    form.addEventListener('submit', send);
</script>
</body>
</html>
