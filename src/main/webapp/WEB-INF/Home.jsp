<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang='en'>
<head>
	<title>TechIT Home</title>
  	<meta charset="utf-8">
	<meta name="viewport" http-equiv="Content-Type" content="width=device-width,initial-scale=1" charset=ISO-8859-1>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- STYLESHEET -->
	<link rel="stylesheet" href=" <c:url value='/resources/mythemes/css/jquery-ui.css' />">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/home.css' />">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/navbar.css' />">
	
	<!-- SCRIPTS -->
	<script type="text/javascript" src = "https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" ></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-3.1.1.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-ui.js' />"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$( function() {
		
	    $( "#accordion" ).accordion({
	        collapsible: true,
	        heightStyle: "content"
	    });
	    $( "#accordion_2" ).accordion({
	        collapsible: true,
	        heightStyle: "content"
	    });
	    $( "#accordion_3" ).accordion({
	        collapsible: true,
	        heightStyle: "content"
	    });
	  } );
	
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
	
	function validateForm(){
		var input = document.forms["rejectForm"]["rejectInput"].value;
		if(input == '' || input == null){
			alert("Please input a reason for the decline.");
			event.preventDefault();
		}
	}
	
	function cleanInput(type, id){
		if(type == "reject"){
			if(document.forms["rejectForm"]["rejectInput"].value != '' && document.forms["rejectForm"]["rejectInput"].value != null){
				document.forms["rejectForm"]["rejectInput"].value = '';
			}

			document.forms["rejectForm"]["cancelBt"].value = id;
		}
		else{

			document.forms["cancelForm"]["cancelBt"].value = id;
		}
		
	}
	</script>
	<style>
		form{
			display:inline;
		}
	</style>
</head>
<body onload="onLoadUp()">
	<nav class='navbar navbar-default'>
		<div class="container-fluid">
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
		</div>
	</nav>
	
		<!-- Cancel Modal Pop up -->
	<div class="modal fade" id="myModalCancel" role="dialog" >
		<div class="modal-dialog">
			<div class="modal-content">
			
				<!-- Modal header -->
				
				<div class="modal-header" id="cancelModalHeader">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Ticket Cancellation Confirmation</h4>
				</div>
				
				<!-- Modal Body Content -->
				
				<div class="modal-body" id="cancelModalBody">
					<p>Are you sure you want to cancel this ticket?</p>
				</div>
				
				<!-- Modal Footer & Button options -->
			
				<div class="modal-footer">
					<form class="Cancel" name="cancelForm" action="Cancel" method="post">
						<button type="button" class="btn btn-danger" data-dismiss="modal"> No </button>
						<button type="submit" class="btn btn-success" name="cancelBt" id="cancelBt" value="${item.id}"> Yes </button>
					</form>
					
				</div>
			
			</div>
		</div>
	</div>
	
	<!-- Reject Modal Pop up -->
	<div class="modal fade" id="myModalReject" role="dialog" >
		<div class="modal-dialog">
			<div class="modal-content">
			
				<!-- Modal header -->
				
				<div class="modal-header" id="rejectModalHeader">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Ticket Decline Confirmation</h4>
				</div>
				
				
				<form class="Cancel" name="rejectForm" onsubmit="validateForm();" action="Cancel" method="post">
					<!-- Modal Body Content -->
					<div class="modal-body" id="rejectModalBody">
						<p>Are you sure you want to decline this ticket?</p>
						<p>Please input the reason for decline: </p>
						<textarea class="form-control" rows="2" style="width:100%" name="rejectInput" id="rejectInput"></textarea>
					</div>
					
					<!-- Modal Footer & Button options -->
				
					<div class="modal-footer" id="rejectModalFooter">
							<button type="button" class="btn btn-danger" data-dismiss="modal"> No </button>
							<button type="submit" class="btn btn-success" name="cancelBt" id="cancelBt" value="${item.id}"> Yes </button>
						
						
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- ALERT MESSAGES SECTION -->
	<c:if test="${!empty fn:trim(errorMessage)}">
		<div class="alert alert-danger" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			${ errorMessage }
		</div>
	</c:if>
	<c:if test="${!empty fn:trim(successMessage)}">
		<div class="alert alert-success" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			${ successMessage }
		</div>
	</c:if>
	
	<!-- This section holds the tabs and ticket view for the users. -->
	
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
		
	
	
		<ul class="nav nav-tabs pull-center">
		  <li id="recent" class="active" onclick="switchView(1)"><a href="#">RECENT</a></li>
		  <li id="active" class="" onclick="switchView(2)"><a href="#">ACTIVE</a></li>
		  <li id="completed" class="" onclick="switchView(3)"><a href="#">COMPLETED</a></li>
		</ul>
		

		<!-- ------------------------------------------------ RECENT TICKETS SECTION ----------------------------------------------- -->
		
