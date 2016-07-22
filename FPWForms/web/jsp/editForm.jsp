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

                <h2>Titolo del form</h2>
                <form action="editForm.html" method="post">
                    <input type="hidden" name="formId" value="${form.getId()}"
                           class="largeInput"/>
                    <label for="formTitle">
                        Titolo
                    </label>
                    <input id="formTitle" type="text" name="formTitle" 
                           class="largeInput" value="${form.getTitle()}"/>
                    
                    <button type="submit" name="cmd" value="saveForm" class="save">
                        <img src="img/floppy.png" alt="Salva modifiche domanda" 
                             width="24" height="24" />
                    </button>
                </form>

                <c:forEach items="${form.getQuestions()}" var="question">
                    <h3>Domanda ${question.getOrder()}</h3>
                    <form action="editForm.html" method="post">
                        <input type="hidden" name="formId" value="${form.getId()}"/>
                        <input type="hidden" name="questionId" value="${question.getId()}"/>

                        <label for="questionTitle${question.getId()}">
                            Domanda
                        </label>
                        <input id="questionTitle${question.getId()}" 
                               type="text" name="questionTitle" 
                               value="${question.getTitle()}"
                               class="largeInput"/>

                        <label for="questionOrder${question.getId()}">
                            Posizione nel questionario
                        </label>
                        <input id="questionOrder${question.getId()}" 
                               type="number" name="questionOrder" 
                               value="${question.getOrder()}"
                               class="largeInput"/>

                        <label for="questionType${question.getType()}">
                            Tipologia di domanda
                        </label>
                        <select id="questionType${question.getId()}" 
                                name="questionType" class="largeInput">
                            <option value="1" <c:if test="${question.getType()== 1}">selected=""</c:if> >
                                    Risposta aperta testuale
                                </option>
                                <option value="2" <c:if test="${question.getType()== 2}">selected=""</c:if> >
                                    Risposta aperta numerica
                                </option>
                                <option value="3" <c:if test="${question.getType()== 3}">selected=""</c:if> >
                                    Scelta singola
                                </option>
                                <option value="4" <c:if test="${question.getType()== 4}">selected=""</c:if> >
                                    Scelta multipla
                                </option>

                            </select>

                            <button type="submit" name="cmd" class="save"
                                    value="saveQuestion">
                                <img src="img/floppy.png" alt="Salva modifiche domanda" 
                                     width="24" height="24" />
                            </button>
                        </form>

                        <h4>Opzioni</h4>  
                        <form action="editForm.html" method="post">
                        <c:set var="i" value="${1}"/>
                        <c:forEach items="${question.getOptions()}" var="option">

                            <input type="hidden" name="formId" value="${form.getId()}"/>
                            <input type="hidden" name="questionId" value="${question.getId()}"/>
                            <input type="hidden" name="optionId" value="${option.getId()}"/>
                            <label for="optionValue${option.getId()}">
                                Opzione ${i}
                            </label>
                            <input id="optionValue${option.getId()}" 
                                   type="text" name="optionValue" 
                                   value="${option.getValue()}"
                                   class="largeInput"/>   
                            <c:set var="i" value="${i + 1}"/>
                            <button type="submit" name="cmd" class="save"
                                    value="saveOption">
                                <img src="img/floppy.png" alt="Salva modifiche opzione" 
                                     width="24" height="24" />
                            </button>
                        </c:forEach>                   
                    </form>
                    <form action="editForm.html" method="post">
                        <input type="hidden" name="formId" value="${form.getId()}"/>
                        <input type="hidden" name="questionId" value="${question.getId()}"/>
                        <input type="hidden" name="optionId" value="${option.getId()}"/>
                        <label for="optionValueNew">
                            Nuova opzione
                        </label>
                        <input id="optionValueNew" 
                               type="text" name="optionValue" 
                               placeholder="Valore della nuova opzione"
                               class="largeInput"/> 
                        <button type="submit" name="cmd" class="save"
                                value="newOption">
                            <img src="img/floppy.png" alt="Aggiungi nuova opzione" 
                                 width="24" height="24" />
                        </button>
                    </form>
                </c:forEach>
                <h3>Nuova domanda</h3>
                <form action="editForm.html" method="post">
                    <input type="hidden" name="formId" value="${form.getId()}"/>
                    <button class="large" type="submit" name="cmd" value="addQuestion" id="addQuestion">Aggiungi</button>
                </form>

            </div>

            <jsp:include page="include/footer.jsp"/>
        </div>
    </body>
</html>

