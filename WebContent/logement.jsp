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
	<%Collection<Logement> logements = (Collection<Logement>) request.getAttribute("listeLogement");
	if (logements==null){
		%> 
		Aucun logement disponible avec le budget restant <br>
		<input type="submit" name="Validation" value="Recommencer la recherche">
	<%
	}else{
	for (Logement logement : logements){ 
		int i =0;%>
		
		<input type="radio" name="idLogement" value="<%=logement.getId()%>" checked> <label><%=logement.getNom()%>  Distance du centre ville : <%=logement.getRadius()%><%=logement.getRadiusUnit()%> Prix /nuit: <%=logement.getPrix()%> <%=logement.getMonnaire()%></label> <br>
	<% i++; } %>
	<input type="submit" name="Validation" value="Valider">  <%} %>
	<input type="hidden" name="op" value="validerLogement">
	<input type="hidden" name="idVoyage" value="<%=request.getAttribute("idVoyage")%>">
	
</body>
</html>