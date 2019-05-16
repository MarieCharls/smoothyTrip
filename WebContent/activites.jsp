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
	<%Collection<Activite> activites = (Collection<Activite>) request.getAttribute("listeActivite");%>
	<%if (activites.isEmpty()){
		%> 
		Aucune activite disponible <br>
		<input type="submit" name="Validation" value="Recommencer la recherche">
	<%
	}else{ %>
		Activites disponibles :
	<% for (Activite activite : activites){ %>
		<input type="checkbox" name="idActivite" value="<%=activite.getId()%>" unchecked> <label><%=activite.getName()%>  Type d'activite : <%=activite.getType()%> Adresse de l'activite : <%=activite.getAddress()%> Contact de l'activite : <%=activite.getTel()%></label> <br>
	<%} %>
	
	<input type="submit" name="Validation" value="Valider">  
	<%} %>
	<input type="hidden" name="op" value="validerActivites">
	<input type="hidden" name="idVoyage" value="<%=request.getAttribute("idVoyage")%>">
	</form>
</body>
</html>