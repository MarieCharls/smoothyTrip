<%@ page language="java" import="java.util.* , pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Questionnaire</title>
</head>
<body>
<form action="/smoothy_trip/ServletOp" method="post">
	<%Collection<Vols> vols = (Collection<Vols>) request.getAttribute("listeVol");
	if (vols==null){
		%> 
		Aucuns vols disponible avec votre budget <br>
		<input type="submit" name="Validation" value="Recommencer la recherche">
	<%
	}else{
	%><ul><% 
		for (Vols vol : vols){ 
		int i = 0;%>
		<li><input type="radio" name="idVol" value="<%= vol.getId() %>" checked>  Prix total: <%=String.valueOf(vol.getPrix())%>  <%=vol.getVolAller().getMonnaie() %></li>
		<ul>
			<li> Aller: <%=vol.getVolAller().getOrigine() %> ->  <%=vol.getVolAller().getDestination() %> Départ : <%=vol.getVolAller().getDateDepart().toString() %>  Arrivée : <%=vol.getVolAller().getDateArrivee().toString() %></li>
			<li> Retour: <%=vol.getVolRetour().getOrigine()%> -> <%=vol.getVolRetour().getDestination() %>  Départ : <%=vol.getVolRetour().getDateDepart().toString() %> Arrivée: <%=vol.getVolRetour().getDateArrivee().toString() %></li>	
		</ul>
	<% i++; } %>
	</ul>
	<input type="submit" name="Validation" value="Valider">  <%} %>
	<input type="hidden" name="op" value="validerVol">
	<input type="hidden" name="idVoyage" value="<%=request.getAttribute("idVoyage")%>">
	
</body>
</html>