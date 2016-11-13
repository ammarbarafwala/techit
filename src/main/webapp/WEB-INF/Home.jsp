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
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/home.css' />">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
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
	</script>
	<style>
		form{
			display:inline;
		}
	</style>
</head>
<body onload="onLoadUp()">
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
	
	<div class="text-center col-lg-12" style="text-align: center">
		<ul class="nav nav-tabs pull-center">
		  <li id="recent" class="active" onclick="switchView(1)"><a href="#">RECENT</a></li>
		  <li id="active" class="" onclick="switchView(2)"><a href="#">ACTIVE</a></li>
		  <li id="completed" class="" onclick="switchView(3)"><a href="#">COMPLETED</a></li>
		</ul>
		
		
		<!-- Contains the Create Ticket button, and search capabilities -->
		<div class="container-fluid">
			<span class="pull-left">
				<a href="CreateTicket" class="navbar-btn btn btn-default">Compose</a>
			</span>
			
			<form action="Search" method="post">
				<div class ="col-lg-offset-2 ">
					<input name="search" id="search" class="form-control" placeholder="Search Ticket" /></div>
					<button class="navbar-btn btn btn-default" name="searchBtn" value="Search" type="submit">Search</button>
					<button class="navbar-btn btn btn-default" name="searchBtn" value="Reset" type="submit">Reset</button>
			</form>
		</div>
		

		<!-- ------------------------------------------------ RECENT TICKETS SECTION ----------------------------------------------- -->
		
		
		
		<div id="rv">
		<div id="accordion">
			<c:forEach items ="${tickets}" var="item">
					<h3 ><span class="pull-left">${ item.user }: ${ item.details } </span> <span class="pull-right">STATUS: ${item.progress } </span>
					</h3>
					
					<div>
					<p> Requester: ${item.user } <br>
						Phone: ${item.phone }    <br>
						Email: ${item.email }    <br>
						Date Commissioned: ${item.startDate } <br>
						<b>Details:</b> ${item.details }      <br>
						<b>Location:</b> ${item.ticketLocation }<br><br>	
					</p>
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
						<c:when test="${sessionScope.user eq item.user and item.progress eq 'OPEN'}">
															<!--  Cancel  -->
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal${item.id}">Cancel</button>
															<!--   Edit   -->                                    
								<a href= "EditTicket?id=${item.id}" class="navbar-btn btn btn-default">Edit</a>
						</c:when>
						
						<c:when test="${ sessionScope.position <= 1 and item.progress eq 'OPEN' and sessionScope.user ne item.user }">
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal${item.id}">Reject</button>
						</c:when>
					</c:choose>
   
					<c:if test="${sessionScope.position <= 2 and (item.progress eq 'IN PROGRESS' or item.progress eq 'ON HOLD')}">
						<a href="Update?id=${item.id}&prog=${item.progress}" type="button" class="navbar-btn btn btn-default">Update Progress</a> 
					</c:if>
					<c:if test="${sessionScope.position <= 1}">
						<a href="AssignTechnician?id=${item.id}&prog=${item.progress}" type="button" class="navbar-btn btn btn-default" >Assign Technician</a> 
					</c:if>
					
							<!-- Modal Pop up -->
							<div class="modal fade" id="myModal${item.id}" role="dialog" >
								<div class="modal-dialog">
									<div class="modal-content">
									
										<!-- Modal header -->
										
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<c:choose>
												<c:when test="${sessionScope.position > 1 and item.progress eq 'OPEN' }">
													<h4 class="modal-title" id="myModalLabel">Ticket Cancellation Confirmation</h4>
												</c:when>
												<c:when test="${sessionScope.position <= 1 and item.progress eq 'OPEN'}" >
													<h4 class="modal-title" id="myModalLabel">Ticket Rejection Confirmation</h4>
												</c:when>
											</c:choose>
										</div>
										
										<!-- Modal Body Content -->
										
										<div class="modal-body">
											<c:choose>
												<c:when test="${sessionScope.position > 1 }">
													<p>Are you sure you want to cancel this ticket?</p>
												</c:when>
												<c:otherwise>
													<p>Are you sure you want to reject this ticket?</p>
												</c:otherwise>
											</c:choose>
										</div>
										
										<!-- Modal Footer & Button options -->
									
										<div class="modal-footer">
											<form class="Cancel" action="Cancel" method="post">
												<button type="button" class="btn btn-danger" data-dismiss="modal"> No </button>
												<button type="submit" class="btn btn-success" name="cancelBt" value="${item.id}"> Yes </button>
											</form>
											
										</div>
									
									</div>
								</div>
							</div>

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
								<p> Requester: ${item.user } <br>
									Phone: ${item.phone }    <br>
									Email: ${item.email }    <br>
									Date Commissioned: ${item.startDate } <br>
									<b>Details:</b> ${item.details }      <br>
									<b>Location:</b> ${item.ticketLocation }<br><br>	
								</p>
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
						<c:when test="${sessionScope.user eq item.user and item.progress eq 'OPEN'}">
															<!--  Cancel  -->
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal2${item.id}">Cancel</button>
															<!--   Edit   -->                                    
								<a href= "EditTicket?id=${item.id}" class="navbar-btn btn btn-default">Edit</a>
						</c:when>
						
						<c:when test="${ sessionScope.position <= 1 and item.progress eq 'OPEN' and sessionScope.user ne item.user }">
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal2${item.id}">Reject</button>
						</c:when>
					</c:choose>

					<c:if test="${sessionScope.position <= 2 and (item.progress eq 'IN PROGRESS' or item.progress eq 'ON HOLD')}">
						<a href="Update$id=${item.id}" type="button" class="navbar-btn btn btn-default">Update Progress</a> 
					</c:if>
					<c:if test="${sessionScope.position <= 1}">
							<a href="AssignTechnician?id=${item.id}&prog=${item.progress}" type="button" class="navbar-btn btn btn-default" >Assign Technician</a> 
					</c:if>
					
							<!-- Modal Pop up -->
							<div class="modal fade" id="myModal2${item.id}" role="dialog" >
								<div class="modal-dialog">
									<div class="modal-content">
									
										<!-- Modal header -->
										
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<c:choose>
												<c:when test="${sessionScope.position > 1 and item.progress eq 'OPEN' }">
													<h4 class="modal-title" id="myModalLabel">Ticket Cancellation Confirmation</h4>
												</c:when>
												<c:when test="${sessionScope.position <= 1 and item.progress eq 'OPEN'}" >
													<h4 class="modal-title" id="myModalLabel">Ticket Rejection Confirmation</h4>
												</c:when>
											</c:choose>
										</div>
										
										<!-- Modal Body Content -->
										
										<div class="modal-body">
											<c:choose>
												<c:when test="${sessionScope.position > 1 }">
													<p>Are you sure you want to cancel this ticket?</p>
												</c:when>
												<c:otherwise>
													<p>Are you sure you want to reject this ticket?</p>
												</c:otherwise>
											</c:choose>
										</div>
										
										<!-- Modal Footer & Button options -->
									
										<div class="modal-footer">
											<form class="Cancel" action="Cancel" method="post">
												<button type="button" class="btn btn-danger" data-dismiss="modal"> No </button>
												<button type="submit" class="btn btn-success" name="cancelBt" value="${item.id}"> Yes </button>
											</form>
											
										</div>
									
									</div>
								</div>
							</div>
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
							Requester: ${item.user }	<br>
							Phone: ${item.phone }		<br>
							Email: ${item.email 		}<br>
							Date Commissioned: ${item.startDate }<br>
							<b>Details:</b> ${item.details }	 <br>
							<b>Location:</b> ${item.ticketLocation }<br><br>	
							</p>
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
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
		</div>
		</div>
		
	</div>
</body>
</html>