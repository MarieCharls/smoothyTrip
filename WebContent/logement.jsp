<%@ page language="java" import="java.util.* , pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Questionnaire</title>
</head>
<body>
<form action="/Smoothy trip/ServletOp" method="post">
	<%Collection<Logement> logements = (Collection<Logement>) request.getAttribute("listeLogement");
	for (Logement logement : logements){ 
		int i =0;%>
		
		<input type="radio" name="indexLogement" value="<%=i%>" checked> <label><%=logement.getNom()%>  Distance du centre ville : <%=logement.getRadius()%><%=logement.getRadiusUnit()%> Prix : <%=logement.getPrix()%> <%=logement.getMonnaire()%></label> <br>
	<% i++; } %>
	<input type="submit" value="Valider">
	<input type="hidden" name="op" value="validerLogement">
</body>
</html>