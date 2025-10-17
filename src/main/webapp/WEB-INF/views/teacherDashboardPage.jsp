<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Teacher Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #ff9966, #9933ff);
            margin: 0;
            padding: 0;
            color: #fff;
        }
        .container {
            max-width: 800px;
            margin: 80px auto;
            background: rgba(0, 0, 0, 0.6);
            padding: 40px;
            border-radius: 12px;
            text-align: center;
            box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.4);
        }
        h1 {
            margin-bottom: 20px;
            font-size: 36px;
            color: #ffd966;
        }
        h2 {
            margin-bottom: 30px;
            font-size: 22px;
            font-weight: normal;
        }
        .logout-btn {
            display: inline-block;
            padding: 12px 24px;
            background: #ff5722;
            color: #fff;
            text-decoration: none;
            font-weight: bold;
            border-radius: 8px;
            transition: background 0.3s ease;
        }
        .logout-btn:hover {
            background: #e64a19;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Teacher Dashboard</h1>
        <h2>Welcome ${username}</h2>
        <a class="logout-btn" href="${pageContext.request.contextPath}/teacher/logout?username=${username}">Logout</a>
    </div>
</body>
</html>