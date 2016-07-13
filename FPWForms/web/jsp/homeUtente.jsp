<%-- 
    Document   : homeUtente
    Created on : 11-lug-2016, 17.43.49
    Author     : davide
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html lang="it">
    <head>
        <title>Descrizione FPWForm</title>
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
                <article>
                    <h2 id="primo-titolo" title="Questo è un titolo">Benvenuto ${usr.getUsername()}</h2>
                    <p>
                        In questo sito potrai creare i tuoi questionari da sottporre 
                        a diversi utenti sul web. 
                    </p>
                    <h2 class="faq">Quali tipi di domande posso inserire?</h2>
                    <p>
                        Puoi inserire queste domande:
                    </p>
                    <ul>
                        <li>Domande a risposta breve (testo o numero)</li>
                        <li>Domanda a riposta lunga (testo)</li>
                        <li>Domanda a scala di risposta </li>
                    </ul>
                    <h2 class="faq">Come posso fare per creare un questionario?</h2>
                    <p>Per creare un questionario devi seguire questi passi:</p>
                    <ol>
                        <li>Registrati al sito</li>
                        <li>Utilizza la funzione Crea Questionario</li>
                        <li>Inserisci le domande</li>
                        <li>Invia il questionario al tuo campione</li>
                    </ol>
                    <p>Immagine con URL assoluta</p>
                    <img src="http://people.unica.it/davidespano/files/2015/09/photo2.png" 
                         alt="una foto del docente con lecca-lecca">
                    <p>
                        Immagine con URL relativa
                    </p>
                    <img src="img/class.png" 
                         alt="uno schema delle classi della libreria per i gesti">

                    <h2>Esempi di tag di formattazione</h2>
                    <ul>
                        <li>Tag per <strong>rafforzare un concetto</strong></li>
                        <li>Tag per il <b>grassetto</b></li>
                        <li>Tag per <em>enfasi</em></li>
                        <li>Tag per <i>corsivo</i></li>
                    </ul>



                    <h2>Piani di abbonamento</h2>
                    <table>
                        <tr>
                            <th>Nome del piano</th>
                            <th>Numero questionari</th>
                            <th>Prezzo</th>
                        </tr>
                        <tr>
                            <td>Light</td>
                            <td>15</td>
                            <td>3 euro al mese</td>
                        </tr>
                        <tr>
                            <td>No problems</td>
                            <td>Infiniti</td>
                            <td>90 euro al mese</td>
                        </tr>
                    </table>
                </article>
            </div>
            
            <jsp:include page="include/footer.jsp"/>        
        </div>
    </body>
</html>

