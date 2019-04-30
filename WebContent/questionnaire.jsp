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
	Où souhaites-tu aller ? <input type="text" name="destination"/> <br>
	D'où souhaites-tu partir ? <input type="text" name="origine"/> <br>
	Qu'est ce que tu aimes lorsque tu voyages ? <br>
	<input type="radio" name="response2" value="1-amoureux" checked> <label> En amoureux </label> <br>
	<input type="radio" name="response2" value="2-culinaire" checked> <label> Culinaire </label> <br>
	<input type="radio" name="response2" value="3-culture" checked> <label> Culture </label> <br>
	<input type="radio" name="response2" value="4-soleil" checked> <label> Soleil </label> <br>
	<input type="radio" name="response2" value="5-montagne" checked> <label> Montagne </label> <br>
	A quelle date voulez-vous partir ? <br>
	<input type="date" name="dateDebut">
	
	<br> A quelle date voulez-vous revenir? <br>
	<input type="date" name="dateFin">
	
	<br> Combien de personnes participent au voyage? 
	<br>
	<select name="response5" size="1">
	<option value="0"> Nombre de personnes
	<option value="1"> 1
	<option value="2"> 2
	<option value="3"> 3
	<option value="4"> 4
	<option value="5"> 5
	</select>
	<br> Quel est ton budget maximum par personnes ? <br>
	<input type="radio" name="response6" value="200" checked> <label> Max 200€ </label> <br>
	<input type="radio" name="response6" value="300" checked> <label> Max 300€ </label> <br>
	<input type="radio" name="response6" value="500" checked> <label> Max 500€ </label> <br>
	<input type="radio" name="response6" value="10000" checked> <label> Plus de 500€ </label> <br>
	<br> A quelle distance du centre-ville veux tu loger? <br>
	<select name="response7" size="1">
	<option value="0"> Nombre de KM
	<option value="1"> 1
	<option value="2"> 2
	<option value="5"> 5
	<option value="7"> 7
	<option value="10"> 10
	</select>
	<input type="submit" value="Sauvegarder">
	<input type="hidden" name="op" value="questionnaire">
</form>
</body>
</html>