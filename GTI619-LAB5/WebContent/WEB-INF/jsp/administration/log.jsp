<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="communication.DataMapping.DataProvider,communication.DataObjects.*,database.mysql.Mysql, java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logs</title>
<%@include file="../style.jsp" %>
</head>
<body>
	<%@include file="../tableheader.jsp" %>
	<h1>Voici les Logs parce que evidemment quand tu clicques sur un lien qui dit "Voir les logs" ben ca va montrer les logs</h1>
	<h1>10 derniers événements </h1>
		
	<table>
	<tr>
		<th>Evenement</th>
		<th>UserID</th>
		<th>Nom utilisateur</th>
		<th>Role</th>
		<th>Moment</th>
	</tr>
		
	
   	<% 
  		DataProvider dp = new DataProvider();
   		Objects.User u;
   		Objects.Role r;
   		ArrayList<Objects.Log> logs = dp.getLogs();
   	
   		
  		for(int i = logs.size() - 1; i >= logs.size() - 10; i--){
  			Objects.Log data = logs.get(i);
  			
  			u = dp.GetUser(data.userLogId);
  			if(u!=null){
  			out.println("<tr>");
  			out.println("<td>" + data.logName + "</td>");
  			out.println("<td>" + data.userLogId + "</td>");
  			r = dp .GetRole(u.roleId);
  			out.println("<td>" + u.name + "</td>");
  			out.println("<td>" + r.roleName + "</td>");
  			out.println("<td>" + data.logDate + "</td>");
  			out.println("</tr>");}
  		}
  	
  	%>
  	</table>
  	<br/> 
	<a href="/GTI619-LAB5/AdminPage.do">Retour</a>
</body>
</html>