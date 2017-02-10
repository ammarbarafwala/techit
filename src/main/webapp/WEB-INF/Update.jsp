<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang='en'>
<head>
	<title>Update Ticket</title>
  	<meta charset="utf-8">
	<meta name="viewport" http-equiv="Content-Type" content="width=device-width,initial-scale=1" charset=ISO-8859-1>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- STYLESHEET -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href=" <c:url value='/resources/mythemes/css/jquery-ui.css' />">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/navbar.css' />">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/update.css' />">
	<!-- SCRIPTS -->
	<script type="text/javascript" src = "https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" ></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-3.1.1.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-ui.js' />"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	
	<nav class='navbar navbar-default'>
		<div class='navbar-header'>
			<a class='navbar-brand' href='#'>TechIT</a>
			<p class='navbar-text'>Signed in as ${sessionScope.firstname}
				${sessionScope.lastname }</p>

			<a href='Logout' type='button' id='logout-button'
				class='navbar-btn btn btn-default logout-button navbar-right'>Logout</a>
				
			<a class="navbar-btn btn btn-default setting-button" href = 'Settings'>My Settings</a>
			<c:if test="${sessionScope.position == 0 }">
				<a class="navbar-btn btn btn-default account-button" href = 'AcctManagement'>Account Manager</a>
			</c:if>	
		</div>
	</nav>
	
	
	
	<!-- ALERT MESSAGES SECTION -->
	<c:if test="${!empty fn:trim(errorMessage)}">
		<div class="alert alert-danger" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			${ errorMessage }
		</div>
	</c:if>
	
	<div id="ticketInfo" class="col-md-offset-1 col-md-10">
		<div id="info" class="col-md-5">
			<b>Requester:</b> ${ticket.user }	<br>
			<b>Phone:</b> ${ticket.phone }<br>
			<b>Email:</b> ${ticket.email }<br>
			<b>Date Submitted:</b> ${ticket.startDate }<br>
			<b>Location of Problem:</b> ${ticket.ticketLocation }<br>
			<b>Details:</b> ${ticket.details }	 <br><br>
		</div>
		<div id="updates" class="col-md-5 text-center pull-right">
			<p><b>Updates</b><br></p>
			<table class="table table-striped table-bordered table-hover">
			 <thead class="thead-inverse">
			    <tr>
			      <th align="center">Update Date</th>
			      <th align="center">Details</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach items="${ticket.updates}" var="upds">
			  		<tr>
			  			<td>${upds.modifiedDate}</td>
			  			<td>${upds.updateDetails}</td>
			  		</tr>
			  	</c:forEach>
			  </tbody>
			</table>
		</div>
	</div>
	
	<div id="updateform" class="col-md-offset-3 col-md-6">
		<form class="form-inline" action="Update" method="post">
			<div id="formdiv">
				<input type="hidden" id="ticket_id" name="ticket_id" value="${ticket_id}">
				<input type="hidden" id="oldProg" name="oldProg" value="${ticket_progress}">
				
				<div class="form-group">
					 <label for="newProg">Progress <font color="red">*</font> </label>
					 <select id="newProg" name ="newProg">
					 	<option value="${ticket_progress}">${ticket_progress}</option>
					 	<c:forEach items="${ticketProg}" var="tprog">
					 	  	<c:if test="${tprog ne ticket_progress }">
					 	  		 <option value="${tprog}">${tprog}</option>
					 	  	</c:if>
						</c:forEach>
						</select> 
				</div>
				
				<p>Update Description: <font color="red">*</font> </p>
				<div>
					<textarea class="form-control" rows="5" style="width:100%" name="updateMessage" id="updateMessage"></textarea>
				</div>

				<div id="btn" class="text-center">
					<button class="btn btn-outline btn-primary" name="Update" value="Update" type="submit">Submit</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>