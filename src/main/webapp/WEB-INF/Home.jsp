<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang='en'> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
<link rel="stylesheet" href="./home.css" type='text/css'>
<title>TechIT Home</title>
</head>
<body>

<nav class='navbar navbar-default'>
	<div class='container-fluid'>
		<div class='navbar-header'>
			<a class='navbar-brand' href='#'>TechIT</a>
			<p class='navbar-text'> Signed in as ${sessionScope.firstname} ${sessionScope.lastname }</p>
				<a href='Logout' type='button' class='btn btn-default logout-button navbar-btn pull-right'>Logout</a>
		</div>
	</div>
</nav>

</body>
</html>