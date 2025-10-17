<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>Teacher Register Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #ff9966, #9933ff);
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 420px;
            margin: 100px auto;
            background: rgba(0, 0, 0, 0.7);
            padding: 40px;
            border-radius: 12px;
            color: #fff;
            box-shadow: 0px 6px 15px rgba(0, 0, 0, 0.4);
        }
        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #ffd966;
        }
        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #ffeaa7;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 12px;
            border: none;
            border-radius: 6px;
            outline: none;
        }
        .error {
            color: #ff4d4d;
            font-size: 13px;
            margin-top: -8px;
            margin-bottom: 10px;
            display: block;
        }
        .global-error {
            background: #ff4d4d;
            color: #fff;
            padding: 10px;
            text-align: center;
            border-radius: 6px;
            margin-bottom: 15px;
        }
        .submit-btn {
            width: 100%;
            padding: 12px;
            background: #ff5722;
            border: none;
            border-radius: 8px;
            color: #fff;
            font-weight: bold;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        .submit-btn:hover {
            background: #e64a19;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Teacher Register Account</h2>

    <!-- Global error message -->
    <c:if test="${not empty loginError}">
        <div class="global-error">${loginError}</div>
    </c:if>

    <form:form action="${pageContext.request.contextPath}/teacher/newaccount" method="POST" modelAttribute="teacherCreateRequestDTO">

        <div>
            <form:label path="username">Username</form:label>
            <form:input path="username" placeholder="Enter new username"/>
            <form:errors path="username" cssClass="error"/>
        </div>

        <div>
            <form:label path="password">Password</form:label>
            <form:password path="password" placeholder="Enter new password"/>
            <form:errors path="password" cssClass="error"/>
        </div>

        <div>
            <input type="submit" value="Register" class="submit-btn"/>
        </div>

    </form:form>
</div>
</body>
</html>