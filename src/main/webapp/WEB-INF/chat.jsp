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
            flex-direction: column;
            gap: 8px;
        }

        a {
            color: #222;
            text-decoration: none;
        }

        #header {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        #title {
            display: flex;
            align-items: flex-start;
        }

        .tag {
            font-size: 8px;
            color: #888;
            background-color: white;
            border: 1px solid #888;
            border-radius: 4px;
            padding: 2px 6px;
            margin: 4px 0 0 4px;
        }

        #content {
            flex: 1;
            min-height: 0;
            overflow-y: scroll;
            background-color: #eee;
            scrollbar-color: #888 #eee;
            white-space: pre;
            padding: 8px;
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        #action {
            display: flex;
            gap: 8px;
        }

        #targetSelect {
            min-width: 160px;
        }

        #messageInput {
            flex: 1;
        }
    </style>
</head>
<body>
<div id="header">
    <div id="title">
        <h1>在线聊天室</h1>
        <div class="tag">Pro Plus Max Ultra</div>
    </div>
    <a href="<c:url value="/auth/logout" />">登出</a>
</div>
<p id="content"></p>
<form id="action">
    <select id="targetSelect">
        <option value="-1">发送至公屏</option>
    </select>
    <input type="text" id="messageInput" placeholder="输入消息…"/>
    <input type="submit" id="sendButton" value="发送"/>
</form>
<script type="module">
    const form = document.getElementById('action');
    const content = document.getElementById('content');
    const targetSelect = document.getElementById('targetSelect');
    const msgInput = document.getElementById('messageInput');
    const sendBtn = document.getElementById('sendButton');

    let lastMessageId = -1;

    const fetchWithCheck = async (url, options = {}) => {
        const response = await fetch(url, { ...options, redirect: 'manual' });
        if (response.status % 100 === 3 || response.status === 0) {
            window.location.href = '<c:url value="/auth/login" />';
            return null;
        }
        return response;
    }

    const sendMessage = async (e) => {
        e.preventDefault();

        const content = msgInput.value;
        const targetId = Number(targetSelect.value);
        if (!content) {
            alert('未输入内容');
            return;
        }

        msgInput.disabled = true;
        sendBtn.disabled = true;

        try {
            await fetchWithCheck('<c:url value="/message" />', {
                method: 'POST',
                body: JSON.stringify({ content, targetId }),
            });
            await refresh();
            msgInput.value = '';
        } catch (e) {
            alert(`发送消息失败：\${e.message}`);
        }

        msgInput.disabled = false;
        sendBtn.disabled = false;
        msgInput.focus();
    };

    const getMessages = async () => {
        const response = await fetchWithCheck(`<c:url value="/message" />?lastId=\${lastMessageId}`);
        const result = await response.json();
        const messages = result.messages;
        if (!messages.length) return;

        const isAtBottom = content.scrollTop + content.clientHeight >= content.scrollHeight - 80;
        const buildMessage = (message) => {
            const div = document.createElement('div');
            const sender = {
                system: '[系统]',
                broadcast: `[公屏] @\${message.senderName}:`,
                privateIn: `[私聊] @\${message.senderName} → 你:`,
                privateOut: `[私聊] 你 → @\${message.targetName}:`,
                unknown: '[未知]'
            }[message.type]
            div.textContent = `\${sender} \${message.content}`;
            div.classList.add('message');
            return div;
        };
        lastMessageId = Math.max(lastMessageId, messages.at(-1).id);
        content.append(...messages.map(buildMessage));

        if (isAtBottom) {
            await new Promise((resolve) => setTimeout(resolve, 0));
            content.scrollTo({ top: content.scrollHeight, behavior: 'smooth' });
        }

        if (document.visibilityState !== 'visible') {
            document.title = '【新消息】在线聊天室 Pro Plus Max Ultra';
            document.addEventListener('visibilitychange', () => {
                if (document.visibilityState === 'visible') {
                    document.title = '在线聊天室 Pro Plus Max Ultra';
                }
            }, { once: true });
        }
    }

    const getOnlineUsers = async () => {
        const response = await fetchWithCheck('<c:url value="/user/online" />')
        const result = await response.json();
        const users = result.users;

        const buildOption = (name, id) => {
            const option = document.createElement('option');
            option.value = id;
            option.textContent = name;
            return option;
        }
        const selection = targetSelect.value;
        targetSelect.replaceChildren(
            buildOption('发送至公屏', -1),
            ...users.filter((user) => !user.isSelf).map((user) => buildOption(`私聊 @\${user.username}`, user.id))
        );
        targetSelect.value = users.map((user) => user.id).includes(Number(selection)) ? selection : -1;
    }

    const refresh = async () => {
        try {
            await Promise.allSettled([getMessages(), getOnlineUsers()]);
        } catch (e) {
            alert(`刷新状态失败：\${e.message}`);
        }
    };

    await refresh();
    setInterval(refresh, 1000);
    form.addEventListener('submit', sendMessage);
</script>
</body>
</html>
