<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang='en'>
<head>
	<title>TechIT Home</title>
  	<meta charset="utf-8">
	<meta name="viewport" http-equiv="Content-Type" content="width=device-width,initial-scale=1" charset=ISO-8859-1>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href=" <c:url value='/resources/mythemes/css/jquery-ui.css' />">
	<link rel="stylesheet" href="<c:url value='/resources/mythemes/css/home.css' />">
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-3.1.1.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/scripts/jquery-ui.js' />"></script>
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
</head>
<body onload="onLoadUp()">
	<nav class='navbar navbar-default'>
		<div class='navbar-header'>
			<a class='navbar-brand' href='#'>TechIT</a>
			<p class='navbar-text'>Signed in as ${sessionScope.firstname}
				${sessionScope.lastname }</p>

			<a href='Logout' type='button' id='logout-button'
				class='navbar-btn btn btn-default logout-button navbar-right'>Logout</a>
			<a class="navbar-btn btn btn-default account-button" href = 'AcctManagement'>Account Manager</a>	
			<a class="navbar-btn btn btn-default setting-button" href = 'Settings'>My Settings</a>
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
	<!-- This section holds the tabs and ticket view for the users. -->
	
	<div class="text-center col-lg-12" style="text-align: center">
		<ul class="nav nav-tabs pull-center">
		  <li id="recent" class="active" onclick="switchView(1)"><a href="#">RECENT</a></li>
		  <li id="active" class="" onclick="switchView(2)"><a href="#">ACTIVE</a></li>
		  <li id="completed" class="" onclick="switchView(3)"><a href="#">COMPLETED</a></li>
		</ul>
		
		
		<!-- Contains the Create Ticket button, and search capabilities -->
		<div class="container-fluid">
			<span class="pull-left"><button class="navbar-btn btn btn-default">Compose</button></span>
			<form>
				<div class ="col-lg-offset-2 "><input name="search" id="search" class="form-control" placeholder="Search Ticket" /></div>
				<button class="navbar-btn btn btn-default">Search</button></span>
			</form>
		</div>
		
		<div id="rv">
		<div id="accordion">
			<c:forEach items ="${tickets}" var="item">
					<h3 ><span class="pull-left">${ item.requestor }: ${ item.details } </span> <span class="pull-right">STATUS: ${item.progress } </span>
					</h3>
					
					<div>
					<p>
						Requester: ${item.requestor } </br>
						Phone: ${item.phone }</br>
						Email: ${item.email }</br>
						Date Commissioned: ${item.startDate }</br>
						<b>Details:</b> ${item.details }</br>
						<b>Location:</b> ${item.ticketLocation }</br>
					</p>
						<p>
							<!--  Cancel and confirmation -->
							
							<button type="button" class ="btn btn-default" data-toggle="modal" data-target="#cancel-button">Cancel</button>
							
							<div class="modal fade" id="cancel-button"	tabindex="-1" role="dialog" aria-labeledby="myModalLabel" aria-hidden="True">
								<div class="modal-dialog">
									<div class="modal-content">
									
										<!-- Modal header -->
										
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<h4 class="modal-title" id=myModalLabel>Ticket Cancellation Confirmation</h4>
										</div>
										
										<!-- Modal Body Content -->
										
										<div class="modal-body">
											<p>Are you sure you want to cancel this ticket?</p>
										</div>
										
										<!-- Modal Footer & Button options -->
									
										<div class="modal-footer">
											<button type="button" class="btn btn-danger" data-dismiss="modal"> No </button>
											
											<form class="Cancel" action="Cancel" method="post">
												<button class="navbar-btn btn btn-default" name ="cancelBt" value="${item.id}">Cancel</button>
											</form>
											
										</div>
									
									</div>
								</div>
							</div>

							
							<!--   Edit   -->                                                 -->
							<form class="Edit" action="Edit" method="post">
								<button class="navbar-btn btn btn-default">Edit</button>
							</form>
							
							<!--   Assign Technician   -->       
							<form class="Assign" action="Assign" method="post">
								<button class="navbar-btn btn btn-default">Assign</button>
							</form>
					</p>
					</div>
			</c:forEach>
		</div>
		</div>
			
		<div id="av">
		<div id="accordion_2">
			<c:forEach items ="${tickets}" var="item">
					<c:choose>
						<c:when test = "${item.progress ne 'Completed'}">
							<h3 class="col-span" style="background-color:#ffeead "><span class="pull-left" style="color:black">${ item.requestor }: ${ item.details } </span> <span class="pull-right"style="color:white">STATUS: ${item.progress }</span></h3>
							<div>
								<p>
									Requester: ${item.requestor }</br>
									Phone: ${item.phone }</br>
									Email: ${item.email }</br>
									Date Commissioned: ${item.startDate }</br></br>
									<b>Details:</b> ${item.details }</br>
									<b>Location:</b> ${item.ticketLocation }
								</p>
								<p>
									<button class="navbar-btn btn btn-default">Cancel</button>
									<button class="navbar-btn btn btn-default">Edit</button>
									<button class="navbar-btn btn btn-default">Assign</button>
								</p>
							</div>
						</c:when>
					</c:choose>
			</c:forEach>
		</div>
		</div>
			
		<div id="cv">
		<div id="accordion_3">
			<c:forEach items ="${tickets}" var="item">
				<c:choose>
					<c:when test = "${item.progress eq 'Completed'}">
						<h3 ><span class="pull-left">TICKET# ${item.id }</span> <span class="pull-right">STATUS: ${item.progress }</span></h3>
						<div>
							<p>
							Requester: ${item.requestor }</br>
							Phone: ${item.phone }</br>
							Email: ${item.email }</br>
							Date Commissioned: ${item.startDate }</br></br>
							<b>Details:</b> ${item.details }</br>
							<b>Location:</b> ${item.ticketLocation }
							</p>
							<p>
								<button class="navbar-btn btn btn-default">Cancel</button>
								<button class="navbar-btn btn btn-default">Edit</button>
								<button class="navbar-btn btn btn-default">Assign</button>
							</p>
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
		</div>
		</div>
		
	</div>
</body>
</html>