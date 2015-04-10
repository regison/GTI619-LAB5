<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

<title>Bienvenue!</title>
</head>

<body class="homepage">
	<div>
  		<h1>Connexion</h1>
     	<p>Veuillez vous identifier pour vous connecter au sysème.</p>
    </div>
    
	<form method="post" action="ExecuteSecondLogin.do">
                <fieldset>
                    <legend>Informations de l'utilisateur</legend>
                        
                    <p>Entrez les caractères aux numéros suivants : </p>
                    <p><%
                    	int[] in = (int[]) session.getAttribute("indexes");
                    	for(int i=0;i<in.length;i++){
                    		out.print(":" + in[i]);
                    	}
                    %>
                    </p>
                    <input type="password" id="password" name="password" value="" size="25" maxlength="25" />
                    <br />
                </fieldset>
                <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
                <input type="submit" value="Valider"  />
                
     </form>
</body>

</html>
