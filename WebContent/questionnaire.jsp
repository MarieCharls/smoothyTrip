<%@ page language="java" import="java.util.* , pack.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Associer</title>
</head>
<body>
<form action="/Smoothy Trip/ServletOp" method="post">
	As-tu une idée précise de la destination ou désires-tu une suggestion adaptée à tes attentes ?  <br>
	<input type="radio" name="reponse1" value="1-insipireMoi" checked> <label> Insipire moi </label> <br>
	<input type="radio" name="reponse1" value="2-nimporteOu" checked> <label> N'importe-où </label> <br>
	<input type="radio" name="reponse1" value="3-Jesais" checked> <label> Je sais où aller ! </label> <br>
	Qu'est ce que tu aimes lorsque tu voyages ? <br>
	<input type="radio" name="reponse2" value="1-amoureux" checked> <label> En amoureux </label> <br>
	<input type="radio" name="reponse2" value="2-culinaire" checked> <label> Culinaire </label> <br>
	<input type="radio" name="reponse2" value="3-culture" checked> <label> Culture </label> <br>
	<input type="radio" name="reponse2" value="4-soleil" checked> <label> Soleil </label> <br>
	<input type="radio" name="reponse2" value="5-montagne" checked> <label> Montagne </label> <br>
	Dates fixes ou flexibles ? (entrer la date de départ) <br>
	<select name="response3-jour" size="1">
	<option value="0"> jour
	<option value="1"> 1
	<option value="2"> 2
	<option value="3"> 3
	<option value="4"> 4
	<option value="5"> 5
	<option value="6"> 6
	<option value="7"> 7
	<option value="8"> 8
	<option value="9"> 9
	<option value="10"> 10
	<option value="12"> 12
	<option value="13"> 13
	<option value="14"> 14
	<option value="15"> 15
	<option value="16"> 16
	<option value="17"> 17
	<option value="18"> 18
	<option value="19"> 19
	<option value="20"> 20
	<option value="21"> 21
	<option value="22"> 22
	<option value="23"> 23
	<option value="24"> 24
	<option value="25"> 25
	<option value="26"> 26
	<option value="27"> 27
	<option value="28"> 28
	<option value="29"> 29
	<option value="30"> 30
	<option value="31"> 31
	</select>
	<select name="response3-mois" size="1"> 
	<option value="0"> mois
	<option value="1"> Janvier
	<option value="2"> Février
	<option value="3"> Mars
	<option value="4"> Avril
	<option value="5"> Mai
	<option value="6"> Juin
	<option value="7"> Juillet
	<option value="8"> Août
	<option value="9"> September
	<option value="10"> Octobre
	<option value="11"> Novembre
	<option value="12"> Décembre
	</select>
	<select name="response3-annee">
	<option value="0"> année
	<option value="1"> 2019
	<option value="2"> 2020
	</select>
	Quel est le Nombre de personnes ? <br>
	<select name="response4" size="1">
	<option value="0"> Nombre de personnes
	<option value="1"> 1
	<option value="2"> 2
	<option value="3"> 3
	<option value="4"> 4
	<option value="5"> 5
	</select>
	Quelle est la durée du séjour ? <br>
	<select name="response5" size="1">
	<option value="0"> Nombre de jours
	<option value="1"> 1
	<option value="2"> 2
	<option value="3"> 3
	<option value="4"> 4
	<option value="5"> 5
	<option value="6"> 6
	<option value="7"> 7
	<option value="8"> 8
	<option value="9"> 9
	<option value="10"> 10
	<option value="12"> 12
	<option value="13"> 13
	<option value="14"> 14
	</select>
	Quel est ton budget / pers ? <br>
	<input type="radio" name="reponse6" value="1-200" checked> <label> Max 200€ </label> <br>
	<input type="radio" name="reponse6" value="2-300" checked> <label> Max 300€ </label> <br>
	<input type="radio" name="reponse6" value="3-500" checked> <label> Max 500€ </label> <br>
	<input type="radio" name="reponse6" value="4-500+" checked> <label> Plus de 500€ </label> <br>
	<input type="submit" value="Sauvegarder">
	<input type="hidden" name="op" value="questionnaire">
</form>
</body>
</html>