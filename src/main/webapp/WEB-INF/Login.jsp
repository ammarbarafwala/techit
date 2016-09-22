<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8">
	<title>TechIT Login</title>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
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
				<form class="form-signin" action="Login" method="post">
					<h2 class="form-signin-heading">Please Sign In</h2>
					<label for="username" class="sr-only">Username</label> <input
						type="username" name="username" id="username" class="form-control"
						placeholder="Username" /> 
						
						<label for="password"
						class="sr-only">Password</label> <input type="password"
						name="password" id="password" class="form-control"
						placeholder="Password" />
						
					<div style="color: #FF0000;">${errorMessage}</div>

					
					<div class="checkbox">
						<label> <input type="checkbox" name="remember" value="yes">
								Remember Me </label>
					</div>
					<button class="btn btn-lg btn-primary btn-block" name="Login"
						value="Login" type="submit">Sign in</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
