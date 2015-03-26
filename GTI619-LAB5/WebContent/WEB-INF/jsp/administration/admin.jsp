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
			<td><a href="ChangePassword.do">Changer mot de passe</a></td>
			<td><a href="GestionUtilisateur.do">Gestion Utilisateurs</a></td>
			<td width="100">Log out</td>
		</tr>
	
	</table>
	 <br />
    <br />
	<a href="Cercle.do">Cercle</a><br/>
	<a href="Carre.do">Carre</a>
	 <br />
    <br />
	
	
	<form method="post" action="SaveConfiguration.do">
                <fieldset>
                    <legend>Protection contre Brute Force</legend>
    
                    <label for="tentativeMax">Nombre de tentatives maximales :</label>
                    <input type="text" id="tentativeMax" name="tentativeMax" value="" size="5" />
                    <br />
                    <br />
                    
                    <label for="delais">Delais en cas d'atteinte du nombre maximal :</label>
                    <input type="text" id="delais" name="delais" value="" size="5" />secondes
                     <br />
                     <br />
                    
                    <label>Bloquage acces suite deuxieme fois nombre maximal atteint :</label>
                    <input type="radio" name="bloquage" value="Oui" text=""/>Oui
                    <input type="radio" name="bloquage" value="Non" />Non
                    <br />
                </fieldset>
                <input type="submit" value="Valider"  />
                <br />
                <br />
                <fieldset>
                    <legend>Gestion mots de passe</legend>
    
                    <label>Changement de mot de passe apres :</label><br/>
                    <input type="checkbox" name="changement" value="Oublie" />Oubli<br/>
                    <input type="checkbox" name="changement" value="Depassement" />Depassement de la limite de tentatives
                    <br />
                    <br/>
                    
                    <label for="lmin">Longueur minimale du mot de passe :</label>
                    <input type="text" id="lmin" name="lmin" value="" size="5"/>
                    <br />
                    <label for="lmax">Longueur maximale du mot de passe :</label>
                    <input type="text" id="lmax" name="lmax" value="" size="5"/>
                    <br />
                    <br/>
                    
                    <label>Mots de passe doivent contenir :</label><br/>
                    <input type="checkbox" name="politique" value="Minuscules" />Minuscules <br />
                    <input type="checkbox" name="politique" value="Majuscules" />Majuscules <br />
                    <input type="checkbox" name="politique" value="Speciaux" />Caracteres Speciaux <br />
                    <input type="checkbox" name="politique" value="Chiffre" />Chiffres <br />
                    <br />
                    <label>Forcer un changement de mot de passe à la prochaine connexion?</label><br/>
                    <input type="checkbox" name="changePassConnect" value="changePassConnect" />Majuscules <br />
                    <br />
                    <label>Forcer un changement de mot de passe au prochain accès à une page?</label><br/>
                    <input type="checkbox" name="changePassPage" value="changePassPage" />Majuscules <br />
                    <br />
                </fieldset>
                <% if(request.getAttribute("message")!=null){ out.print(request.getAttribute("message"));} %>
                <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
                <input type="submit" value="Valider"  />
     </form>
</body>

</html>
