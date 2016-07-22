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
                <h1>Benvenuto ${user.getName()}</h1>
                
                <h2>I miei form</h2>
                <ul>
                    <c:forEach items="${forms}" var="form">
                        <li>
                            <a href="editForm.html?formId=${form.getId()}">
                                ${form.getTitle()}
                            </a>
                        </li>
                        
                    </c:forEach>
                        <li>
                            <a href="formList.html?cmd=new">Nuovo form</a>
                        </li>
                </ul>
    
            </div>
            
            <jsp:include page="include/footer.jsp"/>
        </div>
    </body>
</html>

