<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TechIT Request</title>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<h1 align=center>
				<small>TechIt!</small>
			</h1>
		</div>
		<div class="row">
			<div class="col-sm-offset-3 col-sm-6">
				<form class="form" action="Login" method="post">
					<h2 class="form-signin-heading">Request Form</h2>
					<label for="name" >Name</label> 
					<input type="name" name="name" id="name" class="form-control" /> 
					<label for="phone" >Phone #</label> 
					<input type="phone" name="phone" id="phone" class="form-control"/> 
					<label for="email" >Email</label> 
					<input type="email" name="email" id="email" class="form-control"/> 
					<div class="dropdown">
					<label for="dempartment" >Department</label> 
   					 	<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Administrative Technology
   						<span class="caret"></span></button>
    					<ul class="dropdown-menu">
     						 <li><a href="#">Administrative Technology</a></li>
     						 <li><a href="#">Math</a></li>
      						 <li><a href="#">Biology</a></li>
    					</ul>
    				
  					</div>
  					<div class="form-group">
  						<label for="request">Request</label>
  						<textarea class="form-control" rows="5" id="request"></textarea>
					</div>
					<div style="color: #FF0000;"></div>
					<div class="checkbox">
					
					</div>
					<button name="cancel"
						value="cancel" type="submit">Cancel</button>
					<button name="submit"
						value="submit" type="submit">Submit</button>
					
						
				</form>
			</div>
		</div>
	</div>

</body>
</html>