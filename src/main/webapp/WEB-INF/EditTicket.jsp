<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<script type="text/javascript" src="<c:url value='/resources/scripts/mask.js' />"></script>
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
		<div class='navbar-header'>
			<a class='navbar-brand' href='#'>TechIT</a>
			<p class='navbar-text'>Signed in as ${sessionScope.firstname}
				${sessionScope.lastname }</p>

			<a href='Logout' type='button' id='logout-button'
				class='navbar-btn btn btn-default logout-button navbar-right'>Logout</a>
			<button class="navbar-btn btn btn-default account-button">Account Manager</button>
			<button class="navbar-btn btn btn-default setting-button">My Settings</button>
		</div>
	</nav>
			<div class="alert alert-info fade in">
 				<h2><strong>Create New Ticket!</strong></h2>
 				<h3>Please fill the missing fields below and confirm the 
 				existing ones. 
 				</h3>
			</div>	
			<div class="row" >
			<div class="col-sm-offset-2" >
				<form action="CreateAccount" method="post">
					<div class="form-group col-xs-5 col-md-5" >
					    <label for="firstName">First Name <font color="red">*</font> </label>
					    <input type="text" class="form-control" name="firstName" value="${firstname}" >
					 </div>
					 <div class="form-group col-xs-5 col-md-5" >
					    <label for="firstName">First Name <font color="red">*</font> </label>
					    <input type="text" class="form-control" name="firstName" value="${firstname}" >
					 </div>
					 
					  <div class="form-group col-xs-5 col-md-5">
					    <label for="lastName">Last Name <font color="red">*</font></label>
					    <input type="text" class="form-control" name="lastName" value="${lastname}">
					 </div>
					 <div class="form-group col-xs-5 col-md-5">
					 	<label for="email">Email <font color="red">*</font></label>
					    <input type="text" class="form-control" name="email" value="${email}">
					 </div>
					 
					 <div class="form-group col-xs-5 col-md-5">
					 	<label for="phoneNumber">Phone Number <font color="red">*</font></label>
					    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${phoneNumber}" >
					 </div>
					 <div class="form-group col-xs-5 col-md-5">
					 	<label for="location">Location <font color="red">*</font></label>
					    <input type="text" class="form-control" id="location" name="location" value = "${location}">
					 </div>
					 <div class = "form-group col-xs-5 col-md-5">
					 <label for="units">Units: <font color="red">*</font></label><br>
					 <select class="selectpicker" id= "units">
						  <option value = "1">TechOp</option>
						  <option value = "2" >IT</option>
						  <option value = "3" >ECST</option>
				     </select>
					 </div>
					 <div class="form-group col-xs-10 col-md-10">
					      <label for="description">Description: <font color ="red">*</font></label>
					      <textarea class="form-control" rows="10" id="description" name ="description" value= "${description}"></textarea>
				     </div>				 
					<div class="form-group col-xs-10 col-md-10">
				 		<b>NOTE: <font color="red">*</font> means that the field is required.</b>
				 	</div>
				 	<div class ="form-group col-xs-10 col-md-10">
				 	<a href="Home"><button type="button" class="btn btn-lg btn-primary ">Cancel</button></a>
					<button class="btn btn-lg btn-primary " name="Submit" value="Submit" type="submit">Submit</button>
					</div>
				</form>
			</div>
		</div>
	
			
	
	
</body>
</html>