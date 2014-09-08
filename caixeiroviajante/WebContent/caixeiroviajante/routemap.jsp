<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>Google Maps JavaScript API v3 Example: Directions
	Waypoints</title>
<link
	href="http://code.google.com/apis/maps/documentation/javascript/examples/default.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>

<!-- <script type="text/javascript" src="/riofrotas/javascript/scripts.js"></script> -->

<script type="text/javascript">



  function parseToWayPoints(points) {

    if (points == "")
      return "";	      
    else
      points = points.split(";;");
	
    var waypts = [];
	
      for ( var i = 0; i < points.length; i++) {
        waypts.push({
          location : points[i],
          stopover : true
        });
      }
	
      return waypts;
	
  }

  /**
   * Converte valor de comprimento, com unidade 'arg1', para unidade 'arg2'
   * 
   * @param value
   *            O valor de comprimento a ser convertido
   * @param arg1
   *            A unidade do valor de comprimento de entrada
   * @param arg2
   *            A unidade do valor de comprimento de saida
   * @returns O comprimento convertido
   */
  function converterComprimento(value, arg1, arg2) {

  	var tmp = parseFloat(value);

  	// convertendo de metros para quilometros
  	if (arg1 == 'm' && arg2 == 'km')
  		return (tmp / 1000);

  	// convertendo de quilometros para metros
  	if (arg1 == 'km' && arg2 == 'm')
  		return (tmp * 1000);

  	// erro
  	return tmp;

  }

   /**
    * Retorna a unidade de distancia conforme o parametro distance (na qual possui
    * o seguinte formato: ###,### un)
    * 
    * @param distance
    *            A distancia a ser retornada a unidade
    * @returns a unidade da distancia
    */
   function getDistanceUnit(distance) {
   	var tmp = distance.indexOf(' ', 0);
   	var unit = distance.slice(tmp + 1);
   	return unit;
   }

   /**
    * Retorna apenas o valor de distancia conforme o parametro distance (na qual
    * possui o seguinte formato: ###,### un)
    * 
    * @param distance
    *            A distancia a ser retornado o valor
    * @returns o valor da distancia
    */
   function getDistanceValue(distance) {
   	var tmp = distance.indexOf(' ', 0);
   	var tmpValue = distance.slice(0, tmp);
   	var value = tmpValue.replace(',', '.');
   	return value;
   }

  var directionDisplay;
  var directionsService = new google.maps.DirectionsService();
  var map;

  function initialize() {

	  directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);

	  var centerMap = new google.maps.LatLng(-7.230779, -35.887946); //CDRDP

      var myOptions = {
          zoom: 15,
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          center: centerMap
      };

      map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	  
      var rendererOptions = {
          draggable: true
      };

      directionsDisplay.setMap(map);
      
  	  calcRoute(false);
  	
  }

  /**
   * Metodo para calcular a distancia entre dois pontos: start -> end;
   * param adresses Enderecos entre start & and
   * @param tsp_on Ativa ou nao o algoritmo de caixeiro viajante que o google usa
   */
  function calcRoute(tsp_on) {
	   
	this.start = document.getElementById("start").value;
	this.end = document.getElementById("end").value;
	this.waypts = document.getElementById("waypts").value;
	
	this.waypts = parseToWayPoints(this.waypts);
	
	this.request = {
			origin : this.start,
			destination : this.end,
			waypoints : this.waypts,
			optimizeWaypoints : tsp_on,
			provideRouteAlternatives: false,
		    travelMode: google.maps.TravelMode.DRIVING				
			//travelMode : google.maps.DirectionsTravelMode.DRIVING
		};
	
	this.view = function (total){		
		alert(total);	
	};
	
	this.total = 0;
	
    this.directionsService.route(request, function(response, status) {
      if (status == google.maps.DirectionsStatus.OK) {
    	  
        directionsDisplay.setDirections(response);
        var route = response.routes[0];

        // For each route, calcule the total distance.
        for (var i = 0; i < route.legs.length; i++) {
          var routeSegment = i + 1;

          var distancia = route.legs[i].distance.text;
          var unidade = getDistanceUnit(distancia);
          var valor= getDistanceValue(distancia);
       
          this.total += converterComprimento(valor, unidade, 'm');

        }
        
//         view(total);
        
        
      }
    });
    
  }

   google.maps.event.addDomListener(window, 'load', initialize);

</script>

</head>

<body onload="initialize()">

	<!-- 
	 -->
	<div id="map_canvas" style="float: left; width: 70%; height: 100%;"></div>

	<%
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String waypts = request.getParameter("waypts");
	%>

	<form method="get">

		<input type="hidden" id="start" name="start" value="<%=start%>"> <br>
		<input type="hidden" id="end" name="end" value="<%=end%>"> <br>
		<input type="hidden" id="waypts" name="waypts" value="<%=waypts%>"> <br>

	</form>

	<script type="text/javascript">
			
// 		calcRoute(false);

     </script>


	<div id="directions_panel"
		style="margin: 20px; background-color: #FFEE77;">
		
<!-- 		<select name="Ativar o google TSP?"> -->
<!-- 		<option label="sim" value="sim" onclick='teste();'> -->
<!-- 		<option label="nao" value="nao" onclick='teste();'> -->
<!-- 		</select> -->
				
	</div>


</body>

</html>