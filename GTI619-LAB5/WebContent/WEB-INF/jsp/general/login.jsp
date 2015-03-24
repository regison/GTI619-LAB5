<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

<title>Bienvenue!</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style.css" />
</head>

<body class="homepage">
	<div>
  		<h1>Connexion</h1>
     	<p>Veuillez vous identifier pour vous connecter au sysème.</p>
    </div>
    
	<form method="post" action="ExecuteLogin.do">
                <fieldset>
                    <legend>Informations de l'utilisateur</legend>
    
                    <label for="username">User name <span class="requis">*</span></label>
                    <input type="text" id="username" name="username" value="" size="60" maxlength="60" />
                    <br />
                    
                    <label for="password">Mot de passe <span class="requis">*</span></label>
                    <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
                    <br />
                </fieldset>
                <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
                <input type="submit" value="Valider"  />
                
     </form>
</body>

</html>
