<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<jsp:include page="../staticContent.jsp"></jsp:include>
</head>
<body class="index-page">
	<jsp:include page="headerLogged.jsp"></jsp:include>
	<div class="page-header header-filter" data-parallax="true"
		style="background-image: url('<c:url value="/img/bg2.jpg"/>');">
		<div class="container">
			<div class="row">
				<div class="col-md-8 ml-auto mr-auto">
					<div class="brand">
						<h1>Create order</h1>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">
				<div class="title text-centre">
					<h2>Basic Elements</h2>
				</div>
				<c:if test="${userInfoMsg != null }">
				<div class="alert alert-warning">
				    <div class="container-fluid">
					  <div class="alert-icon">
						<i class="material-icons">warning</i>
					  </div>
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true"><i class="material-icons">clear</i></span>
					  </button>
				      <b>Warning:</b> <c:out value="${ userInfoMsg }"></c:out> <a href="/LaundryService/settings">GO HERE!</a>
				    </div>
				</div>
				</c:if>
				<form method="POST" action="/LaundryService/orderCreate/createOrder">
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputEmail4">First Name</label> 
							<input type="text" class="form-control" id="inputEmail4" placeholder="Enter first name..." name="firstName" value="${firstName}">
							<p class="text-danger">
								<strong>${firstNameError}</strong>
							</p>
						</div>
						<div class="form-group col-md-6">
							<label for="inputPassword4">Last Name</label> <input type="text" class="form-control" id="inputPassword4" placeholder="Enter last name..."
								name="lastName" value="${lastName}">
							<p class="text-danger">
								<strong>${lastNameError}</strong>
							</p>
						</div>
					</div>
					<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputAddress">Email</label> <input type="email" class="form-control" id="inputAddress" placeholder="Enter email..."
							name="email" value="${email}">
						<p class="text-danger">
							<strong>${emailError}</strong>
						</p>
					</div>
					<div class="form-group col-md-6">
						<label for="inputAddress2">Street address</label> <input type="text" class="form-control" id="inputAddress2" placeholder="Enter street address..."
							name="streetAddress" value="${streetAddress}">
						<p class="text-danger">
							<strong>${streetError}</strong>
						</p>
					</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputCity">City</label> 
							<input type="text" class="form-control" id="inputCity" name="city" value="${city}" placeholder="Enter city...">
							<p class="text-danger">
								<strong>${cityError}</strong>
							</p>
						</div>
						<div class="form-group col-md-6">
							<label for="inputZip">Telephone number</label> <input type="text"
								class="form-control" id="inputZip" name="telNumber" value="${telNumber}"  placeholder="Enter telephone number...">
								<p class="text-danger">
								<strong>${telephoneError}</strong>
								</p>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-12">
				    <label>Enter note for order</label>
				    <textarea type="text" class="form-control" id="exampleFormControlTextarea1" rows="4" name="text">${enteredText}</textarea>
				  </div>
				  </div>
					<p class="text-danger">
						<strong>${msgError}</strong>
					</p>
					<p class="text-success">
						<strong>${msgSuccess}</strong>
					</p>
					<div class="form-row">
						<div class="col-md-6">
					<button type="submit" class="btn btn-primary">Create order</button>
					</div>
					<div class="form-check">
				      <label class="form-check-label">
				          <input id="checkbox" class="form-check-input" type="checkbox" name="isExpress" value="${isExpressV}">
				          Express order
				          <span class="form-check-sign">
				              <span class="check"></span>
				          </span>
				      </label>
			  		</div>
			  		</div>
				</form>

			</div>
		</div>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
<script>
let checkbox = document.getElementById('checkbox');
let val = checkbox.value;
if(val === 'on'){
	checkbox.checked = true;
}
</script>
</html>