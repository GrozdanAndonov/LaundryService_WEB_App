<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contacts</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<script src='https://www.google.com/recaptcha/api.js'></script>
<jsp:include page="../staticContent.jsp"></jsp:include>
<style>
@media screen and (max-height: 575px) {
	#rc-imageselect, .g-recaptcha {
		transform: scale(0.77);
		-webkit-transform: scale(0.77);
		transform-origin: 0 0;
		-webkit-transform-origin: 0 0;
	}
}
</style>
</head>
<body class="index-page">
	<jsp:include page="headerNotLogged.jsp"></jsp:include>
	<div class="page-header header-filter" data-parallax="true"
		style="background-image: url('<c:url value="/img/8.jpg"/>');">
		<div class="container">
			<div class="row">
				<div class="col-md-8 ml-auto mr-auto">
					<div class="brand">
					<br>
					<br>
						<h1><s:message code="contacts.contacts"></s:message></h1>
						<!--  <h3>A Badass Bootstrap 4 UI Kit based on Material Design.</h3> -->
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">

				<div class="section section-contacts">
					<div class="row">
						<div class="col-md-8 ml-auto mr-auto">
							<h2 class="text-center title"><s:message code="contacts.workwithus"></s:message></h2>
							<h4 class="text-center description"><s:message code="contacts.clientinfo"></s:message></h4>
								
								
							<form class="contact-form" method="POST"
									action="/LaundryService/contacts">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="bmd-label-floating"><s:message code="contacts.yourname"></s:message></label> <input
										type="text" class="form-control" name="name"
										value="${enteredName}" required>
									<p class="text-danger">
										<strong>${errorName}</strong>
									</p>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="bmd-label-floating"><s:message code="contacts.yourmail"></s:message></label>  <input
										type="email" class="form-control" name="email"
										value="${enteredEmail}" required>
									<p class="text-danger">
										<strong>${errorEmail}</strong>
									</p>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="exampleMessage" class="bmd-label-floating"><s:message code="contacts.yourmessage"></s:message></label>
									<textarea type="text" class="form-control" rows="4"
								id="exampleMessage" name="text" required>${enteredText}</textarea>
							<p class="text-danger">
								<strong>${errorText}</strong>
							</p>
								</div>
								<div class="row">
									 <div class="col-md-4 ml-auto mr-auto text-center">
										<div class="g-recaptcha"
											data-sitekey="6Lc0YlIUAAAAAJsfviOZa0O5Iywijibkj_Q_4gDW"
											style="float: center; transform: scale(0.77); -webkit-transform: scale(0.77); transform-origin: 0 0; -webkit-transform-origin: 0 0;"></div>
										<p class="text-danger">
											<strong>${recaptchaError}</strong>
										</p> 
										<button class="btn btn-primary btn-raised"><s:message code="contacts.send"></s:message></button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>





	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>