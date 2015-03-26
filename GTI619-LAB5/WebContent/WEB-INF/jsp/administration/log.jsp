<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="communication.DataMapping.DataProvider,communication.DataObjects.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Accès refusé!</title>
</head>
<body>
	Voici les Logs parce que evidemment quand tu clicques sur un lien qui dit "Voir les logs" ben ca va montrer les logs
	
   	<% 
  		DataProvider dp = new DataProvider();
  		for(Objects.Log data : dp.getLogs()){
  			out.println(data.logName + "<br/>   ");
  		}
  	
  	%>
  	<br/> 
	<a href="AdminPage.do">Retour</a>
</body>
</html>