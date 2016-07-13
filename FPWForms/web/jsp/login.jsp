<%-- 
    Document   : login
    Created on : 11-lug-2016, 16.33.12
    Author     : davide
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>FPWForm - Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="keywords" content="fpw, esempio, fpwforms">
        <meta name="author" content="Davide Spano">
        <link type="text/css" rel="stylesheet" media="screen" 
              href="css/style.css" />
    </head>
    <body>
        <div class="page">
            
            <jsp:include page="include/header.jsp" />
            <jsp:include page="include/sidebar1.jsp"/> 
            <jsp:include page="include/sidebar2.jsp" />
            
            <div class="content">
            <h1>Login</h1>

            <c:if test="${ mostraErrori == true}">
                <p>
                    Username o password non corretti. 
                </p>
            </c:if>


            <form method="post" action="loginServlet.html">
                <label for="username"> Nome utente: </label>
                <input type="text" name="username" id="username" placeholder="davide">
                <label for="password">Password:</label>
                <input type="password" name="password" id="password">
                <button type="submit" name="login" id="login">Login</button>
            </form>
            </div>
            
            <jsp:include page="include/footer.jsp"/>
        </div>
    </body>
</html>
