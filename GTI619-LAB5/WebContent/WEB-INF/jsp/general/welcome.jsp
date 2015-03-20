<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

<title>Bienvenue!</title>

</head>

<body class="homepage">
<div id="templatemo_wrapper">

	<div id="templatemo_middle">
    
    	<div id="intro">
        	<h2>Voici une première page...</h2>
            <p>et un premier paragraphe!</p>
        </div>
        
	</div>
	
    <div id="templatemo_main">
    		
			<h3>Le contenue principal</h3>
			<br />
			<br />
           
    </div> <!-- end of main -->
        
</div> <!-- end of wrapper -->

<form method="post" action="Login.do">
                <fieldset>
                    <legend>Informations de l'utilisateur</legend>
    
                    <label for="courriel">Courriel <span class="requis">*</span></label>
                    <input type="text" id="courriel" name="courriel" value="" size="60" maxlength="60" />
                    <br />
                    
                    <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                    <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                    <br />
                </fieldset>
                <input type="submit" value="Valider"  />
            </form>
</body>

</html>
