<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang='en'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TechIT Home</title>
<meta charset="utf-8">
	<meta name="viewport" http-equiv="Content-Type" content="width=device-width,initial-scale=1" charset=ISO-8859-1>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- STYLESHEET -->
	<link rel="stylesheet" href=" <c:url value='/resources/mythemes/css/jquery-ui.css' />">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/homeTest.css' />">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/navbar.css' />">
	
	<!-- SCRIPTS -->
	<script type="text/javascript" src = "https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" ></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-3.1.1.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-ui.js' />"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function onLoadUp(){
			document.getElementById("rv").style.display = "block";
			document.getElementById("av").style.display = "none";
			document.getElementById("cv").style.display = "none";
		};
		
		function switchView(selectView){
			if (selectView == 1){
				document.getElementById("recent").className = "active";
				document.getElementById("active").className = "";
				document.getElementById("completed").className = "";
				currentView = 1;

				document.getElementById("rv").style.display = "block";
				document.getElementById("av").style.display = "none";
				document.getElementById("cv").style.display = "none";
			}
			else{
				if(selectView == 2){
					document.getElementById("recent").className = "";
					document.getElementById("active").className = "active";
					document.getElementById("completed").className = "";
					currentView = 2;
					
					document.getElementById("rv").style.display = "none";
					document.getElementById("av").style.display = "block";
					document.getElementById("cv").style.display = "none";
				}
				else{
					document.getElementById("recent").className = "";
					document.getElementById("active").className = "";
					document.getElementById("completed").className = "active";
					currentView = 3;
					
					document.getElementById("rv").style.display = "none";
					document.getElementById("av").style.display = "none";
					document.getElementById("cv").style.display = "block";
				}
			}
		};
	</script>


<body onload="onLoadUp()">
	<!-- Navbar -->
	<nav class='navbar navbar-default navbar-fixed-top'>
			<div class="container-fluid">
				<div class='navbar-header'>
					<a class='navbar-brand' href='#'>TechIT</a>
					<p class='navbar-text'>Signed in as Bobby Hill</p>
		
					<a href='Logout' type='button' id='logout-button'
						class='navbar-btn btn btn-default logout-button navbar-right'>Logout</a>
						
					<a class="navbar-btn btn btn-default setting-button" href = 'Settings'>My Settings</a>
						<a class="navbar-btn btn btn-default account-button" href = 'AcctManagement'>Account Manager</a>
				</div>
			</div>
		</nav>

	<!--  -->
	<div id="info">
	</div>
	
	<!-- Compose, Search, and Reset -->
	
	<div class="text-center col-lg-12" style="text-align: center;">
		<!-- Contains the Create Ticket button, and search capabilities -->
		<div class="container-fluid">
			<span class="pull-left">
				<a href="CreateTicket" class="btn btn-default">Compose</a>
			</span>
			
			<form action="Search" method="post">
				<div class ="col-lg-offset-2 ">
					<input name="search" id="search" class="form-control" placeholder="Search Ticket" /></div>
					<button class="navbar-btn btn btn-default" name="searchBtn" value="Search" type="submit">Search</button>
					<button class="navbar-btn btn btn-default" name="searchBtn" value="Reset" type="submit">Reset</button>
			</form>
		</div>
	</div>
		
	<!-- Ticket Filter -->
	<ul class="nav nav-tabs pull-center">
	  <li id="recent" class="active" onclick="switchView(1)"><a href="#">RECENT</a></li>
	  <li id="active" class="" onclick="switchView(2)"><a href="#">ACTIVE</a></li>
	  <li id="completed" class="" onclick="switchView(3)"><a href="#">COMPLETED</a></li>
	</ul>
	
	<!-- Tickets -->
	<div id="rv" class="container-fluid">
	<div id="base">
		<div class="ticket">
			<div>
				<b>Ticket #1 - Requestor:</b> Hank Hill <br>
				<b>SUBJECT: </b>I sell propane and propane accessories.<br>
			</div>
		</div>

		
	</div>
	</div>
	<!-- Active Tickets -->
	<div id="av" class="container-fluid">
	</div>
	
	<!-- Closed / Completed Tickets -->
	<div id="cv" class="container-fluid">
	
	</div>






</body>
</html>