<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang='en'>
<head>
	<title>Assign Technician</title>
	<meta charset="utf-8">
	<meta name="viewport" http-equiv="Content-Type" content="width=device-width,initial-scale=1" charset=ISO-8859-1>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- STYLESHEET -->	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/home.css' />">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/css/dataTables.bootstrap.min.css" rel="stylesheet" />
	<!-- SCRIPTS -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		  $('#technicians').dataTable();
		});
	</script>
</head>

<body>
	<nav class='navbar navbar-default'>
		<div class='navbar-header'>
			<a class='navbar-brand' href='Home'>TechIT</a>
			<p class='navbar-text'>Signed in as ${sessionScope.firstname}
				${sessionScope.lastname }</p>

			<a href='Logout' type='button' id='logout-button'
				class='navbar-btn btn btn-default logout-button navbar-right'>Logout</a>

			<a class="navbar-btn btn btn-default setting-button" href='Settings'>My
				Settings</a>
			<c:if test="${sessionScope.position == 0 }">
				<a class="navbar-btn btn btn-default account-button"
					href='AcctManagement'>Account Manager</a>
			</c:if>
		</div>
	</nav>

	<!-- ALERT MESSAGEs SECTION -->
	<c:if test="${!empty fn:trim(errorMessage)}">
		<div class="alert alert-danger" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			${ errorMessage }
		</div>
	</c:if>

	<div class = "container">
		<form action="AssignTechnician" method="post">
			<input type="hidden" id="thisField" name="ticket_id" value="${ticket_id}">
			<input type="hidden" id="thisField" name="ticket_progress" value="${ticket_progress}">
			<table class="table table-striped table-bordered table-hover" id="technicians">
			
				<thead class="thead-inverse">
					<tr>
						<th>Select</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Phone Number</th>
						<th>Email</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items ="${techList}" var="tech">
						<tr>
							<c:choose>
								<c:when test="${empty techVerify[tech.username]}">
									<td><input type="checkbox" name="tech" value="${tech.username}"></td>
								</c:when>
								
								<c:otherwise>
									<td><input type="checkbox" name="tech" value="${tech.username}" checked="checked"></td>
								</c:otherwise>
							</c:choose>
							<td>${tech.firstName}</td>
							<td>${tech.lastName}</td>
							<td>${tech.phoneNumber}</td>
							<td>${tech.email}</td>
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
			<button class="btn btn-outline-primary" name="Submit" value="AssignTechnicianSubmit" type="submit">Submit</button>
			<a href="Home" type="button" class="btn btn-outline-danger">Cancel</a>
		</form>
	</div>

</body>
</html>