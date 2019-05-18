<%@ page language="java" import="java.util.* , pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Page Personnelle</title>
</head>
<body>
<%Voyageur voyageur = (Voyageur) request.getAttribute("voyageur"); %>
<%=voyageur.getLogin() %>
<% 
Collection<Voyage> listVoyage = voyageur.getListVoyage();
%>
<%System.out.println("Voyaaageeeee"+listVoyage.size()); %>
<% 
for (Voyage voyage : listVoyage) {
	Vols vols = voyage.getVols();
	Logement logement = voyage.getLogement();%>
	<%=vols.getVolAller().getOrigine() %> <br>
	<%=vols.getVolAller().getDestination() %> <br>
	<%=logement.getNom() %> <br>
<%} %>
<li id="menu-item-19" class="menu-item menu-item-type-custom menu-item-object-custom current-menu-item current_page_item menu-item-home menu-item-17"><a href="questionnairebis.jsp" aria-current="page">Nouvelle recherche</a></li>
</body>
</html>