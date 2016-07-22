<%-- 
    Document   : registration
    Created on : 22-lug-2016, 12.10.19
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
        <script type="text/javascript" src="js/jquery-3.1.0.min.js"></script>
        <script type="text/javascript" src="js/checkUsername.js"></script>
    </head>
    <body>
        <div class="page">
            
            <jsp:include page="include/header.jsp" />
            <jsp:include page="include/sidebar1.jsp"/> 
            <jsp:include page="include/sidebar2.jsp" />
            
            <div class="content">
            <h1>Nuovo utente</h1>

           

            <form method="post" action="loginServlet.html">
                <label for="username"> Nome utente: </label>
                <input class="largeInput" type="text" name="username" id="username" placeholder="davide">
                <p id="checkUsername" >Username Ok</p>
                <label for="password1">Password:</label>
                <input class="largeInput" type="password" name="password1" id="password1">
                <label for="password1">Ripeti la password</label>
                <input class="largeInput" type="password" name="password2" id="password2">
                <button class="large" type="submit" name="register" id="register">Registrati</button>
            </form>
            </div>
            
            <jsp:include page="include/footer.jsp"/>
        </div>
    </body>
</html>
