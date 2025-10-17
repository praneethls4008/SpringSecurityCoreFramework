<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Dashboard</title>
    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #89f7fe, #66a6ff);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .dashboard-container {
            background: #fff;
            padding: 40px 60px;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
            text-align: center;
            width: 400px;
        }
        h1 {
            color: #333;
            margin-bottom: 15px;
        }
        h2 {
            color: #555;
            font-weight: normal;
            margin-bottom: 30px;
        }
        .logout-btn {
            display: inline-block;
            padding: 12px 24px;
            background: #f56565;
            color: #fff;
            font-size: 15px;
            font-weight: bold;
            border: none;
            border-radius: 8px;
            text-decoration: none;
            transition: background 0.3s;
        }
        .logout-btn:hover {
            background: #c53030;
        }
    </style>
</head>
<body>
<div class="dashboard-container">
    <h1>Student Dashboard</h1>
    <h2>Welcome, ${username}</h2>
    <a href="${pageContext.request.contextPath}/student/logout?username=${username}" class="logout-btn">Logout</a>
</div>
</body>
</html>