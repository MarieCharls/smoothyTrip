<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.amadeus.*, java.text.SimpleDateFormat, java.util.Date,com.amadeus.resources.Location,com.amadeus.resources.HotelOffer,com.amadeus.shopping.HotelOffer.*, com.amadeus.referenceData.Locations, com.amadeus.exceptions.ResponseException"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
    Amadeus amadeus = Amadeus
            .builder("9fu39sHfnYgpnuoADIwAYhs4PZfO6iLq", "66cmOYECyA6mwb01")
            .build();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date checkIn = formatter.parse("2019-06-16");
	Date checkOut = formatter.parse("2019-06-20");
	HotelOffer[] offers=amadeus.shopping.hotelOffers.get(Params
		.with("cityCode", "LON")
		.and("adults","2")
		.and("checkInDate",checkIn)
		.and("checkOutDate",checkOut)
		.and("adults",Integer.parseInt("2")));
	Location[] location = amadeus.referenceData.locations.get(Params
			.with("keyword","London")
			.and("subType",Locations.CITY));
	
%>
<%=offers.length %> <br>
<%=location.length %>
<%-- <%= offers[1].getHotel().getName()%> --%>
</body>
</html>
