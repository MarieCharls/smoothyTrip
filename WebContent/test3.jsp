<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.amadeus.*, java.text.SimpleDateFormat, java.util.Date,com.amadeus.resources.FlightOffer,com.amadeus.resources.FlightOffer.*, com.amadeus.referenceData.Locations, com.amadeus.exceptions.ResponseException"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
	Amadeus amadeus = Amadeus
		.builder("6nRL5xnhTjIla3lB9DZDozVolhFxQWtH", "oKTXhjPY2rFKMoGs")
		.build();
	SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
	Date departDate = formatter.parse("2019-06-16");
	Date retourDate = formatter.parse("2019-06-20");
	String budget_string = String.valueOf(500);
	FlightOffer[] vols =amadeus.shopping.flightOffers.get(Params
			.with("origin","TLS")
			.and("destination","LON")
			.and("departureDate", "2019-06-16")
			.and("returnDate", "2019-06-20")
			.and("maxPrice", budget_string)
			.and("currency", "EUR")
			.and("adults", 2)
			);
%>

<%=vols.length %>  bonjouuuur <br>
<%-- <%= offers[1].getHotel().getName()%> --%>
</body>
</html>
