<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Settings</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<jsp:include page="staticContent.jsp"></jsp:include>
</head>
<body class="index-page">
	<jsp:include page="headerLogged.jsp"></jsp:include>
	<div class="page-header header-filter" data-parallax="true"
		style="background-image: url('<c:url value="/img/bg2.jpg"/>');">
		<div class="container">
			<div class="row">
				<div class="col-md-8 ml-auto mr-auto">
					<div class="brand">
						<h1>Settings</h1>
						<h3>A Badass Bootstrap 4 UI Kit based on Material Design.</h3>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">
				<div class="title text-center">
					<h2>Basic Elements</h2>
					<form action="uploadPicture" method="post"
						enctype="multipart/form-data">
						<div class="login-form">
							<div class="control-group">
								<input type="file" name="userAvatar"> <label
									class="login-field-icon fui-user" for="login-name"></label>
							</div>
							<p>Formats allowed: png, jpg and jpeg</p>
							<p class="text-danger">
								<strong>${errorFile}</strong>
							</p>
							<p class="text-success">
								<strong>${successFile}</strong>
							</p>
							<input class="btn btn-primary btn-large btn-block" type="submit"
								value="Upload">
						</div>
					</form>
				</div>
			</div>
			<div class="container">
				<form method="POST" action="/LaundryService/changeUserData">
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputEmail4">First Name</label> <input type="text"
								class="form-control" id="inputEmail4" placeholder="First name..."
								name="firstName" value="${firstName}">
							<p class="text-danger">
								<strong>${firstNameError}</strong>
							</p>
						</div>
						<div class="form-group col-md-6">
							<label for="inputPassword4">Last Name</label> <input type="text"
								class="form-control" id="inputPassword4" placeholder="Last name..."
								name="lastName" value="${lastName}">
							<p class="text-danger">
								<strong>${lastNameError}</strong>
							</p>
						</div>
					</div>
					 <div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputAddress">Email</label> <input type="email"
							class="form-control" id="inputAddress" placeholder="Email..."
							name="email" value="${email}">
						<p class="text-danger">
							<strong>${emailError}</strong>
						</p>
					</div>
					<div class="form-group col-md-6">
						<label for="inputAddress">Telephone number</label> <input type="text"
							class="form-control" id="inputAddress" placeholder="Telephone number..."
							name="telNumber" value="${telNumber}">
						<p class="text-danger">
							<strong>${telNumberError}</strong>
						</p>
					</div>
					</div>
					<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputAddress2">Street address</label> <input type="text"
							class="form-control" id="inputAddress2" placeholder="Street address..."
							name="streetAddress" value="${streetAddress}">
						<p class="text-danger">
							<strong>${streetError}</strong>
						</p>
					</div>
					<div class="form-group col-md-6">
						<label for="inputAddress2">Bulstat</label> <input type="text"
							class="form-control" id="inputAddress2" placeholder="Bulstat..."
							name="bulstat" value="${bulstat}">
						<p class="text-danger">
							<strong>${streetError}</strong>
						</p>
					</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputCity">City</label> <input type="text"
								class="form-control" id="inputCity" name="city" value="${city}">
							<p class="text-danger">
								<strong>${cityError}</strong>
							</p>
						</div>
						<div class="form-group col-md-3">
							<label for="inputState">Date of creation</label> <input
								type="text" class="form-control" id="date" disabled
								value="${dateOfCreation}">
						</div>
						<div class="form-group col-md-2 ml-auto">
							<label for="inputZip">Zip</label> <input type="number"
								class="form-control" id="inputZip" name="zip" value="${zip}">
						</div>
					</div>
					<p class="text-danger">
						<strong>${msgError}</strong>
					</p>
					<p class="text-success">
						<strong>${msgSuccess}</strong>
					</p>
					<button type="submit" class="btn btn-primary">Submit</button>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>