<div id="rv" class="contrainer">
		<div id="accordion">
			<c:forEach items ="${tickets}" var="item">
					<h3 ><span class="pull-left">${ item.user }: ${ item.details } </span> <span class="pull-right">STATUS: ${item.progress } </span>
					</h3>
					
					<div>
					<p> 
						<b>Requester:</b> ${item.user }	<br>
						<b>Phone:</b> ${item.phone }<br>
						<b>Email:</b> ${item.email }<br>
						<b>Date Commissioned:</b> ${item.startDate }<br>
						<b>Location:</b> ${item.ticketLocation }<br>
						<b>Details:</b> ${item.details }	 <br><br>
						
						<c:choose>
							<c:when test="${item.numOfTechnician > 0}">
							<b>Technicians: </b>
								<c:forEach items="${item.technicians}" var="technicians">
									${technicians.firstName} ${technicians.lastName } | 
								</c:forEach>
							</c:when>
						</c:choose>
					</p>
					<hr>
					<div class="container">
						<c:if test="${item.progress ne 'OPEN' and item.numOfUpdates > 0}">
						<p><b>Updates</b><br></p>
							<table class="table table-striped table-bordered table-hover">
							 <thead class="thead-inverse">
							    <tr>
							      <th align="center">Update Date</th>
							      <th align="center">Details</th>
							    </tr>
							  </thead>
							  <tbody>
							  	<c:forEach items="${item.updates}" var="upds">
							  		<tr>
							  			<td>${upds.modifiedDate}</td>
							  			<td>${upds.updateDetails}</td>
							  		</tr>
							  	</c:forEach>
							  </tbody>
							</table>
						</c:if>
					</div>
							<!--  Cancel and confirmation -->
					<c:choose>
						<c:when test="${(sessionScope.user eq item.user and item.progress eq 'OPEN') or sessionScope.position == 0}">
															<!--  Cancel  -->
								<button type="button" class="btn btn-default" onClick="cleanInput('cancel', ${item.id})" data-toggle="modal" data-target="#myModalCancel">Cancel</button>
															<!--   Edit   -->              
								<a href= "EditTicket?id=${item.id}" class="navbar-btn btn btn-default">Edit</a>
						</c:when>
						
						<c:when test="${ sessionScope.position == 1 and item.progress eq 'OPEN' and sessionScope.user ne item.user }">
								<button type="button" class="btn btn-default" onClick="cleanInput('reject', ${item.id})" data-toggle="modal" data-target="#myModalReject">Reject</button>
						</c:when>
					</c:choose>
   
					<c:if test="${sessionScope.position <= 2 and item.progress ne 'OPEN'}">
						<a href="Update?id=${item.id}&prog=${item.progress}" type="button" class="navbar-btn btn btn-default">Update</a> 
					</c:if>
					<c:if test="${sessionScope.position <= 1 and item.progress ne 'COMPLETED' and item.progress ne 'CLOSED'}">
						<a href="AssignTechnician?id=${item.id}&prog=${item.progress}" type="button" class="navbar-btn btn btn-default" >Assign Technician</a> 
					</c:if>
					</div>
			</c:forEach>
		</div>
		</div>
		
		<!-- ------------------------------------------------- ACTIVE TICKETS SECTION ----------------------------------------------- -->
		
		<div id="av">
		<div id="accordion_2">
			<c:forEach items ="${tickets}" var="item">
					<c:choose>
						<c:when test = "${item.progress ne 'COMPLETED' and item.progress ne 'CLOSED' }">
							<h3 class="col-span"><span class="pull-left">${ item.user }: ${ item.details } </span> <span class="pull-right">STATUS: ${item.progress }</span></h3>
							<div>
								<p> 
									<b>Requester:</b> ${item.user }	<br>
									<b>Phone:</b> ${item.phone }<br>
									<b>Email:</b> ${item.email }<br>
									<b>Date Commissioned:</b> ${item.startDate }<br>
									<b>Location:</b> ${item.ticketLocation }<br>
									<b>Details:</b> ${item.details }	 <br><br>	
									
									<c:choose>
										<c:when test="${item.numOfTechnician > 0}">
										<b>Technicians: </b>
											<c:forEach items="${item.technicians}" var="technicians">
												${technicians.firstName} ${technicians.lastName } | 
											</c:forEach>
										</c:when>
									</c:choose>
								</p>
								<hr>
					<div class="container">
						<c:if test="${item.progress ne 'OPEN' and item.numOfUpdates > 0}">
							<p><b>Updates</b><br></p>
							<table class="table table-striped table-bordered table-hover">
							 <thead class="thead-inverse">
							    <tr>
							      <th align="center">Update Date</th>
							      <th align="center">Details</th>
							    </tr>
							  </thead>
							  <tbody>
							  	<c:forEach items="${item.updates}" var="upds">
							  		<tr>
							  			<td>${upds.modifiedDate}</td>
							  			<td>${upds.updateDetails}</td>
							  		</tr>
							  	</c:forEach>
							  </tbody>
							</table>
						</c:if>
					</div>
					<c:choose>
						<c:when test="${(sessionScope.user eq item.user and item.progress eq 'OPEN') or sessionScope.position == 0}">
															<!--  Cancel  -->
								<button type="button" class="btn btn-default" onClick="cleanInput('cancel', ${item.id})" data-toggle="modal" data-target="#myModalCancel">Cancel</button>
															<!--   Edit   -->                                    
								<a href= "EditTicket?id=${item.id}" class="navbar-btn btn btn-default">Edit</a>
						</c:when>
						
						<c:when test="${ sessionScope.position == 1 and item.progress eq 'OPEN' and sessionScope.user ne item.user }">
								<button type="button" class="btn btn-default" onClick="cleanInput('reject', ${item.id})" data-toggle="modal" data-target="#myModalReject">Reject</button>
						</c:when>
					</c:choose>

					<c:if test="${sessionScope.position <= 2 and item.progress ne 'OPEN'}">
						<a href="Update$id=${item.id}" type="button" class="navbar-btn btn btn-default">Update</a> 
					</c:if>
					<c:if test="${sessionScope.position <= 1 and item.progress ne 'COMPLETED' and item.progress ne 'CLOSED'}">
							<a href="AssignTechnician?id=${item.id}&prog=${item.progress}" type="button" class="navbar-btn btn btn-default" >Assign Technician</a> 
					</c:if>
							</div>
						</c:when>
					</c:choose>
			</c:forEach>
		</div>
		</div>
		
		
		
		
		<!-- -------------------------------------- COMPLETED / CLOSED TICKETS SECTION ----------------------------------------------- -->
		
		
		
		
		<div id="cv">
		<div id="accordion_3">
			<c:forEach items ="${tickets}" var="item">
				<c:choose>
					<c:when test = "${item.progress eq 'COMPLETED' or item.progress eq 'CLOSED' }">
						<h3 ><span class="pull-left">${ item.user }: ${ item.details } </span> <span class="pull-right">STATUS: ${item.progress }</span></h3>
						<div>
							<p>
							<b>Requester:</b> ${item.user }	<br>
							<b>Phone:</b> ${item.phone }<br>
							<b>Email:</b> ${item.email }<br>
							<b>Date Commissioned:</b> ${item.startDate }<br>
							<b>Location:</b> ${item.ticketLocation }<br>
							<b>Details:</b> ${item.details }	 <br><br>
	
							
							<c:choose>
								<c:when test="${item.numOfTechnician > 0}">
								<b>Technicians: </b>
									<c:forEach items="${item.technicians}" var="technicians">
										${technicians.firstName} ${technicians.lastName } | 
									</c:forEach>
								</c:when>
							</c:choose>
							
							</p>
							<hr>
						<div class="container">
							<c:if test="${item.progress ne 'OPEN' and item.numOfUpdates > 0}">
								<p><b>Updates</b><br></p>
								<table class="table table-striped table-bordered table-hover">
								 <thead class="thead-inverse">
								    <tr>
								      <th align="center">Update Date</th>
								      <th align="center">Details</th>
								    </tr>
								  </thead>
								  <tbody>
								  	<c:forEach items="${item.updates}" var="upds">
								  		<tr>
								  			<td>${upds.modifiedDate}</td>
								  			<td>${upds.updateDetails}</td>
								  		</tr>
								  	</c:forEach>
								  </tbody>
								</table>
							</c:if>
						</div>
					
						<c:if test="${sessionScope.position == 0 }">
							<a href= "EditTicket?id=${item.id}" class="navbar-btn btn btn-default">Edit</a>
						</c:if>
						<c:if test="${sessionScope.position <= 1}" >
							<a href="Update$id=${item.id}" type="button" class="navbar-btn btn btn-default">Update</a> 
						</c:if>
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
		</div>
		</div>
		
	</div>
</body>
</html>