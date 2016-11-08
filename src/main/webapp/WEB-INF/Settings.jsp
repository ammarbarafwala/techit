<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang='en'>
<head>
<title>Account Management</title>
<meta charset="utf-8">
<meta name="viewport" http-equiv="Content-Type"
	content="width=device-width,initial-scale=1" charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<c:url value='/resources/mythemes/css/jquery-ui.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/mythemes/css/home.css' />">
<script type="text/javascript"
	src="<c:url value='/resources/scripts/jquery-3.1.1.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/scripts/jquery-ui.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/scripts/mask.js' />"></script>
<script type="text/javascript">
	jQuery(function($){
		   $("#phoneNumber").mask("(999) 999-9999");
		});
	jQuery('.phoneNumber').keyup(function () {  
	    this.value = this.value.replace(/[^0-9\.]/g,''); 
	});
	</script>
</head>
<body onload="onLoadUp()">
	<nav class='navbar navbar-default'>
		<div class='container-fluid'>
			<a class='navbar-brand' href='Home'>TechIT</a>
			<p class='navbar-text'>Signed in as ${sessionScope.firstname}
				${sessionScope.lastname }</p>

			<a href='Logout' type='button' id='logout-button'
				class='navbar-btn btn btn-default logout-button navbar-right'>Logout</a>

			<a class="navbar-btn btn btn-default account-button"
				href='AcctManagement'>Account Manager</a>
			<button class="navbar-btn btn btn-default navbar-right">My
				Settings</button>
		</div>
	</nav>

	<!--  Side Navigation Bar not needed right now...
	<div class="col-md-2" id="line-left">
		<ul class="nav nav-pills nav-stacked pull-right">
			<li class="active"><a href="#">Home</a></li>
			<li><a href="Add">Add</a></li>
			<li><a href="#"> ${sessionScope.emailId} </a></li>
			<li><a href="#"> ${sessionScope.distinguishedName} </a></li>
			<li><a href="#">Add</a></li>
		</ul>
	</div>
	-->
	<form action="Settings" method="post">
		<div>
			<div class="form-group col-xs-10 col-md-10">
				<label for="email">Email <font color="red">*</font></label> <input
					type="text" class="form-control" name="email" value="${email}">
			</div>
			<div class="form-group col-xs-10 col-md-10">
				<label for="phoneNumber">Phone Number <font color="red">*</font></label>
				<input type="text" class="form-control" id="phoneNumber"
					name="phoneNumber" value="${phoneNumber}">
			</div>
			<div class="form-group col-xs-10 col-md-10">
				<input type="submit" id="Save" name="Save" value="Save" />
			</div>
		</div>

	</form>




</body>
</html>