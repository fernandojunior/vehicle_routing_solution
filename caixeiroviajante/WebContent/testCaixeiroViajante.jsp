<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<script type="text/javascript">

	function adicionarCampoEndereco(endhtml){
		
		var html = "\<input type='text' size='50' name='adress' name='adress' value=''><br>";
		
		var enderecos = endhtml;
		
		alert(enderecos.innerHTML);
		alert(endhtml);
		
		
		enderecos.innerHTML +=html;
		
	}
	
	var input = 5;

	function mais(campo) {

		var valor = "input "+input+" - "+campo+" <input type='text' size='50' name='"+campo+"' value=''><br>";
		var nova = document.getElementById("enderecos");
		var novadiv = document.createElement("div");
		var nomediv = "div";
		novadiv.innerHTML = ""+input+" <input type='text' size='50' name='"+campo+"' value=''>";
		nova.appendChild(novadiv);

		input++;
	}

</script>

<body>

<h1> Endereços </h1>


<form action="ServletCaixeiroViajante" method="post">

<input type="hidden" readonly="readonly" name="type" value="minimum_route">

1 <input type="text" size="50" name="adress" value="Av. Assis Chateaubriand, Campina grande, PB"> <br>
		
2 <input type="text" size="50"name="adress" value="Rua Acre, Campina grande, PB"> <br>
		
3 <input type="text" size="50" name="adress" value="Rua Dionisio Marques de Almeida, campina grande, PB"> <br>
		
4 <input type="text" size="50" name="adress" value="Rua Espirito Santo, campina grande, PB"> <br>

<div id="enderecos">
</div>
<input type="button" value="adicionar input" onClick="mais('adress')">
<input type="submit" value="enviar endereços">
</form>


</body>
</html>