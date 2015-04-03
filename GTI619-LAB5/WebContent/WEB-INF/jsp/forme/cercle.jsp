<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
        <meta charset="utf-8" />
        <title>Cercle</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/WEB-INF/css/style.css" />
    </head>
    <body>

	<table class=common>
		<tr>
			<td class=name>Bonjour <% out.print(request.getSession().getAttribute("Username"));%></td>

			<td><a href="ChangePassword.do">Changer mot de passe</a></td>
			<td width="100">Log out</td>
		</tr>
	
	</table>



        <pre>

                           *** ### ### ***
                       *##                 ##*
                   *##                         ##*
                *##                               ##*
              *##                                   ##*
            *##                                       ##*
           *##                                         ##*
          *##                                           ##*
         *##                                             ##*
         *##                                             ##*
         *##                                             ##*
         *##                                             ##*
         *##                                             ##*
          *##                                           ##*
           *##                                         ##*
            *##                                       ##*
              *##                                   ##*
                *#                                ##*
                   *##                         ##*
                       *##                 ##*
                           *** ### ### ***

        </pre>
	</body>
</html>
