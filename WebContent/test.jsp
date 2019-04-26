<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.amadeus.*, com.amadeus.resources.Location, com.amadeus.referenceData.Locations, com.amadeus.exceptions.ResponseException"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
    Amadeus amadeus = Amadeus
            .builder("jvcgO6WcMxZkKmDQYrPQ9l0XG1LBkKPy", "OIJ03moU3renCAPs")
            .build();

    Location[] locations = amadeus.referenceData.locations.get(Params
      .with("keyword", "LON")
      .and("subType", Locations.ANY));
%>
<%= locations[1].getAddress() %>
</body>
</html>
