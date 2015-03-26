<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="communication.DataMapping.DataProvider,communication.DataObjects.*,database.mysql.Mysql"%>

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
	<form method="post" action="GestionUtilisateur.do">
          <fieldset>
              <legend>Ajouter un utilisateur</legend>

              <label for="username">Nom de l'utilisateur <span class="requis">*</span></label>
              <input type="text" id="username" name="username" value="" size="60" maxlength="60" />
              <br />
              
              <label for="tpassword">Mot de passe temporaire</label>
              <input type="password" id="tpassword" name="tpassword" value="" size="20" maxlength="20" />
              <br />
              <label for="acces">Type d'utilisateur</label>
              <select id="acces" name="acces">
              	<option value="cercle" selected="selected" value="Cercle">Cercle</option>
              	<option value="carre">Carre</option>
              	<option value="admin">Administrateur</option>
              </select>
               <br />
              <label for="password">Entrez votre mot de passe pour confirmer l'action</label>
              <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
               <br />
              <% if(request.getAttribute("ajoutMessage")!=null){ out.print(request.getAttribute("ajoutMessage"));} %>
          </fieldset>
          <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
          <input type="submit" name="submit" value="Ajouter"  />
                
   </form>
           <br />
       <br />
       
     	<form method="post" action="GestionUtilisateur.do">
          <fieldset>
              <legend>Reactivation d'un utilisateur</legend>

              <label for="username">User name <span class="requis">*</span></label>
              <select name="user">
              	<% 
              		DataProvider dp = new DataProvider(Mysql.MYSQL_DATABASE_LOG619LAB5);
              		for(Objects.User data : dp.Users()){
              			out.println("<option value=\"" + data.idUser + "\">" + data.name + "</option>");
              		}
              	
              	%>
              </select>
              	 <br />
              <label for="password">Entrez votre mot de passe pour confirmer l'action</label>
              <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
              	 <br />
              <% if(request.getAttribute("suppMessage")!=null){ out.print(request.getAttribute("suppMessage"));} %>
              <% if(request.getAttribute("activateMessage")!=null){ out.print(request.getAttribute("activateMessage"));} %>
          </fieldset>
          <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
          <input type="submit" name="submit" value="Reactiver"  />
          <input type="submit" name="submit" value="Supprimer"  />
                
     </form>
	 <br />
    <br />
	
	<form method="post" action="GestionUtilisateur.do">
          <fieldset>
              <legend>Changer privilege</legend>

              <label for="username">User name <span class="requis">*</span></label>
              <select name="user">
              	<% 
              		for(Objects.User data : dp.Users()){
              			out.println("<option value=\"" + data.idUser + "\">" + data.name + "</option>");
              		}
              	
              	%>
              </select>
               <br />
              <label for="privilege">Nouveau Type</label>
              <select id="privilege" name="privilege">
              	<option value="cercle" selected="selected" value="Cercle">Cercle</option>
              	<option value="carre">Carre</option>
              	<option value="admin">Administrateur</option>
              </select>
              	 <br />
              <label for="password">Entrez votre mot de passe pour confirmer l'action</label>
              <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
              	 <br />
              <% if(request.getAttribute("privMessage")!=null){ out.print(request.getAttribute("privMessage"));} %>
          </fieldset>
          <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
          <input type="submit" name="submit" value="Valider"  />
                
     </form>
	 <br />
    <br />
	
</body>

</html>