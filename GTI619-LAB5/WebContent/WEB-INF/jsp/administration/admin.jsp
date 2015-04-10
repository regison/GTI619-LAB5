<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="communication.DataMapping.DataProvider,communication.DataObjects.*,database.mysql.Mysql"%>
<% 
	DataProvider dp = new DataProvider();
	Objects.PasswordLoginPolitic pwp = dp.getPasswordLoginPolitic();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

<title>Administration</title>
<%@include file="../style.jsp" %>

</head>

<body class="homepage">
	<%@include file="../tableheader.jsp" %>
	<a href="GestionUtilisateur.do">Gestion Utilisateurs</a><br/>
	<a href="Logs.do">Voir les 10 Logs les plus récent</a><br/>
    <br />
	<a href="Cercle.do">Cercle</a><br/>
	<a href="Carre.do">Carre</a>
	 <br />
    <br />
	
	
	<form method="post" action="SaveConfiguration.do">
                <fieldset>
                    <legend>Protection contre Brute Force</legend>
    
                    <label for="tentativeMax">Nombre de tentatives maximales :</label>
                    <input type="text" id="tentativeMax" name="tentativeMax" value="<% out.print(pwp.maxTentative); %>" size="5" onkeyup="validate('tentativeMax',0)"/>
                    <br />
                    <br />
                    
                    <label for="delais">Delais exponentiel en cas d'atteinte du nombre maximal :</label>
                    <input type="text" id="delais" name="delais" value="<% out.print(pwp.delais); %>" size="5" onkeyup="validate('delais',0)"/>e degré
                     <br />
                     <br />
                    
                    <label>Bloquage acces suite deuxieme fois nombre maximal atteint :</label>
                    <input type="radio" name="bloquage" value="true" <% if(pwp.bloquage2tentatives) out.print("checked"); %> text=""/>Oui
                    <input type="radio" name="bloquage" value="false" <% if(!pwp.bloquage2tentatives) out.print("checked"); %>/>Non
                    <br />
                </fieldset>
                <input type="submit" name="submit" value="Valider"  />
                <br />
                <br />
                <fieldset>
                    <legend>Gestion mots de passe</legend>
    
                    <label>Changement de mot de passe apres :</label><br/>
                    <input type="checkbox" name="changementOublie" value="Oublie" <% if(pwp.changementOublie) out.print("checked"); %>/>Oubli<br/>
                    <input type="checkbox" name="changementDepassement" value="Depassement" <% if(pwp.changementDepassement) out.print("checked"); %>/>Depassement de la limite de tentatives
                    <br />
                    <br/>
                    
                    <label for="lmin">Longueur minimale du mot de passe :</label>
                    <input type="text" id="lmin" name="lmin" value="<% out.print(pwp.min); %>" size="5" onkeyup="validate('lmin',parseInt(document.getElementById('lmax').value))"/>
                    <br />
                    <label for="lmax">Longueur maximale du mot de passe :</label>
                    <input type="text" id="lmax" name="lmax" value="<% out.print(pwp.max); %>" size="5" onkeyup="validate('lmax',0)"/>
                    <br />
                    <br/>
                    
                    <label>Mots de passe doivent contenir :</label><br/>
                    <input type="checkbox" name="politiqueMin" value="Minuscules" <% if((pwp.complexity & 8) == 8) out.print("checked"); %>/>Minuscules <br />
                    <input type="checkbox" name="politiqueMaj" value="Majuscules" <% if((pwp.complexity & 1) == 1) out.print("checked"); %>/>Majuscules <br />
                    <input type="checkbox" name="politiqueCarac" value="Speciaux" <% if((pwp.complexity & 2) == 2) out.print("checked"); %>/>Caracteres Speciaux <br />
                    <input type="checkbox" name="politiqueChiffres" value="Chiffre" <% if((pwp.complexity & 4) == 4) out.print("checked"); %>/>Chiffres <br />
                    <br />
                    <!--  <label>Forcer un changement de mot de passe à la prochaine connexion?</label><br/>
                    <input type="checkbox" name="changePassConnect" value="changePassConnect" />Majuscules <br />
                    <br />
                    <label>Forcer un changement de mot de passe au prochain accès à une page?</label><br/>
                    <input type="checkbox" name="changePassPage" value="changePassPage" />Majuscules <br />
                    <br />-->
                    <label>Impossibilite d'utiliser un mot de passe parmis les </label>
                    <input type="text" id="ancien" name="ancien" value="<% out.print(pwp.lastPasswords); %>" onkeyup="validate('ancien',20)"/>derniers (20 maximum)<br />
                    <br />
                </fieldset>
                <% if(request.getAttribute("message")!=null){ out.print(request.getAttribute("message") + "<br />");} %>
                <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
                <input type="submit" name="submit" value="Valider"  />
     </form>
</body>

<script>
function validate(id,max){
	var input = document.getElementById(id);
	if(input.value.match(/[^0-9]+/g) || (max>0 && parseInt(input.value)>max)){
		input.value = input.value.substring(0,input.value.length-1);
	}
}
</script>

</html>
