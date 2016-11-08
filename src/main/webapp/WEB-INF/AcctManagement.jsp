<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang='en'>
<head>
<title>Account Management</title>
<meta charset="utf-8">
<meta name="viewport" http-equiv="Content-Type"
	content="width=device-width,initial-scale=1" charset=ISO-8859-1>
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
<script type="text/javascript">
	</script>

</head>
<div class="col-md-2" id="line-left">
	<ul class="nav nav-pills nav-stacked pull-right">
		<li class="active"><a href="#">Home</a></li>
		<li><a href="Add">Add</a></li>
		<li><a href="#"> ${sessionScope.emailId} </a></li>
		<li><a href="#"> ${sessionScope.distinguishedName} </a></li>
		<li><a href="#">Add</a></li>
	</ul>
	
	<style>
ul.tab {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	border: 1px solid #ccc;
	background-color: #f1f1f1;
}

/* Float the list items side by side */
ul.tab li {
	float: left;
}

/* Style the links inside the list items */
ul.tab li a {
	display: inline-block;
	color: black;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	transition: 0.3s;
	font-size: 17px;
}

/* Change background color of links on hover */
ul.tab li a:hover {
	background-color: #ddd;
}

/* Create an active/current tablink class */
ul.tab li a:focus, .active {
	background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
	display: none;
	padding: 6px 12px;
	border: 1px solid #ccc;
	border-top: none;
}
</style>
</div>
<body onload="onLoadUp()">
	<nav class='navbar navbar-default'>
		<div class='container-fluid'>
			<a class='navbar-brand' href='Home'>TechIT</a>
			<p class='navbar-text'>Signed in as ${sessionScope.firstname}
				${sessionScope.lastname }</p>

			<a href='Logout' type='button' id='logout-button'
				class='navbar-btn btn btn-default logout-button navbar-right'>Logout</a>

			<a class="navbar-btn btn btn-default account-button"
				href='AcctManagement'>Account Manager</a> <a
				class="navbar-btn btn btn-default navbar-right" href='Settings'>My
				Settings</a>
		</div>
	</nav>

	<!--  Side Navigation Bar not needed right now...
	-->

	<ul class="tab">
		<li><a href="javascript:void(0)" class="tablinks"
			onclick="adminaction(event, 'Search')">Search User</a></li>
		<li><a href="javascript:void(0)" class="tablinks"
			onclick="adminaction(event, 'New')">New User</a></li>
	</ul>

	<div id="Search" class="tabcontent">
		<h3>Search User</h3>
		<p>You Are Going To Search For a User</p>
	</div>

	<div id="New" class="tabcontent">
		<h3>New User</h3>
		<p>You are going to create a new user</p>
	</div>

	<script>
function adminaction(evt, action) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(action).style.display = "block";
    evt.currentTarget.className += " active";
}
</script>
</body>
</html>