<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="communication.DataMapping.DataProvider,communication.DataObjects.Objects.*,database.mysql.Mysql"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

<title>Administration</title>
<%@include file="../style.jsp" %>
<% DataProvider dp = new DataProvider(true);%>
</head>

<body class="homepage">
	<%@include file="../tableheader.jsp" %>
    <br />
	<form method="post" action="GestionUtilisateur.do">
          <fieldset>
              <legend>Ajouter un utilisateur</legend>

              <label for="username">Nom de l'utilisateur <span class="requis">*</span></label>
              <input type="text" id="username" name="username" value="" size="60" maxlength="60" />
              <br />
              
              <label for="courriel">Courriel</label>
              <input type="text" id="courriel" name="courriel" value="" size="20" maxlength="20" />
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
              <legend>Oubli mot de passe</legend>

              <label for="username">User name <span class="requis">*</span></label>
              <select name="username">
              	<% 
              		dp = new DataProvider();
              		for(User data : dp.Users()){
              			out.println("<option value=\"" + data.name + "\">" + data.name + "</option>");
              		}
              	
              	%>
              </select>
              	 <br />
             	<label for="courriel">Courriel</label>
              <input type="text" id="courriel" name="courriel" value="" size="20" maxlength="20" />
              <br />
              <label for="password">Entrez votre mot de passe pour confirmer l'action</label>
              <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
              	 <br />
              <% if(request.getAttribute("oubMessage")!=null){ out.print(request.getAttribute("oubMessage"));} %>
          </fieldset>
          <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
          <input type="submit" name="submit" value="Modifier"  />
                
     </form>
	 <br />
    <br />
          
     	<form method="post" action="GestionUtilisateur.do">
          <fieldset>
              <legend>Reactivation et Suppression d'un utilisateur</legend>

              <label for="username">User name <span class="requis">*</span></label>
              <select name="username">
              	<% 
              		dp = new DataProvider();
              		for(User data : dp.Users()){
              			if(!dp.GetRole(data.roleId).roleName.equals(Role.AdministratorRoleName))
              				out.println("<option value=\"" + data.name + "\">" + data.name + "</option>");
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
              <select name="username">
              	<% 
              		for(User data : dp.Users()){
              			if(!dp.GetRole(data.roleId).roleName.equals(Role.AdministratorRoleName))
              				out.println("<option value=\"" + data.name + "\">" + data.name + "</option>");
              		}
              	
              	%>
              </select>
               <br />
              <label for="privilege">Nouveau Type</label>
              <select id="privilege" name="privilege">
              	<%    
              		for(Role data : dp.Roles()){
              			out.println("<option value=\"" + data.idRole + "\">" + data.roleName + "</option>");
              		}
              	%>
              </select>
              	 <br />
              <label for="password">Entrez votre mot de passe pour confirmer l'action</label>
              <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
              	 <br />
              <% if(request.getAttribute("privMessage")!=null){ out.print(request.getAttribute("privMessage"));} %>
          </fieldset>
          <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
          <input type="submit" name="submit" value="Valider"  />
          <input type="button" value="Retour" onclick="window.location.href='/GTI619-LAB5/AdminPage.do'" />      
     </form>
	 <br />
    <br />
    
       </form>
           <br />
       <br />
       
     	<form method="post" action="GestionUtilisateur.do">
          <fieldset>
              <legend>Activer et Désactiver authentification Forte</legend>

              <label for="username">User name <span class="requis">*</span></label>
              <select name="username">
              	<% 
              		dp = new DataProvider();
              		for(User data : dp.Users()){
              			if(!dp.GetRole(data.roleId).roleName.equals(Role.AdministratorRoleName))
              				out.println("<option value=\"" + data.name + "\">" + data.name + "</option>");
              		}
              	
              	%>
              </select>
              	 <br />
              <label for="password">Entrez votre mot de passe pour confirmer l'action</label>
              <input type="password" id="password" name="password" value="" size="25" maxlength="25" />
              	 <br />
              <% if(request.getAttribute("reauthMessage")!=null){ out.print(request.getAttribute("reauthMessage"));} %>
          </fieldset>
          <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
          <input type="submit" name="submit" value="Activer"  />
          <input type="submit" name="submit" value="D&eacute;sactiver"  />
                
     </form>
	 <br />
    <br />
	
</body>

</html>
