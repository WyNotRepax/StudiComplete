<%-- 
    Document   : billboard.jsp
    Created on : 28.05.2020, 10:31:27
    Author     : benno
--%>

<%@page import="de.hsos.vs.BillBoardServlet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Billboard Summer term 2019 edition</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <script type="text/javascript" src="billboard.js"></script>
        <script>
            setContext("<%out.print(BillBoardServlet.CONTEXT);%>");
            setInstantParam("<%out.print(BillBoardServlet.INSTANT_PARAM);%>");
            setGetMethod(1); /* html einstellen */
        </script>
    </head>
    <body style="font-family:arial;">
        <h1>Plakatwand</h1> 
        <h3>Neues Plakat einstellen:</h3>
        <input type="text" size="100" maxlength="100" id="contents">
        <button onClick="postHttpRequest(globContext)">Post</button>
        <h3>Plakate:</h3>
        <div id="posters">
        </div>
        <br>
        <div id="timestamp" style="font-family:arial;font-size:10pt;">
        </div>
        <script type="text/javascript">
            setGetMethod(1);
            getHttpRequest(globContext);
        </script>
    </body>
</html>

