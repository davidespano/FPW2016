<%-- 
    Document   : listaForm
    Created on : 20-lug-2016, 15.40.28
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
                <h1>Benvenuto ${user.getUsername()}</h1>
                
                <h2>Titolo</h2>
                <form action="modificaForm.html" method="post">
                    <input type="hidden" name="formId" value="${form.getId()}"/>
                    <label for="formTitle">
                        Titolo
                    </label>
                    <input id="formTitle" type="text" name="formTitle" value="${form.getTitle()}"/>
                    <button type="submit" name="cmd" value="changeTitle">
                        Modifica
                    </button>
                </form>
            </div>
            
            <jsp:include page="include/footer.jsp"/>
        </div>
    </body>
</html>

