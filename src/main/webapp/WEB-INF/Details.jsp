<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang='en'>
<head>
<title>Details</title>
<meta charset="utf-8">
<meta name="viewport" http-equiv="Content-Type" content="width=device-width,initial-scale=1" charset=ISO-8859-1>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/jquery-ui.css' />">
<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/navbar.css' />">
<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/details.css' />">
<link href="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.12/css/dataTables.bootstrap.min.css" rel="stylesheet" />

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-3.1.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-ui.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/scripts/mask.js' />"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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

	<!-- Cancel Modal Pop up -->
	<div class="modal fade" id="myModalCancel" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal header -->

				<div class="modal-header" id="cancelModalHeader">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Ticket Cancellation
						Confirmation</h4>
				</div>

				<!-- Modal Body Content -->

				<div class="modal-body" id="cancelModalBody">
					<p>Are you sure you want to cancel this ticket?</p>
				</div>

				<!-- Modal Footer & Button options -->

				<div class="modal-footer">
					<form class="Cancel" name="cancelForm" action="Cancel"
						method="post">
						<button type="button" class="btn btn-danger" data-dismiss="modal">
							No</button>
						<button type="submit" class="btn btn-success" name="cancelBt"
							id="cancelBt" value="${ticket.id}">Yes</button>
					</form>

				</div>

			</div>
		</div>
	</div>

	<!-- Reject Modal Pop up -->
	<div class="modal fade" id="myModalReject" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal header -->

				<div class="modal-header" id="rejectModalHeader">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Ticket Decline
						Confirmation</h4>
				</div>


				<form class="Cancel" name="rejectForm" onsubmit="validateForm();"
					action="Cancel" method="post">
					<!-- Modal Body Content -->
					<div class="modal-body" id="rejectModalBody">
						<p>Are you sure you want to decline this ticket?</p>
						<p>Please input the reason for decline:</p>
						<textarea class="form-control" rows="2" style="width: 100%"
							name="rejectInput" id="rejectInput"></textarea>
					</div>

					<!-- Modal Footer & Button options -->

					<div class="modal-footer" id="rejectModalFooter">
						<button type="button" class="btn btn-danger" data-dismiss="modal">
							No</button>
						<button type="submit" class="btn btn-success" name="cancelBt"
							id="cancelBt" value="${ticket.id}">Yes</button>


					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- ALERT MESSAGES SECTION -->
	<c:if test="${!empty fn:trim(errorMessage)}">
		<div class="alert alert-danger" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			${ errorMessage }
		</div>
	</c:if>
	<c:if test="${!empty fn:trim(successMessage)}">
		<div class="alert alert-success" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			${ successMessage }
		</div>
	</c:if>

	<div class="container">
		<div id="ticketInfo" class="col-md-offset-1 col-md-10 text-center">
			<b>Requester:</b> ${ticket.user } <br> 
			<b>Phone:</b> ${ticket.phone }<br>
			<b>Email:</b> ${ticket.email }<br> 
			<b>Date Submitted:</b> ${ticket.startDate }<br> 
			<b>Location of Problem:</b> ${ticket.ticketLocation }<br> 
			<b>Details:</b> ${ticket.details }
			<br> <br>
	
	
			<c:choose>
				<c:when test="${ticket.numOfTechnician > 0}">
					<b>Technicians: </b>
					<c:forEach items="${ticket.technicians}" var="technicians">
						${technicians.firstName} ${technicians.lastName } | 
					</c:forEach>
				</c:when>
			</c:choose>
		</div>
		
		<div id="ticketUpdates" class="col-md-offset-1 col-md-10">
			<c:if test="${ticket.numOfUpdates > 0}">
				<p>
					<b>Updates</b><br>
				</p>
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
			</c:if>
		</div>
		<div class="col-md-offset-1 col-md-10 text-center">
			<c:choose>
				<c:when
					test="${(sessionScope.user eq ticket.user and ticket.progress eq 'OPEN') or sessionScope.position == 0}">
					<!--  Cancel  -->
					<button type="button" class="btn btn-default"
						onClick="cleanInput('cancel', ${ticket.id})" data-toggle="modal"
						data-target="#myModalCancel">Cancel</button>
					<!--   Edit   -->
					<a href="EditTicket?id=${ticket.id}"
						class="navbar-btn btn btn-default">Edit</a>
				</c:when>
	
				<c:when test="${ sessionScope.position == 1 and ticket.progress eq 'OPEN'  and sessionScope.user ne ticket.user}">
					<button type="button" class="btn btn-default"
						onClick="cleanInput('reject', ${ticket.id})" data-toggle="modal"
						data-target="#myModalReject">Decline</button>
				</c:when>
			</c:choose>
	
	
			<c:if test="${sessionScope.position <= 2}">
				<a href="Update?id=${ticket.id}" type="button"
					class="navbar-btn btn btn-default">Update</a>
			</c:if>
			<c:if test="${sessionScope.position <= 1 and ticket.progress ne 'COMPLETED' and ticket.progress ne 'CLOSED'}">
						<a href="AssignTechnician?id=${ticket.id}&prog=${ticket.progress}" type="button" class="navbar-btn btn btn-default" >Assign Technician</a> 
			</c:if>
		</div>
	</div>
</body>