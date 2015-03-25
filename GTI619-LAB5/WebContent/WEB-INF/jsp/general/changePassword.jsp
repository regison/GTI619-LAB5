<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

<title>Administration</title>
<link type="text/css" rel="stylesheet" href="../../style.css" / >

</head>

<body class="homepage">
	    <table class=common>
		<tr>
			<td class=name>Bonjour <% out.print(request.getSession().getAttribute("Username"));%></td>
			<td width="100">Log out</td>
		</tr>
	
	</table>
	 <br />
	 <br />
    <br />
	
	
	<form method="post" action="ChangePassword.do">

      		 <fieldset>
                 <legend>Informations de l'utilisateur</legend>
 
                 <label for="opassword">Ancien mot de passw </label>
                 <input type="password" id="opassword" name="opassword" value="" size="20" maxlength="20" />
                 <br />
                 
                 <label for="npassword">Nouveau mot de passe</label>
                 <input type="password" id="npassword" name="npassword" value="" size="20" maxlength="20" />
                 <br />
                 
                 <label for="cnpassword">Confirmer nouveau mot de passe</label>
                 <input type="password" id="cnpassword" name="cnpassword" value="" size="20" maxlength="20" />
                 <br />
                 
                 <% if(request.getAttribute("message")!=null){ out.print(request.getAttribute("message"));} %>
                 
             </fieldset>
             <input type="hidden" name="updatePassword" value="true">
             <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
             <input type="submit" value="Valider"  /><input type="button" value="Retour" onclick="window.location.href='<% out.print(session.getAttribute("from") + ".do"); %>'" />
     </form>
     <!--<form method="post" action="Carre.do"><input type="submit" value="Retour" /> </form>-->
</body>

</html>
