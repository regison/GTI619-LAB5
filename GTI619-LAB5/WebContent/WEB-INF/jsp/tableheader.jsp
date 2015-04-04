	<table class=common>
		<tr>
			<td class="name">Bonjour <% out.print(request.getSession().getAttribute("Username"));%></td>
			<td class="last" ><a href="Login.do">Log out</a></td>
		</tr>
	
	</table>
	 <br />