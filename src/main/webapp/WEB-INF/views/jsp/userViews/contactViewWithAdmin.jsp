<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contact with admin</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<script src='https://www.google.com/recaptcha/api.js'></script>
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
						<h1>Contact With Admin</h1>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">
				<div class="title text-center">
					<h2>Contact form:</h2>
				</div>
				<div class="section section-contacts">
					<div class="row">
						<div class="col-md-8 ml-auto mr-auto">
							<h2 class="text-center title">Work with us</h2>
							<h4 class="text-center description">Divide details about
								your product or agency work into parts. Write a few lines about
								each one and contact us about any further collaboration. We will
								responde get back to you in a couple of hours.</h4>


							<form class="contact-form" method="POST"
								action="/LaundryService/contacts/contactWithAdmin">
								<div class="form-group">
									<label for="exampleMessage" class="bmd-label-floating">Your
										Message</label>
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
										<p class="text-danger">
											<strong>${errorMsg}</strong>
										</p>
										<p class="text-success">
											<strong>${msgSuccess}</strong>
										</p>
										<button class="btn btn-primary btn-raised">Send
											Message</button>
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