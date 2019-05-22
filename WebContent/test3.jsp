<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.amadeus.*, com.amadeus.resources.Location, java.util.Date,com.amadeus.resources.*, com.amadeus.referenceData.Locations, com.amadeus.exceptions.ResponseException, pack.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/smoothy_trip/ServletOp" >
test
<%Voyage voy = (Voyage) request.getAttribute("voyage"); 
List<Activite> act = voy.getListeActivites();
for(Activite activite : act){ %>
	<%= activite.getName() %>
<% }%>

</form>
</body>
</html>
