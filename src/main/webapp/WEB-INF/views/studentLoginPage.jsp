<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Student Login Page</title>
    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #74ebd5, #ACB6E5);
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .login-container {
            background: #fff;
            padding: 40px 50px;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
            width: 350px;
            text-align: center;
        }
        h2 {
            margin-bottom: 25px;
            color: #333;
        }
        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }
        label {
            display: block;
            font-weight: 600;
            margin-bottom: 6px;
            color: #444;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
            transition: border 0.3s;
        }
        input[type="text"]:focus, input[type="password"]:focus {
            border-color: #5a67d8;
            outline: none;
        }
        .error {
            color: #e53e3e;
            font-size: 13px;
            margin-top: 5px;
            display: block;
        }
        .global-error {
            background: #ffe6e6;
            color: #cc0000;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
        }
        .checkbox-group {
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 14px;
        }
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #5a67d8;
            color: #fff;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s;
        }
        input[type="submit"]:hover {
            background: #434190;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Student Login</h2>

    <!-- Global login error message -->
    <c:if test="${not empty loginError}">
        <div class="global-error">${loginError}</div>
    </c:if>

    <form:form action="${pageContext.request.contextPath}/student/auth" method="POST" modelAttribute="studentLoginRequestDTO">

        <div class="form-group">
            <form:label path="username">Username</form:label>
            <form:input path="username" placeholder="Enter username"/>
            <form:errors path="username" cssClass="error"/>
        </div>

        <div class="form-group">
            <form:label path="password">Password</form:label>
            <form:password path="password" placeholder="Enter password"/>
            <form:errors path="password" cssClass="error"/>
        </div>
        
        <div class="form-group checkbox-group">
            <form:checkbox path="rememberMe" label="Remember me"/>
            <form:errors path="rememberMe" cssClass="error"/>
        </div>

        <div class="form-group">
            <input type="submit" value="Login"/>
        </div>

    </form:form>
</div>
</body>
</html>
