<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Student Register Page</title>
    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #FFDEE9, #B5FFFC);
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .register-container {
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
            border-color: #38b2ac;
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
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #38b2ac;
            color: #fff;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s;
        }
        input[type="submit"]:hover {
            background: #2c7a7b;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h2>Student Register Account</h2>

    <!-- Global register error message -->
    <c:if test="${not empty loginError}">
        <div class="global-error">${loginError}</div>
    </c:if>

    <form:form action="${pageContext.request.contextPath}/student/newaccount" method="POST" modelAttribute="studentCreateRequestDTO">

        <div class="form-group">
            <form:label path="username">Username</form:label>
            <form:input path="username" placeholder="Enter new username"/>
            <form:errors path="username" cssClass="error"/>
        </div>

        <div class="form-group">
            <form:label path="password">Password</form:label>
            <form:password path="password" placeholder="Enter new password"/>
            <form:errors path="password" cssClass="error"/>
        </div>

        <div class="form-group">
            <input type="submit" value="Register"/>
        </div>

    </form:form>
</div>
</body>
</html>
