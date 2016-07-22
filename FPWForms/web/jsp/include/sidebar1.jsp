<%-- 
    Document   : sidebar1
    Created on : 13-lug-2016, 15.25.32
    Author     : davide
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="sidebar1">
    <h2>Esempi di collegamento</h2>
    <ul>
        <li>
            <a href="loginServlet.html?cmd=logout" 
               title="Termina la sessione utente">
                Logout
            </a>
        </li>
        <li>
            <a href="formList.html" 
               title="Visualizza l'elenco dei form">
                Elenco dei form
            </a>
        </li>
        <li> 
            <a href="http://people.unica.it/davidespano/" target="_blank" 
               title="selezionalo per aprire la pagina su un nuovo tab">
                Home page del docente
            </a>
        </li>
        <li>
            <a href="prova-collegamento.html">
                Seconda pagina del sito
            </a>
        </li>
    </ul>
</nav>
