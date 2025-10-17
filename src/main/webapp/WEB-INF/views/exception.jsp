<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Something went wrong!</title>
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f85032, #e73827);
            color: #fff;
            margin: 0;
            padding: 0;
        }
        .error-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            text-align: center;
        }
        .error-box {
            background: rgba(255,255,255,0.1);
            border-radius: 15px;
            padding: 30px 40px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
        }
        h1 {
            font-size: 80px;
            margin: 0;
            color: #fff;
        }
        h2 {
            font-size: 26px;
            margin: 10px 0 20px;
        }
        p {
            font-size: 18px;
            margin: 8px 0;
        }
        .btn-home {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 25px;
            background: #fff;
            color: #e73827;
            text-decoration: none;
            font-weight: bold;
            border-radius: 25px;
            transition: 0.3s;
        }
        .btn-home:hover {
            background: #e73827;
            color: #fff;
        }
        .error-message {
            margin-top: 20px;
            background: rgba(0,0,0,0.3);
            padding: 15px;
            border-radius: 10px;
            font-family: monospace;
            font-size: 15px;
            color: #ffe0e0;
            white-space: pre-wrap;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-box">
            <h1>500</h1>
            <h2>Oops! Something went wrong</h2>
            <p>We encountered an unexpected error while processing your request.</p>
            
            <!-- Show custom error message passed from model -->
            <c:if test="${not empty errorMessage}">
                <div class="error-message">
                    ${errorMessage}
                </div>
            </c:if>

            <a href="${pageContext.request.contextPath}" class="btn-home">Go Back Home</a>
        </div>
    </div>
</body>
</html>
