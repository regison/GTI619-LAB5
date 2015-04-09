<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.List"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

<title>Administration</title>
<%@include file="../style.jsp" %>

</head>

<body class="homepage">
	<%@include file="../tableheader.jsp" %>
	
	
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
                 
                 <% if(request.getAttribute("message")!=null){ 
                	 	out.print(request.getAttribute("message"));
                	}
                 	List<String> errors = (List<String>) request.getAttribute("error");
	                 if(errors!=null){
	                	 for(String err : errors){
	                		 out.print("<p color=\"RED\">" + err + "</p>");
	                	 }
	                 }
                 %>
                 
             </fieldset>
             <input type="hidden" name="updatePassword" value="true">
             <input type="hidden" name="hidden" value="<% out.print(request.getAttribute("hidden"));%>">
             <input type="submit" value="Valider"  /><input type="button" value="Retour" onclick="window.location.href='<% out.print(session.getAttribute("from") + ".do"); %>'" />
     </form>
</body>

</html>
