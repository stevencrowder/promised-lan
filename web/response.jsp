<%-- 
    Document   : response
    Created on : Feb 2, 2017, 11:16:54 AM
    Author     : steven
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:useBean id="steamGamesBean" scope="session" class="org.promisedlan.steamgames" />
        <jsp:setProperty name="steamGamesBean" property="l_steam_id" />
        <h1>Owned Games:</h1>
        <p><jsp:getProperty name="steamGamesBean" property="l_game_name" /></p>
    </body>
</html>
