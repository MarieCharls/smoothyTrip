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
	<%Collection<Vol> vols = (Collection<Vol>) request.getAttribute("listeVol");
	if (vols==null){
		%> 
		Aucuns vols disponible avec votre budget <br>
		<input type="submit" name="Validation" value="Recommencer la recherche">
	<%
	}else{
	for (Vol vol : vols){ 
		int i =0;%>
		<input type="radio" name="idVol" value="<%=vol.getId()%>" checked> <label> Prix total: <%=vol.getPrix()%> <%=vol.getMonnaie()%>|  Date de départ: <%=vol.getDateDepart() %> | Date de retour :<%=vol.getDateDepart() %> </label> <br>
	<% i++; } %>
	<input type="submit" name="Validation" value="Valider">  <%} %>
	<input type="hidden" name="op" value="validerVol">
	<input type="hidden" name="idVoyage" value="<%=request.getAttribute("idVoyage")%>">
	
</body>
</html>