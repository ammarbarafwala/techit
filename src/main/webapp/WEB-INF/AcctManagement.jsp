<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang='en'>
<head>
	<title>Account Management</title>
  	<meta charset="utf-8">
	<meta name="viewport" http-equiv="Content-Type" content="width=device-width,initial-scale=1" charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/jquery-ui.css' />">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/home.css' />">
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-3.1.1.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-ui.js' />"></script>
	<script type="text/javascript">
	</script>
</head>
<body onload="onLoadUp()">
	<nav class='navbar navbar-default'>
		<div class='container-fluid'>
			<a class='navbar-brand' href='#'>TechIT</a>
			<p class='navbar-text'>Signed in as ${sessionScope.firstname}
				${sessionScope.lastname }</p>
			
			<a href='Logout' type='button' id='logout-button'
				class='navbar-btn btn btn-default logout-button navbar-right'>Logout</a>
				
			<a class="navbar-btn btn btn-default account-button" href = 'AcctManagement' >Account Manager</a>
			<button class="navbar-btn btn btn-default navbar-right">My Settings</button>
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
	<form action="AcctManagement" method="post">
		<div>
			<div class="page-header">
			First Name
				<input type="text" class="form-control" name="firstname"
					value="${sessionScope.firstname}" />

			</div>
			<div class="page-header">
			Last Name
				<input type="text" class="form-control" name="lastname"
					value="${sessionScope.lastname}" />

			</div>
		</div>
		<input type="submit" name="save" value="Save" />


	</form>
	

			

</body>
</html>